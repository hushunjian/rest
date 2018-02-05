package com.m2m.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AcKingdomListRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;
	
	private Integer page;
	private Long ceTopicId;
	private Integer resultType;
}
