package com.m2m.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class GetQiniuAccessTokenResponse extends BaseResponse {
	private static final long serialVersionUID = 8149492100563774676L;

	private String token;
	private long expireTime;
}
