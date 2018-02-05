package com.m2m.response;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserSimpleInfo implements Serializable {
	private static final long serialVersionUID = 2536552071083254091L;

	private long uid;
	private String nickName;
	private String avatar;
	private String avatarFrame;
	private int v_lv;
}
