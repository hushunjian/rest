package com.m2m.response;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;
import com.m2m.entity.ExtOutContent;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class IndustryContentListResponse extends BaseResponse {

	private static final long serialVersionUID = 1362830938537350160L;
	private List<ExtOutContent> contentData = Lists.newArrayList();
	private List<IndustryElement> industryData = Lists.newArrayList();

    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class IndustryElement implements Serializable{
    	
		private static final long serialVersionUID = 2771215844784729600L;
		private long industryId;
        private String industry;
        
    }
    
}
