package com.m2m.response;

import java.util.List;

import com.google.common.collect.Lists;
import com.m2m.entity.ExtBaseContent;
import com.m2m.entity.ExtOutData;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AttentionResponse extends BaseResponse {

	private static final long serialVersionUID = 1362830938537350160L;
	private List<ContentElement> attentionData = Lists.newArrayList();


    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class ContentElement extends ExtBaseContent{
    	
		private static final long serialVersionUID = 735013142092506502L;

		private long tagId;
    	
    	private String tagName;
    	
        private List<ExtOutData> textData = Lists.newArrayList();
        private List<ExtOutData> audioData = Lists.newArrayList();
        private List<ExtOutData> imageData = Lists.newArrayList();
        private List<ExtOutData> ugcData = Lists.newArrayList();
        
        
    }
    
}
