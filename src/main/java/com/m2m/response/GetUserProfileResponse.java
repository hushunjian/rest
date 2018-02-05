package com.m2m.response;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class GetUserProfileResponse extends BaseResponse {
	private static final long serialVersionUID = -1372897549730087527L;

	private long uid;
	private String nickName;
	private int gender;
	private String avatar;
	private String avatarFrame;
	private String birthday;
	private String meNumber;
	private int followedCount;
	private int fansCount;
	private String userName;
	private String token;
	private String introduced;
	private int isPromoter;
	private int power;
	private int ugcCount;
	private int liveCount;
	private String thirdPartBind;
	private int v_lv;
	private int acCount;
	private int level;
	private int availableCoin;
	private double priceRMB;
	private String levelIcon;
	private List<HobbyElement> hobbyList = Lists.newArrayList();
	private int hasPwd;
	private int hasInfoCoin;
	private long industryId;
	private String industry;
	
	@Data
	public static class HobbyElement implements Serializable {
		private static final long serialVersionUID = -1296443599045626135L;
		
		private long hobby;
		private String value;
	}
}
