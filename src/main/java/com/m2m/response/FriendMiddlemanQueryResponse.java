package com.m2m.response;

import java.util.List;

import com.google.common.collect.Lists;
import com.m2m.entity.Middleman;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class FriendMiddlemanQueryResponse extends BaseResponse {
	private static final long serialVersionUID = -114677563220159191L;

	private List<Middleman> middlemanData = Lists.newArrayList();
}
