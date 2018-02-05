package com.m2m.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.m2m.domain.AppUiControl;
import com.m2m.domain.UserNotice;
import com.m2m.entity.ExtFriend;
import com.m2m.entity.ExtFriendContactsInfo;
import com.m2m.entity.ExtHobbyInfo;
import com.m2m.entity.ExtIndustryStat;
import com.m2m.entity.ExtUserProfile;

public interface ExtUserMapper {

	public int getUserTotalCoinsByDay(@Param("uid")long uid, @Param("dayStr")String dayStr);
	
	public int countRuleLogByUidAndRuleCodeAndDay(@Param("uid")long uid, @Param("ruleCode")int ruleCode, @Param("dayStr")String dayStr);
	
	public int countRuleLogByUidAndRuleCodeAndExt(@Param("uid")long uid, @Param("ruleCode")int ruleCode, @Param("ext")long ext);
	
	public void addUserCoins(@Param("uid")long uid, @Param("addCoins")int addCoins);
	
	public void modifyUserLevel(@Param("uid")long uid, @Param("targetLevel")int targetLevel);
	
	public int insertUserNotice(UserNotice userNotice);
	
	public List<Long> getBlacklist(@Param("uid")long uid);
	
	public void updateUserInvitationHisReceive(@Param("id")long id);

	public void updateUserCoins(@Param("uid")long uid, @Param("price")int price);
	
	public List<ExtHobbyInfo> getUserHobbyInfoList(@Param("uid")long uid);
	
	public List<ExtFriend> getUserFriendRecList(@Param("uid")long uid, @Param("start")int start, @Param("pageSize")int pageSize);
	
	/**
	 * 统计好友的行业数据
	 * @param uid
	 * @return
	 */
	public List<ExtIndustryStat> statUserFriendIndustry(@Param("uid")long uid);
	
	/**
	 * 查询用户好友联系人列表（带消息的）
	 * @param uid
	 * @param start
	 * @param pageSize
	 * @return
	 */
	public List<ExtFriendContactsInfo> queryFriendContacts(@Param("uid")long uid, @Param("industryId")long industryId, @Param("start")int start, @Param("pageSize")int pageSize);

	public List<ExtUserProfile> getExtUserProfileListByIds(@Param("idList")List<Long> idList);
	
	public ExtUserProfile getExtUserProfileByUid(@Param("uid")long uid);

	public AppUiControl getAppUiControl();

	public List<Long> getCanStealTopicId(@Param("uid")long uid, @Param("todayStart")Date todayStart);

	public List<Long> getCanSpeakTopicId(long uid);

}
