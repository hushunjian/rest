package com.m2m.push;

import com.m2m.web.Specification;
import com.m2m.web.SpringContextHolder;

public class ClientPushFactory {

	public static ClientPushService getPushService(int type){
		ClientPushService instance = null;
		if(type == Specification.PushType.JIGUANG.index){//极光推送
			instance = SpringContextHolder.getBean(JPushServiceImpl.class);
		}else if(type == Specification.PushType.XIAOMI.index){//小米推送
			instance = SpringContextHolder.getBean(XiaomiPushServiceImpl.class);
		} else if(type == Specification.PushType.HUAWEI.index){//华为推送
			instance = SpringContextHolder.getBean(HuaweiPushServiceImpl.class);
		} else{
			throw new RuntimeException("参数非法....");
		}
		return instance;
	}
}
