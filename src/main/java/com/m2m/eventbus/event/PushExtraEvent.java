package com.m2m.eventbus.event;

import java.io.Serializable;
import java.util.Map;

import lombok.Data;

@Data
public class PushExtraEvent implements Serializable {
	private static final long serialVersionUID = -6511611855551851188L;
	
	private String uid;
	private String message;
	private Map<String,String> extraMaps;
}
