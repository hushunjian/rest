package com.m2m.response;

import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ShowListResponse extends BaseResponse {
	private static final long serialVersionUID = -5246901472474989441L;

	private List<BangDanData> listData = Lists.newArrayList();
	private List<PricedKingdomInfo> listPricedTopic = Lists.newArrayList();
}
