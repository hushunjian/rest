package com.m2m.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.m2m.entity.ExtUserEmotion;

public interface ExtEmotionMapper {

	public List<ExtUserEmotion> getUserEmotions(@Param("uid")long uid, @Param("limitSize")int limitSize);
}
