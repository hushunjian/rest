package com.m2m.response;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by pc339 on 2017/9/22.
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class GetLotteryResponse extends BaseResponse  {
	private static final long serialVersionUID = 7002311410794738477L;
	private long uid;
	private String avatar;
	private String avatarFrame;
    private String nickName;
    private int v_lv;
    private int level;
    private long createTime;
    private String title;
    private String summary;
    private int status;
    private int signUp;
    private int isWin;
    private long timeInterval;
    private long endTime;
    private int winNumber;
    private int joinNumber;
    private long topicId;
    
    private List<UserElement> winUsers = Lists.newArrayList();
    @Data
    public static class UserElement implements Serializable{
		private static final long serialVersionUID = 986248317266716695L;
		private long uid;
		private String avatar;
		private String avatarFrame;
		private String nickName;
		private int v_lv;
		private int level;
    }
}
