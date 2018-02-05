package com.m2m.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 上海拙心网络科技有限公司出品
 * Author: 赵朋扬
 * Date: 2016/3/22.
 */
@Data
public class ExtContentDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private String feeling;
    private String content;
    private String imageUrls;
    private String imageWidths;
    private String imageHeights;
    private int contentType;
    private long forwardCid;
    private int type;
    private long uid;
    private long id;
    private String title;
    private Date createTime;
    private int rights;
    private String forWardUrl;
    private String forwardTitle;
    private String coverImage;
    private int action;
    private int isTop;
    private long targetTopicId;
    private String version;
}
