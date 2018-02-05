package com.m2m.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class FriendMiddlemanQueryRequest extends BaseRequest {
	private static final long serialVersionUID = -6425688955732719612L;

	private long customerId;
	private long fromUid;
}
