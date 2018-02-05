package com.m2m.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.m2m.domain.AppConfig;
import com.m2m.domain.AppConfigExample;
import com.m2m.domain.SystemConfig;
import com.m2m.domain.SystemConfigExample;
import com.m2m.mapper.AppConfigMapper;
import com.m2m.mapper.SystemConfigMapper;


@Repository
public class AppConfigDao {
    @Autowired
    private AppConfigMapper appConfigMapper;
    
    @Autowired
    private SystemConfigMapper systemConfigMapper;
    
	public AppConfig getAppConfigByKey(String key){
		AppConfigExample example = new AppConfigExample();
		AppConfigExample.Criteria criteria  = example.createCriteria();
		criteria.andConfigKeyEqualTo(key);
		List<AppConfig> list = appConfigMapper.selectByExample(example);
		if(null != list && list.size() > 0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
    public SystemConfig getSystemConfig() {
        SystemConfigExample example = new SystemConfigExample();
        example.setOrderByClause(" id desc");
        List<SystemConfig> systemConfigs =systemConfigMapper.selectByExample(example);
        return systemConfigs.size()>0?systemConfigs.get(0):null;
    }
}
