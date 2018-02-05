package com.m2m.response;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class MyTopicResponse extends BaseResponse {
	private static final long serialVersionUID = -2219724352386175871L;

    private int isUpdate;
    private List<GivenKingdom> givenKingdoms = Lists.newArrayList();
    private List<ShowTopicElement> showTopicElements = Lists.newArrayList();

    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class GivenKingdom extends ShowTopicElement implements Serializable{
		private static final long serialVersionUID = 1L;
		private long givenKingdomId;
    	private String summary;
    	private String tags;
    }
    
    @Data
    public static class ShowTopicElement implements Serializable{
		private static final long serialVersionUID = -1828917034797716578L;

		private long cid;
        private long topicId;
        private String title;
        private String coverImage;
        private long uid;
        private String avatar;
        private String avatarFrame;
        private Date createTime;
        private int lastContentType;
        private String lastFragment;
        private String lastFragmentImage;
        private String nickName;
        private int status;
        private int reviewCount;
        private int likeCount;
        private int isLike;
        private int personCount;
        private int favorite;
        private int favoriteCount;
        private long updateTime;
        private int isFollowed;
        private int isFollowMe;
        private long lastUpdateTime;
        private int topicCount;
        private int isUpdate;
        private int readCount;
        private int v_lv;
        private int contentType;
        private int acCount;
        private int lastStatus;
        private String lastExtra;
        private int isTop;
        private int lastType;
        private long lastAtUid;
        private int internalStatus;//0圈外 1圈内 2核心圈
        private long lastUid;
        private String lastNickName;
        private String lastAvatar;
        private String lastAvatarFrame;
        private int lastV_lv;
        private int price;
        private int level;
        private int lastLevel;
        private String kcName;
    }
}
