package com.m2m.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CheckNameOpenIdRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;
	private String nickName;
	private String openId;
	private String unionId;
	private int thirdPartType;
}
