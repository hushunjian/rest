package com.m2m.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.m2m.domain.QuotationInfo;
import com.m2m.domain.RobotInfo;

public interface ExtRobotInfoMapper {

	public RobotInfo getRandomRobotInfo();
	
	public QuotationInfo getRandomQuotationInfoByType(@Param("type") int type);
	
	public List<QuotationInfo> selectListQuotationInfo(@Param("limit") int limit);
}
