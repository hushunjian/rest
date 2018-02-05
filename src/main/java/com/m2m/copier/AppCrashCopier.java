package com.m2m.copier;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.m2m.domain.AndroidCrash;
import com.m2m.domain.IosCrash;
import com.m2m.request.CrashRequest;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AppCrashCopier {
	AppCrashCopier INSTANCE = Mappers.getMapper(AppCrashCopier.class);
	
	IosCrash asIosCrash(CrashRequest bean);
	
	AndroidCrash asAndroidCrash(CrashRequest bean);
}
