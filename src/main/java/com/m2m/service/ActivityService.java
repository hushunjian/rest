package com.m2m.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.m2m.dao.ActivityDao;
import com.m2m.domain.ActivityWithBLOBs;
import com.m2m.domain.AppLightboxSource;
import com.m2m.request.GetLightBoxInfoRequest;
import com.m2m.response.GetLightBoxInfoResponse;
import com.m2m.web.Response;
import com.m2m.web.ResponseStatus;

@Service
public class ActivityService {
	
	private static final Logger log = LoggerFactory.getLogger(ActivityService.class);
	
	@Autowired
	private ActivityDao activityDao;
	
	@Autowired
	private BaseService baseService;
	
	
	
    public boolean isTopicRec(long topicId){
    	ActivityWithBLOBs banner = activityDao.getActivityByCid(topicId ,2);
    	if(null != banner){
    		return true;
    	}
    	
    	return false;
    }

	public Response<GetLightBoxInfoResponse> getLightBoxInfo(GetLightBoxInfoRequest request) {
		Date nowDate = new Date();
		GetLightBoxInfoResponse getLightBoxInfoResponse = new GetLightBoxInfoResponse();
        AppLightboxSource appLightboxSource = activityDao.getAppLightboxSource(nowDate);
        if (appLightboxSource != null) {
            BeanUtils.copyProperties(appLightboxSource, getLightBoxInfoResponse);
            getLightBoxInfoResponse.setImage(baseService.genQiNiuImageUrl(appLightboxSource.getImage()));
            log.info("get getlightboxInfo success");
            return Response.success(getLightBoxInfoResponse);
        }
        return Response.success(ResponseStatus.SEARCH_LIGHTBOX_NOT_EXISTS.status, ResponseStatus.SEARCH_LIGHTBOX_NOT_EXISTS.message);
	}
}
