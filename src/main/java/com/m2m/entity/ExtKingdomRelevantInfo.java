package com.m2m.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class ExtKingdomRelevantInfo   implements Serializable{
	
	private static final long serialVersionUID = 8745855691369387486L;

	private long topicId;

    private Long topicCount;
    
    private Long reviewCount;
    
    private Long favoriteCount;
    
    private Long nonCoreCount;
    
    private Long coreCount;
    
	
}
