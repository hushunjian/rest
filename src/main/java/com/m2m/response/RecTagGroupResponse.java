package com.m2m.response;

import java.util.List;

import com.google.common.collect.Lists;
import com.m2m.entity.ExtTagInfo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class RecTagGroupResponse extends BaseResponse {

	private static final long serialVersionUID = 1362830938537350160L;
	private List<ExtTagInfo> recTags = Lists.newArrayList();


    
}
