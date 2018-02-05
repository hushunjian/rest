package com.m2m.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class DetailPageStatusRequest extends BaseRequest {
	private static final long serialVersionUID = -9086127812205118127L;

	private long topicId;
	private int pageNo;
	private int offset;
}
