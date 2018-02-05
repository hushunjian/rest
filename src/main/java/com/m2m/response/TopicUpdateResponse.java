package com.m2m.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class TopicUpdateResponse extends BaseResponse {
	private static final long serialVersionUID = -1123068436969127894L;

	private int totalRecords;
    private int updateRecords;
    private int totalPages;
    private int startPageNo;
    private long lastFragmentId;
    private int firstPage;
    private int isForbid;//是否被禁言  0 否 1 是
    private int isAllForbid;//是否全禁言 0 否 1 是
}
