package com.m2m.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Author: 马秀成
 * Date: 2017/2/4
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SettingsResponse extends BaseResponse {
	private static final long serialVersionUID = 1L;
	private long topicId;
    private String coverImage;
    private String title;
    private int readCount;
    private int favoriteCount;
    private long topicCount;
    private long createTime;
    private String summary;
    private int ceCount;
    private int acCount;
    private int pushType;
    private int ceAuditType;
    private int acAuditType;
    private int acPublishType;
    private String tags;
    private int kcid;
    private String kcImage;
    private String kcName;
    private String kcIcon;
    private int secretType;
    private int autoCoreType;
    private int onlyFriend;
}
