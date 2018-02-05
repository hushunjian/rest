package com.m2m.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class MyTopicRequest extends BaseRequest {
	private static final long serialVersionUID = 935412317730454202L;

	private long updateTime;
}
