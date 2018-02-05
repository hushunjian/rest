package com.m2m.response;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by pc339 on 2017/9/22.
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class GetLevelListResponse extends BaseResponse  {
	private static final long serialVersionUID = 7002311410794738477L;
	private List<UserLevelDto> levels = Lists.newArrayList();
    //等级定义
    private String levelDefinition;
    
    @Data
    public static class UserLevelDto implements Serializable{
		private static final long serialVersionUID = 389949153357514819L;

		private int level;
        private String icon;
        private String name;
        private String lvName;
        private int needCoins;
        private int[] permissions;

    }

}
