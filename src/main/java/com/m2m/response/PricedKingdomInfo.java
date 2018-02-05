package com.m2m.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PricedKingdomInfo extends BaseResponse {
	private static final long serialVersionUID = 1451596533436314103L;

	private long subListId;
	private int subType;
	private long uid;
	private String avatar;
	private String nickName;
	private int v_lv;
	private int level;
	private String avatarFrame;
	private int contentType;
	private long id;
	private long cid;
	private long topicId;
	private long forwardCid;
	private String title;
	private String coverImage;
	private int internalStatus;
	private int price;
	private double priceRMB;
	private int showRMBBrand;
	private int showPriceBrand;
	private int type;
}
