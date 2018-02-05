package com.m2m.eventbus.event;

import java.io.Serializable;

import lombok.Data;

/**
 * 上海拙心网络科技有限公司出品
 * Author: 代宝磊
 * Date: 2016/8/15
 * Time :16:58
 */
@Data
public class CacheLiveEvent implements Serializable{
	private static final long serialVersionUID = 1L;
	private long uid;
    private long topicId;
}
