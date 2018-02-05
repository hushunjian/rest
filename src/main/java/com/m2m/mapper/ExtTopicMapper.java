package com.m2m.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.m2m.domain.Topic;
import com.m2m.domain.TopicFragmentWithBLOBs;
import com.m2m.domain.UserStealLog;
import com.m2m.entity.ExtIdCount;
import com.m2m.entity.ExtTopic;
import com.m2m.entity.ExtTopicCount;
import com.m2m.response.GetLiveByCidResponse.AcImageElement;

public interface ExtTopicMapper {

    List<ExtIdCount> getNoCoreCircleMembers(@Param("idList") List<Long> idList); 
    
    List<ExtIdCount> getCoreCircleMembers(@Param("idList") List<Long> idList); 
    
    int saveTopicFragment(TopicFragmentWithBLOBs record);

	List<Topic> getAcTopicListByCeTopicId(@Param("ceTopicId")long ceTopicId, @Param("start")int start, @Param("pageSize")int pageSize);
    
    List<UserStealLog> getUserStealLogByDay(@Param("uid") long uid,@Param("day") String day); 

    int getUserCoinsByDay(@Param("uid") long uid,@Param("day") String day);
    
    ExtTopicCount getTopicCount(@Param("topicId")long topicId);
    
    List<ExtTopicCount> getTopicCountByTids(@Param("tidList")List<Long> tidList);
    
    List<TopicFragmentWithBLOBs> getTopicFragmentForPage(@Param("topicId")long topicId, @Param("start")int start, @Param("pageSize")int pageSize, @Param("sinceId")long sinceId);

    int countListingKingdoms();
    
    List<ExtTopic> getMyLivesByUpdateTimeNew(@Param("uid")long uid, @Param("updateTime")long updateTime);
    
    List<Long> getTopicIdsByUids(@Param("uidList")List<Long> uidList);

	List<Topic> getConvergeTopic(long uid);

	/**
	 * 退出所有target的王国
	 * @param uid
	 * @param targetUid
	 */
	void delLiveFavoriteFromAllOtherUser(@Param("uid")long uid, @Param("targetUid")long targetUid);
	
	/**
	 * 查询target所有的核心圈包含我的王国
	 * @param uid
	 * @param targetUid
	 * @return
	 */
	List<Topic> getTargetTopicsInCore(@Param("uid")long uid, @Param("targetUid")long targetUid);
	
	/**
	 * 更新王国核心圈
	 * @param topicId
	 * @param newCore
	 */
	void updateTopicCore(@Param("topicId")long topicId, @Param("newCore")String newCore);

	void saveTopic(Topic topic);

	/**
	 * 查询用户所有王国的订阅人数（去重）
	 * @param uid
	 * @return
	 */
	int countUserKingdomSubscribeCount(@Param("uid")long uid);
	
	/**
	 * 获取用户的所有王国的图库内容（视频/图片）取前{limit}个
	 * @param uid
	 * @return
	 */
	List<String> getTopTopicImageByUid(@Param("uid")long uid, @Param("limit")int limit);

	List<AcImageElement> getAggregationImage(long topicId);
	
	void updateTopicDataStealPriceByTopicId(@Param("price")int price,@Param("topicId")long topicId);
	
	void updateTopicDataHarvestPriceByTopicId(@Param("price")int price,@Param("topicId")long topicId);
	
	void updateTopicPrice(@Param("price")int price,@Param("topicId")long topicId);

	List<Integer> getBalancePriceByTopicId(@Param("topicId")long topicId);

}
