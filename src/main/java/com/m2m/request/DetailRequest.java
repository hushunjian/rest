package com.m2m.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class DetailRequest extends BaseRequest {
	private static final long serialVersionUID = -3291465974142062475L;

	private long topicId;
	private int pageNo;
	private int offset;
	private long sinceId;
	private int direction;
	private int reqType;
}
