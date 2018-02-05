package com.m2m.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class GetQiniuAccessTokenRequest extends BaseRequest {
	private static final long serialVersionUID = -1804942553679180020L;

	private int bucket;
}
