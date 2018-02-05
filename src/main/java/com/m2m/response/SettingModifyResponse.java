package com.m2m.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SettingModifyResponse extends BaseResponse {
	private static final long serialVersionUID = 1L;
	private String coverImage;
}
