package com.m2m.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CreateKingdomResponse extends BaseResponse {
	private static final long serialVersionUID = 1L;
	
	private long topicId;
	private long v_lv;
	private int currentLevel;
	private int upgrade ;
}
