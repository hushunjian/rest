package com.m2m.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class IndustryOptRequest extends BaseRequest {

	private static final long serialVersionUID = 9167501597810610102L;
	private long industryId;
    private int action;
}
