package com.m2m.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ShowListDetailRequest extends BaseRequest {
	private static final long serialVersionUID = -2948273102797512725L;

	private long sinceId;
    private long listId;
}
