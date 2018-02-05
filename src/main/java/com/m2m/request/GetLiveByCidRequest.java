package com.m2m.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class GetLiveByCidRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;
	private long cid;
}