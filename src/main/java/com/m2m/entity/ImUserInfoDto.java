package com.m2m.entity;

import java.io.Serializable;

import lombok.Data;

/**
 * Author: 马秀成
 * Date: 2017/4/1
 */
@Data
public class ImUserInfoDto implements Serializable {
	private static final long serialVersionUID = 6915139941366725284L;

	private int code;
    private String token;
    private String userId;
}
