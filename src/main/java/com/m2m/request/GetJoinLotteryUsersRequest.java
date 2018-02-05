package com.m2m.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class GetJoinLotteryUsersRequest extends BaseRequest {
	private static final long serialVersionUID = -3310663380683173635L;

	private long sinceId;
	private long lotteryId;
}
