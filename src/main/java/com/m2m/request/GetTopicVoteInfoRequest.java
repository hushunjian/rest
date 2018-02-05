package com.m2m.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class GetTopicVoteInfoRequest extends BaseRequest {
	private static final long serialVersionUID = 844750127119262470L;

	private long voteId;
}
