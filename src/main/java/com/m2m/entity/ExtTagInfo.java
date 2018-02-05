package com.m2m.entity;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;

@Data
public class ExtTagInfo implements Serializable {

	private static final long serialVersionUID = -8944390412549190805L;

	private long tagId;
	
	private String tagName;
	
	private int personCount;
	
	private int kingdomCount;
	
	private int isShowLikeButton;
	
    private List<ExtOutImageData> imageData = Lists.newArrayList();

}
