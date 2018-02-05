package com.m2m.response;

import java.io.Serializable;

import com.m2m.entity.ExtPermissionDescription;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 上海拙心网络科技有限公司出品
 * Author: 郭世同
 * Date: 2017/6/13 0013.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MyLevelResponse extends BaseResponse {
	private static final long serialVersionUID = 1L;
	private ExtPermissionDescription permissions;
    //头像
    private  String avatar ;
    //当前米汤币
    private  int availableCoin ;
    //下一等级所需米汤币
    private  int nextLevelCoin ;
    //人民币
    private double priceRMB;
    private  InnerLevel preLevel;
    private  InnerLevel currentLevel ;
    private  InnerLevel nextLevel;
    private  long stealTopicId;
    private  long randomTopicId;

    public InnerLevel createInnerLevel(){
        return new InnerLevel();
    }

    @Data
    public  static class  InnerLevel implements  Serializable{
		private static final long serialVersionUID = 1L;
		private  int level ;
        private  String  name ;
    }
}
