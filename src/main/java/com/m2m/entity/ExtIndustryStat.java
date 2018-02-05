package com.m2m.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class ExtIndustryStat implements Serializable {
	private static final long serialVersionUID = -9039022527876454524L;

	private long industryId;
	private String industry;
	private int friendCount;
}
