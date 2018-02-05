package com.m2m.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.m2m.domain.UserIndustry;

public interface ExtIndustryMapper {

	UserIndustry getGuessIndustryByUid(@Param("uid") long uid);
	
	List<UserIndustry> getIndustryListByUid(@Param("uid") long uid);
	
}
