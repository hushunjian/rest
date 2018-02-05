package com.m2m.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SpeakResponse extends BaseResponse {
	private static final long serialVersionUID = -7082675624418362189L;

	private long fragmentId;
    private int score;
    private int upgrade ;
    private int currentLevel;
    private int isFirstUpdate;
}
