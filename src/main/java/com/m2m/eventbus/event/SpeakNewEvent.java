package com.m2m.eventbus.event;

import java.io.Serializable;

import lombok.Data;

@Data
public class SpeakNewEvent implements Serializable {
	private static final long serialVersionUID = -2515279985958302669L;

	private long topicId = 0;
    private int type = 0;
    private int contentType = 0;
    private String fragmentContent;
    private long uid = 0;
    private long atUid = 0;
    private long fragmentId = 0;
    private String fragmentExtra;
}
