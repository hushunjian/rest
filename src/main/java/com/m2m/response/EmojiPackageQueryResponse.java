package com.m2m.response;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class EmojiPackageQueryResponse extends BaseResponse{
	
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private List<PackageData> packageData = Lists.newArrayList();
	
	@Data
	public static class PackageData implements Serializable{
		private static final long serialVersionUID = 1L;
		int id;
		String name;
		String cover;
		int emojiType;
		int version;
		int pVersion;
		String extra;
	}
}
