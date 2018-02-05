package com.m2m.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class ExtAdBanner implements Serializable {

	private static final long serialVersionUID = 8873577544809507095L;

	private Long bannerId;

    private int adBannerHeight;

    private int adBannerWidth;
    
    private int position;

}
