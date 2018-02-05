package com.m2m.response;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;
import com.m2m.domain.Dictionary;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 上海拙心网络科技有限公司出品
 * Author: 代宝磊
 * Date: 2016/2/29
 * Time :22:05
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BasicDataSuccessResponse extends BaseResponse {
	private static final long serialVersionUID = 1L;
	private List<BasicDataSuccessElement> results = Lists.newArrayList();

    public static BasicDataSuccessElement createElement(){
        return new BasicDataSuccessElement();
    }

    @Data
    public static class BasicDataSuccessElement implements Serializable {
		private static final long serialVersionUID = 1L;
		private long tid;
        private String type;
        private List<Dictionary> list;
    }
}
