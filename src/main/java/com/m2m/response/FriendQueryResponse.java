package com.m2m.response;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class FriendQueryResponse extends BaseResponse {
	private static final long serialVersionUID = 8335346514396520269L;
	
	private List<FriendElement> friendData = Lists.newArrayList();
	
	@Data
	public static class FriendElement implements Serializable {
		private static final long serialVersionUID = -2454896081015720457L;
		
		private long uid;
		private String nickName;
		private String avatar;
		private String avatarFrame;
		private int v_lv;
		private long industryId;
		private String industry;
		private String introduced;
		private int internalStatus;
	}
}
