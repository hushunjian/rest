package com.m2m.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class ExtOutImageData implements Serializable{
	
	private static final long serialVersionUID = 6789976960028032989L;

	
	private int contentType;
	
	private int type;
	
	private String fragment;
	
	private String fragmentImage;
	
	private long atUid;
	
	private String atNickName;
	
	private String extra;
	
	private long fid;
	
	private long topicId;
}
