package com.m2m.response;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class GetUserResponse extends BaseResponse {
	private static final long serialVersionUID = 4995609996565014089L;

	private String nickName;
    private long uid;
    private String avatar;
    private String avatarFrame;
    private int gender;
    private int isFollowed;
    private int isFollowMe;
    private int v_lv;
    private Date createTime;
    private int level;
    private long industryId;
    private String industry;
}
