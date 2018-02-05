package com.m2m.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class FriendOptRequest extends BaseRequest {
	private static final long serialVersionUID = -2223963218309657309L;

	private long targetUid;
	private String content;
	private int action;
	private long fromUid;
}
