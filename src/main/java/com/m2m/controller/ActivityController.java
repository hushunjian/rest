package com.m2m.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.m2m.request.GetLightBoxInfoRequest;
import com.m2m.response.GetLightBoxInfoResponse;
import com.m2m.service.ActivityService;
import com.m2m.web.Response;

@RestController
@Transactional
@RequestMapping("/activity")
public class ActivityController extends BaseController {
	
	@Autowired
	private ActivityService activityService;
	
	/**
	 * 灯箱内容获取接口
	 * @param request
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "getlightboxInfo")
	public Response<GetLightBoxInfoResponse> getlightboxInfo(GetLightBoxInfoRequest request){
		return activityService.getLightBoxInfo(request);
	}
}
