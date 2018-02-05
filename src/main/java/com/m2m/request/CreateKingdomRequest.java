package com.m2m.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CreateKingdomRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;
	private String title;
	private String liveImage;
	private int contentType;
	private String fragment;
	private int source;
	private String extra;
	private int kType;
	private String cExtra;
	private String kConfig;
	private String tags;
	private String autoTags;
	private int subType=0;
	private int kcid;
	private int secretType;
	private int autoCoreType;
	private int onlyFriend;
}
