package com.m2m.response;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;
import com.m2m.entity.Middleman;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserDataResponse extends BaseResponse {
	private static final long serialVersionUID = -5815699098255601401L;

	private long uid;
	private String nickName;
	private String avatar;
	private String avatarFrame;
	private int gender;
	private long industryId;
	private String industry;
	private int v_lv;
	private String introduced;
	private int isFriend;
	private int isRecFriend;
	private int isRec;
	private int subscribeCount;
	private long silenceTime;
	private int silencePeriod;
	private int totalRecFriendCount;
	private List<UserSimpleInfo> recFriendData = Lists.newArrayList();
	private int ownKingdomCount;
	private List<KingdomImageElement> ownKingdomImageData = Lists.newArrayList();
	private Middleman middleman = null;
	
	@Data
	public static class KingdomImageElement implements Serializable {
		private static final long serialVersionUID = -1052292330575975011L;
		
		private String fragmentImage;
	}
}
