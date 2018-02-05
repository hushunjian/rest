package com.m2m.request;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Live4LiveCoverRequest extends BaseRequest implements Serializable {
	
	private static final long serialVersionUID = -5242082580640473318L;
	
	private long topicId;
	
	private int source;
	
	private long fromUid;
	
}