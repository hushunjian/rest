package com.m2m.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SpeakRequest extends BaseRequest {
	private static final long serialVersionUID = -6774707171219236305L;

    private long topicId;
    private int contentType;
    private int type;
    private String fragmentImage;
    private String fragment;
    private long topId;
    private long bottomId;
    private long atUid;
    private int mode;
    private int source;
    private String extra;
    
    private long quotationInfoId;
}
