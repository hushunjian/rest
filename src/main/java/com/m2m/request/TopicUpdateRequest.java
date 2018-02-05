package com.m2m.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class TopicUpdateRequest extends BaseRequest {
	private static final long serialVersionUID = -1915094862392602731L;

	private long sinceId;
	private int offset;
	private long topicId;
}
