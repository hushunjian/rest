package com.m2m.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class ExtUserEmotion implements Serializable {
	private static final long serialVersionUID = 232246336224653319L;

	private long rid;
	private Date createTime;
	private Date timeInterval;
	private int freeValue;
	private int happyValue;
	private long eid;
	private String emotionName;
	private String extra;
	private String content;
	private int h;
	private int w;
	private long pid;
	private String image;
	private String thumb;
	private int thumb_h;
	private int thumb_w;
	private String title;
}
