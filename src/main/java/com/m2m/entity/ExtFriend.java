package com.m2m.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class ExtFriend implements Serializable {
	private static final long serialVersionUID = -3781646774586964719L;

	private long uid;
	private String nickName;
	private String avatar;
	private String avatarFrame;
	private int v_lv;
	private long industryId;
	private String industry;
	private String introduced;
}
