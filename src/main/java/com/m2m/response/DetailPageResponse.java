package com.m2m.response;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class DetailPageResponse extends BaseResponse {
	private static final long serialVersionUID = -705432693167785060L;

	private int totalRecords;
    private int totalPages;
    private int pageNo;
    private PageInfo pageInfo = new PageInfo();

    @Data
    public static class LiveElement implements Serializable {
		private static final long serialVersionUID = -1587643548950307074L;

		private long uid;
        private String nickName;
        private int isFollowed;
        private long fragmentId;
        private Date createTime;
        private int contentType;
        private int type;
        private String fragment;
        private String fragmentImage;
        private String avatar;
        private String avatarFrame;
        private long id;
        private int internalStatus;
        private long atUid;
        private String atNickName;
        private int source;
        private String extra;
        private int v_lv;
        private int level;
        private int status;
        private int teaseStatus;
        private int score;
        private int giftStatus;
        private int likeCount;
        private int isLike;
    }

    @Data
    public static class PageInfo implements Serializable {
		private static final long serialVersionUID = -2985602947599366977L;
		
		private int start;
    	private int end;
    	private List<PageDetail> detail = Lists.newArrayList();
    }
    
    @Data
    public static class PageDetail implements Serializable {
		private static final long serialVersionUID = 2962104434827607050L;
		
		private int page;
    	private int records;
    	private int isFull;
    	private long updateTime;
    	private  List<LiveElement> liveElements = Lists.newArrayList();
    }
}
