package com.m2m.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class RecFriendInfoRequest extends BaseRequest {
	private static final long serialVersionUID = -2098453688861321229L;
	
	private long customerId;
	private long fromUid;
}
