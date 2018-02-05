package com.m2m.response;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class FriendApplyQueryResponse extends BaseResponse {
	private static final long serialVersionUID = -809447397224055418L;

	private List<ApplyInfoElement> applyData = Lists.newArrayList();
	
	@Data
	public static class ApplyInfoElement implements Serializable {
		private static final long serialVersionUID = -8702429923542097835L;
		
		private long uid;
		private String nickName;
		private String avatar;
		private String avatarFrame;
		private int v_lv;
		private long industryId;
		private String industry;
		private String content;
		private int status;
		private Date applyTime;
	}
}
