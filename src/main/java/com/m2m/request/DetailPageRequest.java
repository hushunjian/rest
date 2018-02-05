package com.m2m.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class DetailPageRequest extends BaseRequest {
	private static final long serialVersionUID = 50349762832176329L;

	private int offset;
    private int pageNo;
    private long topicId;
    private long sinceId;
    private int direction;
    private int reqType;
    
    private int currentCount = 0;
}
