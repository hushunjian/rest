package com.m2m.response;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class TeaseListQueryResponse extends BaseResponse {
	private static final long serialVersionUID = -4584117836473345812L;

	private List<TeaseElement> teaseData = Lists.newArrayList();
	
	@Data
	public static class TeaseElement implements Serializable {
		private static final long serialVersionUID = 1891076617072589444L;
		
		private long id;
		private String name;
		private String audio;
		private String image;
	}
}
