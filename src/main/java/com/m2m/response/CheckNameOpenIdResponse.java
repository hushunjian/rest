package com.m2m.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by pc339 on 2017/9/22.
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class CheckNameOpenIdResponse extends BaseResponse  {
	private static final long serialVersionUID = 7002311410794738477L;

	private long uid;
	private String nickName;
	private String avatar;
	private String summary;
}
