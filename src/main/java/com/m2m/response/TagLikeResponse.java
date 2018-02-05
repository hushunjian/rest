package com.m2m.response;

import com.m2m.entity.ExtTagInfo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class TagLikeResponse extends BaseResponse {

	private static final long serialVersionUID = 1362830938537350160L;
	private ExtTagInfo newKingdomTag;


    
}
