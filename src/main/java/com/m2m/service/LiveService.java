package com.m2m.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.m2m.Constant;
import com.m2m.cache.MyLivesStatusModel;
import com.m2m.cache.MySubscribeCacheModel;
import com.m2m.cache.TeaseAutoPlayStatusModel;
import com.m2m.copier.ContentCopier;
import com.m2m.copier.TopicCopier;
import com.m2m.copier.TopicItemCopier;
import com.m2m.dao.AppConfigDao;
import com.m2m.dao.ContentDao;
import com.m2m.dao.TopicDao;
import com.m2m.dao.TopicTagDao;
import com.m2m.dao.UserDao;
import com.m2m.domain.AppDownloadLog;
import com.m2m.domain.Content;
import com.m2m.domain.ContentImage;
import com.m2m.domain.GiftHistory;
import com.m2m.domain.GiftInfo;
import com.m2m.domain.LiveFavorite;
import com.m2m.domain.LotteryInfo;
import com.m2m.domain.SystemConfig;
import com.m2m.domain.TeaseInfo;
import com.m2m.domain.Topic;
import com.m2m.domain.TopicCategory;
import com.m2m.domain.TopicData;
import com.m2m.domain.TopicFragmentLikeHis;
import com.m2m.domain.TopicFragmentTemplate;
import com.m2m.domain.TopicFragmentWithBLOBs;
import com.m2m.domain.TopicGiven;
import com.m2m.domain.TopicImage;
import com.m2m.domain.TopicNews;
import com.m2m.domain.TopicReadHis;
import com.m2m.domain.TopicTag;
import com.m2m.domain.TopicTagDetail;
import com.m2m.domain.TopicUserConfig;
import com.m2m.domain.TopicUserForbid;
import com.m2m.domain.UserFirstLog;
import com.m2m.domain.UserFollow;
import com.m2m.domain.UserFriend;
import com.m2m.domain.UserProfile;
import com.m2m.domain.UserStealLog;
import com.m2m.domain.VoteInfo;
import com.m2m.entity.ExtContentDto;
import com.m2m.entity.ExtFragmentUpdateCount;
import com.m2m.entity.ExtJoinLotteryUser;
import com.m2m.entity.ExtLotteryWinUser;
import com.m2m.entity.ExtPermissionDescription;
import com.m2m.entity.ExtTopic;
import com.m2m.entity.ExtTopicCount;
import com.m2m.entity.ExtVoteOptionCount;
import com.m2m.entity.Middleman;
import com.m2m.entity.UserCoinDto;
import com.m2m.eventbus.ApplicationEventBus;
import com.m2m.eventbus.event.CacheLiveEvent;
import com.m2m.eventbus.event.SpeakAtEvent;
import com.m2m.eventbus.event.SpeakNewEvent;
import com.m2m.exception.KingdomStealException;
import com.m2m.mapper.AppDownloadLogMapper;
import com.m2m.mapper.ExtContentMapper;
import com.m2m.mapper.ExtFriendMapper;
import com.m2m.mapper.ExtLotteryMapper;
import com.m2m.mapper.ExtTopicFragmentMapper;
import com.m2m.mapper.ExtTopicMapper;
import com.m2m.mapper.ExtUserDislikeMapper;
import com.m2m.mapper.ExtVoteMapper;
import com.m2m.mapper.LotteryInfoMapper;
import com.m2m.mapper.TopicCategoryMapper;
import com.m2m.mapper.TopicImageMapper;
import com.m2m.mapper.TopicMapper;
import com.m2m.mapper.TopicReadHisMapper;
import com.m2m.mapper.TopicTagDetailMapper;
import com.m2m.mapper.UserFirstLogMapper;
import com.m2m.mapper.VoteInfoMapper;
import com.m2m.request.AddAppDownloadLogRequest;
import com.m2m.request.BaseRequest;
import com.m2m.request.CreateKingdomRequest;
import com.m2m.request.DetailFidPageRequest;
import com.m2m.request.DetailPageRequest;
import com.m2m.request.DetailPageStatusRequest;
import com.m2m.request.DetailRequest;
import com.m2m.request.GetJoinLotteryUsersRequest;
import com.m2m.request.GetLiveByCidRequest;
import com.m2m.request.GetLotteryRequest;
import com.m2m.request.GetTopicVoteInfoRequest;
import com.m2m.request.MyTopicRequest;
import com.m2m.request.SettingModifyRequest;
import com.m2m.request.SettingsRequest;
import com.m2m.request.SpeakRequest;
import com.m2m.request.TopicUpdateRequest;
import com.m2m.response.BaseResponse;
import com.m2m.response.CreateKingdomResponse;
import com.m2m.response.DetailFidPageResponse;
import com.m2m.response.DetailPageResponse;
import com.m2m.response.DetailPageStatusResponse;
import com.m2m.response.DetailResponse;
import com.m2m.response.GetGiftInfoListResponse;
import com.m2m.response.GetJoinLotteryUsersResponse;
import com.m2m.response.GetLiveByCidResponse;
import com.m2m.response.GetLotteryResponse;
import com.m2m.response.GetTopicVoteInfoResponse;
import com.m2m.response.ImageInfoResponse;
import com.m2m.response.LiveCoverResponse;
import com.m2m.response.MyTopicResponse;
import com.m2m.response.SettingModifyResponse;
import com.m2m.response.SettingsResponse;
import com.m2m.response.SpeakResponse;
import com.m2m.response.StealResultResponse;
import com.m2m.response.TeaseListQueryResponse;
import com.m2m.response.TopicUpdateResponse;
import com.m2m.util.CommonUtil;
import com.m2m.util.DateUtil;
import com.m2m.web.Response;
import com.m2m.web.ResponseStatus;
import com.m2m.web.Specification;

@Service
public class LiveService  {

	private static final Logger log = LoggerFactory.getLogger(LiveService.class);
	
	/** 聚合王国内容下发次数 */
    private static final String TOPIC_AGGREGATION_PUBLISH_COUNT = "TOPIC_AGGREGATION_PUBLISH_COUNT";
    /** 王国发言数据缓存key */
	private static final String TOPIC_FRAGMENT_NEWEST_MAP_KEY = "TOPIC_FRAGMENT_NEWEST";
    
	@Autowired
    private ApplicationEventBus applicationEventBus;
	@Autowired
	private TopicDao topicDao;
	@Autowired
	private ContentDao contentDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private TopicTagDao topicTagDao;
	@Autowired
	private AppConfigDao appConfigDao;
	
	@Autowired
	private ExtTopicMapper extTopicMapper;
	@Autowired
	private TopicImageMapper topicImageMapper;
	@Autowired
	private TopicMapper topicMapper;
	@Autowired
	private UserFirstLogMapper userFirstLogMapper;
	@Autowired
	private TopicTagDetailMapper topicTagDetailMapper;
	@Autowired
	private ExtContentMapper extContentMapper;
	@Autowired
	private TopicReadHisMapper topicReadHisMapper;
	@Autowired
	private TopicCategoryMapper topicCategoryMapper;
	@Autowired
	private ExtTopicFragmentMapper extTopicFragmentMapper;
	@Autowired
	private ExtFriendMapper extFriendMapper;
	@Autowired
	private AppDownloadLogMapper appDownloadLogMapper;
	@Autowired
	private VoteInfoMapper voteInfoMapper;
	@Autowired
	private ExtVoteMapper extVoteMapper;
	@Autowired
	private LotteryInfoMapper lotteryInfoMapper;
	@Autowired
	private ExtLotteryMapper extLotteryMapper;
	
	@Autowired
	private AppConfigService appConfigService;
	@Autowired
	private RedisService redisService;
	@Autowired
	private UserService userService;
	@Autowired
	private ContentTagService contentTagService;
	@Autowired
	private BaseService baseService;
	@Autowired
	private ExtUserDislikeMapper extUserDislikeMapper;
	@Autowired
	private ActivityService activityService;
	
	
    @Value("${app.live_web}")
    private String live_web;
	
	/**
	 * 王国发言接口
	 * @param request
	 * @return
	 */
	public Response<SpeakResponse> speak(SpeakRequest request){
		log.info("speak start...");
		SpeakResponse result = new SpeakResponse();
		
		Topic topic = topicMapper.selectByPrimaryKey(request.getTopicId());
    	if(null == topic){
    		return Response.failure(ResponseStatus.LIVE_HAS_DELETED.status, ResponseStatus.LIVE_HAS_DELETED.message);
    	}

    	//先判断王国是否处于全体禁言
    	TopicUserForbid topicForbid = topicDao.findTopicForbidStatus(request.getTopicId());
    	//全体禁言时除了发礼物外，只能国王进行发言
    	if(null != topicForbid && request.getContentType() != 24
    			&& request.getUid()!=topic.getUid().longValue()){//不是送礼物，不是国王
    		return Response.failure(ResponseStatus.KINGDOM_ALL_FORBID.status, ResponseStatus.KINGDOM_ALL_FORBID.message);
    	}
    	//判断该用户是否被单禁言
    	TopicUserForbid userForbid = topicDao.findTopicUserForbidByTopicIdAndUid(request.getTopicId(), request.getUid());
    	if(null != userForbid && request.getContentType()!=24){
    		return Response.failure(50072, "你已被此王国禁言");
    	}
    	
        if (request.getType() != Specification.LiveSpeakType.LIKES.index 
        		&& request.getType() != Specification.LiveSpeakType.SUBSCRIBED.index 
        		&& request.getType() != Specification.LiveSpeakType.SHARE.index 
        		&& request.getType() != Specification.LiveSpeakType.FOLLOW.index 
        		&& request.getType() != Specification.LiveSpeakType.INVITED.index) {
            if(!StringUtils.isEmpty(request.getFragment())){
            	//START 防刷
                int speakDelayCfg = Integer.parseInt(appConfigService.getAppConfigByKey(Constant.REPEAT_SPEAK_DELAY_KEY));
                int maxRepeakCountCfg = Integer.parseInt(appConfigService.getAppConfigByKey(Constant.MAX_REPEAT_SPEAK_COUNT_KEY));

                String speakContentCacheKey = "USER_SPEAK_CONTENT@"+request.getUid();
                String speakRepeatCountCacheKey = "USER_SPEAK_REPEAT_COUNT@"+request.getUid();

                String cacheContent = redisService.get(speakContentCacheKey);
                String strCacheRepeatCount =redisService.get(speakRepeatCountCacheKey);
                int cacheRepeatCount=strCacheRepeatCount==null?0:Integer.parseInt(strCacheRepeatCount);
                if(null == cacheContent || !cacheContent.equals(request.getFragment())){
                	cacheRepeatCount = 1;
                }else{
                	cacheRepeatCount++;
                	if(cacheRepeatCount>=maxRepeakCountCfg){
                        return Response.failure(50034,"重复发言");
                    }
                }
                redisService.setex(speakContentCacheKey,request.getFragment(),speakDelayCfg);
                redisService.setex(speakRepeatCountCacheKey,cacheRepeatCount+"",speakDelayCfg);
                // END 防刷
            }
        	
            //由于低版本前端在at的时候有bug，故关于at这里要做一个保护措施
            if(request.getType() == Specification.LiveSpeakType.AT.index
                    || request.getType() == Specification.LiveSpeakType.ANCHOR_AT.index
                    || request.getType() == Specification.LiveSpeakType.AT_CORE_CIRCLE.index){
                if(!StringUtils.isEmpty(request.getExtra())){
                    JSONObject obj = JSON.parseObject(request.getExtra());
                    int start = 0;
                    int end = 0;
                    if(null != obj.get("atStart")){
                        start = obj.getIntValue("atStart");
                    }
                    if(null != obj.get("atEnd")){
                        end = obj.getIntValue("atEnd");
                    }
                    if(start > end){
                        obj.put("atStart", 0);
                        obj.put("atEnd", 0);
                        request.setExtra(obj.toJSONString());
                    }
                }
                if(!StringUtils.isEmpty(request.getFragment())){
                    JSONObject obj = JSON.parseObject(request.getFragment());
                    int start = 0;
                    int end = 0;
                    if(null != obj.get("atStart")){
                        start = obj.getIntValue("atStart");
                    }
                    if(null != obj.get("atEnd")){
                        end = obj.getIntValue("atEnd");
                    }
                    if(start > end){
                        obj.put("atStart", 0);
                        obj.put("atEnd", 0);
                        request.setFragment(obj.toJSONString());
                    }
                }
            }

            TopicFragmentWithBLOBs topicFragment = new TopicFragmentWithBLOBs();
            topicFragment.setFragmentImage(request.getFragmentImage());
            topicFragment.setFragment(request.getFragment());
            topicFragment.setUid(request.getUid());
            topicFragment.setContentType(request.getContentType());
            topicFragment.setType(request.getType());
            topicFragment.setTopicId(request.getTopicId());
            topicFragment.setBottomId(request.getBottomId());
            topicFragment.setTopId(request.getTopId());
            int score = getTopicFragmentScore(request.getType(),request.getContentType());
            topicFragment.setScore(score);
            result.setScore(score);
            topicFragment.setAtUid(request.getAtUid());
            topicFragment.setSource(request.getSource());
            //这里擦个屁股，由于前端有点问题，这里做下兼容
            if((request.getType()==51||request.getType()==52) && request.getContentType() == 73){//活动链接subtype=1,这样的话就能在米汤内部打开
            	if(!StringUtils.isEmpty(request.getExtra())){
                    JSONObject obj = JSON.parseObject(request.getExtra());
                    obj.put("subType", 1);
                    request.setExtra(obj.toJSONString());
            	}
            }
            topicFragment.setExtra(request.getExtra());
            boolean isOut = false;//判断是否外露的内容
            if(request.getType() == 12 || request.getType() == 13
            		|| ((request.getType() == 0 || request.getType()==52) && (request.getContentType()==0||request.getContentType()==1||request.getContentType()==22||request.getContentType()==19||request.getContentType()==72||request.getContentType()==74||request.getContentType()==23||request.getContentType()==18 ||request.getContentType()==25))
            		|| (request.getType()==55 && (request.getContentType()==0||request.getContentType()==63||request.getContentType()==51||request.getContentType()==62||request.getContentType()==72||request.getContentType()==74))){
            	topicFragment.setOutType(1);
            	isOut = true;
            }
            extTopicMapper.saveTopicFragment(topicFragment);

            //当保存的为图片相关的内容时（左侧图片，右侧图片，图组，日签，情绪周总结），需要保存到王国图库
            if((request.getType() == 0 && request.getContentType() == 1)
            		|| (request.getType() == 51 && request.getContentType() == 51)
            		|| (request.getType() == 1 && request.getContentType() == 51)){//左侧图片 or 右侧图片 or 日签
            	TopicImage topicImage = new TopicImage();
            	topicImage.setCreateTime(new Date());
            	topicImage.setExtra(topicFragment.getExtra());
            	topicImage.setFid(topicFragment.getId());
            	topicImage.setImage(topicFragment.getFragmentImage());
            	topicImage.setTopicId(topicFragment.getTopicId());
            	topicImageMapper.insertSelective(topicImage);
            }else if((request.getType() == 0 && request.getContentType() == 25)
            		|| (request.getType() == 52 && request.getContentType() == 25)){//图组
            	JSONObject extra = JSON.parseObject(topicFragment.getExtra());
            	if(null != extra.get("images")){
            		JSONArray imagesArray = extra.getJSONArray("images");
            		JSONArray imageInfoArray = null;
            		if(null != extra.get("imageInfo")){
            			imageInfoArray = extra.getJSONArray("imageInfo");
            		}else{
            			imageInfoArray = new JSONArray();
            		}
            		String image = null;
            		for(int i=0;i<imagesArray.size();i++){
            			TopicImage topicImage = new TopicImage();
            			topicImage.setCreateTime(new Date());
            			if(imageInfoArray.size() > i){
            				topicImage.setExtra(imageInfoArray.getJSONObject(i).toJSONString());
            			}else{
            				topicImage.setExtra("");
            			}
            			topicImage.setFid(topicFragment.getId());
            			image = imagesArray.getString(i);
                    	if(image.startsWith("http")){
                    		image = image.substring(image.lastIndexOf("/")+1);
                    	}
            			topicImage.setImage(image);
            			topicImage.setTopicId(topicFragment.getTopicId());
            			topicImageMapper.insertSelective(topicImage);
            		}
            	}
            }else if(request.getType() == 12){//视频，也需要入王国图库
            	TopicImage topicImage = new TopicImage();
            	topicImage.setCreateTime(new Date());
            	topicImage.setExtra(topicFragment.getExtra());
            	topicImage.setFid(topicFragment.getId());
            	topicImage.setImage(topicFragment.getFragmentImage());
            	topicImage.setTopicId(topicFragment.getTopicId());
            	topicImage.setType(2);//视频
            	topicImage.setVideoUrl(topicFragment.getFragment());
            	topicImageMapper.insertSelective(topicImage);
            }
            
            //由于系统消息和足迹不参与王国更新排序计算，故这里不需要更新时间
            if(request.getType() != Specification.LiveSpeakType.SYSTEM.index
                    && (request.getType() != 51 || request.getContentType() != 16)){
                Topic updateTopic = new Topic();//待更新的字段，不想那些不更新的字段也出现在sql中
                updateTopic.setId(topic.getId());
                if(topic.getSubType()!=null && topic.getSubType()==2){// 如果是赠送的待激活的王国，发言一次之后激活为正常的王国。
                	topic.setSubType(0);
                	updateTopic.setSubType(0);
                }
                if(topic.getSubType() == 1 && topic.getRights() == 3){
                	if(request.getType() == 0 && request.getContentType() == 1 && request.getExtra().contains("image_daycard")){
                	}else{
                		topic.setRights(1);
                		updateTopic.setRights(1);
                	}
                }

                Calendar calendar = Calendar.getInstance();
                topic.setUpdateTime(calendar.getTime());
                updateTopic.setUpdateTime(calendar.getTime());
                topic.setLongTime(calendar.getTimeInMillis());
                updateTopic.setLongTime(calendar.getTimeInMillis());
                if(isOut){
                	topic.setOutTime(calendar.getTime());
                	updateTopic.setOutTime(calendar.getTime());
                }
                topicMapper.updateByPrimaryKeySelective(updateTopic);
                
                //国王/核心圈发言，需要更新content表的updateTime
                if(topic.getUid().longValue() == request.getUid() 
                		|| request.getType() == 0 || request.getType() == 3
                		|| request.getType() == 11 || request.getType() == 12
                		|| request.getType() == 13 || request.getType() == 15
                		|| request.getType() == 52 || request.getType() == 55){
                	contentDao.updateContentUpdateInfo4Kingdom(request.getTopicId(), calendar.getTime(), redisService.incr("UPDATE_ID"));
                	
                	boolean needCheck = true;
					if(topic.getSubType().intValue() == 1){
						needCheck = false;
					}
                	
					if(needCheck){
	                	//判断是否第一次更新
						if(null != userDao.getUserFirstLogByUidAndActionType(request.getUid(), Specification.UserFirstActionType.SPEAK_UPDATE.index)){
							result.setIsFirstUpdate(0);
						}else{
							result.setIsFirstUpdate(1);
							UserFirstLog ufl = new UserFirstLog();
							ufl.setUid(request.getUid());
							ufl.setActionType(Specification.UserFirstActionType.SPEAK_UPDATE.index);
							ufl.setCreateTime(new Date());
							userFirstLogMapper.insertSelective(ufl);
						}
					}
                }
                
                /* 去除留言机器人 3.2.0版本修改
                //判断是否启动机器人
                boolean needRobot = true;
                if(topic.getSubType() == 1 && request.getType() == 0 
                		&& request.getContentType() == 1 && request.getExtra().contains("image_daycard")){
                	needRobot = false;
                }
                //王国创建3天内，国王的前两条发言触发机器人回复
                if(needRobot){
                	String robotSwitch = appConfigService.getAppConfigByKey("KINGDOMROBOT_AUTO_REPLY");
                	if(!StringUtils.isEmpty(robotSwitch) && "on".equals(robotSwitch) && request.getQuotationInfoId() == 0){//自动回复开关开 并且 不是机器人的回复
                		Calendar cal = Calendar.getInstance();
                        cal.setTime(topic.getCreateTime());
                        cal.add(Calendar.DAY_OF_MONTH, 3);
                        Date now = new Date();
                        Date limitTime = cal.getTime();
                        if(limitTime.getTime() > now.getTime()){
                        	//再判断是否国王，只有国王才有机器人
                        	if(topic.getUid().longValue() == request.getUid()){
                        		//再判断是否国王发言多少条了
                        		int c = topicDao.countTotalUserFragment(topic.getId(), request.getUid());
                        		if(c == 8){//第8条触发
                        			applicationEventBus.post(new AutoReplyEvent(topic.getUid(), topic.getId(), topic.getCreateTime()));
                        		}
                        	}
                        }
                	}
                }
                */
                
                log.info("updateTopic updateTime");
            }

            long fid = topicFragment.getId();

            SpeakNewEvent speakNewEvent = new SpeakNewEvent();
            speakNewEvent.setTopicId(request.getTopicId());
            speakNewEvent.setType(request.getType());
            speakNewEvent.setContentType(request.getContentType());
            speakNewEvent.setUid(request.getUid());
            speakNewEvent.setFragmentId(fid);
            speakNewEvent.setAtUid(request.getAtUid());
            speakNewEvent.setFragmentContent(request.getFragment());
            speakNewEvent.setFragmentExtra(request.getExtra());
            applicationEventBus.post(speakNewEvent);
            
            //--add update kingdom cache -- modify by zcl -- begin --
            //此处暂不考虑原子操作
            int total = topicDao.countTotalFragmentByTopicId(request.getTopicId());
            String value = fid + "," + total;
            redisService.hSet(TOPIC_FRAGMENT_NEWEST_MAP_KEY, "T_" + request.getTopicId(), value);
            //--add update kingdom cache -- modify by zcl -- end --
            
            result.setFragmentId(fid);
        }
        log.info("createTopicFragment success");
        
        if (request.getType() == Specification.LiveSpeakType.AT.index
        		|| request.getType() == Specification.LiveSpeakType.ANCHOR_AT.index
        		|| request.getType() == Specification.LiveSpeakType.AT_CORE_CIRCLE.index) {
        	SpeakAtEvent event = new SpeakAtEvent();
        	event.setUid(request.getUid());
        	event.setTopicId(request.getTopicId());
        	event.setFragment(request.getFragment());
        	event.setAtUid(request.getAtUid());
        	event.setExtra(request.getExtra());
        	applicationEventBus.post(event);
        }
        log.info("speakat push end ...");
        
        int isJion = 0 ;
        if (request.getType() == Specification.LiveSpeakType.ANCHOR.index 
        		|| request.getType() == Specification.LiveSpeakType.ANCHOR_WRITE_TAG.index) {
            //更新或者是核心圈跟新加分    送礼物不加分
        	if(request.getContentType()!=24){
	            isJion = 1;
	            UserCoinDto muDto= userService.coinRule(request.getUid(), userService.getCoinRules().get(Specification.CoinRuleCode.SPEAK_KEY.index));
	            result.setUpgrade(muDto.getUpgrade());
	            result.setCurrentLevel(muDto.getCurrentLevel());
        	}
        }
        int share = 0 ;
        //判断是分享的Type
        if(request.getType() == 52 || request.getType() == 51 || request.getType() == 72 ){
	        UserCoinDto muDto= userService.coinRule(request.getUid(), userService.getCoinRules().get(Specification.CoinRuleCode.SHARE_KING_KEY.index));
	        result.setUpgrade(muDto.getUpgrade());
	        result.setCurrentLevel(muDto.getCurrentLevel());
	        share = 1 ;
        }
        //如果不是 加入王国  喜欢王国  分享王国  不是送礼物 进入 只加2分
        if(isJion != 1 && share != 1 && request.getContentType()!=24) {
            UserCoinDto muDto = userService.coinRule(request.getUid(), userService.getCoinRules().get(Specification.CoinRuleCode.SPEAK_KEY.index));
            result.setUpgrade(muDto.getUpgrade());
            result.setCurrentLevel(muDto.getCurrentLevel());
        }
        // 核心圈发文本,自动打Tag
        if(request.getType()==0 && request.getContentType()==0 && !StringUtils.isEmpty(request.getFragment())){
        	//判断下自动打标签开关是否开启
        	Integer IS_TOPIC_AUTO_TAG = appConfigService.getIntegerAppConfigByKey("IS_TOPIC_AUTO_TAG");// 是否自动打标签开关。
        	if(null != IS_TOPIC_AUTO_TAG && IS_TOPIC_AUTO_TAG==1){
        		//先判断是否有自动打的标签，如果有，则不需要打了
            	if(!topicTagDao.isExistsTopicAutoTag(request.getTopicId())){
            		List<String> autoTags = null;
            		try{
            			autoTags = contentTagService.getContentTag(request.getFragment(), 1);
            		}catch(Exception e){
            			log.error("自动打标签失败", e);
            		}
            		if(null != autoTags && autoTags.size() > 0){
            			String autoTag = autoTags.get(0);
            			//判断是否打过这个标签 以及 是否已经把这个标签拉黑
            			if(!topicTagDao.isExistsTopicTag(request.getTopicId(), autoTag)
            					&& !topicTagDao.isExistsTopicBadTag(request.getTopicId(), autoTag)){
            				TopicTag ttag = topicTagDao.getTopicTagByTag(autoTag);
            				if(null != ttag){
            					TopicTagDetail detail = new TopicTagDetail();
    	    	        		detail.setTopicId(request.getTopicId());
    	    	        		detail.setUid(-1L);//自动打的，目前设置-1
    	    	        		detail.setTagId(ttag.getId());
    	    	        		detail.setTag(autoTag);
    	    	        		detail.setCreateTime(new Date());
    	    	        		detail.setStatus(0);
    	    	        		detail.setAutoTag(1);
    	    	        		topicTagDetailMapper.insertSelective(detail);
            				}
            			}
            		}
            	}
        	}
        }
        
        // 记录操作日志
        userService.addUserOprationLog(request.getUid(), Specification.UserOperateType.SPEAK_KINGDOM.action, request.getTopicId());
        
		log.info("speak end!");
		return Response.success(ResponseStatus.USER_SPEAK_SUCCESS.status, ResponseStatus.USER_SPEAK_SUCCESS.message, result);
	}
	
	private int getTopicFragmentScore(int type, int contentType){
        int result = 0;

        String key = "TOPICFRAGMENTSCORE_"+type+"_"+contentType;

        String res = appConfigService.getAppConfigByKey(key);
        if(!StringUtils.isEmpty(res)){
            try{
                result = Integer.valueOf(res);
            }catch(Exception e){
                log.error("数字转换失败", e);
            }
        }

        return result;
	}

    public Response<LiveCoverResponse> liveCover(long topicId, long uid, int source,Long fromUid) {
        log.info("liveCover start ...");
        LiveCoverResponse liveCoverResponse = new LiveCoverResponse();
        String KINGDOM_VIEW_KEY = "USER_VIEW_KINGDOM_"+uid+"_"+topicId;
        redisService.set(KINGDOM_VIEW_KEY, "1");
        Topic topic = topicMapper.selectByPrimaryKey(topicId);
        if(topic==null){
            return Response.failure(ResponseStatus.LIVE_HAS_DELETED.status,ResponseStatus.LIVE_HAS_DELETED.message);
        }
        liveCoverResponse = TopicCopier.INSTANCE.asLiveCoverResponse(topic);
        //私密王国处理
        if(topic.getRights()==Specification.KingdomRights.PRIVATE_KINGDOM.index){
        	if(!baseService.isKing(uid, topic.getUid()) && !baseService.isInCore(uid, topic.getCoreCircle())){
				return Response.failure(ResponseStatus.LIVE_HAS_DELETED.status,"此王国需要经过国王邀请才允许进入");
			}
        }
        
        //私密属性
        if(topic.getRights()==Specification.KingdomRights.PRIVATE_KINGDOM.index){
        	liveCoverResponse.setRights(Specification.KingdomRights.PRIVATE_KINGDOM.index);
        }else{
        	liveCoverResponse.setRights(Specification.KingdomRights.PUBLIC_KINGDOM.index);
        }
        //判断王国是否设置了仅好友可见
        if(topic.getOnlyFriend()==Specification.OnlyFriendStatus.ONLY_FRIEND.index){
        	liveCoverResponse.setOnlyFriend(Specification.OnlyFriendStatus.ONLY_FRIEND.index);
        }else{
        	liveCoverResponse.setOnlyFriend(Specification.OnlyFriendStatus.ALL.index);
        }
        
        boolean isCanView = true;
        if(uid != topic.getUid().longValue()){
        	//判断当前用户与国王是否是好友
            UserFriend userFriend = userDao.getUserFriendBySourceUidAndTargetUid(uid, topic.getUid());
            if(null != userFriend && userFriend.getStatus().intValue() == 0){
            	liveCoverResponse.setIsFriend(1);
            }else{
            	liveCoverResponse.setIsFriend(0);
            	if(topic.getOnlyFriend()==Specification.OnlyFriendStatus.ONLY_FRIEND.index){
            		isCanView = false;
            	}
            }
            
			// 王国是否可见
			if (topic.getRights() == Specification.KingdomRights.PRIVATE_KINGDOM.index) {
				// 当前用户是否可见
				if (!baseService.isInCore(uid, topic.getCoreCircle())) {
					isCanView = false;
				}
			} 
        }
        if(isCanView){
        	liveCoverResponse.setCanView(Specification.CanViewStatus.CAN_VIEW.index);
        }else{
        	liveCoverResponse.setCanView(Specification.CanViewStatus.NOT_CAN_VIEW.index);
        }
        liveCoverResponse.setCoverImage(baseService.genQiNiuImageUrl(topic.getLiveImage()));
        UserProfile userProfile = userDao.getUserProfileByUid(topic.getUid());
        liveCoverResponse.setAvatar(baseService.genAvatar(userProfile.getAvatar()));
        liveCoverResponse.setAvatarFrame(baseService.genQiNiuImageUrl(userProfile.getAvatarFrame()));
        liveCoverResponse.setNickName(userProfile.getNickName());
        ExtTopicCount topicCount = extTopicMapper.getTopicCount(topic.getId());
        if(null != topicCount){
        	liveCoverResponse.setReviewCount((int)topicCount.getReviewCount());
            liveCoverResponse.setTopicCount((int)topicCount.getTotalCount());
        }
        liveCoverResponse.setV_lv(userProfile.getvLv());
        liveCoverResponse.setLevel(userProfile.getLevel());

        LiveFavorite hasFavorite = topicDao.getLiveFavoriteByTopicIdAndUid(uid,topicId);
        liveCoverResponse.setHasFavorite(hasFavorite==null?0:1);
        liveCoverResponse.setFavorite(liveCoverResponse.getHasFavorite());
        int internalStatus = baseService.getInternalStatus(topic,uid);
        if(internalStatus==Specification.SnsCircle.OUT.index){
        	if(hasFavorite!=null){
        		internalStatus=Specification.SnsCircle.IN.index;
        	}
        }
        liveCoverResponse.setInternalStatus(internalStatus);
        liveCoverResponse.setLiveWebUrl(live_web+topicId+"?uid="+uid);//返回直播URL地址

        //标签
        String tags = "";
        List<Long> tagIdList = new ArrayList<Long>();
        List<TopicTagDetail> topicTagDetails = topicTagDao.getTopicTagDetailsByTopicId(topicId);
        if(topicTagDetails != null && topicTagDetails.size() > 0){
            StringBuilder builder = new StringBuilder();
            for (TopicTagDetail detail : topicTagDetails){
                tagIdList.add(detail.getTagId());
                String tag = detail.getTag();
                if(tags.equals("")){
                    tags = builder.append(tag).toString();
                }else {
                    builder.append(";"+tag);
                }
                
            }
            liveCoverResponse.setTags(builder.toString());
        }

        //临时逻辑，本王国的标签如果自己有不喜欢的，将被撤销不喜欢
        if(tagIdList.size() > 0){
        	topicTagDao.removeUserDislikeUserTags(uid, tagIdList);
        }

        //这里需要判断是否需要返回足迹信息
        //条件：不是国王，不是核心圈，没有加入过王国，并且是第一次进入这个王国
        if(!baseService.isKing(uid, topic.getUid()) && !baseService.isInCore(uid, topic.getCoreCircle())
        		&& null == hasFavorite && topicDao.isNewInTopic(uid, topicId)){
        	TopicFragmentTemplate topicFragmentTemplate = topicDao.getTopicFragmentTemplate();
            if(topicFragmentTemplate != null && !StringUtils.isEmpty(topicFragmentTemplate.getContent())){
                String text = topicFragmentTemplate.getContent();
                String[] temp = text.split("##");
                if(null != temp && temp.length > 0){
                	liveCoverResponse.setTrackContent(temp[0]);
                    if(temp.length > 1 && !StringUtils.isEmpty(temp[1])){
                    	liveCoverResponse.setTrackImage(baseService.genQiNiuImageUrl(temp[1]));
                    }
                }
            }
        }
        
        //记录阅读历史
        TopicReadHis trh = new TopicReadHis();
        trh.setUid(uid);
        trh.setTopicId(topicId);
        trh.setReadCount(1);
        trh.setFromUid(fromUid);
        if(source == 0){//APP内
            trh.setInApp(1);
        }else{//APP外
            trh.setInApp(0);
        }
        trh.setCreateTime(new Date());

        
        //添加直播阅读数
        Content content = contentDao.getContentByTopicId(topicId);
        if(content.getReadCount() == 1 || content.getReadCount() == 2){
            liveCoverResponse.setReadCount(1);
            extContentMapper.updateContentReadCount(content.getId(), 1, 1);
            trh.setReadCountDummy(1);
        }else {
            SystemConfig systemConfig = appConfigDao.getSystemConfig();
            if(systemConfig!=null){
	            int start = systemConfig.getReadCountStart();
	            int end = systemConfig.getReadCountEnd();
	            Random random = new Random();
	            //取1-6的随机数每次添加
	            int value = random.nextInt(end) + start;
	            extContentMapper.updateContentReadCount(content.getId(), 1, value);
	            liveCoverResponse.setReadCount(content.getReadCountDummy() + value);
	            trh.setReadCountDummy(value);
            }
        }
        topicReadHisMapper.insertSelective(trh);

        // 添加成员数量
        Long favoriteCount = Long.valueOf(0);
        List<Long> topicIdList = new ArrayList<Long>();
        topicIdList.add(Long.valueOf(topicId));
        Map<String, Long> memberMap = topicDao.getTopicMembersCount(topicIdList);
        if(null != memberMap && memberMap.size() > 0){
            if(null != memberMap.get(String.valueOf(topicId))){
                favoriteCount = memberMap.get(String.valueOf(topicId));
            }
        }
        if(favoriteCount.longValue() > 0){
            liveCoverResponse.setMembersCount(favoriteCount.intValue() + 1);
        }else{
            liveCoverResponse.setMembersCount(1);
        }

        //聚合相关属性--begin--add by zcl 20170205
        int max = 10;
        String count = redisService.get(TOPIC_AGGREGATION_PUBLISH_COUNT);
        if(!StringUtils.isEmpty(count)){
            max = Integer.valueOf(count);
        }
        liveCoverResponse.setPublishLimit(max);
        liveCoverResponse.setType(topic.getType());
        if(topic.getType() == Specification.KingdomType.NORMAL.index){//个人王国
            //被聚合次数
            int ceCount = topicDao.countAggregationParentTopic(topicId);
            liveCoverResponse.setCeCount(ceCount);
        }if(topic.getType() == Specification.KingdomType.AGGREGATION.index){//聚合王国
            //子王国数
            int acCount = topicDao.countAggregationSubTopic(topicId);
            liveCoverResponse.setAcCount(acCount);
            //子王国top列表
            int needNum = 30;
            //置顶的按置顶时间倒序，非置顶的按更新时间倒叙
            List<Topic> acTopList = extTopicMapper.getAcTopicListByCeTopicId(topicId, 0, needNum);
            
            if(null != acTopList && acTopList.size() > 0){
                List<Long> uidList = new ArrayList<Long>();
                Long id = null;
                for(Topic t : acTopList){
                    id = t.getUid();
                    if(!uidList.contains(id)){
                        uidList.add(id);
                    }
                }
                List<Long> tidList = new ArrayList<Long>();
                for(Topic t : acTopList){
                   Long tid = t.getId();
                    if(!tidList.contains(tid)){
                    	tidList.add(tid);
                    }
                }
                //一次性获取当前用户针对于各王国是否收藏过
                Map<String, LiveFavorite> liveFavoriteMap = new HashMap<String, LiveFavorite>();
                List<LiveFavorite> liveFavoriteList = topicDao.getLiveFavoritesByUidAndTopicIds(uid, tidList);
                if(null != liveFavoriteList && liveFavoriteList.size() > 0){
                    for(LiveFavorite lf : liveFavoriteList){
                        liveFavoriteMap.put(String.valueOf(lf.getTopicId()), lf);
                    }
                }
                LiveCoverResponse.TopicElement e = null;
                for(Topic t : acTopList){
                    e = new LiveCoverResponse.TopicElement();
                    e.setTopicId(t.getId());
                    e.setTitle(t.getTitle());
                    e.setCoverImage(baseService.genQiNiuImageUrl(t.getLiveImage()));
                    int internalStatust = baseService.getInternalStatus(topic, uid);
                    if(internalStatust==Specification.SnsCircle.OUT.index){
                    	if(liveFavoriteMap.get(String.valueOf(t.getId()))!=null){
                    		internalStatust=Specification.SnsCircle.IN.index;
                    	}
                    }
                    e.setInternalStatus(internalStatust);
                    liveCoverResponse.getAcTopList().add(e);
                }
            }
        }else{
            //暂不支持
        }
        //聚合相关属性--end--

        /* 跑马灯逻辑出去
        //跑马灯列表信息处理
        Date date = new Date();
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date);
        cal1.add(Calendar.DATE, -1);
        List<TopicNews> topicNewsList = topicDao.getTopicNewsList24h(cal1.getTime());
        List<Long> newsTopicIdList = new ArrayList<Long>();
        if(null != topicNewsList && topicNewsList.size() > 0){
        	TopicNews topicNews = null;
        	TopicNewsModel topicNewsModel = null;
        	for (int i = 0; i < topicNewsList.size(); i++) {
        		topicNews = topicNewsList.get(i);
        		topicNewsModel = new TopicNewsModel(topicNews.getId(), uid, "0");
                String isTopicNews= redisService.hGet(topicNewsModel.getKey(), topicNewsModel.getField());
                if (!StringUtils.isEmpty(isTopicNews)) {
                	topicNewsList.remove(i);
                	i--;
                    continue;
                } else {
                	redisService.hSet(topicNewsModel.getKey(), topicNewsModel.getField(), topicNewsModel.getValue());
                    if(!newsTopicIdList.contains(topicNews.getTopicId())){
                    	newsTopicIdList.add(topicNews.getTopicId());
                    }
                }
        	}
        }
        
        Map<String, Topic> newsTopicMap = new HashMap<String, Topic>();
        if(newsTopicIdList.size() > 0){
        	List<Topic> newsTopicList = topicDao.getTopicListByIds(newsTopicIdList);
        	if(null != newsTopicList && newsTopicList.size() > 0){
        		for(Topic t : newsTopicList){
        			newsTopicMap.put(t.getId().toString(), t);
        		}
        	}
        }
        
        if(null != topicNewsList && topicNewsList.size() > 0){
        	LiveCoverResponse.TopicNewsElement  topicNewsElement = null;
        	Topic newsTopic = null;
        	
            List<Long> tidList = new ArrayList<Long>();
            for(TopicNews tnews : topicNewsList){
               Long tid = tnews.getTopicId();
                if(!tidList.contains(tid)){
                	tidList.add(tid);
                }
            }
            //一次性获取当前用户针对于各王国是否收藏过
            Map<String, LiveFavorite> liveFavoriteMap = new HashMap<String, LiveFavorite>();
            List<LiveFavorite> liveFavoriteList = topicDao.getLiveFavoritesByUidAndTopicIds(uid, tidList);
            if(null != liveFavoriteList && liveFavoriteList.size() > 0){
                for(LiveFavorite lf : liveFavoriteList){
                    liveFavoriteMap.put(String.valueOf(lf.getTopicId()), lf);
                }
            }
        	
        	for(TopicNews tnews : topicNewsList){
        		topicNewsElement = new LiveCoverResponse.TopicNewsElement();
                topicNewsElement.setId(tnews.getId());
                topicNewsElement.setContent(tnews.getContent());
                topicNewsElement.setType(tnews.getType());
                topicNewsElement.setTopicId(tnews.getTopicId());
                newsTopic  = newsTopicMap.get(tnews.getTopicId().toString());
                if(newsTopic==null){
                    continue;
                }
                topicNewsElement.setContentType(newsTopic.getType());
                int internalStatust = baseService.getInternalStatus(newsTopic, uid);
                if(internalStatust==Specification.SnsCircle.OUT.index){
                	if(liveFavoriteMap.get(String.valueOf(tnews.getTopicId()))!=null){
                		internalStatust=Specification.SnsCircle.IN.index;
                	}
                }
                topicNewsElement.setInternalStatus(internalStatust);
                liveCoverResponse.getNewsTopList().add(topicNewsElement);
        	}
        }
        */
 
        liveCoverResponse.setTopicPrice(topic.getPrice());
        liveCoverResponse.setTopicRMB(baseService.exchangeKingdomPrice(topic.getPrice()));
        TopicData topicData = topicDao.getTopicDataByTopicId(topicId);
        int percentage=0;
        if(topicData==null){
            liveCoverResponse.setTopicPriceChanged(0);
        }else{
            liveCoverResponse.setTopicPriceChanged(topicData.getLastPriceIncr());
            percentage = new BigDecimal((topicDao.getLessPriceChangeTopicCount(topicData.getLastPriceIncr()) * 100.0 / topicDao.getTopicDataCount() ))
                    .setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
        }
        liveCoverResponse.setBeatTopicPercentage(percentage);
        liveCoverResponse.setIsSteal(1);
        try {
            getAliveCoinsForSteal(uid,topicId,topic);
        } catch (KingdomStealException e2) {
            if(e2.getErrorCode()==KingdomStealException.KINGDOM_STEALED){
                liveCoverResponse.setIsSteal(2);
            }
        } catch(Exception e){
        	
        }

        // 记录操作日志
        userService.addUserOprationLog(topic.getUid(), Specification.UserOperateType.READ_KINGDOM.action, topic.getId());
        
        List<GiftHistory> giftHistoryList = topicDao.getGiftList24h(topicId, DateUtil.addDay(new Date(), -1));
        for (GiftHistory giftHistory : giftHistoryList) {
        	String giftStatusstr = redisService.get("GIFT_STATUS_"+uid+"_"+giftHistory.getFragmentId());
        	if (StringUtils.isEmpty(giftStatusstr)) {
        		if(liveCoverResponse.getGiftList().size()<3){
        			LiveCoverResponse.GiftElement ge = new LiveCoverResponse.GiftElement();
        			ge.setGiftId(giftHistory.getGiftId());
        			ge.setCount(giftHistory.getGiftCount());
        			liveCoverResponse.getGiftList().add(ge);
        		}
        		redisService.setex("GIFT_STATUS_"+uid+"_"+giftHistory.getFragmentId(),"1",60*60*48);
        	}
        }
     
        if(topic.getCategoryId().intValue() > 0){
        	TopicCategory tc = topicCategoryMapper.selectByPrimaryKey(topic.getCategoryId().intValue());
        	if(null != tc){
        		liveCoverResponse.setKcid(tc.getId());
        		liveCoverResponse.setKcName(tc.getName());
        		if(!StringUtils.isEmpty(tc.getCoverImg())){
        			liveCoverResponse.setKcImage(baseService.genQiNiuImageUrl(tc.getCoverImg()));
        		}
        		if(!StringUtils.isEmpty(tc.getIcon())){
        			liveCoverResponse.setKcIcon(baseService.genQiNiuImageUrl(tc.getIcon()));
        		}
        	}
        }
        
        return Response.success(liveCoverResponse);
    }
    /**
     * 获取用户在指定的王国下可偷的金币数量。如果可偷，返回具体可偷取的数量；否则抛出异常，提示不可偷取原因
     * @author zhangjiwei
     * @date Jun 12, 2017
     * @param uid
     * @param topicId
     * @return
     * @throws Exception
     */
    private int getAliveCoinsForSteal(long uid,long topicId,Topic topic) throws Exception{
        //判断用户能不能偷取王国
        if(topic.getUid()==uid){
            throw new KingdomStealException("不能偷取自己的王国");
        }
        // 本王国今日余额
        int topicRemainCoins = 0;
        TopicData topicData  =topicDao.getTopicDataByTopicId(topicId);
        topicRemainCoins= topicData!=null?topicData.getStealPrice():0;

        if(topicRemainCoins<=0){
            throw new KingdomStealException("该王国没有可偷的金币了");
        }
        // 用户今天已偷
        String day = DateUtil.date2string(new Date(), "yyyy-MM-dd");
        // 用户今日可偷
        List<UserStealLog> userStealLog = extTopicMapper.getUserStealLogByDay(uid,day);
        int stealedCoins=0;
        boolean stealed = false;
        for(UserStealLog log:userStealLog){
            int logCoin = log.getStealedCoins();
            stealedCoins+=logCoin;
            if(topicId==log.getTopicId()){
                stealed=true;
            }
        }
        if(stealed){
            throw new KingdomStealException(KingdomStealException.KINGDOM_STEALED,"不能重复偷取此王国");
        }
        // 每天可偷数量
        int userDayLimit = 0;//Integer.parseInt(userService.getAppConfigByKey(Constant.USER_STEAL_COIN_DAY_LIMIT_KEY));
        int userOnceLimit = 0;//Integer.parseInt(userService.getAppConfigByKey(Constant.USER_STEAL_COIN_ONCE_LIMIT_KEY));
        int userTopicLimit = 0;//Integer.parseInt(userService.getAppConfigByKey(Constant.USER_STEAL_TOPIC_DAY_LIMIT_KEY));
        ExtPermissionDescription permisson=  userService.getUserPermission(uid);
        for(ExtPermissionDescription.PermissionNodeDto per: permisson.getNodes()){
        	if(per.getCode()==8){// 可偷王国数量
        		userTopicLimit=per.getNum();
        	}
        	if(per.getCode()==9){// 单次偷取上限
        		userOnceLimit=per.getNum();
        	}
        	if(per.getCode()==10){// 个人每日获取金币上限
        		userDayLimit=per.getNum();
        	}
        }
        
        if(userStealLog.size()>=userTopicLimit){
            throw new KingdomStealException("你当前的等级每天只能偷取"+userTopicLimit+"个王国哦");
        }
        stealedCoins=extTopicMapper.getUserCoinsByDay(uid, day);		// 此处重新计算用户当日获取到的总金币数，包括操作所得和偷取所得。
        int userTodayRemain=userDayLimit-stealedCoins;

        if(userTodayRemain<=0){
            throw new KingdomStealException("你今天可获取的米汤币额度已满");
        }

        int canStealCount = Math.min(userTodayRemain, topicRemainCoins);
     
        canStealCount=Math.min(canStealCount, userOnceLimit);
        // 判断王国剩余价值。
        //随机数
        int coins = RandomUtils.nextInt(1, canStealCount+1);
        return coins;
    }
    
    /**
     * IOS王国详情
     * @param request
     * @return
     */
    public Response<DetailResponse> detail(DetailRequest request){
    	log.info("get live detail start...");
        
        Topic topic = topicMapper.selectByPrimaryKey(request.getTopicId());
        if(null == topic){
        	return Response.failure(ResponseStatus.LIVE_HAS_DELETED.status, ResponseStatus.LIVE_HAS_DELETED.message);
        }

        //消除红点
        MySubscribeCacheModel cacheModel = new MySubscribeCacheModel(request.getUid(), String.valueOf(request.getTopicId()), "0");
        redisService.hSet(cacheModel.getKey(), cacheModel.getField(), cacheModel.getValue());

        int totalRecords = topicDao.countTotalFragmentByTopicId(request.getTopicId());

        DetailResponse result = new DetailResponse();
        result.setTotalRecords(totalRecords);
        int offset = request.getOffset();
        int totalPages =totalRecords%offset==0?totalRecords/offset:totalRecords/offset+1;
        result.setTotalPages(totalPages);

        result.getPageInfo().setStart(request.getPageNo());

        int ss = 0;//预防机制。。防止程序出错死循环
        while(true){
        	ss++;
        	if(ss > 100){//预计不会查询超过100页数据的，预防死循环
        		break;
        	}
			int startIndex = (request.getPageNo() - 1) * request.getOffset();
        	List<TopicFragmentWithBLOBs> list = extTopicMapper.getTopicFragmentForPage(request.getTopicId(), startIndex, request.getOffset(), request.getSinceId());
        	if(null == list || list.size() == 0){//理论上就是到底了
        		if(request.getDirection() == Specification.LiveDetailDirection.DOWN.index){
	        		if(ss == 1){//第一次循环就没拉到数据，那么说明就没有数据了。。这里要补全上下页
	        			result.getPageInfo().setEnd(request.getPageNo());
	        			DetailResponse.PageDetail pd = new DetailResponse.PageDetail();
	        	        pd.setPage(request.getPageNo());
	        	        pd.setRecords(0);
	        	        pd.setIsFull(2);
	        	        result.getPageInfo().getDetail().add(pd);
	        		}
	        		break;
        		}
        	}
        	int flag = buildLiveDetail(request,result,list, topic);
        	if(result.getLiveElements().size() >= offset){
        		break;
        	}
        	if(flag == 1){
        		break;
        	}
        	//还没满，则继续查询上一页或下一页
        	if(request.getDirection() == Specification.LiveDetailDirection.DOWN.index){
        		request.setPageNo(request.getPageNo() + 1);
        	}else{
        		request.setPageNo(request.getPageNo() - 1);
        		if(request.getPageNo() < 1){
            		break;//向上拉到顶了
            	}
        	}
        }

        //将当前用户针对于本王国的相关消息置为已读，也算读过了
        userService.clearUserNoticeUnreadByCid(request.getUid(), Specification.UserNoticeUnreadContentType.KINGDOM.index, request.getTopicId());

        log.info("get live detail end ...");
        return  Response.success(ResponseStatus.GET_LIVE_DETAIL_SUCCESS.status, ResponseStatus.GET_LIVE_DETAIL_SUCCESS.message, result);
    }
    
    //返回 1：到最后了  2：没到最后
    private int buildLiveDetail(DetailRequest request, DetailResponse result, List<TopicFragmentWithBLOBs> fragmentList, Topic topic) {
        log.info("build live detail start ...");
        int listSize = fragmentList.size();
        
        if(request.getReqType() == 1){//只看发布，则过滤掉非发布的内容
        	TopicFragmentWithBLOBs f = null;
        	for(int i=0;i<fragmentList.size();i++){
        		f = fragmentList.get(i);
        		if(f.getUid().longValue() == request.getUid()){
        			continue;
        		}
        		if(f.getType().intValue() == 0 || f.getType().intValue() == 3
        				|| f.getType().intValue() == 12 || f.getType().intValue() == 13
        				|| f.getType().intValue() == 52 || f.getType().intValue() == 54
        				|| f.getType().intValue() == 55){
        			continue;
        		}
        		fragmentList.remove(i);
        		i--;
        	}
        }
        
        result.setPageNo(request.getPageNo());
        result.getPageInfo().setEnd(request.getPageNo());

        List<Long> imageFidList = new ArrayList<Long>();
        List<Long> uidList = new ArrayList<Long>();
        
        for (TopicFragmentWithBLOBs topicFragment : fragmentList) {
    		if(!uidList.contains(topicFragment.getUid())){
    			uidList.add(topicFragment.getUid());
    		}
    		//视频和大图需要在这里就返回点赞数和是否点赞过
    		if(topicFragment.getType().intValue() == 12//视频
    				|| (topicFragment.getType().intValue() == 54 && topicFragment.getContentType().intValue() == 62)//下发视频
    				|| (topicFragment.getType().intValue() == 0 && topicFragment.getContentType().intValue() == 1)//主播图片
    				|| (topicFragment.getType().intValue() == 54 && topicFragment.getContentType().intValue() == 51)//下发图片
    				|| (topicFragment.getType().intValue() == 55 && topicFragment.getContentType().intValue() == 51)//转发图片
    				|| topicFragment.getContentType().intValue() == 23//UGC
    				){
    			imageFidList.add(topicFragment.getId());
    		}
    	}

    	Map<String, UserProfile> userMap = new HashMap<String, UserProfile>();
    	List<UserProfile> userList = userDao.getUserProfilesByUids(uidList);
    	if(null != userList && userList.size() > 0){
    		for(UserProfile u : userList){
    			userMap.put(u.getUid().toString(), u);
    		}
    	}

    	//一次性查询关注信息
        Map<String, String> followMap = new HashMap<String, String>();
        List<UserFollow> userFollowList = userDao.getAllFollows(request.getUid(), uidList);
        if(null != userFollowList && userFollowList.size() > 0){
        	for(UserFollow uf : userFollowList){
        		followMap.put(uf.getSourceUid()+"_"+uf.getTargetUid(), "1");
        	}
        }
        
        //一次性查询那些用户对当前王国的加入情况
        Map<String, String> favoriteMap = new HashMap<>();
        List<LiveFavorite> lfList = topicDao.getLiveFavoriteListByTopicIdAndUidList(request.getTopicId(), uidList);
        if(null != lfList && lfList.size() > 0){
        	for(LiveFavorite lf : lfList){
        		favoriteMap.put(lf.getUid().toString(), "1");
        	}
        }

        //图片点赞相关信息
        Map<String, TopicImage> imageLikeCountMap = new HashMap<String, TopicImage>();
        Map<String, String> imageLikeHisMap = new HashMap<String, String>();
        if(imageFidList.size() > 0){
        	List<TopicImage> imageList = topicDao.getTopicImageListByFids(imageFidList);
        	if(null != imageList && imageList.size() > 0){
        		List<Long> topicImageIds = new ArrayList<Long>();
        		for(TopicImage ti : imageList){
        			imageLikeCountMap.put(ti.getFid().toString()+"_"+ti.getImage(), ti);
        			topicImageIds.add(ti.getId());
        		}
        		if(topicImageIds.size() > 0){
        			List<TopicFragmentLikeHis> likeHisList = topicDao.getTopicFragmentLikeHisListByUidAndImageIds(request.getUid(), topicImageIds);
        			if(null != likeHisList && likeHisList.size() > 0){
        				for(TopicFragmentLikeHis his : likeHisList){
        					imageLikeHisMap.put(his.getImageId().toString(), "1");
        				}
        			}
        		}
        	}
        }
        
        int count = 0;
        UserProfile userProfile = null;
        long pageUpdateTime = 0;
        TopicImage topicImage = null;
        DetailResponse.LiveElement liveElement = null;
        long currentTime = System.currentTimeMillis();
        for (TopicFragmentWithBLOBs topicFragment : fragmentList) {
        	if(null != topicFragment.getUpdateTime() && topicFragment.getUpdateTime().getTime() > pageUpdateTime){
        		pageUpdateTime = topicFragment.getUpdateTime().getTime();
        	}
        	//低版本内容类型兼容
            if(!CommonUtil.isNewVersion(request.getVersion(), "2.2.2")){
            	if(topicFragment.getType() == 51&&topicFragment.getContentType()==16){//足迹
            		continue;
            	}else if(topicFragment.getType() == 1000){//系统灰条
            		continue;
            	}
            }
            if(!CommonUtil.isNewVersion(request.getVersion(), "2.2.4")){
            	if(topicFragment.getType() == 51 || topicFragment.getType() == 52){
            		if(topicFragment.getContentType() == 17 || topicFragment.getContentType() == 18){//表情包
                		continue;
            		}
            	}
            }
            if(!CommonUtil.isNewVersion(request.getVersion(), "2.2.5")){
            	if(topicFragment.getType() == 51 || topicFragment.getType() == 52){
            		if(topicFragment.getContentType() == 20){//逗一逗
                		continue;
            		}
            	}
            	if(topicFragment.getType() == 52){
            		if(topicFragment.getContentType() == 19){//投票
                		continue;
            		}
            	}
            }
            if(!CommonUtil.isNewVersion(request.getVersion(), "3.0.1")){
            	if(topicFragment.getType() == 1000 && topicFragment.getContentType() == 0){//系统灰条（纯文本）
                    continue;
            	}
            }
            if(!CommonUtil.isNewVersion(request.getVersion(), "3.0.2")){
            	if((topicFragment.getType() == 0 || topicFragment.getType() == 52) 
            			&& (topicFragment.getContentType() == 22 || topicFragment.getContentType() == 23)){//抽奖和UGC
                    continue;
            	}
            }
            if(!CommonUtil.isNewVersion(request.getVersion(), "3.0.3")){
            	if(topicFragment.getContentType() == 24
            			|| (topicFragment.getType() == 1 && (topicFragment.getContentType() == 1 || topicFragment.getContentType() == 51))
            			|| (topicFragment.getType() == 51 && topicFragment.getContentType() == 51)){//送礼物和右侧图片
                    continue;
            	}
            }
            if(!CommonUtil.isNewVersion(request.getVersion(), "3.0.6")){
            	if(topicFragment.getContentType() == 25 ){//排版图组过滤
           		 continue;
           	 	}
            }
            //已经删除的也不要了
            if(topicFragment.getStatus().intValue() == 0){
        		continue;
        	}
        	
        	liveElement = new DetailResponse.LiveElement();
        	liveElement.setStatus(topicFragment.getStatus().intValue());
            liveElement.setId(topicFragment.getId());
            
            //逗一逗自动播放状态
            int teaseStatus = 0;
            if((topicFragment.getType() == 51 || topicFragment.getType() == 52) && topicFragment.getContentType() == 20){
            	JSONObject extraJson = JSONObject.parseObject(topicFragment.getExtra());
            	if(String.valueOf(request.getUid()).equals(extraJson.getString("uid"))){
            		TeaseAutoPlayStatusModel teaseAutoPlayStatusModel = new TeaseAutoPlayStatusModel(topicFragment.getId(), "0");
            		String isTeaseStatus = redisService.hGet(teaseAutoPlayStatusModel.getKey(), teaseAutoPlayStatusModel.getField());
            		if (!StringUtils.isEmpty(isTeaseStatus)) {
              		  teaseStatus=0;
                    } else {
                  	  teaseStatus=1;
                  	  redisService.hSet(teaseAutoPlayStatusModel.getKey(), teaseAutoPlayStatusModel.getField(), teaseAutoPlayStatusModel.getValue());
                    }
            	}
            }
            liveElement.setTeaseStatus(teaseStatus);
            
            //送礼物自动播放状态
            int giftStatus = 0;
            if(topicFragment.getContentType() == 24){
            	//24小时内
            	if(currentTime-topicFragment.getCreateTime().getTime()<=24*60*60*1000l){
            		String giftStatusstr = redisService.get("GIFT_STATUS_" + request.getUid() + "_" + topicFragment.getId());
            		if (!StringUtils.isEmpty(giftStatusstr)) {
            			giftStatus=0;
            		} else {
            			giftStatus=1;
            			redisService.setex("GIFT_STATUS_" + request.getUid() + "_" + topicFragment.getId(), "1", 60*60*48);
            		}
            	}
            }
            liveElement.setGiftStatus(giftStatus);

            liveElement.setUid(topicFragment.getUid().longValue());
            userProfile = userMap.get(topicFragment.getUid().toString());
            if(null != userProfile){
            	liveElement.setAvatar(Constant.QINIU_DOMAIN + "/" + userProfile.getAvatar());
            	if(!StringUtils.isEmpty(userProfile.getAvatarFrame())){
            		liveElement.setAvatarFrame(Constant.QINIU_DOMAIN + "/" + userProfile.getAvatarFrame());
            	}
                liveElement.setNickName(userProfile.getNickName());
                liveElement.setV_lv(userProfile.getvLv());
                liveElement.setLevel(userProfile.getLevel());
            }
            liveElement.setFragment(topicFragment.getFragment());
            if (!StringUtils.isEmpty(topicFragment.getFragmentImage())) {
                liveElement.setFragmentImage(Constant.QINIU_DOMAIN + "/" + topicFragment.getFragmentImage());
            }
            liveElement.setCreateTime(topicFragment.getCreateTime());
            liveElement.setType(topicFragment.getType());
            if(null != followMap.get(request.getUid()+"_"+topicFragment.getUid())){
            	liveElement.setIsFollowed(1);
            }else{
            	liveElement.setIsFollowed(0);
            }
            liveElement.setContentType(topicFragment.getContentType());
            liveElement.setFragmentId(topicFragment.getId());
            liveElement.setSource(topicFragment.getSource());
            liveElement.setExtra(topicFragment.getExtra());
            
            int internalStatus = baseService.getInternalStatus(topic,topicFragment.getUid());
            if(internalStatus==Specification.SnsCircle.OUT.index){
            	if(null != favoriteMap.get(topicFragment.getUid().toString())){
            		internalStatus = Specification.SnsCircle.IN.index;
            	}
            }
            liveElement.setInternalStatus(internalStatus);
            liveElement.setScore(topicFragment.getScore());
            
            //视频和大图相关的需要返回点赞相关信息
            if(topicFragment.getType().intValue() == 12//视频
    				|| (topicFragment.getType().intValue() == 54 && topicFragment.getContentType().intValue() == 62)//下发视频
    				|| (topicFragment.getType().intValue() == 0 && topicFragment.getContentType().intValue() == 1)//主播图片
    				|| (topicFragment.getType().intValue() == 54 && topicFragment.getContentType().intValue() == 51)//下发图片
    				|| (topicFragment.getType().intValue() == 55 && topicFragment.getContentType().intValue() == 51)//转发图片
    				|| topicFragment.getContentType().intValue() == 23//UGC
    				){
            	topicImage = imageLikeCountMap.get(topicFragment.getId().toString()+"_"+topicFragment.getFragmentImage());
            	if(null != topicImage){
            		liveElement.setLikeCount(topicImage.getLikeCount().intValue());
            		if(null != imageLikeHisMap.get(topicImage.getId().toString())){
            			liveElement.setIsLike(1);//点赞过
            		}
            	}
    		}
            
            if(request.getDirection() == Specification.LiveDetailDirection.DOWN.index){
            	result.getLiveElements().add(liveElement);
            }else{
            	result.getLiveElements().add(count, liveElement);
            }
            count++;
        }
        DetailResponse.PageDetail pd = new DetailResponse.PageDetail();
        pd.setPage(request.getPageNo());
        pd.setRecords(count);
        pd.setIsFull(listSize >= request.getOffset()?1:2);
        pd.setUpdateTime(pageUpdateTime);
        result.getPageInfo().getDetail().add(pd);
        log.info("build live detail end ...");

        //判断是否到底或到顶
        int returnValue = 2;
        if(request.getDirection() == Specification.LiveDetailDirection.DOWN.index){//向下拉，那么返回的数据不满50条说明到底了
        	if(listSize < request.getOffset()){
        		returnValue = 1;
        	}
        }else{//向上拉，那么page到第一页时说明就到顶了
        	if(request.getPageNo() <= 1){
        		returnValue = 1;
        	}
        }
        return returnValue;
    }
    
    /**
     * 安卓王国详情
     * @param request
     * @return
     */
    public Response<DetailPageResponse> detailPage(DetailPageRequest request){
    	log.info("get live detail start...");
        Topic topic = topicMapper.selectByPrimaryKey(request.getTopicId());
        if(null == topic){
        	return Response.failure(ResponseStatus.LIVE_HAS_DELETED.status, ResponseStatus.LIVE_HAS_DELETED.message);
        }

        //消除红点
        MySubscribeCacheModel cacheModel = new MySubscribeCacheModel(request.getUid(), String.valueOf(request.getTopicId()), "0");
        redisService.hSet(cacheModel.getKey(), cacheModel.getField(), cacheModel.getValue());

        int totalRecords = topicDao.countTotalFragmentByTopicId(request.getTopicId());

        DetailPageResponse liveDetailDto = new DetailPageResponse();
        liveDetailDto.setTotalRecords(totalRecords);
        int offset = request.getOffset();
        int totalPages =totalRecords%offset==0?totalRecords/offset:totalRecords/offset+1;
        liveDetailDto.setTotalPages(totalPages);
        liveDetailDto.getPageInfo().setStart(request.getPageNo());

        int ss = 0;//预防机制。。防止程序出错死循环
        while(true){
        	ss++;
        	if(ss > 100){//预计不会查询超过100页数据的，预防死循环
        		break;
        	}
        	int startIndex = (request.getPageNo() - 1) * request.getOffset();
        	List<TopicFragmentWithBLOBs> list = extTopicMapper.getTopicFragmentForPage(request.getTopicId(), startIndex, request.getOffset(), request.getSinceId());
        	if(null == list || list.size() == 0){//理论上就是到底了
        		if(request.getDirection() == Specification.LiveDetailDirection.DOWN.index){
	        		if(ss == 1){//第一次循环就没拉到数据，那么说明就没有数据了。。这里要补全上下页
	        			liveDetailDto.getPageInfo().setEnd(request.getPageNo());
	        			DetailPageResponse.PageDetail pd = new DetailPageResponse.PageDetail();
	        	        pd.setPage(request.getPageNo());
	        	        pd.setRecords(0);
	        	        pd.setIsFull(2);
	        	        liveDetailDto.getPageInfo().getDetail().add(pd);
	        		}
	        		break;
        		}
        	}
        	int flag = buildLiveDetailPage(request, liveDetailDto, list, topic);
        	if(request.getCurrentCount() >= offset){
        		break;
        	}
        	if(flag == 1){
        		break;
        	}
        	//还没满，则继续查询上一页或下一页
        	if(request.getDirection() == Specification.LiveDetailDirection.DOWN.index){
        		request.setPageNo(request.getPageNo() + 1);
        	}else{
        		request.setPageNo(request.getPageNo() - 1);
        		if(request.getPageNo() < 1){
            		break;//向上拉到顶了
            	}
        	}
        }

        //将当前用户针对于本王国的相关消息置为已读
        userService.clearUserNoticeUnreadByCid(request.getUid(), Specification.UserNoticeUnreadContentType.KINGDOM.index, request.getTopicId());

        log.info("get live detail end ...");
        return  Response.success(ResponseStatus.GET_LIVE_DETAIL_SUCCESS.status, ResponseStatus.GET_LIVE_DETAIL_SUCCESS.message, liveDetailDto);
    }
    
    //返回 1：到最后了  2：没到最后
    private int buildLiveDetailPage(DetailPageRequest request, DetailPageResponse liveDetailDto, List<TopicFragmentWithBLOBs> fragmentList, Topic topic) {
        log.info("build live detail start ...");
        int listSize = fragmentList.size();
        
        if(request.getReqType() == 1){//只看发布，则过滤掉非发布的内容
        	TopicFragmentWithBLOBs f = null;
        	for(int i=0;i<fragmentList.size();i++){
        		f = fragmentList.get(i);
        		if(f.getUid().longValue() == request.getUid()){
        			continue;
        		}
        		if(f.getType().intValue() == 0 || f.getType().intValue() == 3
        				|| f.getType().intValue() == 12 || f.getType().intValue() == 13
        				|| f.getType().intValue() == 52 || f.getType().intValue() == 54
        				|| f.getType().intValue() == 55){
        			continue;
        		}
        		fragmentList.remove(i);
        		i--;
        	}
        }
        liveDetailDto.setPageNo(request.getPageNo());
        liveDetailDto.getPageInfo().setEnd(request.getPageNo());

        List<Long> imageFidList = new ArrayList<Long>();
        List<Long> uidList = new ArrayList<Long>();
        for (TopicFragmentWithBLOBs topicFragment : fragmentList) {
    		if(!uidList.contains(topicFragment.getUid())){
    			uidList.add(topicFragment.getUid());
    		}
    		//视频和大图需要在这里就返回点赞数和是否点赞过
    		if(topicFragment.getType().intValue() == 12//视频
    				|| (topicFragment.getType().intValue() == 54 && topicFragment.getContentType().intValue() == 62)//下发视频
    				|| (topicFragment.getType().intValue() == 0 && topicFragment.getContentType().intValue() == 1)//主播图片
    				|| (topicFragment.getType().intValue() == 54 && topicFragment.getContentType().intValue() == 51)//下发图片
    				|| (topicFragment.getType().intValue() == 55 && topicFragment.getContentType().intValue() == 51)//转发图片
    				|| topicFragment.getContentType().intValue() == 23//UGC
    				){
    			imageFidList.add(topicFragment.getId());
    		}
    	}

    	Map<String, UserProfile> userMap = new HashMap<String, UserProfile>();
    	List<UserProfile> userList = userDao.getUserProfilesByUids(uidList);
    	if(null != userList && userList.size() > 0){
    		for(UserProfile u : userList){
    			userMap.put(u.getUid().toString(), u);
    		}
    	}

    	//一次性查询关注信息
        Map<String, String> followMap = new HashMap<String, String>();
        List<UserFollow> userFollowList = userDao.getAllFollows(request.getUid(), uidList);
        if(null != userFollowList && userFollowList.size() > 0){
        	for(UserFollow uf : userFollowList){
        		followMap.put(uf.getSourceUid()+"_"+uf.getTargetUid(), "1");
        	}
        }
        
        //一次性查询那些用户对当前王国的加入情况
        Map<String, String> favoriteMap = new HashMap<>();
        List<LiveFavorite> lfList = topicDao.getLiveFavoriteListByTopicIdAndUidList(request.getTopicId(), uidList);
        if(null != lfList && lfList.size() > 0){
        	for(LiveFavorite lf : lfList){
        		favoriteMap.put(lf.getUid().toString(), "1");
        	}
        }

        //图片点赞相关信息
        Map<String, TopicImage> imageLikeCountMap = new HashMap<String, TopicImage>();
        Map<String, String> imageLikeHisMap = new HashMap<String, String>();
        if(imageFidList.size() > 0){
        	List<TopicImage> imageList = topicDao.getTopicImageListByFids(imageFidList);
        	if(null != imageList && imageList.size() > 0){
        		List<Long> topicImageIds = new ArrayList<Long>();
        		for(TopicImage ti : imageList){
        			imageLikeCountMap.put(ti.getFid().toString()+"_"+ti.getImage(), ti);
        			topicImageIds.add(ti.getId());
        		}
        		if(topicImageIds.size() > 0){
        			List<TopicFragmentLikeHis> likeHisList = topicDao.getTopicFragmentLikeHisListByUidAndImageIds(request.getUid(), topicImageIds);
        			if(null != likeHisList && likeHisList.size() > 0){
        				for(TopicFragmentLikeHis his : likeHisList){
        					imageLikeHisMap.put(his.getImageId().toString(), "1");
        				}
        			}
        		}
        	}
        }
        
        DetailPageResponse.PageDetail pd = new DetailPageResponse.PageDetail();

        long currentTime = System.currentTimeMillis();
        
        DetailPageResponse.LiveElement liveElement = null;
        int count = 0;
        UserProfile userProfile = null;
        long pageUpdateTime = 0;
        TopicImage topicImage = null;
        for (TopicFragmentWithBLOBs topicFragment : fragmentList) {
        	if(null != topicFragment.getUpdateTime() && topicFragment.getUpdateTime().getTime() > pageUpdateTime){
        		pageUpdateTime = topicFragment.getUpdateTime().getTime();
        	}
        	
        	//低版本内容类型兼容
            if(!CommonUtil.isNewVersion(request.getVersion(), "2.2.2")){
            	if(topicFragment.getType() == 51&&topicFragment.getContentType()==16){//足迹
            		continue;
            	}else if(topicFragment.getType() == 1000){//系统灰条
            		continue;
            	}
            }
            if(!CommonUtil.isNewVersion(request.getVersion(), "2.2.4")){
            	if(topicFragment.getType() == 51 || topicFragment.getType() == 52){
            		if(topicFragment.getContentType() == 17 || topicFragment.getContentType() == 18){//表情包
                		continue;
            		}
            	}
            }
            if(!CommonUtil.isNewVersion(request.getVersion(), "2.2.5")){
            	if(topicFragment.getType() == 51 || topicFragment.getType() == 52){
            		if(topicFragment.getContentType() == 20){//逗一逗
                		continue;
            		}
            	}
            	if(topicFragment.getType() == 52){
            		if(topicFragment.getContentType() == 19){//投票
                		continue;
            		}
            	}
            }
            if(!CommonUtil.isNewVersion(request.getVersion(), "3.0.1")){
            	if(topicFragment.getType() == 1000 && topicFragment.getContentType() == 0){//系统灰条（纯文本）
                    continue;
            	}
            }
            if(!CommonUtil.isNewVersion(request.getVersion(), "3.0.2")){
            	if((topicFragment.getType() == 0 || topicFragment.getType() == 52) 
            			&& (topicFragment.getContentType() == 22 || topicFragment.getContentType() == 23)){//抽奖和UGC
                    continue;
            	}
            }
            if(!CommonUtil.isNewVersion(request.getVersion(), "3.0.3")){
            	if(topicFragment.getContentType() == 24
            			|| (topicFragment.getType() == 1 && (topicFragment.getContentType() == 1 || topicFragment.getContentType() == 51))
            			|| (topicFragment.getType() == 51 && topicFragment.getContentType() == 51)){//送礼物和右侧图片
                    continue;
            	}
            }
            if(!CommonUtil.isNewVersion(request.getVersion(), "3.0.6")){
            	if(topicFragment.getContentType() == 25 ){//排版图组过滤
           		 continue;
           	 	}
            }
            //删除的也不要了
            if(topicFragment.getStatus().intValue() == 0){
            	continue;
            }

            liveElement = new DetailPageResponse.LiveElement();
            liveElement.setStatus(topicFragment.getStatus());
            liveElement.setId(topicFragment.getId());
            
            //逗一逗自动播放状态
            int teaseStatus = 0;
            if((topicFragment.getType() == 51 || topicFragment.getType() == 52) && topicFragment.getContentType() == 20){
            	JSONObject extraJson = JSONObject.parseObject(topicFragment.getExtra());
            	if((String.valueOf(request.getUid())).equals(extraJson.getString("uid"))){
            		TeaseAutoPlayStatusModel teaseAutoPlayStatusModel =  new TeaseAutoPlayStatusModel(topicFragment.getId(), "0");
            		String isTeaseStatus = redisService.hGet(teaseAutoPlayStatusModel.getKey(), teaseAutoPlayStatusModel.getField());
            		if (!StringUtils.isEmpty(isTeaseStatus)) {
            			teaseStatus=0;
            		} else {
            			teaseStatus=1;
            			redisService.hSet(teaseAutoPlayStatusModel.getKey(), teaseAutoPlayStatusModel.getField(), teaseAutoPlayStatusModel.getValue());
            		}
            	}
            }
            liveElement.setTeaseStatus(teaseStatus);
            
            //送礼物自动播放状态
            int giftStatus = 0;
            if(topicFragment.getContentType() == 24){
                if(currentTime-topicFragment.getCreateTime().getTime()<=24*60*60*1000){//24小时内
                    String giftStatusstr = redisService.get("GIFT_STATUS_"+request.getUid()+"_"+topicFragment.getId());
                    if (!StringUtils.isEmpty(giftStatusstr)) {
                    	giftStatus=0;
                    } else {
                    	giftStatus=1;
                    	redisService.setex("GIFT_STATUS_"+request.getUid()+"_"+topicFragment.getId(),"1",60*60*48);
                    }
                }
            }
            liveElement.setGiftStatus(giftStatus);

            userProfile = userMap.get(topicFragment.getUid());
            liveElement.setUid(topicFragment.getUid());
            if(null != userProfile){
            	liveElement.setAvatar(Constant.QINIU_DOMAIN + "/" + userProfile.getAvatar());
            	if(!StringUtils.isEmpty(userProfile.getAvatarFrame())){
            		liveElement.setAvatarFrame(Constant.QINIU_DOMAIN + "/" + userProfile.getAvatarFrame());
            	}
                liveElement.setNickName(userProfile.getNickName());
                liveElement.setV_lv(userProfile.getvLv());
                liveElement.setLevel(userProfile.getLevel());
            }
            liveElement.setFragment(topicFragment.getFragment());
            if (!StringUtils.isEmpty(topicFragment.getFragmentImage())) {
                liveElement.setFragmentImage(Constant.QINIU_DOMAIN + "/" + topicFragment.getFragmentImage());
            }
            liveElement.setCreateTime(topicFragment.getCreateTime());
            liveElement.setType(topicFragment.getType());
            if(null != followMap.get(request.getUid()+"_"+topicFragment.getUid())){
            	liveElement.setIsFollowed(1);
            }else{
            	liveElement.setIsFollowed(0);
            }
            liveElement.setContentType(topicFragment.getContentType());
            liveElement.setFragmentId(topicFragment.getId());
            liveElement.setSource(topicFragment.getSource());
            liveElement.setExtra(topicFragment.getExtra());
            
            int internalStatus = baseService.getInternalStatus(topic,topicFragment.getUid());
            if(internalStatus==Specification.SnsCircle.OUT.index){
            	if(null != favoriteMap.get(topicFragment.getUid().toString())){
            		internalStatus = Specification.SnsCircle.IN.index;
            	}
            }
            liveElement.setInternalStatus(internalStatus);
            liveElement.setScore(topicFragment.getScore());
            //视频和大图相关的需要返回点赞相关信息
            if(topicFragment.getType().intValue() == 12//视频
    				|| (topicFragment.getType().intValue() == 54 && topicFragment.getContentType().intValue() == 62)//下发视频
    				|| (topicFragment.getType().intValue() == 0 && topicFragment.getContentType().intValue() == 1)//主播图片
    				|| (topicFragment.getType().intValue() == 54 && topicFragment.getContentType().intValue() == 51)//下发图片
    				|| (topicFragment.getType().intValue() == 55 && topicFragment.getContentType().intValue() == 51)//转发图片
    				|| topicFragment.getContentType().intValue() == 23//UGC
    				){
            	topicImage = imageLikeCountMap.get(topicFragment.getId().toString()+"_"+topicFragment.getFragmentImage());
            	if(null != topicImage){
            		liveElement.setLikeCount(topicImage.getLikeCount().intValue());
            		if(null != imageLikeHisMap.get(topicImage.getId().toString())){
            			liveElement.setIsLike(1);//点赞过
            		}
            	}
    		}
            
            pd.getLiveElements().add(liveElement);
            count++;
        }
        
        pd.setPage(request.getPageNo());
        pd.setRecords(count);
        pd.setIsFull(listSize >= request.getOffset()?1:2);
        pd.setUpdateTime(pageUpdateTime);
        liveDetailDto.getPageInfo().getDetail().add(pd);
        log.info("build live detail end ...");

        request.setCurrentCount(request.getCurrentCount() + count);
        
        //判断是否到底或到顶
        int result = 2;
        if(request.getDirection() == Specification.LiveDetailDirection.DOWN.index){//向下拉，那么返回的数据不满50条说明到底了
        	if(listSize < request.getOffset()){
        		result = 1;
        	}
        }else{//向上拉，那么page到第一页时说明就到顶了
        	if(request.getPageNo() <= 1){
        		result = 1;
        	}
        }

        return result;
    }
    
    /**
     * 王国互动
     * @param request
     * @return
     */
    public Response<MyTopicResponse> getMyTopic(MyTopicRequest request){
    	log.info("getMyLives start...");
    	MyTopicResponse result = new MyTopicResponse();
        
        if (request.getUpdateTime() == 0) {
        	request.setUpdateTime(Long.MAX_VALUE);
        }
        List<ExtTopic> topicList = extTopicMapper.getMyLivesByUpdateTimeNew(request.getUid(), request.getUpdateTime());
        log.info("getMyLives data success");
        builderMyTopicList(request.getUid(), result, topicList);

        MyLivesStatusModel myLivesStatusModel = new MyLivesStatusModel(request.getUid(), "0");
        String isUpdate = redisService.hGet(myLivesStatusModel.getKey(), myLivesStatusModel.getField());
        if (!StringUtils.isEmpty(isUpdate)) {
        	result.setIsUpdate(Integer.parseInt(isUpdate));
        } else {
        	result.setIsUpdate(0);
        }
        // 赠送王国
        List<TopicGiven> givenKingdomList = topicDao.getMyGivenKingdoms(request.getUid());
        if(null != givenKingdomList && givenKingdomList.size() > 0){
        	UserProfile myProfile= userDao.getUserProfileByUid(request.getUid());
        	MyTopicResponse.GivenKingdom gk = null;
        	for(TopicGiven given : givenKingdomList){
        		gk = new MyTopicResponse.GivenKingdom();
            	gk.setGivenKingdomId(given.getId());
            	gk.setTitle(given.getTitle());
            	gk.setSummary(given.getSummary());
            	gk.setCoverImage(Constant.QINIU_DOMAIN + "/" +given.getCover());
            	gk.setCreateTime(given.getCreateTime());
            	gk.setUid(given.getUid());
            	gk.setTags(given.getTags());
            	if(null != myProfile){
            		gk.setAvatar(baseService.genAvatar(myProfile.getAvatar()));
                	gk.setNickName(myProfile.getNickName());
                	gk.setV_lv(myProfile.getvLv());
                	gk.setLevel(myProfile.getLevel());
                	if(StringUtils.isEmpty(myProfile.getAvatarFrame())){
                		gk.setAvatarFrame(Constant.QINIU_DOMAIN + "/" +myProfile.getAvatarFrame());
                	}
            	}
            	gk.setKcName("记录");
            	result.getGivenKingdoms().add(gk);
        	}
        }
        
        log.info("getMyLives end");
        return Response.success(ResponseStatus.GET_MY_LIVE_SUCCESS.status, ResponseStatus.GET_MY_LIVE_SUCCESS.message, result);
    }
    
    private void builderMyTopicList(long uid, MyTopicResponse result, List<ExtTopic> topicList) {
        if(null == topicList || topicList.size() == 0){
            return;
        }
        List<Long> uidList = new ArrayList<Long>();
        List<Long> tidList = new ArrayList<Long>();
        for(ExtTopic topic : topicList){
            if(!uidList.contains(topic.getUid())){
                uidList.add(topic.getUid());
            }
            if(!tidList.contains(topic.getId())){
                tidList.add(topic.getId());
            }
        }
        
        //一次性查询所有王国的最新一条数据
        Map<String, TopicFragmentWithBLOBs> lastFragmentMap = new HashMap<>();
        List<TopicFragmentWithBLOBs> lastFragmentList = extTopicFragmentMapper.getLastFragmentByTopicIds(tidList);
        if(null != lastFragmentList && lastFragmentList.size() > 0){
            for(TopicFragmentWithBLOBs f : lastFragmentList){
            	lastFragmentMap.put(f.getTopicId().toString(), f);
                if(!uidList.contains(f.getUid())){
                    uidList.add(f.getUid());
                }
            }
        }
        //一次性查询所有用户的属性
        Map<String, UserProfile> profileMap = new HashMap<String, UserProfile>();
        List<UserProfile> profileList = userDao.getUserProfilesByUids(uidList);
        if(null != profileList && profileList.size() > 0){
            for(UserProfile up : profileList){
                profileMap.put(String.valueOf(up.getUid()), up);
            }
        }
        //一次性查询所有topic对应的content
        Map<String, Content> contentMap = new HashMap<String, Content>();
        List<Content> contentList = contentDao.getContentListByTopicIds(tidList);
        if(null != contentList && contentList.size() > 0){
            for(Content c : contentList){
                contentMap.put(c.getForwardCid().toString(), c);
            }
        }
        //一次性获取当前用户针对于各王国是否收藏过
        Map<String, LiveFavorite> liveFavoriteMap = new HashMap<String, LiveFavorite>();
        List<LiveFavorite> liveFavoriteList = topicDao.getLiveFavoritesByUidAndTopicIds(uid, tidList);
        if(null != liveFavoriteList && liveFavoriteList.size() > 0){
            for(LiveFavorite lf : liveFavoriteList){
                liveFavoriteMap.put(lf.getTopicId().toString(), lf);
            }
        }
        //一次性查出所有王国分类
        Map<String, TopicCategory> categoryMap = new HashMap<String, TopicCategory>();
        List<TopicCategory> cgList = topicDao.getAllTopicCategory();
        if(null != cgList && cgList.size() > 0){
        	for(TopicCategory tc : cgList){
        		categoryMap.put(tc.getId().toString(), tc);
        	}
        }

        UserProfile userProfile = null;
        UserProfile lastUserProfile = null;
        TopicFragmentWithBLOBs lastFragment = null;
        TopicCategory topicCategory = null;
        Content content = null;
        MyTopicResponse.ShowTopicElement showTopicElement = null;
        for (ExtTopic topic : topicList) {
            showTopicElement = new MyTopicResponse.ShowTopicElement();
            showTopicElement.setUid(topic.getUid());
            showTopicElement.setCoverImage(Constant.QINIU_DOMAIN + "/" + topic.getLiveImage());
            showTopicElement.setTitle(topic.getTitle());
            userProfile = profileMap.get(String.valueOf(topic.getUid()));
            if(null != userProfile){
            	showTopicElement.setAvatar(baseService.genAvatar(userProfile.getAvatar()));
                showTopicElement.setNickName(userProfile.getNickName());
                if(!StringUtils.isEmpty(userProfile.getAvatarFrame())){
                	showTopicElement.setAvatarFrame(Constant.QINIU_DOMAIN + "/" + userProfile.getAvatarFrame());
                }
                showTopicElement.setV_lv(userProfile.getvLv());
                showTopicElement.setLevel(userProfile.getLevel());
            }
            showTopicElement.setCreateTime(topic.getCreateTime());
            showTopicElement.setTopicId(topic.getId());
            showTopicElement.setStatus(topic.getStatus());
            showTopicElement.setPrice(topic.getPrice()); //2.2.7 王国估值
            //取这个排序
            showTopicElement.setUpdateTime(topic.getLongtimes());
            showTopicElement.setLastUpdateTime(topic.getLongTime());
            
            int internalStatust = baseService.getInternalStatus(topic, uid);
            if(internalStatust==Specification.SnsCircle.OUT.index){
            	if( liveFavoriteMap.get(String.valueOf(topic.getId()))!=null){
            		internalStatust=Specification.SnsCircle.IN.index;
            	}
            }
            showTopicElement.setInternalStatus(internalStatust);
            showTopicElement.setContentType(topic.getType());
            showTopicElement.setIsTop(topic.getIsTop());
            lastFragment = lastFragmentMap.get(topic.getId().toString());
            if (null != lastFragment) {
                showTopicElement.setLastContentType(lastFragment.getContentType());
                showTopicElement.setLastFragment(lastFragment.getFragment());
                if(!StringUtils.isEmpty(lastFragment.getFragmentImage())){
                	showTopicElement.setLastFragmentImage(Constant.QINIU_DOMAIN + "/" + lastFragment.getFragmentImage());
                }
                showTopicElement.setLastType(lastFragment.getType());
                showTopicElement.setLastStatus(lastFragment.getStatus());
                showTopicElement.setLastExtra(lastFragment.getExtra());
                showTopicElement.setLastAtUid(lastFragment.getAtUid());
                showTopicElement.setLastUid(lastFragment.getUid());
                if(null != lastFragment.getCreateTime()){
                    showTopicElement.setLastUpdateTime(lastFragment.getCreateTime().getTime());
                }
                lastUserProfile = profileMap.get(lastFragment.getUid().toString());
                if(null != lastUserProfile){
                    showTopicElement.setLastNickName(lastUserProfile.getNickName());
                    showTopicElement.setLastAvatar(baseService.genAvatar(lastUserProfile.getAvatar()));
                    showTopicElement.setLastV_lv(lastUserProfile.getvLv());
                    showTopicElement.setLastLevel(lastUserProfile.getLevel());
                    if(!StringUtils.isEmpty(lastUserProfile.getAvatarFrame())){
                    	showTopicElement.setLastAvatarFrame(Constant.QINIU_DOMAIN + "/" + lastUserProfile.getAvatarFrame());
                    }
                }
            } else {
                showTopicElement.setLastContentType(-1);
            }
            //判断是否需要有红点
            MySubscribeCacheModel cacheModel = new MySubscribeCacheModel(uid, topic.getId() + "", "0");
            String isUpdate = redisService.hGet(cacheModel.getKey(), topic.getId() + "");
            if(StringUtils.isEmpty(isUpdate) || Integer.parseInt(isUpdate) == 0){
                showTopicElement.setIsUpdate(0);
            }else {
                showTopicElement.setIsUpdate(1);
            }
            content = contentMap.get(String.valueOf(topic.getId()));
            if (content != null) {
                showTopicElement.setLikeCount(content.getLikeCount());
                showTopicElement.setPersonCount(content.getPersonCount());
                showTopicElement.setCid(content.getId());
                showTopicElement.setReadCount(content.getReadCountDummy());
            }
            //判断是否收藏了
            if (null != liveFavoriteMap.get(String.valueOf(topic.getId()))) {
                showTopicElement.setFavorite(Specification.LiveFavorite.FAVORITE.index);
            } else {
                showTopicElement.setFavorite(Specification.LiveFavorite.NORMAL.index);
            }
            topicCategory = categoryMap.get(topic.getCategoryId().toString());
            if(null != topicCategory){
            	showTopicElement.setKcName(topicCategory.getName());
            }
            result.getShowTopicElements().add(showTopicElement);
        }
    }
    
    public Response<TopicUpdateResponse> getUpdate(TopicUpdateRequest request){
    	log.info("get live update start...");
        int totalRecords = 0;
        int updateRecords = 0;
        long lastFragmentId = 0;
        long firstDelCount = 0;

        if(request.getSinceId()>0){
        	//newestId,totalCount
        	String value = redisService.hGet(TOPIC_FRAGMENT_NEWEST_MAP_KEY, "T_" + request.getTopicId());
        	long newestFragmentId = 0;
        	int cacheTotalCount = 0;
        	if(null != value && !"".equals(value)){
        		String[] tmp = value.split(",");
        		if(tmp.length == 2){
        			newestFragmentId = Long.valueOf(tmp[0]);
        			cacheTotalCount = Integer.valueOf(tmp[1]);
        		}
        	}
        	if(newestFragmentId == 0 || newestFragmentId > request.getSinceId()){//没有缓存，或缓存里的数据比传递过来的新，则重新拉取
        		ExtFragmentUpdateCount count = extTopicFragmentMapper.countFragmentByTopicIdWithSince(request.getTopicId(), request.getSinceId());
        		if(null != count){
        			totalRecords = (int)count.getTotalRecords();
        			updateRecords = (int)count.getUpdateRecords();
        			lastFragmentId = count.getLastFragmentId();
        		}
        	}else{
        		totalRecords = cacheTotalCount;
        		updateRecords = 0;//没有更新，则更新数为0
        		lastFragmentId = newestFragmentId;
        	}
        }else {
        	ExtFragmentUpdateCount count = extTopicFragmentMapper.countFragmentByTopicIdWithSince(request.getTopicId(), request.getSinceId());
        	if(null != count){
        		totalRecords = (int)count.getTotalRecords();
    			updateRecords = (int)count.getUpdateRecords();
    			lastFragmentId = count.getLastFragmentId();
    			firstDelCount = count.getFirstDelCount();
        	}
        }
        
        TopicUpdateResponse liveUpdateDto = new TopicUpdateResponse();
        liveUpdateDto.setLastFragmentId(lastFragmentId);
        liveUpdateDto.setTotalRecords(totalRecords);
        int offset = request.getOffset();
        int totalPages =totalRecords%offset==0?totalRecords/offset:totalRecords/offset+1;
        liveUpdateDto.setTotalPages(totalPages);
        liveUpdateDto.setUpdateRecords(updateRecords);

        int nums = totalRecords-updateRecords;
        int startPageNo = nums/offset+1;
        liveUpdateDto.setStartPageNo(startPageNo);

        if(request.getSinceId() == 0){//只有sinceId==0才有效
        	int firstCount = (int)firstDelCount+1;
        	int firstPage = firstCount%offset==0?firstCount/offset:firstCount/offset+1;
        	liveUpdateDto.setFirstPage(firstPage);
        }
        //根据cid以及uid判断是否全禁言以及单禁言
        //是否全禁
        TopicUserForbid topicUserForbid1 = topicDao.findTopicForbidStatus(request.getTopicId());
        if(topicUserForbid1!=null){
        	liveUpdateDto.setIsAllForbid(1);
        }else{
        	liveUpdateDto.setIsAllForbid(0);
        }
        //是否单禁当前用户
        TopicUserForbid topicUserForbid2 = topicDao.findTopicUserForbidByTopicIdAndUid(request.getTopicId(),request.getUid());
        if(topicUserForbid2!=null){
        	liveUpdateDto.setIsForbid(1);
        }else{
        	liveUpdateDto.setIsForbid(0);
        }
        log.info("get live update start ...");
        return Response.success(ResponseStatus.GET_LIVE_UPDATE_SUCCESS.status, ResponseStatus.GET_LIVE_UPDATE_SUCCESS.message, liveUpdateDto);
    }
    
    public Response<DetailFidPageResponse> detailFidPage(DetailFidPageRequest request){
    	DetailFidPageResponse result = new DetailFidPageResponse();
    	
    	int count = topicDao.countTopicFragmentBeforeFid(request.getTopicId(), request.getFid());
    	int offset = request.getOffset();
    	result.setPage(count%offset==0?count/offset:count/offset+1);
    	
    	return Response.success(result);
    }
    
    public Response<DetailPageStatusResponse> detailPageStatus(DetailPageStatusRequest request){
    	DetailPageStatusResponse result = new DetailPageStatusResponse();
    	result.setPage(request.getPageNo());

    	int startIndex = (request.getPageNo() - 1) * request.getOffset();
    	List<TopicFragmentWithBLOBs> fragmentList = extTopicMapper.getTopicFragmentForPage(request.getTopicId(), startIndex, request.getOffset(), 0);
    	if(null != fragmentList && fragmentList.size() > 0){
			long pageUpdateTime = 0;
			int count = 0;
			for (TopicFragmentWithBLOBs topicFragment : fragmentList) {
				if(topicFragment.getStatus().intValue() == 0){
					if(null != topicFragment.getUpdateTime() && topicFragment.getUpdateTime().getTime() > pageUpdateTime){
						pageUpdateTime = topicFragment.getUpdateTime().getTime();
					}
					continue;
				}
				count++;
			}
			result.setRecords(count);
			result.setIsFull(fragmentList.size() >= request.getOffset()?1:2);
			result.setUpdateTime(pageUpdateTime);
    	}else{
    		result.setRecords(0);
    		result.setIsFull(2);
    		result.setUpdateTime(0);
    	}
    	
    	return Response.success(result);
    }

    private boolean isLetterOrDigit(String str) {
		String regex = "^[a-z0-9A-Z]+$";
		return str.matches(regex);
	}
    
    private boolean isSymbol(String str){
    	String regex = "^[,.?'\"\\[\\]+-\\\\(\\\\)!@#$%^&*:;<>]+$";
		return str.matches(regex);
    }
    
    private boolean checkKingdomNameValid(String kingdomName){
    	boolean valid = true;
    	if(kingdomName.length() > 30){
			int titleLength = 0;
			String s = null;
			for(int i=0; i<kingdomName.length();i++){
				s = kingdomName.substring(i,i+1);
				if(this.isLetterOrDigit(s) || this.isSymbol(s)){
					titleLength = titleLength + 1;
				}else{
					titleLength = titleLength + 2;
				}
			}
			if(titleLength > 60){
				valid = false;
			}
		}
    	return valid;
    }

	public Response<CreateKingdomResponse> createKingdom(CreateKingdomRequest request) {
		CreateKingdomResponse createKingdomResponse = new CreateKingdomResponse();
		log.info("createKingdom start...");
		if(StringUtils.isEmpty(request.getLiveImage()) || StringUtils.isEmpty(request.getTitle())){
        	log.info("liveImage or title is empty");
        	return Response.failure(ResponseStatus.KINGDOM_CREATE_FAILURE.status, ResponseStatus.KINGDOM_CREATE_FAILURE.message);
        }
		
		//判断王国名数字是否超过限制
		boolean valid = this.checkKingdomNameValid(request.getTitle());
		if(!valid){
			return Response.failure(ResponseStatus.KINGDM_NAME_OVER_LIMIT.status, ResponseStatus.KINGDM_NAME_OVER_LIMIT.message);
		}
		//特殊用户创建王国无需耗费米汤币
        String freeUser = appConfigService.getAppConfigByKey("FREE_CREATEKINGDOM_USER");
        int needPrice = StringUtils.isEmpty(appConfigService.getAppConfigByKey("CREATE_KINGDOM_PRICE")) ? 168
				: Integer.parseInt(appConfigService.getAppConfigByKey("CREATE_KINGDOM_PRICE"));
        //1免费创建  0需要扣费
        boolean isFree = false;
        if(!StringUtils.isEmpty(freeUser)){
        	String freeUserIds[] = freeUser.split(",");
        	for (String freeUserId : freeUserIds) {
        		if(freeUserId.equals(request.getUid()+"")){
        			isFree = true;
        			break;
        		}
			}
        }
        if(!isFree){
        	//非特殊用户，判断是否设置仅好友可见
        	if(request.getOnlyFriend()==Specification.OnlyFriendStatus.ONLY_FRIEND.index){
        		isFree = true;
	        }
        }
        
        
        UserProfile userProfile = userDao.getUserProfileByUid(request.getUid());
        String userCreateKingdomCountKey = "USER_CREATEKINGDOM_COUNT@"+request.getUid();
        String userCreateKingdomCountStr= redisService.get(userCreateKingdomCountKey);
        if(!isFree){
            if(!StringUtils.isEmpty(userCreateKingdomCountStr)){
            	int userCreateKingdomCount  = Integer.parseInt(userCreateKingdomCountStr);
            	if(userCreateKingdomCount>=1){
                	int price = userProfile.getAvailableCoin();
                	if(price<needPrice){
                		return Response.failure(ResponseStatus.CREATE_KINGDOM_PRICE_LACK.status, ResponseStatus.CREATE_KINGDOM_PRICE_LACK.message.replace("#{price}#", String.valueOf(needPrice)));
                	}
            	}
            }
        }
		log.info("create cover..");
		Date now = new Date();
		Topic topic = new Topic();
		topic = TopicCopier.INSTANCE.asTopic(request);
        topic.setLongTime(now.getTime());
        topic.setCreateTime(now);
        topic.setUpdateTime(now);
        JSONArray array = new JSONArray();
        array.add(request.getUid());
        topic.setCoreCircle(array.toString());
        //聚合版本新加属性
        int kingdomType = Specification.KingdomType.NORMAL.index;
        if(request.getKType() == Specification.KingdomType.AGGREGATION.index){
        	kingdomType = Specification.KingdomType.AGGREGATION.index;
        	// 判断聚合王国是否上限
        	ExtPermissionDescription permissionDescriptionDto= userService.getUserPermission(request.getUid());
            List<ExtPermissionDescription.PermissionNodeDto> perList = permissionDescriptionDto.getNodes();
            int limitCount = 0;
            for(ExtPermissionDescription.PermissionNodeDto p : perList){
                if (p.getCode() == 7){
                    if (p.getNum() == null){
                        limitCount = 0;
                        break;
                    }else {
                    limitCount = p.getNum();
                    break;
                    }
                }
            }
            List<Topic> topics = extTopicMapper.getConvergeTopic(request.getUid());
            int hasCount = topics.size();
            if(hasCount >= limitCount){
                return Response.failure(500,"你当前的等级已经达到了创建聚合王国的上限。");
            }
        }
        topic.setType(kingdomType);
        //初始化王国价值，默认估值:米汤币为15,随机增减0-8
        int price = 15;
        Random random = new Random();
        int incr = random.nextInt(9);
        int flag = random.nextInt(2);
        if(flag == 0){
        	price = price - incr;
        }else{
        	price = price + incr;
        }
        
        //查询新建王国可以被偷的配置值
        int newStealPrice = 0;
        String newKingdomStealPrice = appConfigService.getAppConfigByKey("NEW_KINGDOM_STEAL_PRICE");
        if(!StringUtils.isEmpty(newKingdomStealPrice)){
        	newStealPrice = Integer.valueOf(newKingdomStealPrice).intValue();
        }
        
        topic.setPrice(price + newStealPrice);
        if(request.getKcid() > 0){
        	topic.setCategoryId(request.getKcid());
        }else{
        	if(kingdomType == 0){
        		topic.setCategoryId(1);//默认为记录
        	}
        }
        extTopicMapper.saveTopic(topic);

        //创建直播之后添加到我的UGC
        ExtContentDto extContentDto = new ExtContentDto();
        extContentDto = TopicCopier.INSTANCE.asExtContentDto(request);
        extContentDto.setType(Specification.ArticleType.LIVE.index);
        extContentDto.setForwardCid(topic.getId());
        extContentDto.setRights(Specification.ContentRights.EVERY.index);
        
        Content content = new Content();
        content = TopicCopier.INSTANCE.asContent(extContentDto);
        if(!StringUtils.isEmpty(extContentDto.getImageUrls())){
            String[] images = extContentDto.getImageUrls().split(";");
            // 设置封面
            content.setConverImage(images[0]);
        }
        content.setUpdateId(redisService.incr("UPDATE_ID"));
        extContentMapper.saveContent(content);
        
        if(!StringUtils.isEmpty(extContentDto.getImageUrls())){
            String[] images = extContentDto.getImageUrls().split(";");
            List<ContentImage> contentImages = new ArrayList<ContentImage>();
            // 保存用户图片集合
            for(String image : images){
                ContentImage contentImage = new ContentImage();
                contentImage.setCid(content.getId());
                if(image.equals(images[0])) {
                    contentImage.setCover(1);
                }else{
                	contentImage.setCover(0);
                }
                contentImage.setImage(image);
                contentImages.add(contentImage);
            }
            extContentMapper.saveContentImages(contentImages);
        }
        CacheLiveEvent cacheLiveEvent = new CacheLiveEvent();
        cacheLiveEvent.setUid(request.getUid());
        cacheLiveEvent.setTopicId(topic.getId());
        applicationEventBus.post(cacheLiveEvent);

        //创建王国之后创建相应的价值数据表（主要是可以创建后即可有一定的被偷值）
        TopicData topicData = new TopicData();
        topicData.setStealPrice(newStealPrice);
        topicData.setTopicId(topic.getId());
        topicData.setUpdateTime(now);
        topicDao.saveTopicData(topicData);
        
        //将封面插入王国图库
        TopicImage topicImage = new TopicImage();
        topicImage.setExtra("");
        topicImage.setImage(request.getLiveImage());
        topicImage.setTopicId(topic.getId());
        topicDao.saveTopicImage(topicImage);
        // 找到机器TAG
        Set<String> autoTagSet = new HashSet<>();
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(request.getAutoTags())){
        	for(String t:request.getAutoTags().split(";")){
        		autoTagSet.add(t.trim());
        	}
        }
        //add kingdom tags -- begin --
        if(!StringUtils.isEmpty(request.getTags())){
        	String[] tagsArr = request.getTags().split(";");
        	List<String> needCheckTags = new ArrayList<String>();
        	List<String> allTags = new ArrayList<String>();;
        	for(String tag : tagsArr){
        		needCheckTags.add(tag);
        		allTags.add(tag);
        	}
        	if(null != needCheckTags && needCheckTags.size() > 0){
        		List<Long> tags = new ArrayList<Long>();
        		List<String> existTopicTag = topicTagDao.getExistTopicTagByTags(needCheckTags);
        		needCheckTags.removeAll(existTopicTag);
        		List<TopicTag> needAddTopicTags = new ArrayList<TopicTag>();
        		if(needCheckTags.size()>0){
        			for(String needAddTag : needCheckTags){
        				TopicTag needAddTopicTag = new TopicTag();
        				needAddTopicTag.setTag(needAddTag);
        				needAddTopicTags.add(needAddTopicTag);
        			}
        			topicTagDao.saveNewTopicTag(needAddTopicTags);
        		}
        		List<TopicTag> topicTags =  topicTagDao.getTopicTagsByTags(allTags);
        		List<TopicTagDetail> topicTagDetails = new ArrayList<TopicTagDetail>();
        		for(TopicTag TopicTag : topicTags){
        			tags.add(TopicTag.getId());
        			TopicTagDetail TopicTagDetail = new TopicTagDetail();
        			TopicTagDetail.setTag(TopicTag.getTag());
        			TopicTagDetail.setTagId(TopicTag.getId());
        			TopicTagDetail.setTopicId(topic.getId());
        			TopicTagDetail.setUid(request.getUid());
        			if(autoTagSet.contains(TopicTag.getTag())){
        				TopicTagDetail.setAutoTag(1);
        			}else{
        				TopicTagDetail.setAutoTag(0);
        			}
        			topicTagDetails.add(TopicTagDetail);
        		}
        		topicTagDao.saveTopicTagDetail(topicTagDetails);

        		if(tags!=null && tags.size()>0){
        			extUserDislikeMapper.deleteExistUserLikeTags(request.getUid(),tags);
        			extUserDislikeMapper.batchInsertUserLikeTags(request.getUid(),tags);
        		}
        	}
        }
        //add kingdom tags -- end --

        log.info("createKingdom end");
        createKingdomResponse.setTopicId(topic.getId());
        createKingdomResponse.setV_lv(userProfile.getvLv());
        createKingdomResponse.setCurrentLevel(userProfile.getLevel());
        createKingdomResponse.setUpgrade(0);
        // 记录操作日志
        userService.addUserOprationLog(topic.getUid(), Specification.UserOperateType.CREATE_KINGDOM.action, topic.getId());
        
        //扣除创建王国168米汤币
        if(StringUtils.isEmpty(userCreateKingdomCountStr)){
        	//第一次创建王国免费
        	redisService.set(userCreateKingdomCountKey, "1");
        }else{
        	int userCreateKingdomCount  = Integer.parseInt(userCreateKingdomCountStr);
        	if(userCreateKingdomCount>=1 && !isFree){
        		userService.updateUserCoins(request.getUid(), 0-needPrice);
        	}
        	redisService.incr(userCreateKingdomCountKey);
        }
        return Response.success(ResponseStatus.USER_CREATE_LIVE_SUCCESS.status, ResponseStatus.USER_CREATE_LIVE_SUCCESS.message, createKingdomResponse);
	}

	public Response<SettingsResponse> settings(SettingsRequest request) {
		SettingsResponse settingsResponse = new SettingsResponse();
        Topic topic = topicDao.getTopicById(request.getTopicId());
        if(null == topic){
			return Response.failure(ResponseStatus.LIVE_HAS_DELETED.status,ResponseStatus.LIVE_HAS_DELETED.message);
		}
        settingsResponse = TopicCopier.INSTANCE.asSettingsResponse(topic);
        settingsResponse.setCoverImage(baseService.genQiNiuImageUrl(topic.getLiveImage()));
        Content content = contentDao.getContentByTopicId(topic.getId());
        if(content != null) {
            settingsResponse.setReadCount(content.getReadCountDummy());
        }
        Long favoriteCount = Long.valueOf(0);
        List<Long> topicIdList = new ArrayList<Long>();
        topicIdList.add(Long.valueOf(request.getTopicId()));
        Map<String, Long> memberMap = topicDao.getTopicMembersCount(topicIdList);
        if(null != memberMap && memberMap.size() > 0){
        	if(null != memberMap.get(String.valueOf(request.getTopicId()))){
        		favoriteCount = memberMap.get(String.valueOf(request.getTopicId()));
        	}
        }
        if(favoriteCount.longValue() > 0){
        	settingsResponse.setFavoriteCount(favoriteCount.intValue() + 1);
        }else{
        	settingsResponse.setFavoriteCount(1);
        }
        ExtTopicCount topicCountDTO = extTopicMapper.getTopicCount(request.getTopicId());
        settingsResponse.setTopicCount(topicCountDTO.getUpdateCount());
        settingsResponse.setCreateTime(topic.getCreateTime().getTime());
        if(topic.getType() == 1000){
            //查子王国
            int acCount = topicDao.countAggregationSubTopic(request.getTopicId());
            settingsResponse.setAcCount(acCount);
        }else {
            //查母王国
            int ceCount = topicDao.countAggregationParentTopic(request.getTopicId());
            settingsResponse.setCeCount(ceCount);
        }
        TopicUserConfig topicUserConfig = topicDao.getTopicUserConfigByTopicIdAndUid(request.getUid() ,request.getTopicId());
        if(topicUserConfig != null){
            settingsResponse.setPushType(topicUserConfig.getPushType());
        }
        //标签
        String tags = "";
        List<TopicTagDetail> topicTagDetails = topicTagDao.getTopicTagDetailsByTopicId(request.getTopicId());
        if(topicTagDetails != null && topicTagDetails.size() > 0){
            StringBuilder builder = new StringBuilder();
            for (TopicTagDetail detail : topicTagDetails){
                String tag = detail.getTag();
                if(tags.equals("")){
                    tags = builder.append(tag).toString();
                }else {
                    builder.append(";"+tag);
                }
            }
            settingsResponse.setTags(builder.toString());
        }
        if(topic.getCategoryId().intValue() > 0){
        	TopicCategory tc = topicDao.getTopicCategoryById(topic.getCategoryId().intValue());
        	if(null != tc){
        		settingsResponse.setKcid(tc.getId());
            	settingsResponse.setKcName(tc.getName());
            	settingsResponse.setKcImage(baseService.genQiNiuImageUrl(tc.getCoverImg()));
            	settingsResponse.setKcIcon(baseService.genQiNiuImageUrl(tc.getIcon()));
        	}
        }
        
        //查询是否是私密王国
        if(topic.getRights()==Specification.KingdomRights.PRIVATE_KINGDOM.index){
        	settingsResponse.setSecretType(1);
        }else{
        	settingsResponse.setSecretType(0);
        }
        
        //查询是否自动加入核心圈开关量
        if(topic.getAutoJoinCore()==0){
        	settingsResponse.setAutoCoreType(0);
        }else{
        	settingsResponse.setAutoCoreType(1);
        }
        
        log.info("get settings success");
        return Response.success(settingsResponse);
    
	}

	public Response<SettingModifyResponse> settingModify(SettingModifyRequest request) {
		Topic topic = topicDao.getTopicById(request.getTopicId());
		if(null == topic){
			return Response.failure(ResponseStatus.LIVE_HAS_DELETED.status,ResponseStatus.LIVE_HAS_DELETED.message);
		}
        //每个人都能操作
        if (request.getAction() == Specification.SettingModify.PUSH.index) {
            int pushType = Integer.valueOf(request.getParams()).intValue();
            TopicUserConfig topicUserConfig = topicDao.getTopicUserConfigByTopicIdAndUid(request.getUid(), request.getTopicId());
            if (topicUserConfig != null) {
                topicUserConfig.setPushType(pushType);
                topicDao.updateTopicUserConfig(topicUserConfig);
                log.info("update pushType success");
            } else {
                topicUserConfig = new TopicUserConfig();
                topicUserConfig.setUid(request.getUid());
                topicUserConfig.setTopicId(request.getTopicId());
                topicUserConfig.setPushType(pushType);
                topicDao.createTopicUserConfig(topicUserConfig);
                log.info("update pushType success");
            }
            return Response.success();
        }
        
		//只有国王和管理员才能操作
		if(request.getAction() == Specification.SettingModify.KINGDOM_CATEGORY.index){
			if(topic.getUid().longValue() == request.getUid() || baseService.isAdmin(request.getUid())){
				topic.setCategoryId(Integer.valueOf(request.getParams()));
				topicDao.updateTopic(topic);
	        	log.info("update kingdom category success");
	            return Response.success();
			}else{
				return Response.failure(ResponseStatus.YOU_ARE_NOT_KING_OR_ADMIN.status, ResponseStatus.YOU_ARE_NOT_KING_OR_ADMIN.message);
			}
        }
		
		// 只有国王可以操作
		if (topic.getUid() == request.getUid()) {
			if (request.getAction() == Specification.SettingModify.COVER.index) {
				topic.setLiveImage(request.getParams());
				topicDao.updateTopic(topic);
				extContentMapper.updateTopicContentCover(topic.getId(), request.getParams());
				
				//换完需要将王国图库里的封面也换掉
				TopicImage topicImage = topicDao.getTopicImageByTopicId(topic.getId());
				if(null != topicImage){
					topicImage.setImage(request.getParams());
					topicDao.updateTopicImage(topicImage);
				}else{
					topicImage = new TopicImage();
					topicImage.setCreateTime(topic.getCreateTime());
					topicImage.setExtra("");
					topicImage.setImage(request.getParams());
					topicImage.setTopicId(topic.getId());
					topicDao.saveTopicImage(topicImage);
				}
				
				log.info("update cover success");
				SettingModifyResponse settingModifyResponse = new SettingModifyResponse();
				settingModifyResponse.setCoverImage(baseService.genQiNiuImageUrl(request.getParams()));
				return Response.success(settingModifyResponse);
			} else if (request.getAction() == Specification.SettingModify.SUMMARY.index) {
				topic.setSummary(request.getParams());
				topicDao.updateTopic(topic);
				log.info("update Summary success");

				// 更新成功需要在当前王国中插入一条国王发言
				if (!StringUtils.isEmpty(request.getParams())) {
					TopicFragmentWithBLOBs topicFragment = new TopicFragmentWithBLOBs();
					topicFragment.setFragment("王国简介修改:" + request.getParams());
					topicFragment.setUid(request.getUid());
					topicFragment.setContentType(0);// 文本
					topicFragment.setTopicId(topic.getId());
					topicDao.createTopicFragment(topicFragment);
					
					//王国修改简介，肯定是国王操作，这里需要更新更新时间
					Calendar calendar = Calendar.getInstance();
					topic.setUpdateTime(calendar.getTime());
					topic.setLongTime(calendar.getTimeInMillis());
					topicDao.updateTopic(topic);
					
					extContentMapper.updateContentUpdateTimeByTopicId(topic.getId(), calendar.getTime());
					extContentMapper.updateContentUpdateIdByTopicId(topic.getId(),redisService.incr("UPDATE_ID"));

					long lastFragmentId = topicFragment.getId();
					// 更新缓存
					int total = topicDao.countTotalFragmentByTopicId(topic.getId());
					String value = lastFragmentId + "," + total;
					redisService.hSet(TOPIC_FRAGMENT_NEWEST_MAP_KEY, "T_" + topic.getId(), value);

					SpeakNewEvent speakNewEvent = new SpeakNewEvent();
					speakNewEvent = TopicCopier.INSTANCE.asSpeakNewEvent(topicFragment);
                	speakNewEvent.setFragmentId(lastFragmentId);
                    applicationEventBus.post(speakNewEvent);
				}
				return Response.success();
			} else if (request.getAction() == Specification.SettingModify.TAGS.index) {
				log.info("暂时不考虑标签");
			}  else if (request.getAction() == Specification.SettingModify.AGVERIFY.index) {
				topic.setCeAuditType(Integer.valueOf(request.getParams()));
				topicDao.updateTopic(topic);
				log.info("update CeAuditType success");
				return Response.success();
			} else if (request.getAction() == Specification.SettingModify.VERIFY.index) {
				topic.setAcAuditType(Integer.valueOf(request.getParams()));
				topicDao.updateTopic(topic);
				log.info("update AcAuditType success");
				return Response.success();
			} else if (request.getAction() == Specification.SettingModify.ISSUED_MESSAGE.index) {
				// 下发消息
				topic.setAcPublishType(Integer.valueOf(request.getParams()));
				topicDao.updateTopic(topic);
				log.info("update AcPublishType success");
				return Response.success();
			}else if(request.getAction() == Specification.SettingModify.LIVE_NAME.index){
				//判断王国名数字是否超过限制
				boolean valid = this.checkKingdomNameValid(request.getParams());
				if(!valid){
					return Response.failure(ResponseStatus.KINGDM_NAME_OVER_LIMIT.status, ResponseStatus.KINGDM_NAME_OVER_LIMIT.message);
				}
                topic.setTitle(request.getParams());
                topicDao.updateTopic(topic);
                extContentMapper.updateTopicContentTitle(topic.getId(), request.getParams());
                log.info("update live success");
                return Response.success();
            }else if(request.getAction() == Specification.SettingModify.SECRET_OPT.index){
            	//私密王国设置
            	if(Integer.valueOf(request.getParams())==0){
            		topic.setRights(Specification.KingdomRights.PUBLIC_KINGDOM.index);
            	}else{
            		topic.setRights(Specification.KingdomRights.PRIVATE_KINGDOM.index);
            	}
            	topicDao.updateTopic(topic);
            	log.info("update topic rights");
            	return Response.success();
            }else if(request.getAction() == Specification.SettingModify.AUTO_JOIN_CORE_OPT.index){
            	//加入及自动加入核心圈设置
            	topic.setAutoJoinCore(Integer.valueOf(request.getParams()));
            	topicDao.updateTopic(topic);
            	log.info("update topic auto_join_core");
            	return Response.success();
            }else if(request.getAction() == Specification.SettingModify.ONLY_FRIEND.index){
            	//仅好友可见设置
            	topic.setOnlyFriend(Integer.valueOf(request.getParams()));
            	topicDao.updateTopic(topic);
            	log.info("update topic only_friend");
            	return Response.success();
            }
		} else {
			return Response.failure(ResponseStatus.YOU_ARE_NOT_KING.status, ResponseStatus.YOU_ARE_NOT_KING.message);
		}
		return Response.failure(ResponseStatus.ACTION_NOT_SUPPORT.status, ResponseStatus.ACTION_NOT_SUPPORT.message);
	
	}

	public Response<GetLiveByCidResponse> getLiveByCid(GetLiveByCidRequest request) {
		GetLiveByCidResponse getLiveByCidResponse = new GetLiveByCidResponse();
        Topic topic = topicDao.getTopicById(request.getCid());
        if(topic==null){
        	return Response.failure(ResponseStatus.LIVE_HAS_DELETED.status,ResponseStatus.LIVE_HAS_DELETED.message);
        }
        UserProfile userProfile = userDao.getUserProfileByUid(topic.getUid());
        Content content = contentDao.getContentByTopicId(request.getCid());
        getLiveByCidResponse = TopicCopier.INSTANCE.asGetLiveByCidResponse(topic,userProfile,content);
        
        //如果是私密王国则只有国王和核心圈成员可以进入 
        if(topic.getRights()==Specification.KingdomRights.PRIVATE_KINGDOM.index ){
        	if(!baseService.isKing(request.getUid(), topic.getUid()) && !baseService.isInCore(request.getUid(), topic.getCoreCircle())){
				return Response.failure(ResponseStatus.LIVE_HAS_DELETED.status,"此王国需要经过国王邀请才允许进入");
			}
        }
        
        //私密属性
        if(topic.getRights()==Specification.KingdomRights.PRIVATE_KINGDOM.index){
        	getLiveByCidResponse.setRights(Specification.KingdomRights.PRIVATE_KINGDOM.index);
        }else{
        	getLiveByCidResponse.setRights(Specification.KingdomRights.PUBLIC_KINGDOM.index);
        }
        
        //根据cid以及uid判断是否全禁言以及单禁言
        TopicUserForbid topicForbid = topicDao.findTopicForbidStatus(request.getCid());
        TopicUserForbid topicUserForbid = topicDao.findTopicUserForbidByTopicIdAndUid(request.getCid(), request.getUid());
        if(topicForbid!=null){
        	getLiveByCidResponse.setIsAllForbid(1);
        }else{
        	getLiveByCidResponse.setIsAllForbid(0);
        }
        if(topicUserForbid!=null){
        	getLiveByCidResponse.setIsForbid(1);
        }else{
        	getLiveByCidResponse.setIsForbid(0);
        }
        
        //判断王国是否设置了仅好友可见
        if(topic.getOnlyFriend()==Specification.OnlyFriendStatus.ALL.index){
        	getLiveByCidResponse.setOnlyFriend(Specification.OnlyFriendStatus.ALL.index);
        }else{
        	getLiveByCidResponse.setOnlyFriend(Specification.OnlyFriendStatus.ONLY_FRIEND.index);
        }
        
        boolean isCanView = true;
        if(request.getUid() != topic.getUid().longValue()){
        	//判断当前用户与国王是否是好友
            UserFriend userFriend = userDao.getUserFriendBySourceUidAndTargetUid(request.getUid(), topic.getUid());
            if(null != userFriend && userFriend.getStatus().intValue() == 0){
            	getLiveByCidResponse.setIsFriend(1);
            }else{
            	getLiveByCidResponse.setIsFriend(0);
            	if(topic.getOnlyFriend()==Specification.OnlyFriendStatus.ONLY_FRIEND.index){
            		//非好友，王国设置仅好友可见
            		List<Middleman> middlemans = extFriendMapper.getMiddlemanList(request.getUid(), topic.getUid(), 0, 1);
            		if(middlemans.size() > 0){
	            		Middleman middleman = new Middleman();
	            		middleman.setAvatar(baseService.genAvatar(middlemans.get(0).getAvatar()));
	            		middleman.setAvatarFrame(baseService.genQiNiuImageUrl(middlemans.get(0).getAvatarFrame()));
	            		middleman.setContent(middlemans.get(0).getContent());
	            		middleman.setNickName(middlemans.get(0).getNickName());
	            		middleman.setUid(middlemans.get(0).getUid());
	            		middleman.setV_lv(middlemans.get(0).getV_lv());
	            		getLiveByCidResponse.setMiddleman(middleman);
            		}
            		isCanView = false;
            	}
            }
            
			if (topic.getRights() == Specification.KingdomRights.PRIVATE_KINGDOM.index) {
				// 当前用户是否可见
				if (!baseService.isInCore(request.getUid(), topic.getCoreCircle())) {
					isCanView = false;
				}
			} 
        }
        if(isCanView){
        	getLiveByCidResponse.setCanView(Specification.CanViewStatus.CAN_VIEW.index);
        }else{
        	getLiveByCidResponse.setCanView(Specification.CanViewStatus.NOT_CAN_VIEW.index);
        }
        
        //判断王国是否设置了加入即自动加入核心圈
        if(topic.getAutoJoinCore()==0){
        	getLiveByCidResponse.setAutoCoreType(0);
        }else{
        	getLiveByCidResponse.setAutoCoreType(1);
        }
        
        
        String KINGDOM_VIEW_KEY = "USER_VIEW_KINGDOM_"+request.getUid()+"_"+topic.getId();
        String visited =  redisService.get(KINGDOM_VIEW_KEY);
        
        getLiveByCidResponse.setIsFirstView(visited==null?1:0);
        getLiveByCidResponse.setCoverImage(baseService.genQiNiuImageUrl(topic.getLiveImage()));
        getLiveByCidResponse.setAvatar(baseService.genAvatar(userProfile.getAvatar()));
        getLiveByCidResponse.setAvatarFrame(baseService.genQiNiuImageUrl(userProfile.getAvatarFrame()));
        
        Long favoriteCount = Long.valueOf(0);
        List<Long> topicIdList = new ArrayList<Long>();
        topicIdList.add(Long.valueOf(request.getCid()));
        Map<String, Long> memberMap = topicDao.getTopicMembersCount(topicIdList);
        if(null != memberMap && memberMap.size() > 0){
            if(null != memberMap.get(String.valueOf(request.getCid()))){
                favoriteCount = memberMap.get(String.valueOf(request.getCid()));
            }
        }
        if(favoriteCount.longValue() > 0){
            getLiveByCidResponse.setFavoriteCount(favoriteCount.intValue() + 1);
        }else{
            getLiveByCidResponse.setFavoriteCount(1);
        }
                
        LiveFavorite liveFavorite = topicDao.getLiveFavoriteByTopicIdAndUid(request.getUid(), topic.getId());
        if (liveFavorite != null) {
            getLiveByCidResponse.setFavorite(Specification.LiveFavorite.FAVORITE.index);
        } else {
            getLiveByCidResponse.setFavorite(Specification.LiveFavorite.NORMAL.index);
        }
        UserFollow userFollowed = userDao.getUserFollowBySourceUidAndTargetUid(topic.getUid(), request.getUid());
        int isFollowed = 0;
        if(userFollowed!=null){
        	isFollowed = 1;
        }
        getLiveByCidResponse.setIsFollowed(isFollowed);
        UserFollow userFollowMe = userDao.getUserFollowBySourceUidAndTargetUid(request.getUid(), topic.getUid());
        int isFollowMe = 0;
        if(userFollowMe!=null){
        	isFollowMe = 1;
        }
        getLiveByCidResponse.setIsFollowMe(isFollowMe);
        ExtTopicCount topicCount = extTopicMapper.getTopicCount(topic.getId());
        if(null != topicCount){
        	getLiveByCidResponse.setReviewCount((int)topicCount.getReviewCount());
        }
       
        getLiveByCidResponse.setIsLike(contentDao.isLike(content.getId(), request.getUid()));
        
        int internalStatus = baseService.getInternalStatus(topic, request.getUid());
        if(internalStatus==Specification.SnsCircle.OUT.index){
        	if(liveFavorite != null){
        		internalStatus=Specification.SnsCircle.IN.index;
        	}
        }
        
        getLiveByCidResponse.setInternalStatus(internalStatus);
       

        if(activityService.isTopicRec(request.getCid())){
            getLiveByCidResponse.setIsRec(1);
        }else{
            getLiveByCidResponse.setIsRec(0);
        }

        //标签
        List<Long> tagIdList = new ArrayList<Long>();
        String tags = "";
        List<TopicTagDetail> topicTagDetails = topicTagDao.getTopicTagDetailsByTopicId(request.getCid());
        if(topicTagDetails != null && topicTagDetails.size() > 0){
            StringBuilder builder = new StringBuilder();
            for (TopicTagDetail detail : topicTagDetails){
                tagIdList.add(detail.getTagId());
                String tag = detail.getTag();
                if(tags.equals("")){
                    tags = builder.append(tag).toString();
                }else {
                    builder.append(";"+tag);
                }
            }
            getLiveByCidResponse.setTags(builder.toString());
        }

        if(topic.getType() == Specification.KingdomType.NORMAL.index){//个人王国
            //被聚合次数
        	 int ceCount = topicDao.countAggregationParentTopic(request.getCid());
            getLiveByCidResponse.setCeCount(ceCount);
        }if(topic.getType() == Specification.KingdomType.AGGREGATION.index){//聚合王国
            //子王国数
        	int acCount = topicDao.countAggregationSubTopic(request.getCid());
            getLiveByCidResponse.setAcCount(acCount);
            
            //聚合王国顶部图片列表
            List<GetLiveByCidResponse.AcImageElement> acImageElements = topicDao.getAggregationImage(request.getCid());
            for(GetLiveByCidResponse.AcImageElement t : acImageElements){
            	t.setImageUrl(baseService.genQiNiuImageUrl(t.getImageUrl()));
            	getLiveByCidResponse.getAcImageList().add(t);
            }
        }else{
            //暂不支持
        }
        
        getLiveByCidResponse.setTopicRMB(baseService.exchangeKingdomPrice(topic.getPrice()));
        TopicData topicData = topicDao.getTopicDataByTopicId(request.getUid());
        int percentage = 0;
        if(topicData==null){
            getLiveByCidResponse.setTopicPriceChanged(0);
        }else{
            getLiveByCidResponse.setTopicPriceChanged(topicData.getLastPriceIncr());
            percentage = new BigDecimal((topicDao.getLessPriceChangeTopicCount(topicData.getLastPriceIncr()) * 100.0 / topicDao.getTopicDataCount() ))
                    .setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
        }
        getLiveByCidResponse.setBeatTopicPercentage(percentage);
        getLiveByCidResponse.setIsSteal(1);
        try {
            getAliveCoinsForSteal(request.getUid(),request.getCid(), topic);
        } catch (KingdomStealException e2) {
            if(e2.getErrorCode()==KingdomStealException.KINGDOM_STEALED){
                getLiveByCidResponse.setIsSteal(2);
            }
        } catch(Exception e){
        	
        }
        int lotteryCount  =topicDao.countLotteryByTopicId(topic.getId());
        if(lotteryCount>0){
        	getLiveByCidResponse.setIsLottery(1);
        }else{
        	getLiveByCidResponse.setIsLottery(0);
        }
        if(topic.getCategoryId().intValue() > 0){
        	TopicCategory tc = topicDao.getTopicCategoryById(topic.getCategoryId().intValue());
        	if(null != tc){
        		getLiveByCidResponse.setKcid(tc.getId());
        		getLiveByCidResponse.setKcName(tc.getName());
            	getLiveByCidResponse.setKcImage(baseService.genQiNiuImageUrl(tc.getCoverImg()));
            	getLiveByCidResponse.setKcIcon(baseService.genQiNiuImageUrl(tc.getIcon()));
        	}
        }
        return Response.success(getLiveByCidResponse);
	}
	
	public Response<BaseResponse> addAppDownloadLog(AddAppDownloadLogRequest request){
		AppDownloadLog adl = new AppDownloadLog();
		adl.setUid(request.getRequestUid());
		adl.setFromuid(request.getFromUid());
		adl.setCreateTime(new Date());
		appDownloadLogMapper.insertSelective(adl);
		
		return Response.success();
	}
	
	public Response<GetTopicVoteInfoResponse> getTopicVoteInfo(GetTopicVoteInfoRequest request){
		VoteInfo voteInfo =voteInfoMapper.selectByPrimaryKey(request.getVoteId());
        if(voteInfo==null){
            return Response.failure(500,"没有找到该投票！");
        }
        GetTopicVoteInfoResponse result = TopicItemCopier.INSTANCE.ext2GetTopicVoteInfoResponse(voteInfo);
        List<ExtVoteOptionCount> optionList = extVoteMapper.getVoteOptions(request.getVoteId());
        result.setOptions(TopicItemCopier.INSTANCE.ext2OptionElementList(optionList));
        return Response.success(result);
	}
	
	public Response<TeaseListQueryResponse> teaseListQuery(BaseRequest request){
		TeaseListQueryResponse result = new TeaseListQueryResponse();
		
		List<TeaseInfo> teaseInfoList = topicDao.getAllTeaseInfo();
		for(TeaseInfo info : teaseInfoList){
			info.setAudio(baseService.genQiNiuImageUrl(info.getAudio()));
			info.setImage(baseService.genQiNiuImageUrl(info.getImage()));
			result.getTeaseData().add(TopicItemCopier.INSTANCE.ext2TeaseElement(info));
		}
		
		return Response.success(result);
	}
	public Response<ImageInfoResponse> imageInfo(long uid, long topicId, long fid, String imageName){
		ImageInfoResponse response = new ImageInfoResponse();
		List<TopicImage> topicImageList = topicDao.getTopicImageByTopicIdAndFidAndImageName(topicId, fid, imageName, 0);//图片视屏的都要
		if(null != topicImageList && topicImageList.size() > 0){
			TopicImage topicImage = topicImageList.get(0);
			response.setLikeCount(topicImage.getLikeCount());
			TopicFragmentLikeHis his = topicDao.getTopicFragmentLikeHisByUidAndImageId(uid, topicImage.getId());
			if(null != his){
				response.setIsLike(1);
			}
		}
		return Response.success(response);
	}
	public Response<GetGiftInfoListResponse> getAllGiftInfoList() {
		GetGiftInfoListResponse response = new GetGiftInfoListResponse();
		List<GiftInfo> list =  contentDao.getGiftInfoList();
		for (GiftInfo giftInfo : list) {
			GetGiftInfoListResponse.GiftInfoElement e = GetGiftInfoListResponse.createGiftInfoElement();
			e = ContentCopier.INSTANCE.asGiftInfoElement4GiftInfo(giftInfo);
			e.setImage(Constant.QINIU_DOMAIN + "/" + giftInfo.getImage());
			e.setGifImage(Constant.QINIU_DOMAIN + "/" + giftInfo.getGifImage());
			response.getResult().add(e);
		}
		return Response.success(response);
	}
    public Response<StealResultResponse> stealKingdomCoin(long uid,long topicId) {
    	Topic topic = topicDao.getTopicById(topicId);
    	if(topic==null){
    		return Response.failure(50037, "来晚一步！这个王国已经被删除了......");
    	}
    	//判断该用户是否被单禁言
    	TopicUserForbid topicUserForbid2 = topicDao.findTopicUserForbidByTopicIdAndUid(topicId,uid);
    	if(topicUserForbid2!=null){
    		return Response.failure(50072, "你已被此王国禁言，无法偷取");
    	}
		final int NEW_USER_ZHONGJIANG_COUNT=3;		// 新用户3次必中。
		UserProfile profile= userDao.getUserProfileByUid(uid);
		try {
			int coins=0;
			int isBigRedPack =0;
			try{
				coins = getAliveCoinsForSteal(uid, topicId, topic);
				if(coins>0){ // 大红包逻辑
					List<UserStealLog> list = userDao.getUserStealLogByCountAsc(uid, NEW_USER_ZHONGJIANG_COUNT);
					if(list.size()<NEW_USER_ZHONGJIANG_COUNT){	// 新手3次必中
						boolean has = false;
						for(UserStealLog data:list){
							if(data.getIsBigRedPack()!=null && data.getIsBigRedPack()==1){
								has=true;
								break;
							}
						}
						if(!has){	// 3次以内没中，随机开奖。
							int r = RandomUtils.nextInt(list.size(), NEW_USER_ZHONGJIANG_COUNT);
							if(r==list.size()){
								isBigRedPack=1;
							}
						}
					}else{
						int ratio = appConfigService.getIntegerAppConfigByKey("BIG_RED_PACK_RATIO");
						Set<Integer> jpSet = new HashSet<>();
						while(jpSet.size()<ratio){		// // 设定ratio个随机奖牌
							int r = RandomUtils.nextInt(1, 101);
							if(!jpSet.contains(r)){
								jpSet.add(r);
							}
						}
						
						int r = RandomUtils.nextInt(1, 101);// 翻牌子
						if(jpSet.contains(r)){		// 恭喜你，中奖了。
							isBigRedPack=1;
						}
					}
					if(isBigRedPack==1){ //中奖了
						coins= RandomUtils.nextInt(appConfigService.getIntegerAppConfigByKey("BIG_RED_PACK_MIN"),
								appConfigService.getIntegerAppConfigByKey("BIG_RED_PACK_MAX")+1);
						TopicNews news = new TopicNews();
						news.setTopicId(topicId);
						news.setContent(profile.getNickName()+"在《"+topic.getTitle()+"》获得一个大红包,价值"+coins+"米汤币");
						news.setType(2);
						news.setCreateTime(new Date());
						topicDao.saveTopicNews(news);
					}
				}
			}catch(Exception e){
				return Response.failure(ResponseStatus.KINGDOM_STEAL_FAILURE.status,e.getMessage());
			}
			if(isBigRedPack==0){
				// 	修改王国可被偷数
				extTopicMapper.updateTopicDataStealPriceByTopicId(coins, topicId);
				extTopicMapper.updateTopicPrice(coins, topicId);
				//平衡收割数和王国价值
				int balancePrice = topicDao.balanceTopicStealPriceHarvest(topicId);
				if(balancePrice > 0){//缓存里减掉
					redisService.incrby("TOPIC_HARVEST:"+topicId, 0-balancePrice);
				}
			}
			// 记录偷取日志
			UserStealLog log = new UserStealLog();
			log.setCreateTime(new Date());
			log.setIsBigRedPack(isBigRedPack);
			log.setStealedCoins(coins);
			log.setTopicId(topicId);
			log.setUid(uid);
			userDao.addStealLog(log);
			// 修改用户金币数
			UserCoinDto modifyDetail=userService.modifyUserCoin(profile, userService.getCoinRules().get(Specification.CoinRuleCode.SPEAK_KEY.index));

			StealResultResponse response= new StealResultResponse();
			response.setIsBigRedPack(isBigRedPack);
			response.setStealedCoins(coins);
			response.setUpgrade(modifyDetail.getUpgrade());
			response.setCurrentLevel(modifyDetail.getCurrentLevel());
			return Response.success(response);
		} catch (Exception e) {
			log.error("偷取失败", e);
			return Response.failure(ResponseStatus.KINGDOM_STEAL_FAILURE.status,"偷取失败");
		} 
    }
    
    public Response<GetJoinLotteryUsersResponse> getJoinLotteryUsers(GetJoinLotteryUsersRequest request){
    	LotteryInfo lotteryInfo = lotteryInfoMapper.selectByPrimaryKey(request.getLotteryId());
		if (lotteryInfo == null) {
			return Response.failure(500, "找不到该抽奖信息！");
		}
		GetJoinLotteryUsersResponse result = new GetJoinLotteryUsersResponse();
		List<ExtJoinLotteryUser> joinUsers = extLotteryMapper.getJoinLotteryUsers(request.getLotteryId(), request.getSinceId(), 20);
		for(ExtJoinLotteryUser luser : joinUsers){
			luser.setAvatar(baseService.genAvatar(luser.getAvatar()));
			luser.setAvatarFrame(baseService.genQiNiuImageUrl(luser.getAvatarFrame()));
			result.getJoinUsers().add(TopicItemCopier.INSTANCE.ext2JoinUserElement(luser));
		}
		return Response.success(result);
    }
    public Response<BaseResponse> getRedDot(long uid, long updateTime) {
        List<Long> topics = topicDao.getTopicId(uid);
        Calendar calendar = Calendar.getInstance();
        if (updateTime == 0) {
            updateTime = calendar.getTimeInMillis();
        }
        List<Topic> topicList = topicDao.getMyLivesByUpdateTime2(uid, updateTime, topics);
        List<String> reds = Lists.newArrayList();
        for (Topic topic : topicList) {
            MySubscribeCacheModel cacheModel = new MySubscribeCacheModel(uid, topic.getId() + "", "0");
            String isUpdate = redisService.hGet(cacheModel.getKey(), topic.getId() + "");
            reds.add(isUpdate);
        }
        if (reds.size()>0 && reds.contains("1")) {
            return Response.success(ResponseStatus.GET_REDDOT_SUCCESS.status ,ResponseStatus.GET_REDDOT_SUCCESS.message);
        }else{
            return Response.success(ResponseStatus.GET_REDDOT_FAILURE.status ,ResponseStatus.GET_REDDOT_FAILURE.message);
        }
    }

	public Response<GetLotteryResponse> getLottery(GetLotteryRequest request) {
		LotteryInfo lotteryInfo = lotteryInfoMapper.selectByPrimaryKey(request.getLotteryId());
		if (lotteryInfo == null) {
			return Response.failure(500, "找不到该抽奖信息！");
		}
		GetLotteryResponse dto = new GetLotteryResponse();
		if (lotteryInfo.getStatus() == -1) {
			dto.setStatus(-1);
			dto.setTitle(lotteryInfo.getTitle());
		} else {
			// app外
			UserProfile userProfile = userDao.getUserProfileByUid(lotteryInfo.getUid());
			if (userProfile == null) {
				return Response.failure(500, "找不到该抽奖发起人信息！");
			}
			if (request.getOutApp() == 1) {
				// 记录阅读历史
				TopicReadHis trh = new TopicReadHis();
				trh.setUid(request.getUid());
				trh.setTopicId(lotteryInfo.getTopicId());
				trh.setReadCount(1);
				trh.setFromUid(request.getUid());
				trh.setInApp(0);
				trh.setCreateTime(new Date());
				Content content = contentDao.getContentByTopicId(lotteryInfo.getTopicId());
				SystemConfig systemConfig = appConfigDao.getSystemConfig();
				int start = systemConfig.getReadCountStart();
				int end = systemConfig.getReadCountEnd();
				Random random = new Random();
				// 取1-6的随机数每次添加
				int value = random.nextInt(end) + start;
				extContentMapper.updateContentReadCount(content.getId(), 1, value);
				trh.setReadCountDummy(value);
				topicReadHisMapper.insertSelective(trh);
			}
			dto = TopicCopier.INSTANCE.asGetLotteryResponse(lotteryInfo, userProfile);
			
			dto.setAvatar(baseService.genAvatar(userProfile.getAvatar()));
			dto.setAvatarFrame(baseService.genQiNiuImageUrl(userProfile.getAvatarFrame()));
			dto.setCreateTime(lotteryInfo.getCreateTime().getTime());
			dto.setEndTime(lotteryInfo.getEndTime().getTime());
			if (new Date().compareTo(lotteryInfo.getEndTime()) >= 0 && lotteryInfo.getStatus() == 0) {
				dto.setStatus(1);
			} else {
				dto.setStatus(lotteryInfo.getStatus());
			}
			int countLotteryContent = topicDao.countLotteryContent(request.getLotteryId(), request.getUid());
			if (countLotteryContent > 0) {
				dto.setSignUp(1);
			} else {
				dto.setSignUp(0);
			}
			if (dto.getStatus() == 0) {
				long timeInterval = (lotteryInfo.getEndTime().getTime() - new Date().getTime()) / 1000;
				dto.setTimeInterval(timeInterval);
			} else {
				dto.setTimeInterval(0);
			}
			int joinNumber = topicDao.countLotteryJoinUser(request.getLotteryId());
			dto.setJoinNumber(joinNumber);
			int isWin = topicDao.lotteryIsWin(request.getLotteryId(), request.getUid());
			if (isWin > 0) {
				dto.setIsWin(1);
			} else {
				dto.setIsWin(0);
			}
			List<ExtLotteryWinUser> winUsers = extLotteryMapper.getLotteryWinUserList(request.getLotteryId());
			
			for (ExtLotteryWinUser extLotteryWinUser : winUsers) {
				GetLotteryResponse.UserElement u = new GetLotteryResponse.UserElement();
				
				u.setUid(extLotteryWinUser.getUid());
				u.setAvatar(baseService.genAvatar(extLotteryWinUser.getAvatar()));
				u.setAvatarFrame(baseService.genQiNiuImageUrl(extLotteryWinUser.getAvatarFrame()));
				u.setNickName(extLotteryWinUser.getNickName());
				u.setV_lv(extLotteryWinUser.getV_lv());
				u.setLevel(extLotteryWinUser.getLevel());
				dto.getWinUsers().add(u);
			}
		}
		return Response.success(dto);
	}
}
