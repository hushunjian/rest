package com.m2m.request;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Home4TagLikeRequest extends BaseRequest implements Serializable {
	
	private static final long serialVersionUID = -5242082580640473318L;
	
	private long data;
	
	private int isLike;
	
	private int needNew;
	
	private String tagIds;
}