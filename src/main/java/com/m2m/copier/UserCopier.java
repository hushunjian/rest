package com.m2m.copier;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.m2m.domain.EmotionInfo;
import com.m2m.domain.EmotionPackDetail;
import com.m2m.domain.EmotionRecord;
import com.m2m.domain.OpenDeviceCount;
import com.m2m.domain.UserProfile;
import com.m2m.domain.VersionChannelDownload;
import com.m2m.domain.VersionControl;
import com.m2m.entity.ExtUserProfile;
import com.m2m.request.VersionControlRequest;
import com.m2m.response.GetLastEmotionInfoResponse;
import com.m2m.response.GetLastEmotionInfoResponse.EmotionPack;
import com.m2m.response.GetUserProfileResponse;
import com.m2m.response.GetUserResponse;
import com.m2m.response.UserGroupResponse;
import com.m2m.response.VersionControlResponse;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserCopier {

	UserCopier INSTANCE = Mappers.getMapper(UserCopier.class);
	
	@Mapping(source = "mobile", target = "userName")
	@Mapping(source = "vLv", target = "v_lv")
	GetUserProfileResponse userProfile2Response(UserProfile bean);
	
	@Mapping(source = "vLv", target = "v_lv")
	GetUserResponse userProfile2BaseInfoResponse(UserProfile bean);
	
	@Mapping(source = "vLv", target = "vip")
	@Mapping(source = "introduced", target = "introduce")
	UserGroupResponse.UserElement asUserGroupResponseUserElement(ExtUserProfile bean);
	
	@Mapping(source = "ip", target = "ipAddress")
	OpenDeviceCount asOpenDeviceCount(VersionControlRequest versionControlRequest,String ip);
	
	@Mapping(source = "versionControl.id", target = "id")
	@Mapping(source = "versionControl.updateDescription", target = "updateDescription")
	@Mapping(source = "versionControl.updateTime", target = "updateTime")
	@Mapping(source = "versionControl.platform", target = "platform")
	@Mapping(source = "versionControl.version", target = "version")
	@Mapping(source = "versionChannelDownload.versionAddr", target = "updateUrl")
	VersionControlResponse asVersionControlResponse(VersionControl versionControl,VersionChannelDownload versionChannelDownload);
	
	
	@Mapping(source = "emotionInfo.id", target = "id")
	@Mapping(source = "emotionInfo.emotionname", target = "emotionName")
	@Mapping(source = "emotionInfo.topicid", target = "topicId")
	@Mapping(source = "emotionRecord.happyvalue", target = "happyValue")
	@Mapping(source = "emotionRecord.freevalue", target = "freeValue")
	@Mapping(source = "emotionRecord.createTime", target = "createTime")
	GetLastEmotionInfoResponse asGetLastEmotionInfoResponse(EmotionInfo emotionInfo,EmotionRecord emotionRecord);
	
	@Mapping(source = "extra", target = "content")
	@Mapping(source = "thumbW", target = "thumb_w")
	@Mapping(source = "thumbH", target = "thumb_h")
	EmotionPack asEmotionPack(EmotionPackDetail bean);
	
}
