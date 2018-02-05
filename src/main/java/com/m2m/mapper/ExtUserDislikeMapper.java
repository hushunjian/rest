package com.m2m.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface ExtUserDislikeMapper {
	
	public void deleteExistUserLikeTags(@Param("uid")long uid, @Param("tagIds")List<Long> tagSet);

	public void batchInsertUserLikeTags(@Param("uid")long uid, @Param("tagIds")List<Long> tagSet);

	
}
