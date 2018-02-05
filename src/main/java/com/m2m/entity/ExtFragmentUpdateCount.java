package com.m2m.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class ExtFragmentUpdateCount implements Serializable {
	private static final long serialVersionUID = 7081943794514623419L;

	private long totalRecords;
	private long updateRecords;
	private long lastFragmentId;
	private long firstDelCount;
}
