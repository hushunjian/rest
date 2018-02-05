package com.m2m.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class ExtAttention implements Serializable {

	private static final long serialVersionUID = 8873577544809507095L;

	private Long id;

    private int type;

    private long longTime;

}
