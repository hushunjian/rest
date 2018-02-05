package com.m2m.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class ExtJoinLotteryUser implements Serializable {
	private static final long serialVersionUID = 8876202841284611696L;

	private long sinceId;
	private long uid;
	private String avatar;
	private String avatarFrame;
	private String nickName;
	private int v_lv;
	private int level;
	private String content;
	private int prohibit;
	private Date createTime;
}
