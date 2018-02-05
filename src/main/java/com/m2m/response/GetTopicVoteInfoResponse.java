package com.m2m.response;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class GetTopicVoteInfoResponse extends BaseResponse {
	private static final long serialVersionUID = 6871055582136298038L;

	private long voteId;
	private String title;
	private int type;
	private int status;
	private List<OptionElement> options = Lists.newArrayList();
	
	@Data
	public static class OptionElement implements Serializable {
		private static final long serialVersionUID = -99729271389789644L;
		
		private long id;
		private String option;
		private int count;
	}
}
