package com.m2m.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class CoinRule implements Serializable {
	private static final long serialVersionUID = 4275562085125225605L;
	
	private int code;// 标识
    private String name;// 规则名称
    private int point;// 上限
    private boolean repeatable;// 是否重复计算
    private long ext;//拓展字段

    public CoinRule(int code, String name, int point, boolean repeatable) {
        this.code = code;
        this.name = name;
        this.point = point;
        this.repeatable = repeatable;
    }
}
