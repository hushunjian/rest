package com.m2m.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ImageInfoRequest extends BaseRequest{

	private static final long serialVersionUID = -2101330949061205961L;
	private long topicId;
	private long fid;
	private String imageName;
}
