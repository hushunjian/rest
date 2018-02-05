package com.m2m.response;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class BangDanData extends BaseResponse {
	private static final long serialVersionUID = -8233497729843309662L;

	private Long sinceId;//榜单列表用
	private long listId;
	private String title;
	private int type;
	private String summary;
	private String bgColor;
	private String coverImage;
	private int coverWidth;
	private int coverHeight;
	private int isShowName;
	private int subType;
	private List<BangDanInnerData> subList = Lists.newArrayList();
	
	@Data
	public static class BangDanInnerData implements Serializable {
		private static final long serialVersionUID = 3749833899283245938L;

		private Long sinceId;//榜单详情用
		private Long subListId;//榜单列表用
		private int subType;
		private long uid;
		private String avatar;
		private String nickName;
		private int v_lv;
		private int level;
		private String avatarFrame;
		private int isFollowed;
		private int isFollowMe;
		private int contentType;
		private int favorite;
		private long id;
		private long cid;
		private long topicId;
		private long forwardCid;
		private String title;
		private String coverImage;
		private int internalStatus;
		private int favoriteCount;
		private int readCount;
		private int likeCount;
		private int reviewCount;
		private String introduced;
		private String tags;
		private int price;
		private double priceRMB;
		private int showRMBBrand;
		private int showPriceBrand;
		private String kcName;
	}
}
