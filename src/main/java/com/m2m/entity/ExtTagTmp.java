package com.m2m.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class ExtTagTmp implements Serializable {

	private static final long serialVersionUID = -8944390412549190805L;

	private long tagId;
	
	private String tagName;
	
	private String coverImg;
	
	private int personCount;
	
	private int kingdomCount;
	

}
