package com.m2m.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class RecFriendQueryRequest extends BaseRequest {
	private static final long serialVersionUID = 6204933930616166129L;

	private long customerId;
	private long targetUid;
}
