package com.m2m.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class TagDetailRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;
	private long tagId;
	private String tagName;
	private int page;
}
