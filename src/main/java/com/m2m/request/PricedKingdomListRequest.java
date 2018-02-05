package com.m2m.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PricedKingdomListRequest extends BaseRequest {
	private static final long serialVersionUID = -4623179222100669289L;

	private int page;
	private int pageSize;
}
