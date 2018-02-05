package com.m2m.response;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class GetJoinLotteryUsersResponse extends BaseResponse {
	private static final long serialVersionUID = 362351956839355171L;

	private List<JoinUserElement> joinUsers = Lists.newArrayList();
	
	@Data
	public static class JoinUserElement implements Serializable {
		private static final long serialVersionUID = -4816916399322342515L;
		
		private long sinceId;
		private long uid;
		private String avatar;
		private String avatarFrame;
		private String nickName;
		private int v_lv;
		private int level;
		private String content;
		private int prohibit;
		private long createTime;
	}
}
