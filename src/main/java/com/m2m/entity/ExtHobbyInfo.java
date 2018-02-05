package com.m2m.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class ExtHobbyInfo implements Serializable {
	private static final long serialVersionUID = 8353894693176787724L;

	private long uid;
	private long hobby;
	private String value;
}
