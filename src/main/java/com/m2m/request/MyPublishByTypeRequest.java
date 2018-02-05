package com.m2m.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class MyPublishByTypeRequest extends BaseRequest {
	private static final long serialVersionUID = 3543187660411472258L;

    private int sinceId;

    private long customerId;

    private int type;

    private long updateTime;

    private int newType;
}
