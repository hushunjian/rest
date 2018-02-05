package com.m2m.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper = false)
public class EmojiPackageDetailRequest extends BaseRequest {

	private static final long serialVersionUID = 94200850695583378L;
	private int packageId;
}
