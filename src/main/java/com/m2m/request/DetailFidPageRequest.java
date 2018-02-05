package com.m2m.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class DetailFidPageRequest extends BaseRequest {
	private static final long serialVersionUID = 231441037841467927L;

	private long topicId;
	private long fid;
	private int offset;
}
