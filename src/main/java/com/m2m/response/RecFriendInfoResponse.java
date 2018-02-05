package com.m2m.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class RecFriendInfoResponse extends BaseResponse {
	private static final long serialVersionUID = -3262752582935761862L;

	private long uid;
	private String nickName;
	private String avatar;
	private String avatarFrame;
	private long industryId;
	private String industry;
	private String introduced;
	private int v_lv;
	private String content;
	private int isRec;
	private int isFriend;
	private long silenceTime;
	private int silencePeriod;
}
