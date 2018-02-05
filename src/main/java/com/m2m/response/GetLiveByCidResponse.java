package com.m2m.response;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;
import com.m2m.entity.Middleman;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class GetLiveByCidResponse extends BaseResponse {
	private static final long serialVersionUID = 1L;
	private String avatar;
    private String avatarFrame;
    private String coverImage;
    private Date createTime;
    private String nickName;
    private int status;
    private String title;
    private int favorite;
    private long uid;
    private long topicId;
    private int favoriteCount;
    private int personCount;
    private int reviewCount;
    private int readCount;
    private int isLike;
    private int likeCount;
    private long cid;
    private long updateTime;
    private int isFollowed;
    private int isFollowMe;
    private int v_lv;
    private int level;
    private String tags;
    private String recTags;
    //0圈外 1圈内 2核心圈
    private int internalStatus;
    private int contentType;//王国类型，0个人王国， 1聚合王国
    private int acCount;
    private List<TopicElement> acTopList = Lists.newArrayList();
    private int ceCount;
    
    private int isRec;//是否推荐到banner 0否 1是
    
    private int topicPrice; //王国价值
    
    private double topicRMB; //米汤币换算人民币，单位元
    
    private int topicPriceChanged;//王国价值增量，大于0增值 小于0贬值
    
    private int beatTopicPercentage;//击败王国百分比，整型，贬值默认0
    
    private int isSteal;//偷取状态 0 不可偷取 1可偷取 2已偷过
    
    private String summary;//王国简介
    
    private int isFirstView =0;	//1 第一次访问，0 正常状态。
    
    private int isLottery;//是否有抽奖  0 否  1是
    
    private int kcid;
    private String kcImage;
    private String kcIcon;
    private String kcName;
    private int rights;
    private int canView;
    private int isFriend;
    private int onlyFriend;
    private Middleman middleman;
    
    private int isForbid;//是否被禁言  0 否 1 是
    
    private int isAllForbid;//是否全禁言 0 否 1 是
    
    private int autoCoreType;//判断是否加入及自动加入核心圈
    
    private List<AcImageElement> acImageList = Lists.newArrayList();
    
    @Data
    public static class TopicElement implements Serializable {
		private static final long serialVersionUID = 986248317266706695L;
		private long topicId;
		private String title;
		private String coverImage;
		private int internalStatus;
    }
    
    @Data
    public static class AcImageElement implements Serializable {
		private static final long serialVersionUID = 639820189834699033L;
		private int type;
		private String imageUrl;
		private String extra;
    }
}
