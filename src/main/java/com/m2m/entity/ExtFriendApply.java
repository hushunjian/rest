package com.m2m.entity;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ExtFriendApply extends ExtFriend {
	private static final long serialVersionUID = 1918367521530565525L;

	private String content;
	private int status;
	private Date applyTime;
}
