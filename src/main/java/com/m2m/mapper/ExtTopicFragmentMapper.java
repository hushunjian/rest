package com.m2m.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.m2m.domain.TopicFragmentWithBLOBs;
import com.m2m.entity.ExtFragmentUpdateCount;

public interface ExtTopicFragmentMapper {

	List<TopicFragmentWithBLOBs> getLastFragmentByTopicIds(@Param("idList") List<Long> idList);
	
	ExtFragmentUpdateCount countFragmentByTopicIdWithSince(@Param("topicId")long topicId, @Param("sinceId")long sinceId);
}
