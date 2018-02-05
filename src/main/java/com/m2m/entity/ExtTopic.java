package com.m2m.entity;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.m2m.domain.Topic;

@Data
@EqualsAndHashCode(callSuper=false)
public class ExtTopic extends Topic implements Serializable  {
	private static final long serialVersionUID = 2592984333518901338L;

	private int isTop;
	private long longtimes;
}
