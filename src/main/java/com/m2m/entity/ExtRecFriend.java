package com.m2m.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ExtRecFriend extends ExtFriend {
	private static final long serialVersionUID = 6721743287144906985L;

	private String content;
}
