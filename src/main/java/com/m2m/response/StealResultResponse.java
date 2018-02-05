package com.m2m.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class StealResultResponse extends BaseResponse{
	private static final long serialVersionUID = 6369649571729593474L;

	private int stealedCoins;
    private int upgrade;
	private int currentLevel;
	private int isBigRedPack;
}
