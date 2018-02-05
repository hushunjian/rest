package com.m2m.response;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by pc339 on 2017/9/22.
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class GetLastEmotionInfoResponse extends BaseResponse  {
	private static final long serialVersionUID = 7002311410794738477L;
	private long id;
    private String emotionName;
    private int happyValue;
    private int freeValue;
    private long topicId;
    private int isSummary;
    private int recordCount;
    private long timeInterval;
    private Date createTime;
    private String dateStr;
    private EmotionPack emotionPack;
   
   public static EmotionPack createEmotionPack(){
       return new EmotionPack();
   }

   @Data
   public static class EmotionPack implements Serializable {
	private static final long serialVersionUID = 1L;
	private long id;
	private String title;
	private String content;
	private String image;
	private String thumb;
	private long w;
	private long h;
	private long thumb_w;
	private long thumb_h;
	private String extra;
	private int emojiType;
   }

}
