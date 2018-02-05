package com.m2m.eventbus.listener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.common.eventbus.Subscribe;
import com.m2m.cache.MySubscribeCacheModel;
import com.m2m.dao.TopicDao;
import com.m2m.dao.UserDao;
import com.m2m.domain.Topic;
import com.m2m.domain.UserFollow;
import com.m2m.domain.UserProfile;
import com.m2m.eventbus.ApplicationEventBus;
import com.m2m.eventbus.event.CacheLiveEvent;
import com.m2m.service.PushService;
import com.m2m.service.RedisService;
import com.m2m.web.Specification;

import lombok.extern.slf4j.Slf4j;

/**
 * 上海拙心网络科技有限公司出品
 * Author: 代宝磊
 * Date: 2016/8/15
 * Time :17:01
 */
@Component
@Slf4j
public class CacheLiveListener {

    private final ApplicationEventBus applicationEventBus;

    private final RedisService redisService;

    private final PushService pushService;
    
    private final UserDao userDao;
    
    private final TopicDao topicDao;

    @Autowired
    public CacheLiveListener(ApplicationEventBus applicationEventBus,
                             RedisService redisService,
                             PushService pushService,
                             UserDao userDao,
                             TopicDao topicDao){
        this.applicationEventBus = applicationEventBus;
        this.redisService = redisService;
        this.pushService = pushService;
        this.userDao = userDao;
        this.topicDao = topicDao;
    }

    @PostConstruct
    public void init(){
        this.applicationEventBus.register(this);
    }

    /**
     * sync process after work
     * @param cacheLiveEvent
     */
    @Subscribe
    public void cacheLive(CacheLiveEvent cacheLiveEvent) {
        log.info("invocation by event bus ... ");
        
        //增加缓存，当创建王国后一个小时之内的发言不再推送
        String key = "topic:update:push:status:" + cacheLiveEvent.getTopicId();
        redisService.setex(key, "1",3600);
       
        //判断这个王国是不是聚合王国，如果是聚合王国，则通知粉丝，不是聚合王国则不需要通知
        Topic topic = topicDao.getTopicById(cacheLiveEvent.getTopicId());
        if(null == topic || topic.getType().intValue() != Specification.KingdomType.AGGREGATION.index){
        	return;//非聚合王国，不需要推送
        }
        
        List<UserFollow> list = userDao.getFans(cacheLiveEvent.getUid());
        log.info("get user fans ... ");
        
        UserProfile userProfile = userDao.getUserProfileByUid(cacheLiveEvent.getUid());
        String message = userProfile.getNickName()+"新建了聚合王国《"+topic.getTitle()+"》";
        for (UserFollow userFollow : list) {
            //所有订阅的人显示有红点
            MySubscribeCacheModel cacheModel = new MySubscribeCacheModel(userFollow.getSourceUid(), String.valueOf(cacheLiveEvent.getTopicId()), "1");
            redisService.hSet(cacheModel.getKey(), cacheModel.getField(), cacheModel.getValue());
            Map<String,String> extra = new HashMap<String,String>();
            extra.put("messageType", String.valueOf(Specification.LiveSpeakType.FOLLOW.index));
            extra.put("type",String.valueOf(Specification.PushObjectType.LIVE.index));
            extra.put("topicId",String.valueOf(cacheLiveEvent.getTopicId()));
            extra.put("contentType", String.valueOf(topic.getType()));
            extra.put("internalStatus", String.valueOf(this.getInternalStatus(topic, userFollow.getSourceUid())));
            extra.put("fromInternalStatus", String.valueOf(Specification.SnsCircle.CORE.index));//主播创建的，肯定是核心圈
            pushService.pushWithExtra(userFollow.getSourceUid().toString(),  message, extra);
        }
    }

    //判断核心圈身份
    private int getInternalStatus(Topic topic, long uid) {
        String coreCircle = topic.getCoreCircle();
        JSONArray array = JSON.parseArray(coreCircle);
        int internalStatus = 0;
        for (int i = 0; i < array.size(); i++) {
            if (array.getLong(i) == uid) {
                internalStatus = Specification.SnsCircle.CORE.index;
                break;
            }
        }
        return internalStatus;
    }
}
