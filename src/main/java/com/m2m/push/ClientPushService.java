package com.m2m.push;

import java.util.Map;

/**
 * 除极光推送外的特殊Push
 * @author pc340
 *
 */
public interface ClientPushService {

	void payloadByIdExtra(String uid,String message,Map<String,String> extraMaps);
	
	void payloadByIdForMessage(String uid,String message);
}
