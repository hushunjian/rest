package com.m2m.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class ExtTopicReviewCount implements Serializable {

	private static final long serialVersionUID = 5263158714904508306L;

	private Long topicId;

    private Long topicCount;
    
    private Long reviewCount;

}
