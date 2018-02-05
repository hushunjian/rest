package com.m2m.response;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;
import com.m2m.entity.ExtBaseContent;
import com.m2m.entity.ExtOutData;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by pc339 on 2017/9/22.
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class HotResponse extends BaseResponse  {
	private static final long serialVersionUID = 7002311410794738477L;

	private List<Object> data = Lists.newArrayList();

	private int openPushPositions;
	private int bootFromFollowing;

	@Data
	public static class BaseContentElement implements Serializable {
		
		private static final long serialVersionUID = 510214586715557816L;
		private int type;
		private long cid;
		private String title;

	}
	
	@Data
	@EqualsAndHashCode(callSuper=false)
	public static class TagContentElement extends BaseContentElement {
		
		private static final long serialVersionUID = 102597088990701416L;
		private int size;
		private String nickName;
		private String avatar;
		
	}
	
	@Data
	@EqualsAndHashCode(callSuper=false)
	public static class IndustryContentElement extends BaseContentElement {
		
		private static final long serialVersionUID = -6539080081770472922L;
		private long industryId;
		private String coverImage;
		private String industry;
		
	}
	
	@Data
	@EqualsAndHashCode(callSuper=false)
	public static class HeightWidthContentElement extends BaseContentElement{
		private static final long serialVersionUID = 1L;
		private int h;
		private int w;
		
	}

	@Data
	public static class InvitationElement implements Serializable {
		
		private static final long serialVersionUID = 5791045224437406531L;
		private int type;
		private String cText;
		private String btnText;
		private int htStart;
		private int htEnd;
		private String avatar;
		private String avatarFrame;
		private int v_lv;
		private long uid;
		private int invitationType;
		private int btnAction;
		private int coins;
	}
	
	@Data
	@EqualsAndHashCode(callSuper=false)
	public static class HotContentElement extends ExtBaseContent implements Serializable, Comparable<HotContentElement> {

		private static final long serialVersionUID = 735013142092506502L;
    	
        private List<ExtOutData> textData = Lists.newArrayList();
        private List<ExtOutData> audioData = Lists.newArrayList();
        private List<ExtOutData> imageData = Lists.newArrayList();
        private List<ExtOutData> ugcData = Lists.newArrayList();
        private int vip;
        private long cid;
        private long topicId;
        private int canView;
        private int lastType;
        private int lastContentType;
        private String lastFragment;
        private String lastFragmentImage;
        private int lastStatus;
        private String lastExtra;
        private int isShowLikeButton;
		// 和前端无关的两个字段
		private Long hid;
		private Long operationTime;
		private Long sinceId;

		@Override
		public int compareTo(HotContentElement o) {
			if (o.getOperationTime() > HotContentElement.this.getOperationTime())
				return 1;
			else if (o.getOperationTime() == HotContentElement.this.getOperationTime())
				return 0;
			else
				return -1;
		}

	}
}
