package com.m2m.eventbus.event;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AutoReplyEvent implements Serializable {
	private static final long serialVersionUID = -8459269063605902985L;

	private long uid;

    private long topicId;

    private Date createTime;


    public AutoReplyEvent(long uid,long topicId,Date createTime){
        this.uid = uid;
        this.topicId = topicId;
        this.createTime = createTime;
    }

}
