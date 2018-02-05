package com.m2m.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class NewestRequest extends BaseRequest {
	private static final long serialVersionUID = -3291465974142062475L;
	private long sinceId;
}
