package com.m2m.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Author: chenxiang
 * Date: 2017/7/18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class GuideInfoResponse extends BaseResponse {
	private static final long serialVersionUID = 1L;
	private long uid;
    private String nickName;
    private String avatar;
}
