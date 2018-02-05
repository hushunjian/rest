package com.m2m.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.m2m.entity.ExtVoteOptionCount;

public interface ExtVoteMapper {
	public List<ExtVoteOptionCount> getVoteOptions(@Param("voteId")long voteId);
}
