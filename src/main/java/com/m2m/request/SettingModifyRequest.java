package com.m2m.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SettingModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;
	private int action;
    private String params;
    private long topicId;
    private long uid;
}
