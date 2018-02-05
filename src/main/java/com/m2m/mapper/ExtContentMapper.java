package com.m2m.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.m2m.domain.AdBanner;
import com.m2m.domain.Content;
import com.m2m.domain.ContentImage;
import com.m2m.domain.TopicFragmentWithBLOBs;
import com.m2m.domain.TopicTag;
import com.m2m.entity.ExtAcImage;
import com.m2m.entity.ExtAttention;
import com.m2m.entity.ExtHotContent;
import com.m2m.entity.ExtKingdomRelevantInfo;
import com.m2m.entity.ExtTagOutInfo;
import com.m2m.entity.ExtTagTmp;
import com.m2m.entity.ExtTagTopicInfo;
import com.m2m.entity.ExtTopicReviewCount;

public interface ExtContentMapper {

    List<ExtAttention> getAttentionAndLikeTag(@Param("uid") Long uid,@Param("updateTime") Long updateTime, @Param("pageSize") int pageSize,@Param("beforeThreeDays")String beforeThreeDays);

    List<ExtTagOutInfo> getTagOutList(@Param("idList") List<Long> idList);
    
    List<TopicFragmentWithBLOBs> getOutFragments(@Param("idList") List<Long> idList,@Param("limitMinute") int limitMinute); 
    
    List<ExtTopicReviewCount> getTopicUpdateCount(@Param("idList") List<Long> idList);
    
    List<Long> getLikeContentIdList(@Param("uid") Long uid,@Param("idList") List<Long> idList);
    
	List<ExtTagTmp> getUserFavoriteTags(@Param("uid")long uid,@Param("count")int count,@Param("idList") List<Long> idList);
    
	List<ExtTagTmp> getSysTagsInfo(@Param("uid") Long uid,@Param("count")int count,@Param("idList") List<Long> idList);

	List<Long> getUserDisLikeTagAndSubTagInfo(@Param("uid")long uid);
	
	List<ExtTagTopicInfo> getTagTopicInfo(@Param("idList") List<Long> idList);
	
	void updateContentReadCount(@Param("cid")long cid,@Param("addReadCount")int addReadCount, @Param("addReadCountDummy")int addReadCountDummy);

	int getAttentionAndLikeTagCount(@Param("uid") long uid);
	
	List<AdBanner> getNormalBanners(@Param("uid") long uid);
	
	List<TopicTag> getTopicTagByTypeAndUid(@Param("uid") long uid,@Param("type") int type);
	
	List<Content> getHotContentListByType(@Param("uid")long uid,@Param("type") int type, @Param("start")int start,@Param("pageSize")int pageSize,@Param("ids")List<String> ids,@Param("blacklistUids")List<Long> blacklistUids,@Param("blackTagIds")String blackTagIds);

	List<ExtKingdomRelevantInfo> getKingdomRelevantInfo(@Param("uid") long uid,@Param("idList") List<Long> idList);

	void saveContent(Content content);

	void updateTopicContentCover(@Param("topicId")Long topicId, @Param("coverImage")String coverImage);

	void updateContentUpdateTimeByTopicId(@Param("topicId")Long topicId, @Param("updateTime")Date updateTime);

	void updateContentUpdateIdByTopicId(@Param("topicId")Long topicId, @Param("updateId")long updateId);

	void updateTopicContentTitle(@Param("topicId")Long topicId, @Param("title")String title);

	List<Content> getIndustryContentList(@Param("industryId") long industryId,@Param("blacklistUids") List<Long> blacklistUids,@Param("page") int page,@Param("pageSize") int pageSize);

	void saveContentImages(List<ContentImage> contentImages);
	
	List<ExtHotContent> getHotContentList(@Param("uid") long uid,@Param("blacklistUids") List<Long> blacklistUids,@Param("page") int page,@Param("pageSize") int pageSize);

	List<Content> getAcKingdomList(@Param("topicId") long topicId,@Param("page") int page,@Param("pageSize") int pageSize);

	List<ExtAcImage> getAcKingdomImageList(@Param("topicId") long topicId,@Param("page") int page,@Param("pageSize") int pageSize);

	List<Long> getAcKingdomImageLikeList(@Param("uid") long uid,@Param("imageIdList") List<Long> imageIdList);

	List<Content> getMyOwnKingdom(@Param("uid") long topicId,@Param("updateTime") long updateTime);
	
	List<Content> loadMyJoinKingdom(@Param("uid") long topicId,@Param("updateTime") long updateTime);

	List<Content> loadNewestContent(@Param("uid") long uid,@Param("sinceId") long sinceId, @Param("blacklistUids") List<Long> blacklistUids);

	List<Content> getContentByTagId(@Param("tagId") long tagId,@Param("pageSize") int pageSize );
	
	List<Content> getTagTopicList(@Param("tagId") long tagId, @Param("blacklistUids") List<Long> blacklistUids,@Param("page") int page,@Param("pageSize") int pageSize);
}
