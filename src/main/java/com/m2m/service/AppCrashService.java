package com.m2m.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.m2m.copier.AppCrashCopier;
import com.m2m.domain.AndroidCrash;
import com.m2m.domain.IosCrash;
import com.m2m.mapper.AndroidCrashMapper;
import com.m2m.mapper.IosCrashMapper;
import com.m2m.request.CrashRequest;

@Service
public class AppCrashService {
	
	@Autowired
	private IosCrashMapper iosCrashMapper;
	
	@Autowired
	private AndroidCrashMapper androidCrashMapper;

	public void setIosCrash(CrashRequest request) {
		IosCrash iosCrash = AppCrashCopier.INSTANCE.asIosCrash(request);
		iosCrashMapper.insertSelective(iosCrash);
	}

	public void setaAndroidCrash(CrashRequest request) {
		AndroidCrash androidCrash = AppCrashCopier.INSTANCE.asAndroidCrash(request);
		androidCrashMapper.insertSelective(androidCrash);
	}

}
