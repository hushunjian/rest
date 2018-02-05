package com.m2m.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class ExtEmotionSummaryModel implements Serializable  {
	private static final long serialVersionUID = 1L;
	private static final String KEY_PREFIX = "emotion:summary:";

    /**
     * 用户是否周总结状态缓存模型
     * 该结构采用hashmap
     * redis key 命名规则为 emotion:summary:date
     * field 采用fragment的id
     * value 默认值为0
     */
    private String key;
    private String field;
    private String value;
    public ExtEmotionSummaryModel(String date,long uid,String value){
        this.key = KEY_PREFIX+date ;
        this.field = uid + "";
        this.value = value;
    }
}
