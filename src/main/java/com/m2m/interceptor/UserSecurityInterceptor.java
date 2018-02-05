package com.m2m.interceptor;

import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.m2m.domain.AppHttpAccess;
import com.m2m.exception.TokenNullException;
import com.m2m.exception.UidAndTokenNotMatchException;
import com.m2m.exception.UserGagException;
import com.m2m.mapper.AppHttpAccessMapper;
import com.m2m.service.RedisService;
import com.m2m.service.UserService;
import com.m2m.util.DateUtil;

/**
 * 用户认证拦截器
 */
@Component
public class UserSecurityInterceptor extends HandlerInterceptorAdapter {
	
	private static final Logger log = LoggerFactory.getLogger(UserSecurityInterceptor.class);
	
	@Autowired
    private UserService userService;
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private AppHttpAccessMapper appHttpAccessMapper;

	private ThreadLocal<Long> startTime = new ThreadLocal<Long>();
	
	//需要判断禁言的uri列表
	private static List<String> NEED_CHECK_GAG_LIST = Lists.newArrayList();
	//不需要校验uid和token的uri列表
	private static List<String> WHITE_LIST = Lists.newArrayList();
	
	static {
		//初始化相关列表
		WHITE_LIST.add("/rest/foo/error");
		WHITE_LIST.add("/rest/foo/getPages");
		WHITE_LIST.add("/rest/foo/foo");
		WHITE_LIST.add("/rest/crash/ios/append");
		WHITE_LIST.add("/rest/crash/android/append");
		WHITE_LIST.add("/rest/io/getQiniuAccessToken");
		WHITE_LIST.add("/rest/user/versionControl");
		WHITE_LIST.add("/rest/user/getGuideInfo");
		WHITE_LIST.add("/rest/content/emojiPackageQuery");
		WHITE_LIST.add("/rest/content/emojiPackageDetail");
		WHITE_LIST.add("/rest/live/addAppDownloadLog");
		WHITE_LIST.add("/rest/live/teaseListQuery");
		WHITE_LIST.add("/rest/live/getGiftInfoList");
		
		NEED_CHECK_GAG_LIST.add("/rest/live/speak");
		NEED_CHECK_GAG_LIST.add("/rest/live/createKingdom");
		NEED_CHECK_GAG_LIST.add("/rest/user/friendOpt");
	}
	
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		startTime.set(System.currentTimeMillis());
		
        //判断禁言
        if(NEED_CHECK_GAG_LIST.contains(request.getRequestURI())){
        	String uid = request.getParameter("uid");
        	if(StringUtils.isNotBlank(uid) && userService.isGagged(Long.valueOf(uid))){
        		throw new UserGagException("user is gagged!");
        	}
        }
        
        if(!WHITE_LIST.contains(request.getRequestURI())) {
            String uid = request.getParameter("uid");
            String token = request.getParameter("token");
            if (StringUtils.isBlank(uid) || StringUtils.isBlank(token)) {
                throw new TokenNullException("uid or token is null!");
            } else {
            	if(!userService.checkUserToken(Long.valueOf(uid), token)){
            		throw new UidAndTokenNotMatchException("uid and token not matches!");
            	}
            }
        }
        
        return true;
    }
	
	@Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		//获取所有请求参数
		String uid = "0";
		Map<String, String> paramMap = new HashMap<>();
		Enumeration<String> em = request.getParameterNames();
		String paramName = null;
		while (em.hasMoreElements()) {
			paramName = em.nextElement();
			if("uid".equals(paramName)){
				uid = request.getParameter(paramName);
			}
			paramMap.put(paramName, request.getParameter(paramName));
		}
		
		String httpParams = JSON.toJSONString(paramMap);
		long currentTime = System.currentTimeMillis();
		long execTime = currentTime - startTime.get();
		log.info("[{}]-[{}]-[{}], EXECUTE TIME : [{}ms]", uid, request.getRequestURI(), httpParams, execTime);
		
		long longuid = 0;
		try{
			longuid = Long.valueOf(uid);
		}catch(Exception ignore){}
		
		Date now = new Date();
		AppHttpAccess httpAccess = new AppHttpAccess();
		httpAccess.setUid(longuid);
		httpAccess.setRequestUri(request.getRequestURI());
		httpAccess.setRequestMethod(request.getMethod());
		httpAccess.setRequestParams(httpParams);
		httpAccess.setStartTime(startTime.get());
		httpAccess.setEndTime(currentTime);
		httpAccess.setCreateTime(now);
		appHttpAccessMapper.insertSelective(httpAccess);
		
		//记录用户请求时间
		if(longuid > 0){
			String timeStr = DateUtil.date2string(now, "yyyy-MM-dd HH:mm:ss");
			
			String key = "USER:LASTTIME:" + longuid;
			redisService.set(key, timeStr);
		}
	}
}
