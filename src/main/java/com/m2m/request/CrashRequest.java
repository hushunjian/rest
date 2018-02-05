package com.m2m.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CrashRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;
	
	private String appVersion;
	private String appBuildVersion;
	private String deviceOs;
	private String deviceType;
	private String platform;
	private Long createdAt;
	private String crash;
}
