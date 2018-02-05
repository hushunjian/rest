package com.m2m.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class DetailFidPageResponse extends BaseResponse {
	private static final long serialVersionUID = -2432058185820058389L;

	private int page;
}
