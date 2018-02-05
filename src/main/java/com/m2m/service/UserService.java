package com.m2m.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.m2m.Constant;
import com.m2m.cache.FriendNewApplyModel;
import com.m2m.copier.EmotionCopier;
import com.m2m.copier.FriendCopier;
import com.m2m.copier.UserCopier;
import com.m2m.dao.TopicDao;
import com.m2m.dao.UserDao;
import com.m2m.domain.AppUiControl;
import com.m2m.domain.Dictionary;
import com.m2m.domain.DictionaryType;
import com.m2m.domain.EmotionInfo;
import com.m2m.domain.EmotionPackDetail;
import com.m2m.domain.EmotionRecord;
import com.m2m.domain.ImConfig;
import com.m2m.domain.LiveFavorite;
import com.m2m.domain.OpenDeviceCount;
import com.m2m.domain.RuleLog;
import com.m2m.domain.ThirdPartUser;
import com.m2m.domain.Topic;
import com.m2m.domain.User;
import com.m2m.domain.UserFamous;
import com.m2m.domain.UserFriend;
import com.m2m.domain.UserFriendApply;
import com.m2m.domain.UserFriendHis;
import com.m2m.domain.UserFriendMessage;
import com.m2m.domain.UserFriendReclist;
import com.m2m.domain.UserGag;
import com.m2m.domain.UserIndustry;
import com.m2m.domain.UserNo;
import com.m2m.domain.UserProfile;
import com.m2m.domain.UserToken;
import com.m2m.domain.UserVisitLog;
import com.m2m.domain.VersionChannelDownload;
import com.m2m.domain.VersionControl;
import com.m2m.entity.CoinRule;
import com.m2m.entity.ExtEmotionSummaryModel;
import com.m2m.entity.ExtFriend;
import com.m2m.entity.ExtFriendApply;
import com.m2m.entity.ExtFriendContactsInfo;
import com.m2m.entity.ExtHobbyInfo;
import com.m2m.entity.ExtIndustryStat;
import com.m2m.entity.ExtPermissionDescription;
import com.m2m.entity.ExtPermissionDescription.PermissionNodeDto;
import com.m2m.entity.ExtRecFriend;
import com.m2m.entity.ExtUserEmotion;
import com.m2m.entity.ExtUserProfile;
import com.m2m.entity.ImUserInfoDto;
import com.m2m.entity.Middleman;
import com.m2m.entity.UserCoinDto;
import com.m2m.entity.UserPermissionDto;
import com.m2m.mapper.EmotionPackDetailMapper;
import com.m2m.mapper.ExtEmotionMapper;
import com.m2m.mapper.ExtFriendMapper;
import com.m2m.mapper.ExtTopicMapper;
import com.m2m.mapper.ExtUserMapper;
import com.m2m.mapper.ImConfigMapper;
import com.m2m.mapper.RuleLogMapper;
import com.m2m.mapper.TopicMapper;
import com.m2m.mapper.UserFriendApplyMapper;
import com.m2m.mapper.UserFriendHisMapper;
import com.m2m.mapper.UserFriendMapper;
import com.m2m.mapper.UserFriendMessageMapper;
import com.m2m.mapper.UserFriendReclistMapper;
import com.m2m.mapper.UserIndustryMapper;
import com.m2m.mapper.UserNoMapper;
import com.m2m.mapper.UserVisitLogMapper;
import com.m2m.request.BasicDataRequest;
import com.m2m.request.CheckNameOpenIdRequest;
import com.m2m.request.ContactsQueryRequest;
import com.m2m.request.FriendApplyQueryRequest;
import com.m2m.request.FriendMiddlemanQueryRequest;
import com.m2m.request.FriendOptRequest;
import com.m2m.request.FriendQueryRequest;
import com.m2m.request.GetIMUsertokenRequest;
import com.m2m.request.GetLastEmotionInfoRequest;
import com.m2m.request.GetLevelListRequest;
import com.m2m.request.GetUserProfileRequest;
import com.m2m.request.GetUserRequest;
import com.m2m.request.RecFriendInfoRequest;
import com.m2m.request.RecFriendQueryRequest;
import com.m2m.request.UserDataRequest;
import com.m2m.request.VersionControlRequest;
import com.m2m.response.BaseResponse;
import com.m2m.response.BasicDataSuccessResponse;
import com.m2m.response.CheckNameOpenIdResponse;
import com.m2m.response.ContactsQueryResponse;
import com.m2m.response.EmotionHisListResponse;
import com.m2m.response.FriendApplyQueryResponse;
import com.m2m.response.FriendMiddlemanQueryResponse;
import com.m2m.response.FriendQueryResponse;
import com.m2m.response.GetIMUsertokenResponse;
import com.m2m.response.GetLastEmotionInfoResponse;
import com.m2m.response.GetLevelListResponse;
import com.m2m.response.GetUserProfileResponse;
import com.m2m.response.GetUserResponse;
import com.m2m.response.GuideInfoResponse;
import com.m2m.response.MyLevelResponse;
import com.m2m.response.RecFriendInfoResponse;
import com.m2m.response.RecFriendQueryResponse;
import com.m2m.response.UserDataResponse;
import com.m2m.response.VersionControlResponse;
import com.m2m.util.DateUtil;
import com.m2m.web.Response;
import com.m2m.web.ResponseStatus;
import com.m2m.web.Specification;

import lombok.Getter;

@Service
public class UserService {

	private static final Logger log = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private AppConfigService appConfigService;
	@Autowired
	private BaseService baseService;
	@Autowired
	private RedisService redisService;
	@Autowired
	private PushService pushService;
	@Autowired
	private SmsService smsService;
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private TopicDao topicDao;
	
	@Autowired
	private ExtEmotionMapper extEmotionMapper;
	@Autowired
	private ExtUserMapper extUserMapper;
	@Autowired
	private RuleLogMapper ruleLogMapper;
	@Autowired
	private UserVisitLogMapper userVisitLogMapper;
	@Autowired
	private ExtFriendMapper extFriendMapper;
	@Autowired
	private TopicMapper topicMapper;
	@Autowired
	private UserNoMapper userNoMapper;
	@Autowired
	private UserIndustryMapper userIndustryMapper;
	@Autowired
	private UserFriendMapper userFriendMapper;
	@Autowired
	private UserFriendApplyMapper userFriendApplyMapper;
	@Autowired
	private UserFriendMessageMapper userFriendMessageMapper;
	@Autowired
	private UserFriendHisMapper userFriendHisMapper;
	@Autowired
	private ExtTopicMapper extTopicMapper;
	@Autowired
	private UserFriendReclistMapper userFriendReclistMapper;
	@Autowired
	private ImConfigMapper imConfigMapper;
	@Autowired
    private EmotionPackDetailMapper  emotionPackDetailMapper;
	
	
	@Getter
    private Map<Integer,CoinRule> coinRules = Maps.newConcurrentMap();
	
	private static final String USER_PERMISSIONS = "USER_PERMISSIONS";
	private static final String POWER_KEY = "power:key";
	private  static  final  String LEVEL_DEFINITION = "LEVEL_DEFINITION";
	
	@PostConstruct
    public void init(){
		log.info("初始化规则");
        coinRules.put(Specification.CoinRuleCode.SPEAK_KEY.index,new CoinRule(Specification.CoinRuleCode.SPEAK_KEY.index,"发言",Integer.valueOf(appConfigService.getAppConfigByKey("SPEAK_KEY")),true));
        coinRules.put(Specification.CoinRuleCode.PUBLISH_UGC_KEY.index,new CoinRule(Specification.CoinRuleCode.PUBLISH_UGC_KEY.index,"发布UGC",Integer.valueOf(appConfigService.getAppConfigByKey("PUBLISH_UGC_KEY")),true));
        coinRules.put(Specification.CoinRuleCode.REVIEW_UGC_KEY.index,new CoinRule(Specification.CoinRuleCode.REVIEW_UGC_KEY.index,"回复UGC",Integer.valueOf(appConfigService.getAppConfigByKey("REVIEW_UGC_KEY")),true));
        coinRules.put(Specification.CoinRuleCode.LIKES_UGC_KEY.index,new CoinRule(Specification.CoinRuleCode.LIKES_UGC_KEY.index,"点赞UGC",Integer.valueOf(appConfigService.getAppConfigByKey("LIKES_UGC_KEY")),true));
        coinRules.put(Specification.CoinRuleCode.FOLLOW_USER_KEY.index,new CoinRule(Specification.CoinRuleCode.FOLLOW_USER_KEY.index,"关注一个新用户",Integer.valueOf(appConfigService.getAppConfigByKey("FOLLOW_USER_KEY")),true));
        coinRules.put(Specification.CoinRuleCode.JOIN_KING_KEY.index,new CoinRule(Specification.CoinRuleCode.JOIN_KING_KEY.index,"加入一个新王国",Integer.valueOf(appConfigService.getAppConfigByKey("JOIN_KING_KEY")),true));
        coinRules.put(Specification.CoinRuleCode.SHARE_KING_KEY.index,new CoinRule(Specification.CoinRuleCode.SHARE_KING_KEY.index,"对外分享王国/UGC",Integer.valueOf(appConfigService.getAppConfigByKey("SHARE_KING_KEY")),true));
        coinRules.put(Specification.CoinRuleCode.CREATE_KING_KEY.index,new CoinRule(Specification.CoinRuleCode.CREATE_KING_KEY.index,"建立王国/更新王国",Integer.valueOf(appConfigService.getAppConfigByKey("CREATE_KING_KEY")),false));
        coinRules.put(Specification.CoinRuleCode.LOGIN_KEY.index,new CoinRule(Specification.CoinRuleCode.LOGIN_KEY.index,"登录",Integer.valueOf(appConfigService.getAppConfigByKey("LOGIN_KEY")),false));
    }
	
	/**
	 * 判断用户是否被禁言
	 * @param uid
	 * @return
	 */
	public boolean isGagged(long uid){
		UserGag userGag = userDao.getUserGagByUid(uid);
		if(null != userGag){
			return true;
		}
		return false;
	}
	
	/**
	 * 判断用户uid和token是否正确
	 * @param uid
	 * @param token
	 * @return
	 */
	public boolean checkUserToken(long uid, String token){
		if(StringUtils.isBlank(token)){
			return false;
		}
		UserToken userToken = userDao.getUserTokenByUid(uid);
		if(null != userToken && StringUtils.isNotBlank(userToken.getToken())
				&& token.equals(userToken.getToken())){
			return true;
		}
		return false;
	}
	
	/**
	 * 获取用户最近20个情绪记录
	 * @param uid
	 * @return
	 */
	public Response<EmotionHisListResponse> emotionHisList(long uid){
		EmotionHisListResponse result = new EmotionHisListResponse();
		//先获取用户的情绪王国（也即生活记录王国）
		Topic emotionTopic = topicDao.getUserEmotionTopic(uid);
		if(null != emotionTopic){
			result.setTopicId(emotionTopic.getId().longValue());
		}
		//再获取用户总共的情绪数
		result.setTotalCount(userDao.countUserEmotionRecord(uid));
		
		//获取最近20个情绪记录
		List<ExtUserEmotion> extUserEmotionList = extEmotionMapper.getUserEmotions(uid, 20);
		if(null != extUserEmotionList && extUserEmotionList.size() > 0){
			List<EmotionHisListResponse.EmotionElement> eList = EmotionCopier.INSTANCE.ext2EmotionElementList(extUserEmotionList);
			for(EmotionHisListResponse.EmotionElement e : eList){
				e.getEmotionPack().setImage(Constant.QINIU_DOMAIN + "/" + e.getEmotionPack().getImage());
				e.getEmotionPack().setThumb(Constant.QINIU_DOMAIN + "/" + e.getEmotionPack().getThumb());
			}
			result.getEmotionList().addAll(eList);
		}
		
		return Response.success(result);
	}
	
	/**
	 * 用户增加个人米汤币
	 * @param uid
	 * @param rule
	 * @return
	 */
	public UserCoinDto coinRule(long uid,CoinRule rule) {
	    UserProfile userProfile = userDao.getUserProfileByUid(uid);
	    // 根据等级获取今日上限值
        String limits = appConfigService.getAppConfigByKey("GET_COIN_LEVEL_LIMITS");
        List<Integer> array = JSON.parseArray(limits,Integer.class);
        Map<Integer,Integer> map = Maps.newConcurrentMap();
        for(int i = 0;i<array.size();i++){
            map.put(i+1,array.get(i));
        }
        // 日志拉取今日的累计值
        String dayStr = DateUtil.date2string(new Date(), "yyyy-MM-dd");
        int allDayPoints = extUserMapper.getUserTotalCoinsByDay(uid, dayStr);
        
        if(allDayPoints < map.get(userProfile.getLevel())){
            // 并且规则是否允许重复
            if(!rule.isRepeatable()){
            	int result = extUserMapper.countRuleLogByUidAndRuleCodeAndDay(uid, rule.getCode(), dayStr);
                if(result == 0) {
                    return modifyUserCoin(userProfile, rule);
                }
            }else{
                if(rule.getExt()>0){
                	int result = extUserMapper.countRuleLogByUidAndRuleCodeAndExt(uid, rule.getCode(), rule.getExt());
                    if(result == 0){
                        return modifyUserCoin(userProfile, rule);
                    }
                }else{
                    return modifyUserCoin(userProfile, rule);
                }
            }

        }else{
            return modifyUserCoin(userProfile, null);
        }
        return modifyUserCoin(userProfile, null);
    }
	
	public UserCoinDto modifyUserCoin(UserProfile userProfile, CoinRule rule) {
		int coin = 0;
		if(null != rule){
			coin = rule.getPoint();
		}
		
		UserCoinDto modifyUserCoinDto = new UserCoinDto();
        modifyUserCoinDto.setCurrentLevel(userProfile.getLevel());
        modifyUserCoinDto.setUpgrade(0);
        if(coin <= 0){//不需要变更
        	return modifyUserCoinDto;
        }
        //先增加
        extUserMapper.addUserCoins(userProfile.getUid().longValue(), coin);
        //记录获取历史
        RuleLog rl = new RuleLog();
        rl.setUid(userProfile.getUid());
        rl.setRuleCode(rule.getCode());
        rl.setRuleName(rule.getName());
        rl.setCoin(coin);
        rl.setCreateTime(new Date());
        rl.setExt(rule.getExt());
        ruleLogMapper.insertSelective(rl);
        
        int modifyCoin = userProfile.getAvailableCoin()+coin;
        String permissions = appConfigService.getAppConfigByKey(USER_PERMISSIONS);
        UserPermissionDto userPermissionDto = JSON.parseObject(permissions, UserPermissionDto.class);
        int lv = 0;
        for(UserPermissionDto.UserLevelDto userLevelDto : userPermissionDto.getLevels()){
            if(  modifyCoin >= userLevelDto.getNeedCoins()){
                lv++;
            }
        }
        if ( lv > 9){
            lv = 9;
        }
        if(lv <= userProfile.getLevel()){
            return modifyUserCoinDto;
        }else{
            for (UserPermissionDto.UserLevelDto userLevelDto : userPermissionDto.getLevels()) {
                if ((lv - 1) == userLevelDto.getLevel() && modifyCoin >= userLevelDto.getNeedCoins()) {
                	extUserMapper.modifyUserLevel(userProfile.getUid(), lv);
                    modifyUserCoinDto.setUpgrade(1);
                    modifyUserCoinDto.setCurrentLevel(lv);
                    break;
                }
            }
            return modifyUserCoinDto;
        }
    }
	
	/**
	 * 插入操作记录
	 * @param uid
	 * @param action
	 * @param topicId
	 */
	public void addUserOprationLog(long uid, String action, long topicId) {
		UserVisitLog record = new UserVisitLog();
		record.setAction(action);
		record.setCreateTime(new Date());
		record.setExtra(null);
		record.setTopicId(topicId);
		record.setUid(uid);
		userVisitLogMapper.insertSelective(record);
	}

    /**
     * 获取用户权限
     * @author zhangjiwei
     * @date Jun 21, 2017
     * @param uid
     * @return
     */
    public ExtPermissionDescription getUserPermission(long uid){
    	UserProfile userProfile = userDao.getUserProfileByUid(uid);
    	 //获取权限内容
        String level = userProfile.getLevel().toString();
        String value2 = appConfigService.getAppConfigByKey("LEVEL_"+level);
        ExtPermissionDescription extPermissionDescription = JSON.parseObject(value2, ExtPermissionDescription.class);
        return extPermissionDescription;
    }
    
    /**
     * 清楚用户相关消息未读记录
     * @param uid
     * @param contentType
     * @param cid
     */
    public void clearUserNoticeUnreadByCid(long uid, int contentType, long cid){
    	userDao.clearUserNoticeUnreadByCid(uid, contentType, cid);
	}

	public  void updateUserCoins(long uid, int price) {
		extUserMapper.updateUserCoins(uid,price);
	}
    
    /**
     * 好友查询
     * @param request
     * @return
     */
    public Response<FriendQueryResponse> friendQuery(FriendQueryRequest request){
    	FriendQueryResponse result = new FriendQueryResponse();
    	int pageSize = 20;
    	int start = (request.getPage()-1)*pageSize;
    	List<ExtFriend> friendList = extFriendMapper.queryUserFriends(request.getUid(), request.getKeyword(), start, pageSize);
    	if(null != friendList && friendList.size() > 0){
    		List<Long> uidList = new ArrayList<Long>();
    		for(ExtFriend f : friendList){
    			uidList.add(f.getUid());
    			f.setAvatar(baseService.genAvatar(f.getAvatar()));
    			f.setAvatarFrame(baseService.genQiNiuImageUrl(f.getAvatarFrame()));
    		}
    		result.setFriendData(FriendCopier.INSTANCE.ext2FriendElementList(friendList));
    		if(request.getTopicId() > 0){//需要进行用户针对于指定王国的身份查询
    			Map<String, Integer> userStatusMap = new HashMap<>(); 
    			//一次性查询用户订阅情况
    			List<LiveFavorite> favoriteList = topicDao.getLiveFavoriteListByTopicIdAndUidList(request.getTopicId(), uidList);
    			if(null != favoriteList && favoriteList.size() > 0){
    				for(LiveFavorite lf : favoriteList){
    					userStatusMap.put(lf.getUid().toString(), Integer.valueOf(Specification.SnsCircle.IN.index));//订阅人
    				}
    			}
    			//查询核心圈
    			Topic topic = topicMapper.selectByPrimaryKey(request.getTopicId());
    			if(null != topic && !StringUtils.isEmpty(topic.getCoreCircle())){
    				for(Long uid : uidList){
    					if(baseService.isInCore(uid, topic.getCoreCircle())){
    						userStatusMap.put(uid.toString(), Integer.valueOf(Specification.SnsCircle.CORE.index));//核心圈
    					}
    				}
    			}
    			//处理身份信息
    			for(FriendQueryResponse.FriendElement e : result.getFriendData()){
    				if(null != userStatusMap.get(String.valueOf(e.getUid()))){
    					e.setInternalStatus(userStatusMap.get(String.valueOf(e.getUid())).intValue());
    				}else{//没有订阅也不是核心圈，则返回0
    					e.setInternalStatus(Specification.SnsCircle.OUT.index);
    				}
    			}
    		}
    	}
    	
    	return Response.success(result);
    }
    
    /**
     * 获取用户信息
     * @param request
     * @return
     */
    public Response<GetUserProfileResponse> getUserProfile(GetUserProfileRequest request){
    	log.info("getUserProfile start ...");
        UserProfile userProfile = userDao.getUserProfileByUid(request.getUid());
        
        GetUserProfileResponse showUserProfileDto = UserCopier.INSTANCE.userProfile2Response(userProfile);
        
        showUserProfileDto.setAvatar(baseService.genAvatar(userProfile.getAvatar()));
        showUserProfileDto.setAvatarFrame(baseService.genQiNiuImageUrl(userProfile.getAvatarFrame()));
        showUserProfileDto.setLiveCount(topicDao.getUserTopicCount(request.getUid()));
        String value = appConfigService.getAppConfigByKey(USER_PERMISSIONS);
        UserPermissionDto userPermissionDto = JSON.parseObject(value, UserPermissionDto.class);
        for(UserPermissionDto.UserLevelDto userLevelDto : userPermissionDto.getLevels()){
            if(userProfile.getLevel()==userLevelDto.getLevel()){
                showUserProfileDto.setLevelIcon(userLevelDto.getIcon());
                break;
            }
        }
        Set<String> powerKeys = redisService.smembers(POWER_KEY);
        if(powerKeys!=null && !powerKeys.isEmpty()) {
            if (powerKeys.contains(request.getUid() + "")) {
                showUserProfileDto.setPower(1);
            }
        }
        UserToken userToken = userDao.getUserTokenByUid(request.getUid());
        showUserProfileDto.setToken(userToken.getToken());
        UserNo userNo = userNoMapper.selectByPrimaryKey(request.getUid());
        if(null != userNo){
        	showUserProfileDto.setMeNumber(userNo.getMeNumber().toString());
        }
        
        List<ExtHobbyInfo> hobbyList = extUserMapper.getUserHobbyInfoList(request.getUid());
        GetUserProfileResponse.HobbyElement hobby = null;
        for (ExtHobbyInfo userHobby : hobbyList){
        	hobby = new GetUserProfileResponse.HobbyElement();
            hobby.setHobby(userHobby.getHobby());
            hobby.setValue(userHobby.getValue());
            showUserProfileDto.getHobbyList().add(hobby);
        }

        //判断是否有密码
        showUserProfileDto.setHasPwd(1);//默认设置过不需要展示
        if(!StringUtils.isEmpty(userProfile.getThirdPartBind())){
        	if(userProfile.getThirdPartBind().contains("mobile")){//只有手机才有可能需要展示设置密码
        		User user = userDao.getUserByUid(request.getUid());
        		if(null != user && "0".equals(user.getEncrypt())){
        			showUserProfileDto.setHasPwd(0);
        		}
        	}
        }
        //判断是否领取过补全信息的红包
        List<RuleLog> rList = userDao.getRuleLogListByUid(request.getUid()+999999999);
        if(null != rList && rList.size() > 0){
        	showUserProfileDto.setHasInfoCoin(1);
        }else{
        	showUserProfileDto.setHasInfoCoin(0);
        }
        
        showUserProfileDto.setIndustry("");
        if(showUserProfileDto.getIndustryId() > 0){
        	UserIndustry ui = userIndustryMapper.selectByPrimaryKey(showUserProfileDto.getIndustryId());
        	if(null != ui){
        		showUserProfileDto.setIndustry(ui.getIndustryName());
        	}
        }
        
        log.info("getUserProfile end ...");
        return  Response.success(showUserProfileDto);
    }
    
    public Response<GetUserResponse> getUser(GetUserRequest request){
    	UserProfile userProfile = userDao.getUserProfileByUid(request.getUid());
    	GetUserResponse result = UserCopier.INSTANCE.userProfile2BaseInfoResponse(userProfile);
    	result.setAvatar(baseService.genAvatar(userProfile.getAvatar()));
    	result.setAvatarFrame(baseService.genQiNiuImageUrl(userProfile.getAvatarFrame()));
    	result.setIndustry("");
    	if(result.getIndustryId() > 0){
        	UserIndustry ui = userIndustryMapper.selectByPrimaryKey(result.getIndustryId());
        	if(null != ui){
        		result.setIndustry(ui.getIndustryName());
        	}
        }
        return Response.success(result);
    }
    
    public Response<ContactsQueryResponse> contactsQuery(ContactsQueryRequest request){
    	ContactsQueryResponse result = new ContactsQueryResponse();
    	//先看有没有新的申请红点
    	FriendNewApplyModel reddotModel = new FriendNewApplyModel(request.getUid(),"0");
    	String value = redisService.get(reddotModel.getKey());
    	if(StringUtils.isNotBlank(value)){
    		result.setHasNewApply(1);
    	}else{
    		result.setHasNewApply(0);
    	}
    	
    	//当查第一页的时候，返回列表外的其他的数据信息
    	if(request.getPage() == 1){
    		//查询推荐用户
    		List<ExtFriend> resFriendList = extUserMapper.getUserFriendRecList(request.getUid(), 0, 5);//这里只要拿5个就够了
    		if(null != resFriendList && resFriendList.size() > 0){
    			for(ExtFriend extFriend : resFriendList){
    				extFriend.setAvatar(baseService.genAvatar(extFriend.getAvatar()));
    				extFriend.setAvatarFrame(baseService.genQiNiuImageUrl(extFriend.getAvatarFrame()));
    				result.getRecFriendData().add(FriendCopier.INSTANCE.ext2UserSimpleInfo(extFriend));
    			}
    		}
    		
    		//好友行业统计(必须要有好友才能统计)
			List<ExtIndustryStat> statList = extUserMapper.statUserFriendIndustry(request.getUid());
			if(null != statList && statList.size() > 0){
				int totalCount = 0;
				for(ExtIndustryStat stat : statList){
					totalCount = totalCount + stat.getFriendCount();
					if(stat.getIndustryId() > 0){
						result.getIndustryStat().add(FriendCopier.INSTANCE.extStat2IndustryStatElement(stat));
					}
				}
				ContactsQueryResponse.IndustryStatElement e = new ContactsQueryResponse.IndustryStatElement();
    			e.setIndustryId(0);
    			e.setIndustry("全部");
    			e.setCount(totalCount);
    			result.getIndustryStat().add(0, e);//插入到第一位
			}else{
    			//没有好友，则只有默认的全部0
    			ContactsQueryResponse.IndustryStatElement e = new ContactsQueryResponse.IndustryStatElement();
    			e.setIndustryId(0);
    			e.setIndustry("全部");
    			e.setCount(0);
    			result.getIndustryStat().add(e);
    		}
    	}
    	
    	//查询好友信息，分页查询
    	int pageSize = 20;
    	int start = (request.getPage() - 1) * pageSize;
    	List<ExtFriendContactsInfo> friendList = extUserMapper.queryFriendContacts(request.getUid(), request.getIndustryId(), start, pageSize);
    	if(null != friendList && friendList.size() > 0){
    		List<Long> msgIdList = new ArrayList<>();
    		ContactsQueryResponse.FriendElement friendElement = null;
    		for(ExtFriendContactsInfo info : friendList){
    			info.setAvatar(baseService.genAvatar(info.getAvatar()));
    			info.setAvatarFrame(baseService.genQiNiuImageUrl(info.getAvatarFrame()));
    			friendElement = FriendCopier.INSTANCE.extContacts2FriendElement(info);
    			if(info.getType() == 1){
    				friendElement.setGroup("*");
    				msgIdList.add(info.getMsgId());
    			}else if(StringUtils.isBlank(friendElement.getGroup())){
    				friendElement.setGroup("#");
    			}
    			result.getFriendData().add(friendElement);
    		}
    		
    		if(msgIdList.size() > 0){
    			userDao.delFriendMsgByIds(msgIdList);
    		}
    	}
    	
    	return Response.success(result);
    }
    
    public Response<BaseResponse> friendOpt(FriendOptRequest request){
    	if(request.getTargetUid() <= 0){
    		return Response.failure(ResponseStatus.ILLEGAL_REQUEST.status, ResponseStatus.ILLEGAL_REQUEST.message);
    	}
    	if(request.getAction() == 0){//申请加好友
    		//判断两人是否可以申请好友
    		if(request.getUid() == request.getTargetUid()){
    			return Response.failure(ResponseStatus.USER_ADD_FRIEND_ERROR.status, ResponseStatus.USER_ADD_FRIEND_ERROR.message);
    		}
    		UserFriend friend = userDao.getUserFriendBySourceUidAndTargetUid(request.getUid(), request.getTargetUid());
    		if(null != friend && friend.getStatus().intValue() == 0){
    			return Response.failure(ResponseStatus.YOU_ARE_ALREADY_FRIEND.status, ResponseStatus.YOU_ARE_ALREADY_FRIEND.message);
    		}
    		//先删掉原来的有的申请
    		userDao.delUserFriendApply(request.getUid(), request.getTargetUid());
    		
    		boolean isOver = false;
    		if(request.getFromUid() > 0){//有中间人
    			//判断TargetUid对于当前用户是否删除过好友关系，如果删除过，则一样需要申请
    			UserFriendHis his = userDao.getLastUserFriendHis(request.getTargetUid(), request.getUid(), 1);
    			if(null == his){//直接成功
    				this.saveFriendship(request.getUid(), request.getTargetUid(), request.getFromUid(), true);
    				isOver = true;
    			}
    		}
    		if(!isOver){
    			//需要下发申请
        		UserFriendApply apply = new UserFriendApply();
        		apply.setSourceUid(request.getUid());
        		apply.setTargetUid(request.getTargetUid());
        		apply.setFromUid(request.getFromUid());
        		apply.setContent(request.getContent());
        		userFriendApplyMapper.insertSelective(apply);
        		//设置target的好友申请红点
        		FriendNewApplyModel model = new FriendNewApplyModel(request.getTargetUid(), "1");
        		redisService.set(model.getKey(), model.getValue());
        		//并向target发送联系人红点推送
        		JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("friendCount", "1");
                pushService.pushWithExtra(String.valueOf(request.getTargetUid()), jsonObject.toString(), null);
                
        		//申请推送
        		//想target发送推送，A申请加你为好友，XXXX
        		UserProfile sourceUserProfile = userDao.getUserProfileByUid(request.getUid());
        		String message = sourceUserProfile.getNickName()+"申请加你为好友，"+request.getContent();
        		Map<String, String> extraMaps = new HashMap<>();
            	extraMaps.put("type", String.valueOf(Specification.PushObjectType.FRIEND.index));
    			extraMaps.put("messageType", String.valueOf(Specification.PushMessageType.FRIEND_APPLY_LIST.index));//打开申请列表页
                pushService.pushWithExtra(String.valueOf(request.getTargetUid()), message, extraMaps);
    		}
    	}else if(request.getAction() == 1){//同意添加好友
    		//先判断是否好友
    		UserFriend friend = userDao.getUserFriendBySourceUidAndTargetUid(request.getUid(), request.getTargetUid());
    		if(null != friend && friend.getStatus().intValue() == 0){
    			return Response.failure(ResponseStatus.YOU_ARE_ALREADY_FRIEND.status, ResponseStatus.YOU_ARE_ALREADY_FRIEND.message);
    		}
    		//此时操作的人是target
    		UserFriendApply apply = userDao.getUserFriendApplyBySourceUidAndTargetUidAndStatus(request.getTargetUid(), request.getUid(), 0);
    		if(null == apply){
    			return Response.failure(ResponseStatus.FRIEND_APPLY_NOT_EXISTS.status, ResponseStatus.FRIEND_APPLY_NOT_EXISTS.message);
    		}
    		//更新关系
    		UserFriendApply updateApply = new UserFriendApply();
    		updateApply.setId(apply.getId());
    		updateApply.setStatus(1);//同意
    		userFriendApplyMapper.updateByPrimaryKeySelective(updateApply);
    		//加好友
    		this.saveFriendship(apply.getSourceUid(), apply.getTargetUid(), apply.getFromUid(), false);
    	}else if(request.getAction() == 2){//删除好友申请
    		//此时操作的人是target
    		UserFriendApply apply = userDao.getUserFriendApplyBySourceUidAndTargetUidAndStatus(request.getTargetUid(), request.getUid(), -1);
    		if(null != apply){
    			UserFriendApply updateApply = new UserFriendApply();
        		updateApply.setId(apply.getId());
        		updateApply.setStatus(2);//删除
        		userFriendApplyMapper.updateByPrimaryKeySelective(updateApply);
    		}
    	}else if(request.getAction() == 3){//删除好友关系
    		//先判断是否好友
    		UserFriend friend = userDao.getUserFriendBySourceUidAndTargetUid(request.getUid(), request.getTargetUid());
    		if(null != friend){
    			//先退出对方的所有王国
    			extTopicMapper.delLiveFavoriteFromAllOtherUser(request.getUid(), request.getTargetUid());
    			//退出所有对方王国的核心圈
    			//先把对方所有的王国中有我作为核心圈的王国查询出来
    			List<Topic> topicList = extTopicMapper.getTargetTopicsInCore(request.getUid(), request.getTargetUid());
    			if(null != topicList && topicList.size() > 0){
    				for(Topic t : topicList){
    					JSONArray array = JSON.parseArray(t.getCoreCircle());
    	                for (int i = 0; i < array.size(); i++) {
    	                    if (array.getLongValue(i) == request.getUid()) {
    	                        array.remove(i);
    	                    }
    	                }
    	                extTopicMapper.updateTopicCore(t.getId(), array.toJSONString());
    				}
    			}
    			//删除我对于对方的推荐
    			userDao.delFriendRecByUidAndRecUid(request.getUid(), request.getTargetUid());
    			//删除对方对于我的推荐
    			userDao.delFriendRecByUidAndRecUid(request.getTargetUid(), request.getUid());
    			//删除我对于对方的好友
    			userFriendMapper.deleteByPrimaryKey(friend.getId());
    			//变更对方对于我的好友状态
    			userDao.updateUserFriendStatus(request.getTargetUid(), request.getUid(), 1);//对方置为已删除好友状态
    		}//如果已经没有好友，则无所谓了
    	}else if(request.getAction() == 4){//推荐好友
    		//判断是否已经推荐过了，已经推荐过了，可能重新编辑的
    		UserFriendReclist recFriend = userDao.getUserFriendReclistByUidAndTargetUid(request.getUid(), request.getTargetUid());
    		if(null == recFriend){//需要判断是否能推荐
    			//首先判断是否好友，只有好友才能推荐
        		UserFriend friend = userDao.getUserFriendBySourceUidAndTargetUid(request.getUid(), request.getTargetUid());
        		if(null == friend || friend.getStatus() != 0){//不是好友，或则对方把你删了
        			return Response.failure(ResponseStatus.YOU_ARE_NOT_FRIEND.status, ResponseStatus.YOU_ARE_NOT_FRIEND.message);
        		}
        		//判断是否在沉默期，沉默期也是不能推荐的
        		long currentTime = System.currentTimeMillis();
        		if(friend.getSilenceTime().getTime() > currentTime){
        			return Response.failure(ResponseStatus.YOU_ARE_IN_SILENCE_PERIOD.status, ResponseStatus.YOU_ARE_IN_SILENCE_PERIOD.message);
        		}
        		//判断推荐是否达到上限
        		int recCount = userDao.countFriendRecListByUid(request.getUid());
        		String recLimitConfig = redisService.get("FRIEND_REC_LIMIT");
        		int recLimit = 6;//默认6个
        		if(StringUtils.isNotBlank(recLimitConfig)){
        			try{
        				recLimit = Integer.valueOf(recLimitConfig);
        			}catch(Exception ignore){
        			}
        		}
        		if(recCount >= recLimit){//已达推荐上限
        			return Response.failure(ResponseStatus.REC_FRIEND_UPPER_LIMIT.status, ResponseStatus.REC_FRIEND_UPPER_LIMIT.message);
        		}
    		}
    		
    		//都校验完了，那么开始保存推荐记录
    		//先删除以前可能有的已经删除了的推荐
    		userDao.clearUserFriendRecByUid(request.getUid(), request.getTargetUid());
    		recFriend = new UserFriendReclist();
    		recFriend.setUid(request.getUid());
    		recFriend.setRecUid(request.getTargetUid());
    		recFriend.setDescription(request.getContent());
    		userFriendReclistMapper.insertSelective(recFriend);
    		//推送告知target，source向TA的好友推荐你，XXXX
    		UserProfile sourceUserProfile = userDao.getUserProfileByUid(request.getUid());
    		String message = sourceUserProfile.getNickName()+"向TA的好友推荐你，"+request.getContent();
    		Map<String, String> extraMaps = new HashMap<>();
        	extraMaps.put("type", String.valueOf(Specification.PushObjectType.FRIEND.index));
			extraMaps.put("messageType", String.valueOf(Specification.PushMessageType.FRIEND_RECDESC.index));//打开推荐详情页
            pushService.pushWithExtra(String.valueOf(request.getTargetUid()), message, extraMaps);
    	}else if(request.getAction() == 5){//取消推荐（我取消我的推荐）
    		//判断是否有推荐，有则取消掉，没有则直接算成功
    		userDao.delFriendRecByUidAndRecUid(request.getUid(), request.getTargetUid());
    	}else if(request.getAction() == 6){//删除推荐（我删除别人对我的推荐）
    		//先判断target对我有没有推荐，有则取消掉，没有则直接算成功
    		userDao.delFriendRecByUidAndRecUid(request.getTargetUid(), request.getUid());
    	}else{
    		return Response.failure(ResponseStatus.ACTION_NOT_SUPPORT.status, ResponseStatus.ACTION_NOT_SUPPORT.message);
    	}
    	
    	return Response.success(ResponseStatus.OPERATION_SUCCESS.status, ResponseStatus.OPERATION_SUCCESS.message);
    }
    
    private void saveFriendship(long sourceUid, long targetUid, long fromUid, boolean isAuto){
    	//先删掉原先可能存在的半吊子的好友关系
    	extFriendMapper.deleteHalfFriend(sourceUid, targetUid);
    	
    	Date now = new Date();
    	Date silenceTime = now;
    	if(fromUid > 0){//有中间人的，则需要有沉默期
    		String silencePeriodConfig = appConfigService.getAppConfigByKey("FRIEND_REC_SILENCE_PERIOD");
    		int silencePeriod = 0;
    		if(StringUtils.isNotBlank(silencePeriodConfig)){
    			try{
    				silencePeriod = Integer.valueOf(silencePeriodConfig);
    			}catch(Exception ignore){
    			}
    		}
    		if(silencePeriod > 0){
    			silenceTime = DateUtil.addHour(silenceTime, silencePeriod);
    		}
    	}
    	//先保存source
    	UserFriend sourceFriend = new UserFriend();
		sourceFriend.setSourceUid(sourceUid);
		sourceFriend.setTargetUid(targetUid);
		sourceFriend.setFromUid(fromUid);
		sourceFriend.setCreateTime(now);
		sourceFriend.setSilenceTime(silenceTime);
		sourceFriend.setStatus(0);
		userFriendMapper.insertSelective(sourceFriend);
		//再保存target的
		UserFriend targetFriend = new UserFriend();
		targetFriend.setSourceUid(targetUid);
		targetFriend.setTargetUid(sourceUid);
		targetFriend.setFromUid(fromUid);
		targetFriend.setCreateTime(now);
		targetFriend.setSilenceTime(silenceTime);
		targetFriend.setStatus(0);
		userFriendMapper.insertSelective(targetFriend);
		
		//记录交友历史
		UserFriendHis friendHis = new UserFriendHis();
		friendHis.setSourceUid(sourceUid);
		friendHis.setTargetUid(targetUid);
		friendHis.setFromUid(fromUid);
		friendHis.setType(0);
		userFriendHisMapper.insertSelective(friendHis);
		
		UserProfile sourceUserProfile = null;
		UserProfile targetUserProfile = null;
		UserProfile fromUserProfile = null;
		
		//交友消息
		if(isAuto && fromUid > 0){//有中间人，自动完成的
			//先向target发消息，source通过from加你为好友
			UserFriendMessage targetMsg = new UserFriendMessage();
			targetMsg.setUid(targetUid);
			targetMsg.setSourceUid(sourceUid);
			targetMsg.setTargetUid(fromUid);
			fromUserProfile = userDao.getUserProfileByUid(fromUid);
			targetMsg.setContent("通过"+fromUserProfile.getNickName()+"加你为好友");
			userFriendMessageMapper.insertSelective(targetMsg);
			//再向from发消息，source通过你认识了target
			UserFriendMessage fromMsg = new UserFriendMessage();
			fromMsg.setUid(fromUid);
			fromMsg.setSourceUid(sourceUid);
			fromMsg.setTargetUid(targetUid);
			targetUserProfile = userDao.getUserProfileByUid(targetUid);
			fromMsg.setContent("通过你认识了"+targetUserProfile.getNickName());
			userFriendMessageMapper.insertSelective(fromMsg);
		}else{//没有中间人
			//向source发消息，target通过了你的好友申请
			UserFriendMessage targetMsg = new UserFriendMessage();
			targetMsg.setUid(sourceUid);
			targetMsg.setSourceUid(targetUid);
			targetMsg.setContent("通过了你的好友申请");
			userFriendMessageMapper.insertSelective(targetMsg);
			if(fromUid > 0){//有中间人
				//再向from发消息，source通过你认识了target
				UserFriendMessage fromMsg = new UserFriendMessage();
				fromMsg.setUid(fromUid);
				fromMsg.setSourceUid(sourceUid);
				fromMsg.setTargetUid(targetUid);
				targetUserProfile = userDao.getUserProfileByUid(targetUid);
				fromMsg.setContent("通过你认识了"+targetUserProfile.getNickName());
				userFriendMessageMapper.insertSelective(fromMsg);
			}
		}
		
		//联系人红点推送
		//首先target肯定是有的
		JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("friendCount", "1");
        if(fromUid > 0){//有中间人的，那么中间人肯定也有红点了
        	pushService.pushWithExtra(String.valueOf(fromUid), jsonObject.toString(), null);
        }
        if(isAuto){//自动的则是source向target发申请请求就直接成功了
    		//则需要向target发送红点
        	pushService.pushWithExtra(String.valueOf(targetUid), jsonObject.toString(), null);
    	}else{//不是自动的，则是target同意了source的操作
    		//则需要向source发送红点
    		pushService.pushWithExtra(String.valueOf(sourceUid), jsonObject.toString(), null);
    	}
        
        //推送
        if(null == targetUserProfile){//这个貌似都要查的，提出来，别浪费代码行
    		targetUserProfile = userDao.getUserProfileByUid(targetUid);
    	}
        Map<String, String> extraMaps = null;
        String message = null;
        if(isAuto && fromUid > 0){//有中间人，并且是直接自动加好友的
        	sourceUserProfile = userDao.getUserProfileByUid(sourceUid);
        	//先给中间人推送，source通过你的推荐添加target为好友(打开联系人页)
        	message = sourceUserProfile.getNickName()+"通过你的推荐添加" + targetUserProfile.getNickName() + "为好友";
        	extraMaps = new HashMap<>();
        	extraMaps.put("type", String.valueOf(Specification.PushObjectType.FRIEND.index));
			extraMaps.put("messageType", String.valueOf(Specification.PushMessageType.FRIEND_CONTACTS.index));
            pushService.pushWithExtra(String.valueOf(fromUid), message, extraMaps);
            //再给target推送，source通过from的推荐添加你为好友(打开联系人页)
            if(null == fromUserProfile){
            	fromUserProfile = userDao.getUserProfileByUid(fromUid);
            }
            message = sourceUserProfile.getNickName()+"通过"+fromUserProfile.getNickName()+"的推荐添加你为好友";
        	extraMaps = new HashMap<>();
        	extraMaps.put("type", String.valueOf(Specification.PushObjectType.FRIEND.index));
			extraMaps.put("messageType", String.valueOf(Specification.PushMessageType.FRIEND_CONTACTS.index));
            pushService.pushWithExtra(String.valueOf(targetUid), message, extraMaps);
        }else{//点击同意的推送（这个请求是由target发起的）
        	//首先给source推送，target同意了你的好友申请(打开联系人页)
        	message = targetUserProfile.getNickName()+"同意了你的好友申请";
        	extraMaps = new HashMap<>();
        	extraMaps.put("type", String.valueOf(Specification.PushObjectType.FRIEND.index));
			extraMaps.put("messageType", String.valueOf(Specification.PushMessageType.FRIEND_CONTACTS.index));
            pushService.pushWithExtra(String.valueOf(sourceUid), message, extraMaps);
        	
        	if(fromUid > 0){//如果有中间人（如果target对source有过删除好友，则就算是通过中间人也要target点击同意的，所以有本逻辑的存在）
        		sourceUserProfile = userDao.getUserProfileByUid(sourceUid);
        		//推送给中间人，source通过你的推荐添加target为好友(打开联系人页)
        		message = sourceUserProfile.getNickName()+"通过你的推荐添加" + targetUserProfile.getNickName() + "为好友";
            	extraMaps = new HashMap<>();
            	extraMaps.put("type", String.valueOf(Specification.PushObjectType.FRIEND.index));
    			extraMaps.put("messageType", String.valueOf(Specification.PushMessageType.FRIEND_CONTACTS.index));
                pushService.pushWithExtra(String.valueOf(fromUid), message, extraMaps);
        	}
        }
    }
    
    public Response<FriendApplyQueryResponse> friendApplyQuery(FriendApplyQueryRequest request){
    	FriendApplyQueryResponse result = new FriendApplyQueryResponse();
    	
    	//去除申请上的红点
    	FriendNewApplyModel model = new FriendNewApplyModel(request.getUid(), "0");
    	redisService.del(model.getKey());
    	
    	int pageSize = 20;
    	int start = (request.getPage() - 1) * pageSize;
    	List<ExtFriendApply> applyList = extFriendMapper.queryUserFriendApply(request.getUid(), start, pageSize);
    	if(null != applyList && applyList.size() > 0){
    		for(ExtFriendApply apply : applyList){
    			apply.setAvatar(baseService.genAvatar(apply.getAvatar()));
    			apply.setAvatarFrame(baseService.genQiNiuImageUrl(apply.getAvatarFrame()));
    			result.getApplyData().add(FriendCopier.INSTANCE.extApply2ApplyInfoElement(apply));
    		}
    	}
    	
    	return Response.success(result);
    }
    
    public Response<UserDataResponse> userData(UserDataRequest request){
    	UserProfile userProfile = userDao.getUserProfileByUid(request.getCustomerId());
    	if(null == userProfile){
    		return Response.failure(ResponseStatus.USER_NOT_EXISTS.status, ResponseStatus.USER_NOT_EXISTS.message);
    	}
    	UserDataResponse result = new UserDataResponse();
    	result.setUid(userProfile.getUid());
    	result.setNickName(userProfile.getNickName());
    	result.setAvatar(baseService.genAvatar(userProfile.getAvatar()));
    	result.setAvatarFrame(baseService.genQiNiuImageUrl(userProfile.getAvatarFrame()));
    	result.setGender(userProfile.getGender());
    	if(result.getGender() == 2){//H5微信登录设置的性别，坑爹啊。。当初没有转，多出来一个2
    		result.setGender(1);
    	}
    	result.setIndustryId(userProfile.getIndustryId());
    	if(userProfile.getIndustryId() > 0){
    		UserIndustry userIndustry = userIndustryMapper.selectByPrimaryKey(userProfile.getIndustryId());
    		if(null != userIndustry){
    			result.setIndustry(userIndustry.getIndustryName());
    		}
    	}
    	result.setIntroduced(userProfile.getIntroduced());
    	result.setV_lv(userProfile.getvLv());
    	result.setSilenceTime(0);//默认没有沉默期
    	UserFriend friend = userDao.getUserFriendBySourceUidAndTargetUid(request.getUid(), request.getCustomerId());
    	if(null != friend && friend.getStatus().intValue() == 0){//是好友
    		result.setIsFriend(1);
    		//判断是否在沉默期
    		long currentTime = System.currentTimeMillis();
    		if(friend.getSilenceTime().getTime() > currentTime){
    			result.setSilenceTime((friend.getSilenceTime().getTime()-currentTime)/1000);//秒数
    		}
    	}else{
    		result.setIsFriend(0);
    	}
    	//如果是朋友则判断是否推荐过 
    	if(result.getIsFriend() == 1){
    		UserFriendReclist recFriend = userDao.getUserFriendReclistByUidAndTargetUid(request.getUid(), request.getCustomerId());
    		if(null != recFriend){
    			result.setIsRecFriend(1);
    		}else{
    			result.setIsRecFriend(0);
    		}
    	}
    	UserFamous userFamous = userDao.getUserFamousByUid(request.getUid());
    	if(null != userFamous){
    		result.setIsRec(1);
    	}else{
    		result.setIsRec(0);
    	}
    	//查询有多少订阅过我的王国
    	result.setSubscribeCount(extTopicMapper.countUserKingdomSubscribeCount(request.getCustomerId()));
    	//有沉默期，则将沉默期的配置返回
    	if(result.getSilenceTime() > 0){
    		String silencePeriodConfig = appConfigService.getAppConfigByKey("FRIEND_REC_SILENCE_PERIOD");
    		int silencePeriod = 0;
    		if(StringUtils.isNotBlank(silencePeriodConfig)){
    			try{
    				silencePeriod = Integer.valueOf(silencePeriodConfig);
    			}catch(Exception ignore){
    			}
    		}
    		result.setSilencePeriod(silencePeriod);
    	}
    	result.setTotalRecFriendCount(userDao.countFriendRecListByUid(request.getCustomerId()));
    	//如果是好友，或则是自己，则能看到推荐的内容
    	if(result.getIsFriend() == 1 || request.getUid() == request.getCustomerId()){
    		List<ExtRecFriend> recFriendList = extFriendMapper.queryUserFriendRecList(request.getCustomerId(), 10);//拉10个人就够了，让页面自己去处理
    		if(null != recFriendList && recFriendList.size() > 0){
    			for(ExtFriend extFriend : recFriendList){
    				extFriend.setAvatar(baseService.genAvatar(extFriend.getAvatar()));
    				extFriend.setAvatarFrame(baseService.genQiNiuImageUrl(extFriend.getAvatarFrame()));
    				result.getRecFriendData().add(FriendCopier.INSTANCE.ext2UserSimpleInfo(extFriend));
    			}
    		}
    	}
    	//查询自己有几个王国
    	result.setOwnKingdomCount(topicDao.getUserTopicCount(request.getCustomerId()));
    	//查询所有王国的图库图片内容
    	List<String> imageList = extTopicMapper.getTopTopicImageByUid(request.getCustomerId(), 10);//取10个，至于前端用几个，前端自己按屏幕大小判断
    	if(null != imageList && imageList.size() > 0){
    		UserDataResponse.KingdomImageElement imageElement = null;
    		for(String image : imageList){
    			if(StringUtils.isNotBlank(image)){
    				imageElement = new UserDataResponse.KingdomImageElement();
    				imageElement.setFragmentImage(baseService.genQiNiuImageUrl(image));
    				result.getOwnKingdomImageData().add(imageElement);
    			}
    		}
    	}
    	//中间人显示
    	//判断：要不是好友，并且不是自己看自己才有中间人的判断逻辑
    	if(result.getIsFriend() != 1 && request.getUid() != request.getCustomerId()){
    		boolean hasOne = false;
    		//先判断是否有特定指定的人
    		if(request.getFromUid() > 0){//有指定，则判断指定的这个人是否真的是推荐
    			List<Middleman> middlemanList = extFriendMapper.getMiddlemanList(request.getUid(), request.getCustomerId(), request.getFromUid(), 1);
    			if(null != middlemanList && middlemanList.size() > 0){
    				Middleman middleman = middlemanList.get(0);
    				middleman.setAvatar(baseService.genAvatar(middleman.getAvatar()));
    				middleman.setAvatarFrame(baseService.genQiNiuImageUrl(middleman.getAvatarFrame()));
    				result.setMiddleman(middleman);
    				hasOne = true;
    			}
    		}
    		if(!hasOne){//没有指定，或则指定的已经不是好友或推荐已经取消了，则这里还是需要一位中间人 
    			List<Middleman> middlemanList = extFriendMapper.getMiddlemanList(request.getUid(), request.getCustomerId(), 0, 1);
    			if(null != middlemanList && middlemanList.size() > 0){
    				Middleman middleman = middlemanList.get(0);
    				middleman.setAvatar(baseService.genAvatar(middleman.getAvatar()));
    				middleman.setAvatarFrame(baseService.genQiNiuImageUrl(middleman.getAvatarFrame()));
    				result.setMiddleman(middleman);
    			}
    		}
    	}
    	return Response.success(result);
    }
    
    public Response<RecFriendQueryResponse> recFriendQuery(RecFriendQueryRequest request){
    	if(request.getUid() != request.getCustomerId()){//不是看自己，则需要判断是否好友，是否在沉默期
    		//判断当前用户是否是待查询的用户的好友，如果不是好友，则不能查看
        	UserFriend friend = userDao.getUserFriendBySourceUidAndTargetUid(request.getUid(), request.getCustomerId());
        	if(null == friend || friend.getStatus() != 0){//不是好友，或则对方把你删了
    			return Response.failure(ResponseStatus.YOU_ARE_NOT_FRIEND.status, ResponseStatus.YOU_ARE_NOT_FRIEND.message);
    		}
    		//判断是否在沉默期，沉默期也是不能查看推荐的
    		long currentTime = System.currentTimeMillis();
    		if(friend.getSilenceTime().getTime() > currentTime){
    			return Response.failure(ResponseStatus.YOU_ARE_IN_SILENCE_PERIOD.status, ResponseStatus.YOU_ARE_IN_SILENCE_PERIOD.message);
    		}
    	}
		
		RecFriendQueryResponse result = new RecFriendQueryResponse();
		result.setUid(request.getCustomerId());
		//先查询当前用户的一些基本信息
		UserProfile userProfile = userDao.getUserProfileByUid(request.getCustomerId());
		if(null != userProfile){
			result.setNickName(userProfile.getNickName());
			result.setAvatar(baseService.genAvatar(userProfile.getAvatar()));
			result.setAvatarFrame(baseService.genQiNiuImageUrl(userProfile.getAvatarFrame()));
			result.setV_lv(userProfile.getvLv());
		}
		result.setRecFriendCount(userDao.countFriendRecListByUid(request.getCustomerId()));
		
		//一次性查询所有的推荐好友
		List<ExtRecFriend> recFriendList = extFriendMapper.queryUserFriendRecList(request.getCustomerId(), 0);
		if(null != recFriendList && recFriendList.size() > 0){
			List<Long> recUidList = new ArrayList<Long>();
			for(ExtRecFriend recFriend : recFriendList){
				recUidList.add(recFriend.getUid());
			}
			//一次性查询，当前用户和这些被推荐的用户的好友关系
			List<UserFriend> fList = userDao.getUserFriendBySourceUidAndTargetUidList(request.getUid(), recUidList);
			Map<String, String> friendMap = new HashMap<>();
			if(null != fList && fList.size() > 0){
				for(UserFriend f : fList){
					friendMap.put(f.getTargetUid().toString(), "1");
				}
			}
			RecFriendQueryResponse.RecFriendElement recFriendElement = null;
			for(ExtRecFriend recFriend : recFriendList){
				recFriendElement = FriendCopier.INSTANCE.ext2RecFriendElement(recFriend);
				recFriendElement.setAvatar(baseService.genAvatar(recFriendElement.getAvatar()));
				recFriendElement.setAvatarFrame(baseService.genQiNiuImageUrl(recFriendElement.getAvatarFrame()));
				if(null != friendMap.get(String.valueOf(recFriend.getUid()))){
					recFriendElement.setIsFriend(1);
				}else{
					recFriendElement.setIsFriend(0);
				}
				result.getRecFriendData().add(recFriendElement);
			}
		}
		
		//如果有指定的展现人的话，则需要将指定的展现人放在第一个位置
		if(result.getRecFriendData().size() > 1 && request.getTargetUid() > 0){
			RecFriendQueryResponse.RecFriendElement targetElement = null;
			
			RecFriendQueryResponse.RecFriendElement recFriendElement = null;
			for(int i=0;i<result.getRecFriendData().size();i++){
				recFriendElement = result.getRecFriendData().get(i);
				if(recFriendElement.getUid() == request.getTargetUid()){
					targetElement = recFriendElement;
					result.getRecFriendData().remove(i);
					break;
				}
			}
			
			if(null != targetElement){
				result.getRecFriendData().add(0, targetElement);
			}
		}
    	
    	return Response.success(result);
    }
    
    public Response<FriendMiddlemanQueryResponse> friendMiddlemanQuery(FriendMiddlemanQueryRequest request){
    	FriendMiddlemanQueryResponse result = new FriendMiddlemanQueryResponse();
    	
    	List<Middleman> list = extFriendMapper.getMiddlemanList(request.getUid(), request.getCustomerId(), 0, 0);
    	if(null != list && list.size() > 0){
    		Middleman targetMiddleman = null;
    		
    		for(Middleman man : list){
    			man.setAvatar(baseService.genAvatar(man.getAvatar()));
    			man.setAvatarFrame(baseService.genQiNiuImageUrl(man.getAvatarFrame()));
    			if(request.getFromUid() > 0 && request.getFromUid() == man.getUid()){
    				targetMiddleman = man;
    			}else{
    				result.getMiddlemanData().add(man);
    			}
    		}
    		
    		if(null != targetMiddleman){
    			result.getMiddlemanData().add(0, targetMiddleman);
    		}
    	}
    	
    	return Response.success(result);
    }
    
    public Response<RecFriendInfoResponse> recFriendInfo(RecFriendInfoRequest request){
    	RecFriendInfoResponse result = new RecFriendInfoResponse();
    	
    	//判断是否已经存在推荐
    	UserFriendReclist recFriend = userDao.getUserFriendReclistByUidAndTargetUid(request.getFromUid(), request.getCustomerId());
    	if(null != recFriend){//存在推荐，则直接返回推荐相关属性就好
    		result.setIsRec(1);
    		result.setIsFriend(1);//有推荐，肯定是好友
    		
    		ExtUserProfile userProfile = extUserMapper.getExtUserProfileByUid(request.getCustomerId());
    		result.setUid(request.getCustomerId());
    		result.setNickName(userProfile.getNickName());
    		result.setAvatar(baseService.genAvatar(userProfile.getAvatar()));
    		result.setAvatarFrame(baseService.genQiNiuImageUrl(userProfile.getAvatarFrame()));
    		result.setIntroduced(userProfile.getIntroduced());
    		result.setIndustry(userProfile.getIndustry());
    		result.setIndustryId(userProfile.getIndustryId());
    		result.setV_lv(userProfile.getvLv());

    		result.setContent(recFriend.getDescription());
    	}else{
    		result.setIsRec(0);
    		//没有推荐，则说明是需要推荐的，则判断是否好友，只有才能推荐
    		UserFriend userFriend = userDao.getUserFriendBySourceUidAndTargetUid(request.getFromUid(), request.getCustomerId());
    		if(null == userFriend || userFriend.getStatus() != 0){
    			//不是好友，则返回不是好友状态
    			result.setIsFriend(0);
    			return Response.success(result);
    		}
    		result.setIsFriend(1);
    		//判断是否有沉默期
    		long currentTime = System.currentTimeMillis();
    		if(userFriend.getSilenceTime().getTime() > currentTime){//在沉默期,返回沉默期相关参数
    			result.setSilenceTime((userFriend.getSilenceTime().getTime()-currentTime)/1000);//秒数
    			String silencePeriodConfig = appConfigService.getAppConfigByKey("FRIEND_REC_SILENCE_PERIOD");
        		int silencePeriod = 0;
        		if(StringUtils.isNotBlank(silencePeriodConfig)){
        			try{
        				silencePeriod = Integer.valueOf(silencePeriodConfig);
        			}catch(Exception ignore){
        			}
        		}
        		result.setSilencePeriod(silencePeriod);
        		return Response.success(result);
    		}
    		
    		ExtUserProfile userProfile = extUserMapper.getExtUserProfileByUid(request.getCustomerId());
    		result.setUid(request.getCustomerId());
    		result.setNickName(userProfile.getNickName());
    		result.setAvatar(baseService.genAvatar(userProfile.getAvatar()));
    		result.setAvatarFrame(baseService.genQiNiuImageUrl(userProfile.getAvatarFrame()));
    		result.setIntroduced(userProfile.getIntroduced());
    		result.setIndustry(userProfile.getIndustry());
    		result.setIndustryId(userProfile.getIndustryId());
    		result.setV_lv(userProfile.getvLv());
    		//判断以前是否推荐过
    		UserFriendReclist lastRecFriend = userDao.getLastUserFriendReclistByUidAndTargetUid(request.getFromUid(), request.getCustomerId());
    		if(null != lastRecFriend){
    			result.setContent(lastRecFriend.getDescription());
    		}else{
    			result.setContent("");
    		}
    	}
    	
    	return Response.success(result);
    }
    
    public Response<GetIMUsertokenResponse> getIMUsertoken(GetIMUsertokenRequest request){
    	GetIMUsertokenResponse result = new GetIMUsertokenResponse();
    	result.setUserId(String.valueOf(request.getCustomerId()));
        
        ImConfig imConfig = userDao.getImConfigByUid(request.getCustomerId());
        if(null == imConfig || StringUtils.isEmpty(imConfig.getToken())
        		|| imConfig.getToken().contains("测试环境")){
        	//重新获取
        	UserProfile u = userDao.getUserProfileByUid(request.getCustomerId());
        	if(null != u){
        		try{
        			ImUserInfoDto imDTO = smsService.getIMUsertoken(request.getCustomerId(), u.getNickName(), baseService.genAvatar(u.getAvatar()));
        			if(null != imDTO){
        				if(null == imConfig){
            				imConfig = new ImConfig();
            				imConfig.setUid(request.getCustomerId());
            				imConfig.setToken(imDTO.getToken());
            				imConfig.setCreateTime(new Date());
            				imConfigMapper.insertSelective(imConfig);
            			}else{
            				ImConfig updateImConfig = new ImConfig();
            				updateImConfig.setId(imConfig.getId());
            				updateImConfig.setToken(imDTO.getToken());
            				imConfigMapper.updateByPrimaryKeySelective(updateImConfig);
            			}
        				result.setToken(imDTO.getToken());
        				return Response.success(result);
        			}
        		}catch(Exception e){
        			log.error("重新获取失败", e);
        		}
        	}
        	if(null != imConfig){
        		result.setToken(imConfig.getToken());
                return Response.success(result);
        	}
        }else{
        	result.setToken(imConfig.getToken());
            return Response.success(result);
        }
        return Response.failure(ResponseStatus.QI_QUERY_FAILURE.status, ResponseStatus.QI_QUERY_FAILURE.message);
    }

	public Response<GuideInfoResponse> getGuideInfo() {
		String uidStr = appConfigService.getAppConfigByKey("GUIDE_UID");
		if(StringUtils.isEmpty(uidStr)){
			return Response.failure(500, "没有配置导游用户");
		}else{
			long uid  = Long.parseLong(uidStr);
			GuideInfoResponse guideInfoResponse = new GuideInfoResponse();
			UserProfile userProfile = userDao.getUserProfileByUid(uid);
			guideInfoResponse.setUid(uid);
			guideInfoResponse.setNickName(userProfile.getNickName());
			guideInfoResponse.setAvatar(baseService.genAvatar(userProfile.getAvatar()));
			return Response.success(guideInfoResponse);
		}
	}

	public Response<VersionControlResponse> versionControl(VersionControlRequest request, String ip) {
		log.info("ip address :" + ip);
        log.info("add channel count start ...");
        OpenDeviceCount openDeviceCount = new OpenDeviceCount();
        openDeviceCount = UserCopier.INSTANCE.asOpenDeviceCount(request, ip);
        openDeviceCount.setCreatTime(new Date());
        userDao.saveOpenCount(openDeviceCount);
        log.info("add channel count end ...");
        
        VersionControlResponse versionControlResponse = new VersionControlResponse();
        //获取加号页情绪坐标开关配置
        versionControlResponse.setEmotionSwitch(0);//默认关
        String plusEmotionSwitch = appConfigService.getAppConfigByKey("PLUS_EMOTION_SWITCH");
        if(!StringUtils.isEmpty(plusEmotionSwitch) && "1".equals(plusEmotionSwitch)){
        	versionControlResponse.setEmotionSwitch(1);
        }
        
        //春节特定标识
        AppUiControl appUi = extUserMapper.getAppUiControl();
        if(appUi != null){
        	versionControlResponse.setResourceCode(appUi.getSourceCode());
        }else{
        	versionControlResponse.setResourceCode(0);
        }

        boolean hasNewVersion = false;
        VersionControl newestVersion = userDao.getNewestVersion(request.getPlatform());
        if(null != newestVersion && !StringUtils.isEmpty(newestVersion.getVersion())
        		&& !StringUtils.isEmpty(request.getVersion())){
        	hasNewVersion = this.hasNewVersion(request.getVersion(), newestVersion.getVersion());
        }
        if(!hasNewVersion){
        	return Response.success(versionControlResponse);
        }
        
        //根据渠道配置进行提醒
        VersionChannelDownload vcd = userDao.getVersionChannelDownloadByChannel(request.getChannel());
        if(null != vcd && vcd.getType() == 1 && !StringUtils.isEmpty(vcd.getVersionAddr())){
        	versionControlResponse = UserCopier.INSTANCE.asVersionControlResponse(newestVersion, vcd);
            if(newestVersion.getForceUpdate() == 1){
            	versionControlResponse.setIsUpdate(Specification.VersionStatus.FORCE_UPDATE.index);
            }else{
            	versionControlResponse.setIsUpdate(Specification.VersionStatus.UPDATE.index);
            }
        }
        
        return Response.success(versionControlResponse);
	}
	
	private boolean hasNewVersion(String currentVersion, String newestVersion){
    	String[] cv = currentVersion.split("\\.");
    	String[] nv = newestVersion.split("\\.");
    	if(cv.length>=3 && nv.length>=3){
        	try{
        		int cv1 = Integer.valueOf(cv[0]);
        		int nv1 = Integer.valueOf(nv[0]);
        		if(cv1>nv1){
        			return false;
        		}else if(cv1<nv1){
        			return true;
        		}else{
        			int cv2 = Integer.valueOf(cv[1]);
            		int nv2 = Integer.valueOf(nv[1]);
            		if(cv2>nv2){
            			return false;
            		}else if(cv2<nv2){
            			return true;
            		}else{
            			int cv3 = Integer.valueOf(cv[2]);
                		int nv3 = Integer.valueOf(nv[2]);
                		if(cv3>nv3){
                			return false;
                		}else if(cv3<nv3){
                			return true;
                		}else{
                			if(cv.length > 3){
                				if(nv.length > 3){
                					if(nv[3].compareTo(cv[3]) > 0){
                						return true;
                					}else{
                						return false;
                					}
                				}else{
                					return false;
                				}
                			}else{
                				return false;
                			}
                		}
            		}
        		}
        	}catch(Exception ignore){
        	}
    	}
    	return false;
    }

	public Response<MyLevelResponse> getMyLevel(long uid) {
		MyLevelResponse myLevelResponse = new MyLevelResponse();
	    UserProfile userProfile = userDao.getUserProfileByUid(uid);
	    MyLevelResponse.InnerLevel currentLevel = myLevelResponse.createInnerLevel();
        currentLevel.setLevel(userProfile.getLevel());
        MyLevelResponse.InnerLevel nextLevel = myLevelResponse.createInnerLevel();
        if(userProfile.getLevel() != 9){
            nextLevel.setLevel( userProfile.getLevel()+1);
        }

        MyLevelResponse.InnerLevel preLevel = myLevelResponse.createInnerLevel();
        if (userProfile.getLevel() > 1){
            preLevel.setLevel(userProfile.getLevel()-1);
        }
        myLevelResponse.setAvailableCoin(userProfile.getAvailableCoin());
        myLevelResponse.setAvatar(baseService.genAvatar(userProfile.getAvatar()));
        String value = appConfigService.getAppConfigByKey(USER_PERMISSIONS);
        UserPermissionDto userPermissionDto = JSON.parseObject(value, UserPermissionDto.class);
        for(UserPermissionDto.UserLevelDto userLevelDto : userPermissionDto.getLevels()){
            if (userProfile.getLevel() == userLevelDto.getLevel()){
                currentLevel.setName(userLevelDto.getName());
            }
            if (userProfile.getLevel()+1 == userLevelDto.getLevel()  && userProfile.getLevel() != 9){
                nextLevel.setName(userLevelDto.getName());
            }
            if (userProfile.getLevel()-1 == userLevelDto.getLevel() && userProfile.getLevel() > 1 ){
                preLevel.setName(userLevelDto.getName());
            }
            if((userProfile.getLevel()+1) == userLevelDto.getLevel()){
                myLevelResponse.setNextLevelCoin(userLevelDto.getNeedCoins()-userProfile.getAvailableCoin());
            }
        }
        myLevelResponse.setCurrentLevel(currentLevel);
        if (userProfile.getLevel()>1){
            myLevelResponse.setPreLevel(preLevel);
        }
        myLevelResponse.setNextLevel(nextLevel);
        //获取权限内容
        String level = userProfile.getLevel().toString();
        String levelValue = appConfigService.getAppConfigByKey("LEVEL_"+level);
        ExtPermissionDescription permissionDescriptionDto = JSON.parseObject(levelValue, ExtPermissionDescription.class);
        List<ExtPermissionDescription.PermissionNodeDto> permissionNodeDtos = Lists.newArrayList();
        
        Map<Integer,PermissionNodeDto> map = new HashMap<Integer,PermissionNodeDto>();
        
        for(ExtPermissionDescription.PermissionNodeDto nodeDto : permissionDescriptionDto.getNodes()){
        	map.put(nodeDto.getCode(), nodeDto);
        }
        
        Integer[] sorts = new Integer[]{1,2,3,6,5,7,4};
        for(Integer sort : sorts){
        	permissionNodeDtos.add(map.get(sort));
        }

        for(ExtPermissionDescription.PermissionNodeDto nodeDto : permissionDescriptionDto.getNodes()){
            if(nodeDto.getIsShow()==1){
                nodeDto.setIsShow(null);
                if(nodeDto.getStatus()!=1){
                    // 找寻哪个级别开通该功能
                    int openLevel = checkIsOpenLevel(nodeDto.getCode());
                    nodeDto.setOpenLevel(openLevel);
                }
            }
        }
        permissionDescriptionDto.setNodes(permissionNodeDtos);

        myLevelResponse.setPermissions(permissionDescriptionDto);
        //随机获取可偷王国id
        Date todayStart = DateUtil.getStartOfToday();
        List<Long> stealTopics= extUserMapper.getCanStealTopicId(uid,todayStart);
        Random random = new Random();
        if(stealTopics.size()==0){
            myLevelResponse.setStealTopicId(0);
        }else {
            int stealKey = random.nextInt(stealTopics.size());
            myLevelResponse.setStealTopicId(stealTopics.get(stealKey));
        }
        //随机评论王国id
        List<Long> speakTopics= extUserMapper.getCanSpeakTopicId(uid);
        int randomKey = random.nextInt(speakTopics.size());
        myLevelResponse.setRandomTopicId(speakTopics.get(randomKey));
        //转换米汤币为人民币
        String exchangeRate = appConfigService.getAppConfigByKey("EXCHANGE_RATE");
        if (StringUtils.isEmpty(exchangeRate)){
            exchangeRate = "100";
        }
        Integer  i = myLevelResponse.getAvailableCoin();
        Double d = i.doubleValue();
        BigDecimal bg = new BigDecimal(d/Integer.parseInt(exchangeRate));
        double RMB = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        myLevelResponse.setPriceRMB(RMB);
        return Response.success(myLevelResponse);
    }
	
	 private List<ExtPermissionDescription> getPermissionConfig(int lastLevel){
	        List<ExtPermissionDescription> ret = Lists.newArrayList();
	        for(int i = 1;i<=lastLevel;i++){
	            String configValue = appConfigService.getAppConfigByKey("LEVEL_"+i);
	            ExtPermissionDescription permissionDescriptionDto = JSON.parseObject(configValue, ExtPermissionDescription.class);
	            ret.add(permissionDescriptionDto);
	        }
	        return ret;
	    }
	
	 private int checkIsOpenLevel(int code){
	        List<ExtPermissionDescription> config = getPermissionConfig(9);
	        for(ExtPermissionDescription permissionDescriptionDto : config){
	            for(ExtPermissionDescription.PermissionNodeDto node : permissionDescriptionDto.getNodes()){
	                if(node.getStatus()==1 && node.getCode().equals(code)){
	                    return permissionDescriptionDto.getLevel();
	                }
	            }
	        }
	        return 0;
	    }

	public Response<BasicDataSuccessResponse> getBasicDataByType(BasicDataRequest request) {
        log.info("getBasicDataByType start ... type = " + request.getType());
        DictionaryType dictionaryType = userDao.getDictionaryType(request.getType());
        log.info("type name is :" + dictionaryType.getName());
        List<Dictionary> dictionarys = userDao.getDictionary(request.getType());
        BasicDataSuccessResponse basicDataSuccessResponse = new BasicDataSuccessResponse();
        BasicDataSuccessResponse.BasicDataSuccessElement basicDataSuccess = BasicDataSuccessResponse.createElement();
        basicDataSuccess.setTid(request.getType());
        basicDataSuccess.setType(dictionaryType.getName());
        basicDataSuccess.setList(dictionarys);
        basicDataSuccessResponse.getResults().add(basicDataSuccess);
        log.info("get type data success");
        log.info("getBasicDataByType end ...");
        return Response.success(basicDataSuccessResponse);
	}

	public Response<CheckNameOpenIdResponse> checkNameOpenId(CheckNameOpenIdRequest request) {
        if(request.getThirdPartType() == 2){//兼容新老版本 老版本是没有这个字段的 不会走这里(只有微信会传unionId)
            if(!StringUtils.isEmpty(request.getOpenId()) && !StringUtils.isEmpty(request.getUnionId())){
                ThirdPartUser thirdPartUserOpenId = userDao.checkOpenId(request.getOpenId());
                ThirdPartUser thirdPartUserUnionId = userDao.checkUnionId(request.getUnionId());
                if(thirdPartUserOpenId != null && thirdPartUserUnionId != null) {//同一用户
                   UserProfile userProfile = userDao.getUserProfileByUid(thirdPartUserUnionId.getUid());
                    if(userProfile.getIsClientLogin() == 1) {//h5登录过需要检查昵称
                    	CheckNameOpenIdResponse checkNameOpenIdResponse = new CheckNameOpenIdResponse();
                    	checkNameOpenIdResponse.setNickName(userProfile.getNickName());
                        return Response.success(ResponseStatus.THIRDPARTUSER_EXISTS.status,ResponseStatus.THIRDPARTUSER_EXISTS.message,checkNameOpenIdResponse);
                    }
                    return Response.success(ResponseStatus.USER_EXISTS.status,ResponseStatus.USER_EXISTS.message);
                }else if(thirdPartUserOpenId != null && thirdPartUserUnionId == null){//app老用户登陆过 返回注册过 h5没登陆过不需要检查 老用户检查过昵称的
                    return Response.success(ResponseStatus.USER_EXISTS.status,ResponseStatus.USER_EXISTS.message);
                }else if(thirdPartUserOpenId == null && thirdPartUserUnionId !=null){
                    UserProfile userProfile = userDao.getUserProfileByUid(thirdPartUserUnionId.getUid());
                    if(userProfile.getIsClientLogin() == 1) {//h5登录过需要检查昵称
                    	CheckNameOpenIdResponse checkNameOpenIdResponse = new CheckNameOpenIdResponse();
                    	checkNameOpenIdResponse.setNickName(userProfile.getNickName());
                        return Response.success(ResponseStatus.THIRDPARTUSER_EXISTS.status,ResponseStatus.THIRDPARTUSER_EXISTS.message,checkNameOpenIdResponse);
                    }
                    return Response.success(ResponseStatus.USER_EXISTS.status,ResponseStatus.USER_EXISTS.message);
                }else {//都为空的情况下提示不存在 无该用户
                    return Response.success(ResponseStatus.OPENID_DONT_EXISTS.status, ResponseStatus.OPENID_DONT_EXISTS.message);
                }
            }
            else{//以防出错没传openId和unionId报错
                return Response.success(ResponseStatus.OPENID_DONT_EXISTS.status,ResponseStatus.OPENID_DONT_EXISTS.message);
            }
        }else if(!StringUtils.isEmpty(request.getOpenId())) {// qq weibo weixin 老版本
            ThirdPartUser thirdPartUser = userDao.checkOpenId(request.getOpenId());
            if(thirdPartUser!=null) {
                return Response.success(ResponseStatus.USER_EXISTS.status,ResponseStatus.USER_EXISTS.message);
            }else{
                return  Response.success(ResponseStatus.OPENID_DONT_EXISTS.status,ResponseStatus.OPENID_DONT_EXISTS.message);
            }
        }else{
            List<UserProfile> userProfiles = userDao.checkUserNickName(request.getNickName());
            //兼容emoji 这样判断才可以
            if(userProfiles != null && userProfiles.size() > 0) {
                for(UserProfile userProfile : userProfiles){
                    if (request.getNickName().equals(userProfile.getNickName())) {
                        return Response.failure(ResponseStatus.USER_NICKNAME_EXISTS.status, ResponseStatus.USER_NICKNAME_EXISTS.message);
                    }
                }
            }
        }
        return Response.success(ResponseStatus.USER_NICKNAME_DONT_EXISTS.status,ResponseStatus.USER_NICKNAME_DONT_EXISTS.message);
	}

	public Response<GetLevelListResponse> getLevelList(GetLevelListRequest request) {
		GetLevelListResponse getLevelListResponse = new GetLevelListResponse(); 	
		String value = appConfigService.getAppConfigByKey(USER_PERMISSIONS);
        getLevelListResponse = JSON.parseObject(value, GetLevelListResponse.class);
        for(GetLevelListResponse.UserLevelDto  userLevelDto : getLevelListResponse.getLevels()){
            userLevelDto.setPermissions(null);
        }
        String levelDefinition = appConfigService.getAppConfigByKey(LEVEL_DEFINITION);
        getLevelListResponse.setLevelDefinition(levelDefinition);
        return Response.success(getLevelListResponse);
	}

	public Response<GetLastEmotionInfoResponse> getLastEmotionInfo(GetLastEmotionInfoRequest request) {
        try {
        	GetLastEmotionInfoResponse  getLastEmotionInfoResponse = new GetLastEmotionInfoResponse();
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(date);
            int n = -1;
            if(cal1.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
                n=-2;
            }
            String monday;
            cal1.add(Calendar.DATE, n*7);
            cal1.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
            monday = sdf1.format(cal1.getTime());
            Date mondayDate=null;

            mondayDate = sdf1.parse(monday);


            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(date);
            int m = 0;
            if(cal2.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
                m=-1;
            }
            String sunday;
            cal2.add(Calendar.DATE, m*7);
            cal2.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
            sunday = sdf2.format(cal2.getTime());
            Date sundayDate = sdf2.parse(sunday);


            SimpleDateFormat dsdf = new SimpleDateFormat("M月dd日");
            String dateStr = dsdf.format(mondayDate)+"-"+dsdf.format(sundayDate);
            getLastEmotionInfoResponse.setDateStr(dateStr);
            //判断是否周总结
            if(userDao.exsitEmotionRecord(request.getUid(), mondayDate, sundayDate)){
            	ExtEmotionSummaryModel EmotionSummaryModel =  new ExtEmotionSummaryModel(sdf.format(mondayDate),request.getUid(), "0");
                String isSummaryStr = redisService.hGet(EmotionSummaryModel.getKey(), EmotionSummaryModel.getField());
                if (!StringUtils.isEmpty(isSummaryStr)) {
                    getLastEmotionInfoResponse.setIsSummary(0);
                } else {
                    getLastEmotionInfoResponse.setIsSummary(1);
                }
            }else{
                getLastEmotionInfoResponse.setIsSummary(0);
            }
            EmotionRecord emotionRecord = userDao.getLastEmotionRecord(request.getUid());
            if(emotionRecord==null){
                getLastEmotionInfoResponse.setId(0);
            }else{
                EmotionInfo emotionInfo = userDao.getEmotionInfoByKey(emotionRecord.getEmotionid());
                if (emotionInfo == null) {
                    return Response.failure(500, "没有该情绪信息！");
                }
                EmotionPackDetail emotionPackDetail = emotionPackDetailMapper.selectByPrimaryKey(Integer.valueOf(emotionInfo.getEmotionpackid() + ""));
                if (emotionPackDetail == null) {
                    return Response.failure(500, "没有该情绪大表情信息！");
                }
                int recordCount = userDao.countUserEmotionRecord(request.getUid());
                getLastEmotionInfoResponse = UserCopier.INSTANCE.asGetLastEmotionInfoResponse(emotionInfo, emotionRecord);
                getLastEmotionInfoResponse.setRecordCount(recordCount);
                GetLastEmotionInfoResponse.EmotionPack ep =GetLastEmotionInfoResponse.createEmotionPack();
                ep = UserCopier.INSTANCE.asEmotionPack(emotionPackDetail);
                ep.setImage(baseService.genQiNiuImageUrl(emotionPackDetail.getImage()));
                ep.setThumb(baseService.genQiNiuImageUrl(emotionPackDetail.getThumb()));
                ep.setEmojiType(1);
                getLastEmotionInfoResponse.setEmotionPack(ep);
                long timeInterval = (date.getTime()-emotionRecord.getCreateTime().getTime())/1000;
                getLastEmotionInfoResponse.setTimeInterval(timeInterval);
            }
            return  Response.success(getLastEmotionInfoResponse);
        } catch (ParseException e) {
            return  Response.failure(500,"时间转换错误");
        }
    
	}
}
