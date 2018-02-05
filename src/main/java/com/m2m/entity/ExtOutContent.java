package com.m2m.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

@Data
@EqualsAndHashCode(callSuper = false)
public class ExtOutContent extends ExtBaseContent implements Serializable {
	
	private static final long serialVersionUID = -6121011026190718709L;
    private int vip;
    private long cid;
    private long topicId;
    private int canView;
    private int lastType;
    private int lastContentType;
    private String lastFragment;
    private String lastFragmentImage;
    private int lastStatus;
    private String lastExtra;
    private int isShowLikeButton;
    private int isOwner;
    private long tagId;
    private String tagName;
	private List<ExtOutData> textData = Lists.newArrayList();
    private List<ExtOutData> audioData = Lists.newArrayList();
    private List<ExtOutData> imageData = Lists.newArrayList();
    private List<ExtOutData> ugcData = Lists.newArrayList();
}
