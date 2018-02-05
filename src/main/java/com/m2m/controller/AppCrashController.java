package com.m2m.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.m2m.request.CrashRequest;
import com.m2m.service.AppCrashService;

@RestController
@Transactional
@RequestMapping("/crash")
public class AppCrashController extends BaseController {
	
	@Autowired
	private AppCrashService appCrashService;
	
	@ResponseBody
    @RequestMapping(value = "ios/append")
	public void setIosCrash(CrashRequest request){
		appCrashService.setIosCrash(request);
	}
	
	@ResponseBody
    @RequestMapping(value = "android/append")
	public void setaAndroidCrash(CrashRequest request){
		appCrashService.setaAndroidCrash(request);
	}
	
}
