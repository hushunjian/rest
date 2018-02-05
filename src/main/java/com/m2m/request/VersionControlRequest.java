package com.m2m.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 上海拙心网络科技有限公司出品
 * Author: 代宝磊
 * Date: 2016/5/10
 * Time :15:24
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class VersionControlRequest extends BaseRequest{
	private static final long serialVersionUID = 1L;
    private int platform;
    private String device;
    private String params;
}
