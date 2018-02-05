package com.m2m.response;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class LiveCoverResponse extends BaseResponse{
	private static final long serialVersionUID = 6369649571729593474L;

	private String title;

    private Date createTime;

    private long lastUpdateTime;

    private String coverImage;

    private long uid ;

    private String avatar;
    
    private String avatarFrame;

    private String nickName;

    private int topicCount;

    private int reviewCount;

    // 阅读数（暂时未添加）
    private int readCount;

    // 成员数（暂时未添加）
    private int membersCount;

    private int internalStatus;

    //直播二维码
    private String liveWebUrl;

    private int v_lv;

    private int level;

    private int hasFavorite;
    private int favorite;
    
    //王国类型，0个人王国，1000聚合王国
    private int type;
    
    //子王国数
    private int acCount;
    //子王国top列表
    private List<TopicElement> acTopList = Lists.newArrayList();
    
    //被聚合次数，也即被聚合的聚合王国数
    private int ceCount;

    //聚合王国属性，能下发内容的次数
    private int publishLimit;

    //是否推荐到banner 0否 1是
    private int isRec;

    private String tags;

    private String recTags;

    //王国价值
    private int topicPrice;

    //米汤币换算人民币，单位元
    private double topicRMB;

    //王国价值增量，大于0增值 小于0贬值
    private int topicPriceChanged;

    //击败王国百分比，整型，贬值默认0
    private int beatTopicPercentage;

    //偷取状态 0 不可偷取 1可偷取 2已偷过
    private int isSteal;
    
    //足迹相关属性
    private String trackContent;
    private String trackImage;
    
    private String summary;//王国简介
    
    private int kcid;
    private String kcImage;
    private String kcIcon;
    private String kcName;
    
    private int rights;
    
    private int canView;
    
    private int onlyFriend;
    
    private int isFriend;

    //为播放礼物列表
	private List<GiftElement> giftList = Lists.newArrayList();
    
    //跑马灯信息列表
    private List<TopicNewsElement> newsTopList = Lists.newArrayList();

    @Data
    public static class TopicElement implements Serializable{
		private static final long serialVersionUID = 1465887396904072679L;
    	
		private long topicId;
		private String title;
		private String coverImage;
		private int internalStatus;
    }
    
    @Data
    public static class TopicNewsElement implements Serializable{
		private static final long serialVersionUID = 1465887396905072679L;
    	
		private long id;
		private long topicId;
		private String content;
		private int type;
		private int internalStatus;
		private int contentType;
    }

	@Data
	public static class GiftElement implements Serializable {
		private static final long serialVersionUID = 986248317266716695L;

		private long giftId;
		private int count;
	}
}
