package com.m2m.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.m2m.exception.UnKnownHostException;
import com.m2m.request.BasicDataRequest;
import com.m2m.request.CheckNameOpenIdRequest;
import com.m2m.request.ContactsQueryRequest;
import com.m2m.request.EmotionHisListRequest;
import com.m2m.request.FriendApplyQueryRequest;
import com.m2m.request.FriendMiddlemanQueryRequest;
import com.m2m.request.FriendOptRequest;
import com.m2m.request.FriendQueryRequest;
import com.m2m.request.GetIMUsertokenRequest;
import com.m2m.request.GetLastEmotionInfoRequest;
import com.m2m.request.GetLevelListRequest;
import com.m2m.request.GetMyLevelRequest;
import com.m2m.request.GetUserProfileRequest;
import com.m2m.request.GetUserRequest;
import com.m2m.request.GuideInfoRequest;
import com.m2m.request.RecFriendInfoRequest;
import com.m2m.request.RecFriendQueryRequest;
import com.m2m.request.UserDataRequest;
import com.m2m.request.VersionControlRequest;
import com.m2m.response.BaseResponse;
import com.m2m.response.BasicDataSuccessResponse;
import com.m2m.response.CheckNameOpenIdResponse;
import com.m2m.response.ContactsQueryResponse;
import com.m2m.response.EmotionHisListResponse;
import com.m2m.response.FriendApplyQueryResponse;
import com.m2m.response.FriendMiddlemanQueryResponse;
import com.m2m.response.FriendQueryResponse;
import com.m2m.response.GetIMUsertokenResponse;
import com.m2m.response.GetLastEmotionInfoResponse;
import com.m2m.response.GetLevelListResponse;
import com.m2m.response.GetUserProfileResponse;
import com.m2m.response.GetUserResponse;
import com.m2m.response.GuideInfoResponse;
import com.m2m.response.MyLevelResponse;
import com.m2m.response.RecFriendInfoResponse;
import com.m2m.response.RecFriendQueryResponse;
import com.m2m.response.UserDataResponse;
import com.m2m.response.VersionControlResponse;
import com.m2m.service.UserService;
import com.m2m.web.Response;

@RestController
@Transactional
@RequestMapping("/user")
public class UserController extends BaseController {

	@Autowired
	private UserService userService;
	
	/**
	 * 情绪列表接口
	 * @param request
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "emotionHisList")
	public Response<EmotionHisListResponse> emotionHisList(EmotionHisListRequest request){
		return userService.emotionHisList(request.getUid());
	}
	
	/**
	 * 好友查询接口
	 * @param request
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "friendQuery")
	public Response<FriendQueryResponse> friendQuery(FriendQueryRequest request){
		if(request.getPage() <= 0){
			request.setPage(1);
		}
		return userService.friendQuery(request);
	}
	
	/**
	 * 获取用户资料信息接口
	 * @param request
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "getUserProfile")
	public Response<GetUserProfileResponse> getUserProfile(GetUserProfileRequest request){
		return userService.getUserProfile(request);
	}
	
	/**
	 * 获取用户基本信息接口
	 * @param request
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "getUser")
	public Response<GetUserResponse> getUser(GetUserRequest request){
		return userService.getUser(request);
	}
	
	/**
	 * 行业联系人查询接口
	 * @param request
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "contactsQuery")
	public Response<ContactsQueryResponse> contactsQuery(ContactsQueryRequest request){
		if(request.getPage() <= 0){
			request.setPage(1);
		}
		return userService.contactsQuery(request);
	}
	
	/**
	 * 好友操作接口
	 * @param request
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "friendOpt")
	public Response<BaseResponse> friendOpt(FriendOptRequest request){
		return userService.friendOpt(request);
	}
	
	/**
	 * 好友申请列表接口
	 * @param request
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "friendApplyQuery")
	public Response<FriendApplyQueryResponse> friendApplyQuery(FriendApplyQueryRequest request){
		if(request.getPage() <= 0){
			request.setPage(1);
		}
		return userService.friendApplyQuery(request);
	}
	
	/**
	 * 新用户资料信息
	 * @param request
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "userData")
	public Response<UserDataResponse> userData(UserDataRequest request){
		return userService.userData(request);
	}
	
	/**
	 * 好友推荐列表查询接口
	 * @param request
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "recFriendQuery")
	public Response<RecFriendQueryResponse> recFriendQuery(RecFriendQueryRequest request){
		return userService.recFriendQuery(request);
	}
	
	/**
	 * 好友中间人查询接口
	 * @param request
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "friendMiddlemanQuery")
	public Response<FriendMiddlemanQueryResponse> friendMiddlemanQuery(FriendMiddlemanQueryRequest request){
		return userService.friendMiddlemanQuery(request);
	}
	
	/**
	 * 好友推荐信息查询
	 * @param request
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "recFriendInfo")
	public Response<RecFriendInfoResponse> recFriendInfo(RecFriendInfoRequest request){
		return userService.recFriendInfo(request);
	}
	
	/**
	 * 私信获取token接口
	 * @param request
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "getIMUsertoken")
	public Response<GetIMUsertokenResponse> getIMUsertoken(GetIMUsertokenRequest request){
		return userService.getIMUsertoken(request);
	}
	
	/**
	 * 获取导游信息接口
	 * @param request
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "getGuideInfo")
	public Response<GuideInfoResponse> getGuideInfo(GuideInfoRequest request){
		return userService.getGuideInfo();
	}
	
	/**
	 * 前台获取版本信息
	 * @param request
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "versionControl")
	public Response<VersionControlResponse> versionControl(VersionControlRequest request,HttpServletRequest rq){
		//获取ipaddress信息
        String ip = rq.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
            ip = rq.getHeader("Proxy-Client-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
            ip = rq.getHeader("WL-Proxy-Client-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
            ip = rq.getHeader("HTTP_CLIENT_IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
            ip = rq.getHeader("HTTP_X_FORWARDED_FOR");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        	ip = rq.getHeader("X-Real-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
            ip = rq.getRemoteAddr();
        if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip))
            try {
                ip = InetAddress.getLocalHost().getHostAddress();
            }
            catch (UnknownHostException unknownhostexception) {
            	throw new UnKnownHostException("未知主机异常");
            }
		
		return userService.versionControl(request,ip);
	}
	
	/**
	 * 获取用户等级接口
	 * @param request
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "getMyLevel")
	public Response<MyLevelResponse> getMyLevel(GetMyLevelRequest request){
		return userService.getMyLevel(request.getUid());
	}
	
	/**
	 * 获取用户基础数据
	 * @param request
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "getBasicDataByType")
	public Response<BasicDataSuccessResponse> getBasicDataByType(BasicDataRequest request){
		return userService.getBasicDataByType(request);
	}
	
	/**
	 *  检查用户名是否存在接口，判断OPENID是否存在是否还需要上传头像接口
	 * @param request
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "checkNameOpenId")
	public Response<CheckNameOpenIdResponse> checkNameOpenId(CheckNameOpenIdRequest request){
		return userService.checkNameOpenId(request);
	}
	
	/**
	 *  全量获取所有等级的基本信息接口
	 * @param request
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "getLevelList")
	public Response<GetLevelListResponse> getLevelList(GetLevelListRequest request){
		return userService.getLevelList(request);
	}
	
	/**
	 *  最近一次情绪信息查询接口
	 * @param request
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "getLastEmotionInfo")
	public Response<GetLastEmotionInfoResponse> getLastEmotionInfo(GetLastEmotionInfoRequest request){
		return userService.getLastEmotionInfo(request);
	}
}
