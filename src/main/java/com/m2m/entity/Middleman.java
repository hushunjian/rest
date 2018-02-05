package com.m2m.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class Middleman implements Serializable {
	private static final long serialVersionUID = -5508007800158648412L;

	private long uid;
	private String nickName;
	private String avatar;
	private String avatarFrame;
	private int v_lv;
	private String content;
}
