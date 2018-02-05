package com.m2m.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class ExtAcImage implements Serializable {
	private static final long serialVersionUID = -3781646774586964719L;

	private long id;
	private long fid;
	private String fragmentImage;
	private String fragment;
	private int type;
	private int contentType;
	private String extra;
	private Date createTime;
	private long topicId;
	private String title;
	private long uid;
	private String nickName;
	private int v_lv;
	private int level;
	private int likeCount;
	private int isLike;
	private String avatar;
}
