package com.m2m.entity;

import com.google.common.collect.Lists;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserPermissionDto implements Serializable {
	private static final long serialVersionUID = -259089169260218992L;
	
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
