package com.m2m.copier;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.m2m.domain.TeaseInfo;
import com.m2m.domain.VoteInfo;
import com.m2m.entity.ExtJoinLotteryUser;
import com.m2m.entity.ExtVoteOptionCount;
import com.m2m.mapstruct.TimeTransform;
import com.m2m.response.GetJoinLotteryUsersResponse;
import com.m2m.response.GetTopicVoteInfoResponse;
import com.m2m.response.TeaseListQueryResponse;

/**
 * 王国里面小组件相关
 * @author pc340
 *
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = TimeTransform.class)
public interface TopicItemCopier {
	TopicItemCopier INSTANCE = Mappers.getMapper(TopicItemCopier.class);
	
	@Mapping(source = "id", target = "voteId")
	GetTopicVoteInfoResponse ext2GetTopicVoteInfoResponse(VoteInfo bean);
	
	@Mapping(source = "optionId", target = "id")
	@Mapping(source = "optionName", target = "option")
	@Mapping(source = "recordCount", target = "count")
	GetTopicVoteInfoResponse.OptionElement ext2OptionElement(ExtVoteOptionCount bean);
	
	List<GetTopicVoteInfoResponse.OptionElement> ext2OptionElementList(List<ExtVoteOptionCount> beanList);
	
	TeaseListQueryResponse.TeaseElement ext2TeaseElement(TeaseInfo bean);
	
	GetJoinLotteryUsersResponse.JoinUserElement ext2JoinUserElement(ExtJoinLotteryUser bean);
}
