package com.m2m.response;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ContactsQueryResponse extends BaseResponse {
	private static final long serialVersionUID = -916300482237987864L;

	private int hasNewApply;
	private List<UserSimpleInfo> recFriendData = Lists.newArrayList();
	private List<IndustryStatElement> industryStat = Lists.newArrayList();
	private List<FriendElement> friendData = Lists.newArrayList();
	
	@Data
	public static class IndustryStatElement implements Serializable {
		private static final long serialVersionUID = 8569072275533368728L;
		
		private long industryId;
		private String industry;
		private int count;
	}
	
	@Data
	@EqualsAndHashCode(callSuper = false)
	public static class FriendElement extends UserSimpleInfo {
		private static final long serialVersionUID = -7966376278181176105L;
		
		private long industryId;
		private String industry;
		private String group;
		private int isRec;
		private int type;
		private String content;
	}
}
