package com.m2m.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class ExtTagOutInfo implements Serializable {

	private static final long serialVersionUID = 8010893096290218487L;

	private long tagId;

    private long uid;

    private String tag;
    
    private long fid;
    
    private String image;
    
    private String fragment;
    
    private int type;
    
    private int contentType;
    
    private String extra;
    
    private long topicId;
    
    private int rowno;
    
    private Date createTime;

}
