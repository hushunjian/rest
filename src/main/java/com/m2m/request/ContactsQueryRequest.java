package com.m2m.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ContactsQueryRequest extends BaseRequest {
	private static final long serialVersionUID = -3027093079414030455L;

	private long industryId;
	private int page;
}
