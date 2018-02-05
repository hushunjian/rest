package com.m2m.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AddAppDownloadLogRequest extends BaseRequest {
	private static final long serialVersionUID = 4030064791914809092L;

	private long fromUid;
	private long requestUid;
}
