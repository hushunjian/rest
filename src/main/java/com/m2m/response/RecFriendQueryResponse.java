package com.m2m.response;

import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class RecFriendQueryResponse extends BaseResponse {
	private static final long serialVersionUID = -9101168871347794810L;

	private long uid;
	private String nickName;
	private String avatar;
	private String avatarFrame;
	private int v_lv;
	private int recFriendCount;
	private List<RecFriendElement> recFriendData = Lists.newArrayList();
	
	@Data
	@EqualsAndHashCode(callSuper = false)
	public static class RecFriendElement extends UserSimpleInfo {
		private static final long serialVersionUID = 5899219719641650683L;
		
		private long industryId;
		private String industry;
		private String content;
		private int isFriend;
	}
}
