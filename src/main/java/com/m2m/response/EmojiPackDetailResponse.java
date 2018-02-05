package com.m2m.response;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class EmojiPackDetailResponse extends BaseResponse{
	
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private long packageId;
	private int emojiType;
	private String packageName;
	private String packageCover;
	private int  packageVersion;
	private int  packagePversion;
	private List<PackageDetailData> emojiData=Lists.newArrayList();

	@Data
	public static class PackageDetailData implements Serializable{
		private static final long serialVersionUID = 1L;
		private long id;
		private String title;
		private String image;
		private String thumb;
		private long w;
		private long h;
		private long thumb_w;
		private long thumb_h;
		private String extra;
		private String content;
		private int emojiType;
	}
}
