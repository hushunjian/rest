package com.m2m.cache;

import java.io.Serializable;

import lombok.Data;

@Data
public class FriendNewApplyModel implements Serializable {
	private static final long serialVersionUID = -6026123868138940150L;

	private static final String KEY_PREFIX = "friend:newapply:";
	
	private String key;
    private String value;
    
    public FriendNewApplyModel(long uid,String value){
    	this.key = KEY_PREFIX + uid;
    	this.value = value;
    }
}
