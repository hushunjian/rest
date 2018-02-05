package com.m2m.response;

import java.util.List;

import com.google.common.collect.Lists;
import com.m2m.entity.ExtOutContent;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper=false)
public class NewestResponse  extends BaseResponse  {

	private static final long serialVersionUID = 1764455979039777429L;
	private List<ExtOutContent> newestData = Lists.newArrayList();

}
