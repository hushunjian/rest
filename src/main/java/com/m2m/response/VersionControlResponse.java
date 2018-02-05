package com.m2m.response;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 上海拙心网络科技有限公司出品
 * Author: 代宝磊
 * Date: 2016/5/10
 * Time :16:21
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class VersionControlResponse extends BaseResponse{
	private static final long serialVersionUID = 1L;
	private long id;
    private String version;
    private String updateDescription;
    private String updateUrl;
    private Date updateTime;
    private int platform;
    private int isUpdate;
    private int resourceCode;//0不是 1是
    private int emotionSwitch;//加号页情绪图谱开关，0关，1开
}
