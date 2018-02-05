package com.m2m.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ShowListRequest extends BaseRequest {
	private static final long serialVersionUID = 1589903415803238741L;

	private int sinceId;// 分页对象
    private int listType;// 0 找组织，1找谁
}
