package com.m2m.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.m2m.domain.Dictionary;
import com.m2m.domain.DictionaryExample;
import com.m2m.domain.DictionaryType;
import com.m2m.domain.DictionaryTypeExample;
import com.m2m.domain.EmotionInfo;
import com.m2m.domain.EmotionRecord;
import com.m2m.domain.EmotionRecordExample;
import com.m2m.domain.ImConfig;
import com.m2m.domain.ImConfigExample;
import com.m2m.domain.OpenDeviceCount;
import com.m2m.domain.RuleLog;
import com.m2m.domain.RuleLogExample;
import com.m2m.domain.ThirdPartUser;
import com.m2m.domain.ThirdPartUserExample;
import com.m2m.domain.User;
import com.m2m.domain.UserExample;
import com.m2m.domain.UserFamous;
import com.m2m.domain.UserFamousExample;
import com.m2m.domain.UserFirstLog;
import com.m2m.domain.UserFirstLogExample;
import com.m2m.domain.UserFollow;
import com.m2m.domain.UserFollowExample;
import com.m2m.domain.UserFriend;
import com.m2m.domain.UserFriendApply;
import com.m2m.domain.UserFriendApplyExample;
import com.m2m.domain.UserFriendExample;
import com.m2m.domain.UserFriendHis;
import com.m2m.domain.UserFriendHisExample;
import com.m2m.domain.UserFriendMessageExample;
import com.m2m.domain.UserFriendReclist;
import com.m2m.domain.UserFriendReclistExample;
import com.m2m.domain.UserGag;
import com.m2m.domain.UserGagExample;
import com.m2m.domain.UserHobby;
import com.m2m.domain.UserHobbyExample;
import com.m2m.domain.UserIndustry;
import com.m2m.domain.UserIndustryExample;
import com.m2m.domain.UserIndustryWrong;
import com.m2m.domain.UserIndustryWrongExample;
import com.m2m.domain.UserInvitationHis;
import com.m2m.domain.UserInvitationHisExample;
import com.m2m.domain.UserLastChannel;
import com.m2m.domain.UserLastChannelExample;
import com.m2m.domain.UserNoticeUnreadExample;
import com.m2m.domain.UserProfile;
import com.m2m.domain.UserProfileExample;
import com.m2m.domain.UserStealLog;
import com.m2m.domain.UserStealLogExample;
import com.m2m.domain.UserToken;
import com.m2m.domain.UserTokenExample;
import com.m2m.domain.UserVisitLog;
import com.m2m.domain.VersionChannelDownload;
import com.m2m.domain.VersionChannelDownloadExample;
import com.m2m.domain.VersionControl;
import com.m2m.domain.VersionControlExample;
import com.m2m.enums.USER_OPRATE_TYPE;
import com.m2m.mapper.DictionaryMapper;
import com.m2m.mapper.DictionaryTypeMapper;
import com.m2m.mapper.EmotionInfoMapper;
import com.m2m.mapper.EmotionRecordMapper;
import com.m2m.mapper.ImConfigMapper;
import com.m2m.mapper.OpenDeviceCountMapper;
import com.m2m.mapper.RuleLogMapper;
import com.m2m.mapper.ThirdPartUserMapper;
import com.m2m.mapper.UserFamousMapper;
import com.m2m.mapper.UserFirstLogMapper;
import com.m2m.mapper.UserFollowMapper;
import com.m2m.mapper.UserFriendApplyMapper;
import com.m2m.mapper.UserFriendHisMapper;
import com.m2m.mapper.UserFriendMapper;
import com.m2m.mapper.UserFriendMessageMapper;
import com.m2m.mapper.UserFriendReclistMapper;
import com.m2m.mapper.UserGagMapper;
import com.m2m.mapper.UserHobbyMapper;
import com.m2m.mapper.UserIndustryMapper;
import com.m2m.mapper.UserIndustryWrongMapper;
import com.m2m.mapper.UserInvitationHisMapper;
import com.m2m.mapper.UserLastChannelMapper;
import com.m2m.mapper.UserMapper;
import com.m2m.mapper.UserNoticeUnreadMapper;
import com.m2m.mapper.UserProfileMapper;
import com.m2m.mapper.UserStealLogMapper;
import com.m2m.mapper.UserTokenMapper;
import com.m2m.mapper.UserVisitLogMapper;
import com.m2m.mapper.VersionChannelDownloadMapper;
import com.m2m.mapper.VersionControlMapper;
import com.m2m.util.FirstCharUtils;

@Repository
public class UserDao {
	
	@Autowired
	private UserGagMapper userGagMapper;
	@Autowired
	private UserTokenMapper userTokenMapper;
	@Autowired
	private EmotionRecordMapper emotionRecordMapper;
    @Autowired
    private EmotionInfoMapper emotionInfoMapper;
	@Autowired
	private UserProfileMapper userProfileMapper;
	@Autowired
	private UserFollowMapper userFollowMapper;
	@Autowired
	private UserFirstLogMapper userFirstLogMapper;
	@Autowired
	private UserLastChannelMapper userLastChannelMapper;
	@Autowired
	private UserNoticeUnreadMapper userNoticeUnreadMapper;
	@Autowired
	private UserInvitationHisMapper userInvitationHisMapper;
	@Autowired
	private UserHobbyMapper userHobbyMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private RuleLogMapper ruleLogMapper;
	@Autowired
	private UserIndustryMapper userIndustryMapper;
	@Autowired
	private UserIndustryWrongMapper userIndustryWrongMapper;
	@Autowired
	private UserFriendHisMapper userFriendHisMapper;
	@Autowired
	private UserFriendMapper userFriendMapper;
	@Autowired
	private UserFriendApplyMapper userFriendApplyMapper;
	@Autowired
	private UserFriendReclistMapper userFriendReclistMapper;
	@Autowired
	private UserFamousMapper userFamousMapper;
	@Autowired
	private UserVisitLogMapper userVisitLogMapper;
	@Autowired
	private UserStealLogMapper userStealLogMapper;
	@Autowired
	private ImConfigMapper imConfigMapper;
	@Autowired
	private UserFriendMessageMapper userFriendMessageMapper;
	@Autowired
	private OpenDeviceCountMapper openDeviceCountMapper;
	@Autowired
    private VersionControlMapper versionControlMapper;
    @Autowired
    private VersionChannelDownloadMapper versionChannelDownloadMapper;
    @Autowired
    private DictionaryTypeMapper dictionaryTypeMapper;
    @Autowired
    private DictionaryMapper dictionaryMapper;
    @Autowired
    private ThirdPartUserMapper thirdPartUserMapper;
    
    public List<UserProfile> getUserProfilesByUids(List<Long> uids) {
    	if(uids.size()>0){
	        UserProfileExample example = new UserProfileExample();
	        UserProfileExample.Criteria criteria = example.createCriteria();
	        criteria.andUidIn(uids);
	        example.setOrderByClause(" id desc ");
	        return userProfileMapper.selectByExample(example);
    	}else{
    		return new ArrayList<>();
    	}
    }
    
    public UserProfile getUserProfileByUid(long uid){
    	UserProfileExample example = new UserProfileExample();
        UserProfileExample.Criteria criteria = example.createCriteria();
        criteria.andUidEqualTo(uid);
        List<UserProfile> list = userProfileMapper.selectByExample(example);
        if(null != list && list.size() > 0){
        	return list.get(0);
        }
        return null;
    }
    public void updateUserProfile(UserProfile userProfile){
    	if(null != userProfile.getNickName()){
			String initial = FirstCharUtils.getInitial(userProfile.getNickName());
    		userProfile.setNameGroup(initial);
    	}
    	userProfileMapper.updateByPrimaryKeySelective(userProfile);
    }

	public List<UserProfile> checkUserNickName(String nickName) {
		UserProfileExample example = new UserProfileExample();
        UserProfileExample.Criteria criteria = example.createCriteria();
        criteria.andNickNameEqualTo(nickName);
        return userProfileMapper.selectByExample(example);
	}
    
    public List<UserFollow> getAllFollows(long uid, List<Long> uids){
    	UserFollowExample example = new UserFollowExample();
        UserFollowExample.Criteria criteria =  example.createCriteria();
        criteria.andSourceUidEqualTo(uid);
        criteria.andTargetUidIn(uids);
        UserFollowExample.Criteria criteria2 =  example.createCriteria();
        criteria2.andSourceUidIn(uids);
        criteria2.andTargetUidEqualTo(uid);
        example.or(criteria2);
        return userFollowMapper.selectByExample(example);
    }
    
    public UserFollow getUserFollowBySourceUidAndTargetUid(long sourceUid, long targetUid){
    	UserFollowExample example = new UserFollowExample();
        UserFollowExample.Criteria criteria =  example.createCriteria();
        criteria.andSourceUidEqualTo(sourceUid);
        criteria.andTargetUidEqualTo(targetUid);
        List<UserFollow> list = userFollowMapper.selectByExample(example);
        if(null != list && list.size() > 0){
        	return list.get(0);
        }
        return null;
    }

	public UserGag getUserGagByUid(long uid){
		UserGagExample example = new UserGagExample();
		UserGagExample.Criteria criteria = example.createCriteria();
		criteria.andUidEqualTo(uid);
		List<UserGag> list = userGagMapper.selectByExample(example);
		if(null != list && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	public UserToken getUserTokenByUid(long uid){
		UserTokenExample example = new UserTokenExample();
		UserTokenExample.Criteria criteria = example.createCriteria();
		criteria.andUidEqualTo(uid);
		List<UserToken> list = userTokenMapper.selectByExample(example);
		if(null != list && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	public int countUserEmotionRecord(long uid){
		EmotionRecordExample example = new EmotionRecordExample();
		EmotionRecordExample.Criteria criteria = example.createCriteria();
		criteria.andUidEqualTo(uid);
		return emotionRecordMapper.countByExample(example);
	}
	
	public List<EmotionRecord> getUserEmotionRecord(long uid, int limitSize){
    	EmotionRecordExample example = new EmotionRecordExample();
    	EmotionRecordExample.Criteria criteria = example.createCriteria();
    	criteria.andUidEqualTo(uid);
    	if(limitSize > 0){
    		example.setOrderByClause(" id desc limit " + limitSize);
    	}else{
    		example.setOrderByClause(" id desc ");
    	}
    	return emotionRecordMapper.selectByExample(example);
    }
	
	public boolean exsitEmotionRecord(long uid, Date start, Date end) {
		EmotionRecordExample example = new EmotionRecordExample();
		EmotionRecordExample.Criteria criteria  = example.createCriteria();
		criteria.andCreateTimeBetween(start, end);
		criteria.andUidEqualTo(uid);
		int conut = emotionRecordMapper.countByExample(example);
		return conut>0;
	}
	
	public EmotionRecord getLastEmotionRecord(long uid) {
		EmotionRecordExample example = new EmotionRecordExample();
		EmotionRecordExample.Criteria criteria  = example.createCriteria();
		criteria.andUidEqualTo(uid);
		example.setOrderByClause(" id desc limit 0,1 ");
		List<EmotionRecord> list = emotionRecordMapper.selectByExample(example);
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	public UserFirstLog getUserFirstLogByUidAndActionType(long uid, int actionType){
		UserFirstLogExample example = new UserFirstLogExample();
		UserFirstLogExample.Criteria criteria = example.createCriteria();
		criteria.andUidEqualTo(uid);
		criteria.andActionTypeEqualTo(actionType);
		List<UserFirstLog> list = userFirstLogMapper.selectByExample(example);
		if(null != list && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	public UserLastChannel getUserLastChannelByUid(long uid){
		UserLastChannelExample example = new UserLastChannelExample();
		UserLastChannelExample.Criteria criteria = example.createCriteria();
		criteria.andUidEqualTo(uid);
		List<UserLastChannel> list = userLastChannelMapper.selectByExample(example);
		if(null != list && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	public void clearUserNoticeUnreadByCid(long uid, int contentType, long cid){
    	UserNoticeUnreadExample example = new UserNoticeUnreadExample();
    	UserNoticeUnreadExample.Criteria criteria = example.createCriteria();
    	criteria.andUidEqualTo(uid);
    	criteria.andContentTypeEqualTo(contentType);
    	criteria.andCidEqualTo(cid);
    	userNoticeUnreadMapper.deleteByExample(example);
    }

    public UserInvitationHis getUserLastestInvitation(long uid, int status){
    	UserInvitationHisExample example = new UserInvitationHisExample();
    	UserInvitationHisExample.Criteria criteria = example.createCriteria();
    	criteria.andUidEqualTo(uid);
    	criteria.andStatusEqualTo(status);
    	example.setOrderByClause(" id asc limit 1 ");
    	List<UserInvitationHis> list = userInvitationHisMapper.selectByExample(example);
    	if(null != list && list.size() > 0){
    		return list.get(0);
    	}
    	return null;
    }

	public List<UserFollow> getFans(long uid) {
		UserFollowExample example = new UserFollowExample();
		UserFollowExample.Criteria criteria =  example.createCriteria();
		criteria.andTargetUidEqualTo(uid);
		return userFollowMapper.selectByExample(example);
	}
    
    public List<UserHobby> getUserHobbys(long uid){
    	UserHobbyExample example = new UserHobbyExample();
    	UserHobbyExample.Criteria criteria = example.createCriteria();
    	criteria.andUidEqualTo(uid);
    	return userHobbyMapper.selectByExample(example);
    }
    
    public User getUserByUid(long uid){
    	UserExample example = new UserExample();
    	UserExample.Criteria criteria = example.createCriteria();
    	criteria.andUidEqualTo(uid);
    	criteria.andStatusEqualTo(0);
    	List<User> list = userMapper.selectByExample(example);
    	if(null != list && list.size() > 0){
    		return list.get(0);
    	}
    	return null;
    }
    
    public List<RuleLog> getRuleLogListByUid(long uid){
    	RuleLogExample example = new RuleLogExample();
    	RuleLogExample.Criteria criteria = example.createCriteria();
    	criteria.andUidEqualTo(uid);
    	return ruleLogMapper.selectByExample(example);
    }

    public UserIndustry getUserIndustryById(long id){
    	UserIndustryExample example = new UserIndustryExample();
    	UserIndustryExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        List<UserIndustry> list = userIndustryMapper.selectByExample(example);
        if(null != list && list.size() > 0){
        	return list.get(0);
        }
        return null;
    }
    public List<UserIndustry> getUserIndustryByIds(List<Long> ids){
    	UserIndustryExample example = new UserIndustryExample();
    	UserIndustryExample.Criteria criteria = example.createCriteria();
    	if(ids!=null && ids.size()>0){
    		criteria.andIdIn(ids);
    	}
        List<UserIndustry> list = userIndustryMapper.selectByExample(example);
        return list;
    }
    public void delUserIndustryWrongByUid(long uid){
    	UserIndustryWrongExample example = new UserIndustryWrongExample();
    	UserIndustryWrongExample.Criteria criteria = example.createCriteria();
        criteria.andUidEqualTo(uid);
        userIndustryWrongMapper.deleteByExample(example);
    }
    
    public void saveUserIndustryWrong(UserIndustryWrong userIndustryWrong){
    	userIndustryWrongMapper.insertSelective(userIndustryWrong);
    }
    
    public UserFriendHis getLastUserFriendHis(long sourceUid, long targetUid, int type){
    	UserFriendHisExample example = new UserFriendHisExample();
    	UserFriendHisExample.Criteria criteria = example.createCriteria();
    	criteria.andSourceUidEqualTo(sourceUid);
    	criteria.andTargetUidEqualTo(targetUid);
    	criteria.andTypeEqualTo(type);
    	example.setOrderByClause(" id desc limit 1");
    	List<UserFriendHis> list = userFriendHisMapper.selectByExample(example);
    	if(null != list && list.size() > 0){
    		return list.get(0);
    	}
    	return null;
    }
    
    public UserFriend getUserFriendBySourceUidAndTargetUid(long sourceUid, long targetUid){
    	UserFriendExample example = new UserFriendExample();
    	UserFriendExample.Criteria criteria = example.createCriteria();
    	criteria.andSourceUidEqualTo(sourceUid);
    	criteria.andTargetUidEqualTo(targetUid);
    	List<UserFriend> list = userFriendMapper.selectByExample(example);
    	if(null != list && list.size() > 0){
    		return list.get(0);
    	}
    	return null;
    }
    
    public void updateUserFriendStatus(long sourceUid, long targetUid, int status){
    	UserFriendExample example = new UserFriendExample();
    	UserFriendExample.Criteria criteria = example.createCriteria();
    	criteria.andSourceUidEqualTo(sourceUid);
    	criteria.andTargetUidEqualTo(targetUid);
    	
    	UserFriend record = new UserFriend();
    	record.setStatus(status);
    	userFriendMapper.updateByExampleSelective(record, example);
    }
    
    public List<UserFriend> getUserFriendBySourceUidAndTargetUidList(long sourceUid, List<Long> targetUidList){
    	if(null == targetUidList || targetUidList.size() == 0){
    		return new ArrayList<>();
    	}
    	UserFriendExample example = new UserFriendExample();
    	UserFriendExample.Criteria criteria = example.createCriteria();
    	criteria.andSourceUidEqualTo(sourceUid);
    	criteria.andTargetUidIn(targetUidList);
    	criteria.andStatusEqualTo(0);
    	return userFriendMapper.selectByExample(example);
    }
    public List<UserFriend> getUserFriendBySourceUidListAndTargetUid(List<Long>  sourceUidList, long targetUid){
    	if(null == sourceUidList || sourceUidList.size() == 0){
    		return new ArrayList<>();
    	}
    	UserFriendExample example = new UserFriendExample();
    	UserFriendExample.Criteria criteria = example.createCriteria();
    	criteria.andTargetUidEqualTo(targetUid);
    	criteria.andSourceUidIn(sourceUidList);
    	criteria.andStatusEqualTo(0);
    	return userFriendMapper.selectByExample(example);
    }
    
    public UserFriendApply getUserFriendApplyBySourceUidAndTargetUidAndStatus(long sourceUid, long targetUid, int status){
    	UserFriendApplyExample example = new UserFriendApplyExample();
    	UserFriendApplyExample.Criteria criteria = example.createCriteria();
    	criteria.andSourceUidEqualTo(sourceUid);
    	criteria.andTargetUidEqualTo(targetUid);
    	if(status >= 0){
    		criteria.andStatusEqualTo(status);
    	}else{
    		criteria.andStatusNotEqualTo(2);//不等于删除
    	}
    	List<UserFriendApply> list = userFriendApplyMapper.selectByExample(example);
    	if(null != list && list.size() > 0){
    		return list.get(0);
    	}
    	return null;
    }
    
    public void delUserFriendApply(long sourceUid, long targetUid){
    	UserFriendApplyExample example = new UserFriendApplyExample();
    	UserFriendApplyExample.Criteria criteria = example.createCriteria();
    	criteria.andSourceUidEqualTo(sourceUid);
    	criteria.andTargetUidEqualTo(targetUid);
    	userFriendApplyMapper.deleteByExample(example);
    }
    
    public void delFriendRecByUidAndRecUid(long uid, long recUid){
    	UserFriendReclistExample example = new UserFriendReclistExample();
    	UserFriendReclistExample.Criteria criteria = example.createCriteria();
    	criteria.andUidEqualTo(uid);
    	criteria.andRecUidEqualTo(recUid);
    	
    	UserFriendReclist record = new UserFriendReclist();
    	record.setStatus(1);//删除
    	userFriendReclistMapper.updateByExampleSelective(record, example);
    }
    
    public int countFriendRecListByUid(long uid){
    	UserFriendReclistExample example = new UserFriendReclistExample();
    	UserFriendReclistExample.Criteria criteria = example.createCriteria();
    	criteria.andUidEqualTo(uid);
    	criteria.andStatusEqualTo(0);
    	return userFriendReclistMapper.countByExample(example);
    }
    
    public UserFriendReclist getUserFriendReclistByUidAndTargetUid(long uid, long targetUid){
    	UserFriendReclistExample example = new UserFriendReclistExample();
    	UserFriendReclistExample.Criteria criteria = example.createCriteria();
    	criteria.andUidEqualTo(uid);
    	criteria.andRecUidEqualTo(targetUid);
    	criteria.andStatusEqualTo(0);
    	List<UserFriendReclist> list = userFriendReclistMapper.selectByExample(example);
    	if(null != list && list.size() > 0){
    		return list.get(0);
    	}
    	return null;
    }
    
    public UserFriendReclist getLastUserFriendReclistByUidAndTargetUid(long uid, long targetUid){
    	UserFriendReclistExample example = new UserFriendReclistExample();
    	UserFriendReclistExample.Criteria criteria = example.createCriteria();
    	criteria.andUidEqualTo(uid);
    	criteria.andRecUidEqualTo(targetUid);
    	example.setOrderByClause(" id desc limit 1");
    	List<UserFriendReclist> list = userFriendReclistMapper.selectByExample(example);
    	if(null != list && list.size() > 0){
    		return list.get(0);
    	}
    	return null;
    }
    
    public void clearUserFriendRecByUid(long uid, long recUid){
    	UserFriendReclistExample example = new UserFriendReclistExample();
    	UserFriendReclistExample.Criteria criteria = example.createCriteria();
    	criteria.andUidEqualTo(uid);
    	criteria.andRecUidEqualTo(recUid);
    	
    	userFriendReclistMapper.deleteByExample(example);
    }
    
	public List<UserFamous> getUserFamousList(int page, int pageSize, List<Long> blacklistUids) {
		if (page <= 0) {
			page = 1;
		}
		if (pageSize <= 0) {
			pageSize = 20;
		}
		int start = (page - 1) * pageSize;
		UserFamousExample example = new UserFamousExample();
		if (null != blacklistUids && blacklistUids.size() > 0) {
			example.createCriteria().andUidNotIn(blacklistUids);
		}
		example.setOrderByClause(" update_time desc limit " + start + "," + pageSize);
		return userFamousMapper.selectByExample(example);
	}
	
	public UserFamous getUserFamousByUid(long uid){
        UserFamousExample example = new UserFamousExample();
        example.createCriteria().andUidEqualTo(uid);
        List<UserFamous> list = userFamousMapper.selectByExample(example);
        return list.size()>0&&list!=null?list.get(0):null;
    }

	public void addUserOprationLog(long uid, USER_OPRATE_TYPE action, String extra) {
		UserVisitLog record = new UserVisitLog();
		record.setAction(action.toString());
		record.setCreateTime(new Date());
		record.setExtra(extra);
		record.setTopicId(-1L);
		record.setUid(uid);
		userVisitLogMapper.insertSelective(record);
	}
    public List<UserStealLog> getUserStealLogByCountAsc(long uid,int count){
    	UserStealLogExample example = new UserStealLogExample();
    	UserStealLogExample.Criteria criteria =  example.createCriteria();
        criteria.andUidEqualTo(uid);
        example.setOrderByClause(" id asc limit " + count );
        return userStealLogMapper.selectByExample(example);
    }
    public int addStealLog(UserStealLog userStealLog){
    	return userStealLogMapper.insertSelective(userStealLog);
    }
	
	public ImConfig getImConfigByUid(long uid){
		ImConfigExample example = new ImConfigExample();
		ImConfigExample.Criteria criteria = example.createCriteria();
		criteria.andUidEqualTo(uid);
		List<ImConfig> list = imConfigMapper.selectByExample(example);
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	public void delFriendMsgByIds(List<Long> msgIdList){
		if(msgIdList.size() > 0){
			UserFriendMessageExample example = new UserFriendMessageExample();
			UserFriendMessageExample.Criteria criteria = example.createCriteria();
			criteria.andIdIn(msgIdList);
			userFriendMessageMapper.deleteByExample(example);
		}
	}

	public void saveOpenCount(OpenDeviceCount openDeviceCount) {
		openDeviceCountMapper.insertSelective(openDeviceCount);
	}

	public VersionControl getNewestVersion(int platform) {
		VersionControlExample example = new VersionControlExample();
        VersionControlExample.Criteria criteria = example.createCriteria();
        criteria.andPlatformEqualTo(platform);
        example.setOrderByClause(" update_time desc ");
        List<VersionControl> list =  versionControlMapper.selectByExample(example);
        return  (list != null &&list.size() > 0) ? list.get(0) : null;
	}

	public VersionChannelDownload getVersionChannelDownloadByChannel(String channel) {
		VersionChannelDownloadExample example = new VersionChannelDownloadExample();
    	VersionChannelDownloadExample.Criteria criteria = example.createCriteria();
    	criteria.andChannelEqualTo(channel);
    	List<VersionChannelDownload> list = versionChannelDownloadMapper.selectByExample(example);
    	if(null != list && list.size() > 0){
    		return list.get(0);
    	}
    	return null;
	}

	public DictionaryType getDictionaryType(long type) {
		DictionaryTypeExample example = new DictionaryTypeExample();
        DictionaryTypeExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(type);
        List<DictionaryType> list = dictionaryTypeMapper.selectByExample(example);
        return (list != null && list.size() > 0) ? list.get(0) : null;
	}

	public List<Dictionary> getDictionary(long type) {
		 DictionaryExample example = new DictionaryExample();
         DictionaryExample.Criteria criteria = example.createCriteria();
         criteria.andTidEqualTo(type);
         example.setOrderByClause(" sort asc ");
         return dictionaryMapper.selectByExample(example);
	}

	public ThirdPartUser checkOpenId(String openId) {
		ThirdPartUserExample example = new ThirdPartUserExample();
        ThirdPartUserExample.Criteria criteria = example.createCriteria();
        criteria.andThirdPartOpenIdEqualTo(openId);
        criteria.andStatusEqualTo(1);
        List<ThirdPartUser> thirdPartUsers = thirdPartUserMapper.selectByExample(example);
        return thirdPartUsers!=null&&thirdPartUsers.size()>0?thirdPartUsers.get(0):null;
	}

	public ThirdPartUser checkUnionId(String unionId) {
		ThirdPartUserExample example = new ThirdPartUserExample();
        ThirdPartUserExample.Criteria criteria = example.createCriteria();
        criteria.andThirdPartUnionidEqualTo(unionId);
        criteria.andStatusEqualTo(1);
        List<ThirdPartUser> thirdPartUsers = thirdPartUserMapper.selectByExample(example);
        return thirdPartUsers!=null&&thirdPartUsers.size()>0?thirdPartUsers.get(0):null;
	}

	public EmotionInfo getEmotionInfoByKey(Long emotionid) {
		return emotionInfoMapper.selectByPrimaryKey(emotionid);
	}


	
}
