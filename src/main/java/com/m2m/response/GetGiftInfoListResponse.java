package com.m2m.response;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 上海拙心网络科技有限公司出品
 *  Author: chenxiang 
 *  Date: 2017-09-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class GetGiftInfoListResponse extends BaseResponse{

	private static final long serialVersionUID = 9025063372338801852L;
	private List<GiftInfoElement> result = Lists.newArrayList();

	public static GiftInfoElement createGiftInfoElement() {
		return new GiftInfoElement();
	}

	@Data
	public static class GiftInfoElement implements Serializable {
		private static final long serialVersionUID = 9104269954351141321L;

		private Long giftId;

		private String name;

		private String image;

		private Integer price;

		private Integer addPrice;

		private Integer imageWidth;

		private Integer imageHeight;

		private String gifImage;

		private Integer playTime;

		private Integer sortNumber;

		private Integer status;

	}

}
