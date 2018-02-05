package com.m2m.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseRequest implements Serializable {
	private static final long serialVersionUID = 4176219329761301806L;
    private long uid;
    private String token;
	private String version;
	private String channel;
}