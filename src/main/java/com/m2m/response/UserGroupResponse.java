package com.m2m.response;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserGroupResponse extends BaseResponse  {
	private static final long serialVersionUID = -308914526308285491L;
	
	private List<UserElement> userGroup=Lists.newArrayList(); 

    @Data
    public static class UserElement implements Serializable{
		private static final long serialVersionUID = -2635777703786881347L;

		private long uid;

        private String nickName;

        private String avatar;

        private int gender;

        private int vip;

        private int level;

        private String introduce;
        
        private String avatarFrame;
        
        private long industryId;
        
        private String industry;

    }
}
