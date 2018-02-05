package com.m2m.eventbus.listener;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.google.common.eventbus.Subscribe;
import com.m2m.dao.UserDao;
import com.m2m.domain.UserLastChannel;
import com.m2m.eventbus.ApplicationEventBus;
import com.m2m.eventbus.event.PushExtraEvent;
import com.m2m.push.ClientPushFactory;
import com.m2m.web.Specification;

@Component
@Slf4j
public class PushListener {

	private final ApplicationEventBus applicationEventBus;
	private final UserDao userDao;
	
	@Autowired
	public PushListener(ApplicationEventBus applicationEventBus, UserDao userDao){
		this.applicationEventBus = applicationEventBus;
		this.userDao = userDao;
	}
	
	@PostConstruct
    public void init(){
        this.applicationEventBus.register(this);
    }
	
	@Subscribe
	public void pushWithExtra(PushExtraEvent event){
		log.info("push with extra...begin...");
		//极光推送都会推
		if(null != event.getExtraMaps() && event.getExtraMaps().size() > 0){
			ClientPushFactory.getPushService(Specification.PushType.JIGUANG.index).payloadByIdExtra(event.getUid(), event.getMessage(), event.getExtraMaps());
		}else{
			ClientPushFactory.getPushService(Specification.PushType.JIGUANG.index).payloadByIdForMessage(event.getUid(), event.getMessage());
		}
		//特殊推送
		UserLastChannel ulc = userDao.getUserLastChannelByUid(Long.valueOf(event.getUid()));
		if(null != ulc && !StringUtils.isEmpty(ulc.getChannel())){
			if("xiaomi".equals(ulc.getChannel())){//小米的推送
				ClientPushFactory.getPushService(Specification.PushType.XIAOMI.index).payloadByIdExtra(event.getUid(), event.getMessage(), event.getExtraMaps());
			}else if("huawei".equals(ulc.getChannel()) && !StringUtils.isEmpty(ulc.getDeviceToken())){//华为的推送
				ClientPushFactory.getPushService(Specification.PushType.HUAWEI.index).payloadByIdExtra(event.getUid(), event.getMessage(), event.getExtraMaps());
			}
		}
		
		log.info("push with extra...end.");
	}
}
