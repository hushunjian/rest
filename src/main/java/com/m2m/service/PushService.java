package com.m2m.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.m2m.eventbus.ApplicationEventBus;
import com.m2m.eventbus.event.PushExtraEvent;

@Service
public class PushService {
	
	@Autowired
    private ApplicationEventBus applicationEventBus;

	/**
	 * 推送
	 * @param uid
	 * @param message
	 * @param extraMaps
	 */
	public void pushWithExtra(String uid,String message,Map<String,String> extraMaps){
		PushExtraEvent event = new PushExtraEvent();
		event.setUid(uid);
		event.setMessage(message);
		event.setExtraMaps(extraMaps);
		this.applicationEventBus.post(event);
	}
}
