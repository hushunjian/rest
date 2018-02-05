package com.m2m.response;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class EmotionHisListResponse extends BaseResponse {
	private static final long serialVersionUID = 6615432023723836424L;

	private int totalCount;
	private long topicId;
	private List<EmotionElement> emotionList = Lists.newArrayList();
	
	@Data
	public static class EmotionElement implements Serializable {
		private static final long serialVersionUID = -4393184248463389540L;
		
		private long id;
		private long rid;
		private String emotionName;
		private int happyValue;
		private int freeValue;
		private long createTime;
		private int timeInterval;
		private EmotionPackElement emotionPack;
	}
	
	@Data
	public static class EmotionPackElement implements Serializable {
		private static final long serialVersionUID = 6490703145074983799L;
		
		private long id;
		private String title;
		private String content;
		private String image;
		private String thumb;
		private int w;
		private int h;
		private int thumb_w;
		private int thumb_h;
		private String extra;
		private int emojiType = 1;
		
	}
}
