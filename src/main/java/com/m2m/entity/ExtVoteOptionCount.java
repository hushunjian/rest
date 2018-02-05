package com.m2m.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class ExtVoteOptionCount implements Serializable {
	private static final long serialVersionUID = 1565859886137755060L;

	private long optionId;
	private String optionName;
	private int recordCount;
}
