package com.m2m.response;

import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PricedKingdomListResponse extends BaseResponse {
	private static final long serialVersionUID = 525070635803567720L;

	private List<PricedKingdomInfo> listData = Lists.newArrayList();
}
