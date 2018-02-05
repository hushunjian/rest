package com.m2m.copier;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.m2m.domain.Content;
import com.m2m.domain.LotteryInfo;
import com.m2m.domain.Topic;
import com.m2m.domain.TopicFragmentWithBLOBs;
import com.m2m.domain.UserProfile;
import com.m2m.entity.ExtContentDto;
import com.m2m.entity.ExtLotteryWinUser;
import com.m2m.entity.ExtOutData;
import com.m2m.eventbus.event.SpeakNewEvent;
import com.m2m.mapstruct.TimeTransform;
import com.m2m.request.CreateKingdomRequest;
import com.m2m.response.GetLiveByCidResponse;
import com.m2m.response.GetLotteryResponse;
import com.m2m.response.GetLotteryResponse.UserElement;
import com.m2m.response.LiveCoverResponse;
import com.m2m.response.SettingsResponse;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = TimeTransform.class)
public interface TopicCopier {
	TopicCopier INSTANCE = Mappers.getMapper(TopicCopier.class);
	
	ExtOutData asExtOutData(TopicFragmentWithBLOBs bean);
	
	List<ExtOutData> asExtOutDataList(List<TopicFragmentWithBLOBs> beans);
	
	@Mapping(source="id",target="topicId")
	SettingsResponse asSettingsResponse(Topic bean);
	
	@Mapping(source="autoCoreType",target="autoJoinCore")
	@Mapping(source="fragment",target="summary")
	Topic asTopic(CreateKingdomRequest bean);
	
	
	@Mapping(source="title",target="content")
	@Mapping(source="title",target="feeling")
	@Mapping(source="liveImage",target="imageUrls")
	ExtContentDto asExtContentDto(CreateKingdomRequest bean);
	
	Content asContent(ExtContentDto bean);
	
	TopicFragmentWithBLOBs asTopicFragmentWithBLOBs(CreateKingdomRequest bean);
	
	@Mapping(source="longTime",target="lastUpdateTime")
	LiveCoverResponse asLiveCoverResponse(Topic bean);
	
	@Mapping(source="fragment",target="fragmentContent")
	@Mapping(source="extra",target="fragmentExtra")
	SpeakNewEvent asSpeakNewEvent(TopicFragmentWithBLOBs bean);
	
	
	@Mapping(source="topic.longTime",target="updateTime")
	@Mapping(source="topic.id",target="topicId")
	@Mapping(source="topic.type",target="contentType")
	@Mapping(source="topic.price",target="topicPrice")
	@Mapping(source="topic.createTime",target="createTime")
	@Mapping(source="topic.uid",target="uid")
	@Mapping(source="topic.status",target="status")
	@Mapping(source="topic.title",target="title")
	@Mapping(source="userProfile.vLv",target="v_lv")
	@Mapping(source="content.id",target="cid")
	@Mapping(source="content.readCountDummy",target="readCount")
	@Mapping(target="rights",ignore=true)
	GetLiveByCidResponse asGetLiveByCidResponse(Topic topic,UserProfile userProfile,Content content);
	
	@Mapping(source="userProfile.uid",target="uid")
	@Mapping(source="userProfile.vLv",target="v_lv")
	@Mapping(source="lotteryInfo.createTime",target="createTime")
	GetLotteryResponse asGetLotteryResponse(LotteryInfo lotteryInfo,UserProfile userProfile);
	
	UserElement asUserElement(ExtLotteryWinUser bean);
	
	List<UserElement> asUserElements(List<ExtLotteryWinUser> beans);
	
}
