package com.m2m.eventbus.event;

import java.io.Serializable;

import lombok.Data;

@Data
public class SpeakAtEvent implements Serializable {
	private static final long serialVersionUID = -8579558221034824123L;

	private long topicId;
	private long uid;
	private String fragment;
	private long atUid;
	private String extra;
}
