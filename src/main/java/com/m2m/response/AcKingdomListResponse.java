package com.m2m.response;

import java.util.List;

import com.google.common.collect.Lists;
import com.m2m.entity.ExtAcImage;
import com.m2m.entity.ExtOutContent;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AcKingdomListResponse extends BaseResponse {

	private static final long serialVersionUID = -279262965668413842L;

	private List<ExtOutContent> acKingdomList = Lists.newArrayList();

    private List<ExtAcImage> acImageList = Lists.newArrayList();
    
    private int acCount;

    
    
}
