package com.m2m.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.m2m.domain.TopicTag;
import com.m2m.domain.TopicTagDetail;
import com.m2m.entity.ExtAdBanner;

public interface ExtTagMapper {
    
   public void removeUserDislikeUserTags(@Param("uid") Long uid,@Param("idList") List<Long> idList);

   public List<TopicTag> getTopicTagsByTags(List<String> tags);

   public List<String> getExistTopicTagByTags(List<String> tags);

   public void saveNewTopicTag(List<TopicTag> needAddTopicTags);

   public void saveTopicTagDetail(List<TopicTagDetail> topicTagDetails);
   
   public List<ExtAdBanner> getAdBannerByTagId(@Param("tagId") Long tagId);
   
}
