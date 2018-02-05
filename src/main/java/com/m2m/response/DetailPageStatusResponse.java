package com.m2m.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class DetailPageStatusResponse extends BaseResponse {
	private static final long serialVersionUID = -5174717005246010949L;

	private int page;
	private int records;
	private int isFull;
	private long updateTime;
}
