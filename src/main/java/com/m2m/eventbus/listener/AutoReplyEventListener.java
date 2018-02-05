package com.m2m.eventbus.listener;

import com.google.common.eventbus.Subscribe;
import com.m2m.eventbus.ApplicationEventBus;
import com.m2m.eventbus.event.AutoReplyEvent;
import com.m2m.service.KingdomRobot;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class AutoReplyEventListener {

	private final ApplicationEventBus applicationEventBus;

	private final KingdomRobot kingdomRobot;

	@Autowired
    public AutoReplyEventListener(ApplicationEventBus applicationEventBus,KingdomRobot kingdomRobot){
        this.applicationEventBus = applicationEventBus;
        this.kingdomRobot = kingdomRobot;
    }
	
	@PostConstruct
    public void init(){
        this.applicationEventBus.register(this);
    }
	
	@Subscribe
	public void autoReply(AutoReplyEvent autoReplyEvent){
    	log.info("auto replay execute start .... ");
		KingdomRobot.ExecutePolicy policy = new KingdomRobot.ExecutePolicy();
		policy.setCreateTime(autoReplyEvent.getCreateTime());
		policy.setTopicId(autoReplyEvent.getTopicId());
		policy.setKingUid(autoReplyEvent.getUid());
		policy.setLastHour(24);
		policy.setMin(180);
		policy.setMax(60);

		kingdomRobot.start(policy);


	}
}
