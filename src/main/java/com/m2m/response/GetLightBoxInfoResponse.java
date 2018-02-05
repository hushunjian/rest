package com.m2m.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by pc339 on 2017/9/22.
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class GetLightBoxInfoResponse extends BaseResponse  {
	private static final long serialVersionUID = 7002311410794738477L;
	private String image;
	private String mainText;
	private String secondaryText;
	private String mainTone;
	private String linkUrl;
}
