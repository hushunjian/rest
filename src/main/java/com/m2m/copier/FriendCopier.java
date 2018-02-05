package com.m2m.copier;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.m2m.entity.ExtFriend;
import com.m2m.entity.ExtFriendApply;
import com.m2m.entity.ExtFriendContactsInfo;
import com.m2m.entity.ExtIndustryStat;
import com.m2m.entity.ExtRecFriend;
import com.m2m.response.ContactsQueryResponse;
import com.m2m.response.FriendApplyQueryResponse;
import com.m2m.response.FriendQueryResponse;
import com.m2m.response.RecFriendQueryResponse;
import com.m2m.response.UserSimpleInfo;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FriendCopier {

	FriendCopier INSTANCE = Mappers.getMapper(FriendCopier.class);
	
	FriendQueryResponse.FriendElement ext2FriendElement(ExtFriend bean);
	
	List<FriendQueryResponse.FriendElement> ext2FriendElementList(List<ExtFriend> beanList);
	
	UserSimpleInfo ext2UserSimpleInfo(ExtFriend bean);
	
	@Mapping(source = "friendCount", target = "count")
	ContactsQueryResponse.IndustryStatElement extStat2IndustryStatElement(ExtIndustryStat bean);
	
	@Mapping(source = "nameGroup", target = "group")
	ContactsQueryResponse.FriendElement extContacts2FriendElement(ExtFriendContactsInfo bean);
	
	FriendApplyQueryResponse.ApplyInfoElement extApply2ApplyInfoElement(ExtFriendApply bean);
	
	RecFriendQueryResponse.RecFriendElement ext2RecFriendElement(ExtRecFriend bean);
}
