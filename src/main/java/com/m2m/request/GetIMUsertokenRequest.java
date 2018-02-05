package com.m2m.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class GetIMUsertokenRequest extends BaseRequest {
	private static final long serialVersionUID = -8342045483217244039L;

	private long customerId;
}
