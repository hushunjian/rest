package com.m2m.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class FriendQueryRequest extends BaseRequest {
	private static final long serialVersionUID = 2704173829985289352L;

	private String keyword;
	private int page;
	private long topicId;
}
