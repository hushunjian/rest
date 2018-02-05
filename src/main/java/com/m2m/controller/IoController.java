package com.m2m.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.m2m.request.GetQiniuAccessTokenRequest;
import com.m2m.response.GetQiniuAccessTokenResponse;
import com.m2m.service.IoService;
import com.m2m.web.Response;

@RestController
@Transactional
@RequestMapping("/io")
public class IoController extends BaseController {
	
	@Autowired
	private IoService ioService;

	/**
	 * 获取七牛token
	 * @param request
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "getQiniuAccessToken")
	public Response<GetQiniuAccessTokenResponse> getQiniuAccessToken(GetQiniuAccessTokenRequest request){
		return ioService.getQiniuAccessToken(request);
	}
}
