package com.m2m.cache;

import java.io.Serializable;

import lombok.Data;

/**
 * 上海拙心网络科技有限公司出品
 * Author: 赵朋扬
 * Date: 2016/7/19.
 */
@Data
public class MySubscribeCacheModel implements Serializable {
	private static final long serialVersionUID = 3892588857927140917L;

	private static final String KEY_PREFIX = "my:subscribe:";

    /**
     * 我订阅的直播缓存模型结构
     * 该结构采用hashmap
     * redis key 命名规则为 my:subscribe:uid example my:subscribe:10020
     * field 采用订阅直播的id
     * value 默认值为0，若直播更新后将value值设置为1
     */

    private String key;

    private String field;

    private String value;

    public MySubscribeCacheModel(long uid,String field,String value){
        this.key = KEY_PREFIX + uid;
        this.field = field;
        this.value = value;
    }

}
