package com.m2m.eventbus.listener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.eventbus.Subscribe;
import com.google.gson.JsonObject;
import com.m2m.dao.UserDao;
import com.m2m.domain.Topic;
import com.m2m.domain.UserNotice;
import com.m2m.domain.UserNoticeUnread;
import com.m2m.domain.UserProfile;
import com.m2m.eventbus.ApplicationEventBus;
import com.m2m.eventbus.event.SpeakAtEvent;
import com.m2m.mapper.ExtUserMapper;
import com.m2m.mapper.TopicMapper;
import com.m2m.mapper.UserNoticeUnreadMapper;
import com.m2m.service.PushService;
import com.m2m.web.Specification;

@Component
@Slf4j
public class SpeakAtListener {

	private final ApplicationEventBus applicationEventBus;
	private final TopicMapper topicMapper;
	private final UserDao userDao;
	private final PushService pushService;
	private final ExtUserMapper extUserMapper;
	private final UserNoticeUnreadMapper userNoticeUnreadMapper;
	
	@Autowired
    public SpeakAtListener(ApplicationEventBus applicationEventBus, TopicMapper topicMapper, UserDao userDao,
    		PushService pushService, ExtUserMapper extUserMapper, UserNoticeUnreadMapper userNoticeUnreadMapper){
        this.applicationEventBus = applicationEventBus;
        this.topicMapper = topicMapper;
        this.userDao = userDao;
        this.pushService = pushService;
        this.extUserMapper = extUserMapper;
        this.userNoticeUnreadMapper = userNoticeUnreadMapper;
    }
	
	@PostConstruct
    public void init(){
        this.applicationEventBus.register(this);
    }
	
	@Subscribe
    public void remindAndPush(SpeakAtEvent speakDto) {
		log.info("remindAndPush start...");
		
		//at的第一个人事放在atUid里的，其他的人放在extra的array数组里
		List<Long> atUidList = new ArrayList<Long>();//被at的uid集合
		atUidList.add(speakDto.getAtUid());
		if(!StringUtils.isEmpty(speakDto.getExtra())){
			JSONObject obj = JSON.parseObject(speakDto.getExtra());
			if(null != obj.get("array")){
				JSONArray arr = obj.getJSONArray("array");
				if(null != arr && arr.size() > 0){
					for(int i=0;i<arr.size();i++){
						if(null != arr.getJSONObject(i).get("atUid")){
							atUidList.add(arr.getJSONObject(i).getLong("atUid"));
						}
					}
				}
			}
		}

        Topic topic = topicMapper.selectByPrimaryKey(speakDto.getTopicId());
        UserProfile userProfile = userDao.getUserProfileByUid(speakDto.getUid());
        
        String message = userProfile.getNickName() + " 在 『"+topic.getTitle()+"』" + " @你了！";
        
        int fromStatus = this.getInternalStatus(topic, speakDto.getUid());
        Map<String, String> extraMap = null;
        for(Long atUid : atUidList){
            this.liveRemind(atUid, userProfile, topic, speakDto.getFragment());
            
            extraMap = new HashMap<>();
            extraMap.put("messageType", String.valueOf(Specification.PushMessageType.AT.index));
            extraMap.put("topicId",String.valueOf(speakDto.getTopicId()));
            extraMap.put("contentType", topic.getType().toString());
            extraMap.put("type",String.valueOf(Specification.PushObjectType.LIVE.index));
            extraMap.put("internalStatus", String.valueOf(this.getInternalStatus(topic, atUid)));
            extraMap.put("fromInternalStatus", String.valueOf(fromStatus));
            extraMap.put("AtUid",String.valueOf(speakDto.getUid()));//@发起方的uid
            extraMap.put("NickName",userProfile.getNickName());//@发起方的昵称
            pushService.pushWithExtra(atUid.toString(), message, extraMap);
        }
        log.info("remindAndPush end");
	}
	
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
	
	private void liveRemind(long targetUid, UserProfile userProfile, Topic topic,String fragment ){
        if(targetUid == userProfile.getUid().longValue()){
            return;
        }
        UserProfile customerProfile = userDao.getUserProfileByUid(targetUid);
        UserNotice userNotice = new UserNotice();
        userNotice.setFromNickName(userProfile.getNickName());
        userNotice.setFromAvatar(userProfile.getAvatar());
        userNotice.setFromUid(userProfile.getUid());
        userNotice.setToNickName(customerProfile.getNickName());
        userNotice.setReadStatus(0);
        userNotice.setCid(topic.getId());
        userNotice.setCoverImage(topic.getLiveImage());
        userNotice.setSummary(fragment);
        userNotice.setToUid(customerProfile.getUid());
        userNotice.setLikeCount(0);
        userNotice.setReadStatus(0);
        extUserMapper.insertUserNotice(userNotice);
        long unid = userNotice.getId();
        
        Date now = new Date();
        //V2.2.5版本开始使用新的红点体系
        UserNoticeUnread unu = new UserNoticeUnread();
        unu.setUid(targetUid);
        unu.setCreateTime(now);
        unu.setNoticeId(unid);
        unu.setNoticeType(Specification.LiveSpeakType.FANS.index);
        unu.setContentType(Specification.UserNoticeUnreadContentType.KINGDOM.index);
        unu.setCid(topic.getId());
        unu.setLevel(Specification.UserNoticeLevel.LEVEL_1.index);
        userNoticeUnreadMapper.insertSelective(unu);
        
        //推送消息上的红点
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("count", "1");
        pushService.pushWithExtra(String.valueOf(targetUid), jsonObject.toString(), null);
    }
}
