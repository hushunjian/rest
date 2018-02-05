package com.m2m.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserDataRequest extends BaseRequest {
	private static final long serialVersionUID = -7304118026241434887L;

	private long customerId;
	private long fromUid;
}
