package com.m2m.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.m2m.entity.ExtJoinLotteryUser;
import com.m2m.entity.ExtLotteryWinUser;

public interface ExtLotteryMapper {

	List<ExtJoinLotteryUser> getJoinLotteryUsers(@Param("lotteryId")long lotteryId, @Param("sinceId")long sinceId, @Param("limit")int limit);

	List<ExtLotteryWinUser> getLotteryWinUserList(long lotteryId);
}
