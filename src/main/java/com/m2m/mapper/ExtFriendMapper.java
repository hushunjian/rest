package com.m2m.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.m2m.entity.ExtFriend;
import com.m2m.entity.ExtFriendApply;
import com.m2m.entity.ExtRecFriend;
import com.m2m.entity.Middleman;

public interface ExtFriendMapper {

	public List<ExtFriend> queryUserFriends(@Param("uid")long uid, @Param("keyword")String keyword, @Param("start")int start, @Param("pageSize")int pageSize);
	
	/**
	 * 删除半吊子的好友关系
	 * @param sourceUid
	 * @param targetUid
	 */
	void deleteHalfFriend(@Param("sourceUid")long sourceUid,@Param("targetUid")long targetUid);
	
	public List<ExtFriendApply> queryUserFriendApply(@Param("uid")long uid, @Param("start")int start, @Param("pageSize")int pageSize);
	
	List<ExtRecFriend> queryUserFriendRecList(@Param("uid")long uid, @Param("limit")int limit);
	
	/**
	 * 查询中间人列表
	 * @param uid		待查询的人
	 * @param recUid	推荐的人
	 * @param fromUid	指定推荐，大于0时有效
	 * @return
	 */
	List<Middleman> getMiddlemanList(@Param("uid")long uid, @Param("recUid")long recUid, @Param("fromUid")long fromUid, @Param("limit")int limit);
}
