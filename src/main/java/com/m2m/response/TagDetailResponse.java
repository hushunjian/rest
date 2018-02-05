package com.m2m.response;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class TagDetailResponse  extends BaseResponse{

	private static final long serialVersionUID = -633831571169189486L;

	private List<Object> tagKingdomList = Lists.newArrayList();
	
    private long tagId;
    
    private String tagName;
    
    private int isLike;
    
    private List<CoverElement> coverList = Lists.newArrayList();
    
    
    @Data
    public static class AdElement implements Serializable {
    	
		private static final long serialVersionUID = -6240068377784693096L;
		private long cid;
		private int type;
		private int h;
		private int w;
    }
    @Data
    public static class CoverElement implements Serializable {
		private static final long serialVersionUID = 2901955620229846018L;
		private long cid;
		private long topicId;
		private String coverImage;
		private String title;
		private int type;
    }
}
