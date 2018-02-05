package com.m2m.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class ExtTagTopicInfo implements Serializable {

	private static final long serialVersionUID = 9113574761390106135L;

	private Long tagId;
	
	private int kingdomCount;

    private int tagPrice;

    private int tagPersons;

}
