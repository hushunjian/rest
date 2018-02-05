package com.m2m.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ImageInfoResponse extends BaseResponse {
	private static final long serialVersionUID = -5080585121896543134L;

	private int likeCount;
	private int isLike;
}
