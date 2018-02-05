package com.m2m.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class GetIMUsertokenResponse extends BaseResponse {
	private static final long serialVersionUID = -9096062454002339092L;

	private String userId;
	private String token;
}
