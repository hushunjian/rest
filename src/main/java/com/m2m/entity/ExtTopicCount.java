package com.m2m.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class ExtTopicCount implements Serializable {
	private static final long serialVersionUID = 2713147982569759393L;

	private long topicId;
	private long totalCount;
	private long updateCount;
	private long reviewCount;
	
	
}
