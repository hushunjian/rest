package com.m2m.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ExtFriendContactsInfo extends ExtFriend {
	private static final long serialVersionUID = -5223206820010667544L;

	private int type;//1消息，0好友
	private String content;
	private int isRec;
	private String nameGroup;
	private long msgId;
}
