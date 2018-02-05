package com.m2m.eventbus.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.eventbus.Subscribe;
import com.m2m.dao.TopicDao;
import com.m2m.domain.LiveFavorite;
import com.m2m.domain.Topic;
import com.m2m.domain.TopicUserConfig;
import com.m2m.eventbus.ApplicationEventBus;
import com.m2m.eventbus.event.SpeakNewEvent;
import com.m2m.mapper.TopicMapper;
import com.m2m.service.PushService;
import com.m2m.service.RedisService;
import com.m2m.web.Specification;

@Component
public class SpeakNewListener {

	private final ApplicationEventBus applicationEventBus;
	private final RedisService redisService;
	private final TopicMapper topicMapper;
	private final TopicDao topicDao;
	private final PushService pushService;
	
	@Autowired
    public SpeakNewListener(ApplicationEventBus applicationEventBus, RedisService redisService,
    		TopicMapper topicMapper, TopicDao topicDao, PushService pushService){
        this.applicationEventBus = applicationEventBus;
        this.redisService = redisService;
        this.topicMapper = topicMapper;
        this.topicDao = topicDao;
        this.pushService = pushService;
    }
	
	@PostConstruct
    public void init(){
        this.applicationEventBus.register(this);
    }
	
	@Subscribe
	public void speakNew(SpeakNewEvent speakNewEvent){
		//V2.2.5版本开始使用的新逻辑
		//1. 核心圈发言为更新，需要通知非核心圈的并且加入王国的人员有更新
		//2. 非核心圈发言，需要通知国王有评论
		//3. 如果是足迹，则有个特殊逻辑，如果这个王国连续5天没有更新，并且在此期间有有至少两个足迹，则需要通知国王（该推送只推送一次）
		Topic topic = topicMapper.selectByPrimaryKey(speakNewEvent.getTopicId());
		if(null == topic){
			return;
		}
		long currentUid = speakNewEvent.getUid();
		List<Long> coreUidList = new ArrayList<Long>();//核心圈UID集合
		coreUidList.add(topic.getUid());//国王肯定是核心圈
		if(!StringUtils.isEmpty(topic.getCoreCircle())){
			JSONArray array = JSON.parseArray(topic.getCoreCircle());
			Long uid = null;
			for (int i = 0; i < array.size(); i++) {
				uid = array.getLong(i);
				if(!coreUidList.contains(uid)){
					coreUidList.add(uid);
				}
	        }
		}
		List<Long> memberUidList = new ArrayList<Long>();//非核心圈UID集合
		List<LiveFavorite> lfList = topicDao.getLiveFavoriteListByTopicId(topic.getId());
		if(null != lfList && lfList.size() > 0){
			for(LiveFavorite lf : lfList){
				if(!coreUidList.contains(lf.getUid()) && !memberUidList.contains(lf.getUid())){
					memberUidList.add(lf.getUid());
				}
			}
		}
		List<Long> allUidList = new ArrayList<Long>();//所有的成员集合
		if(coreUidList.size() > 0){
			allUidList.addAll(coreUidList);
		}
		if(memberUidList.size() > 0){
			allUidList.addAll(memberUidList);
		}
		
		//首先，除发起人外，其他人都要有王国互动红点以及王国cell红点
		if(allUidList.size() > 0){
			for(Long uid : allUidList){
				if(uid.longValue() != currentUid){
					//王国互动红点
		            redisService.hSet("my:livesStatus:",uid.toString(),"1");
		            
		            //王国cell红点
		            String values = redisService.hGet("my:subscribe:"+uid.toString(), topic.getId().toString());
		            if(StringUtils.isEmpty(values) || Integer.parseInt(values) == 0){//如果不是0，那么认为有更历史的未读信息，则不进行新的设置
		            	redisService.hSet("my:subscribe:"+uid.toString(), topic.getId().toString(), String.valueOf(speakNewEvent.getFragmentId()));
		            }
				}
			}
		}
		
		if(speakNewEvent.getType() == 1000){//系统消息不需要推送
			return;
		}
		
		List<Long> atUidList = new ArrayList<Long>();//被at的uid集合
		if(speakNewEvent.getType() == 11 || speakNewEvent.getType() == 10
				|| speakNewEvent.getType() == 15){
			if(speakNewEvent.getAtUid() > 0){
				atUidList.add(speakNewEvent.getAtUid());
			}
			
			if(!StringUtils.isEmpty(speakNewEvent.getFragmentExtra())){
				JSONObject obj = JSON.parseObject(speakNewEvent.getFragmentExtra());
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
		}
		
		//下面开始推送
		//判断是更新还是评论(卡片为更新，非卡片为评论)
		boolean isUpdate = false;
		if(speakNewEvent.getType() == 0 || speakNewEvent.getType() == 3
				|| speakNewEvent.getType() == 15 || speakNewEvent.getType() == 13
				|| speakNewEvent.getType() == 12 || speakNewEvent.getType() == 52
				|| speakNewEvent.getType() == 55){//卡片算更新
			isUpdate = true;
		}else if(speakNewEvent.getType() == 54 && topic.getUid().longValue() == currentUid){//如果是国王下发的也算更新(和产品沟通的)
			isUpdate = true;
		}
		
		Map<String, String> extraMaps = null;
		if(isUpdate){//卡片，也即王国更新
			//王国更新一小时逻辑
			boolean needPush = false;
			String key = "topic:update:push:status:" + speakNewEvent.getTopicId();
			if(StringUtils.isEmpty(redisService.get(key)) && memberUidList.size() > 0){
				needPush = true;
				
				redisService.setex(key, "1", 3600);//一小时超时时间
			}
			
			if(needPush){
				//通知非核心圈的并且加入王国的人员有更新
				String message = "你关注的王国《"+topic.getTitle()+"》更新了";
				for(Long mid : memberUidList){
					if(mid.longValue() == currentUid || atUidList.contains(mid)
							|| !this.checkTopicPush(topic.getId(), mid.longValue())){//1)本人不需要推，2)at的已经有at的推送了，所以也不需要推,3)关闭了推送的也不推
						continue;
					}
					
					extraMaps = new HashMap<>();
					extraMaps.put("messageType", String.valueOf(Specification.PushMessageType.UPDATE.index));
					extraMaps.put("type", String.valueOf(Specification.PushObjectType.LIVE.index));
					extraMaps.put("topicId", topic.getId().toString());
					extraMaps.put("contentType", topic.getType().toString());
					extraMaps.put("internalStatus", String.valueOf(Specification.SnsCircle.OUT.index));//圈外人
		            pushService.pushWithExtra(mid.toString(), message, extraMaps);
				}
			}
		}else{//非卡片，也即王国评论
			//王国评论一小时逻辑
			boolean needPush = false;
			String key = "topic:review:push:status:" + speakNewEvent.getTopicId();
			if(StringUtils.isEmpty(redisService.get(key)) && topic.getUid().longValue() != currentUid
					&& !atUidList.contains(topic.getUid())){
				needPush = true;
				
				redisService.setex(key, "1", 3600);//一小时超时时间
			}
			
			//需要通知国王有评论
			if(needPush && this.checkTopicPush(topic.getId(), topic.getUid().longValue())){
				extraMaps = new HashMap<>();
				extraMaps.put("messageType", String.valueOf(Specification.PushMessageType.UPDATE.index));
				extraMaps.put("type", String.valueOf(Specification.PushObjectType.LIVE.index));
				extraMaps.put("topicId", topic.getId().toString());
				extraMaps.put("contentType", topic.getType().toString());
				extraMaps.put("internalStatus", String.valueOf(Specification.SnsCircle.CORE.index));//这里是给核心圈的通知，所以直接显示核心圈即可
				pushService.pushWithExtra(topic.getUid().toString(), "你的王国《"+topic.getTitle()+"》有新的评论", extraMaps);
			}
		}

	}

	private boolean checkTopicPush(long topicId, long uid){
    	TopicUserConfig tuc = topicDao.getTopicUserConfigByTopicIdAndUid(topicId, uid);
    	if(null != tuc && tuc.getPushType().intValue() == 1){
    		return false;
    	}
    	return true;
    }
}
