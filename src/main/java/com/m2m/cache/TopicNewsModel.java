package com.m2m.cache;

import java.io.Serializable;

import lombok.Data;

@Data
public class TopicNewsModel implements Serializable {

	private static final long serialVersionUID = -8977142352773604496L;

	private static final String KEY_PREFIX = "topic:news:";

    /**
     * 用户跑马灯是否读取模型
     * 该结构采用hashmap
     * redis key 命名规则为 topicNewsId
     * field 采用fragment的id
     * value 默认值为0
     */

    private String key;

    private String field;

    private String value;

    public TopicNewsModel(long topicNewsId,long uid,String value){
        this.key = KEY_PREFIX+topicNewsId ;
        this.field = uid + "";
        this.value = value;
    }
}
