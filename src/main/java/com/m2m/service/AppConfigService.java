package com.m2m.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.m2m.Constant;
import com.m2m.dao.AppConfigDao;
import com.m2m.domain.AppConfig;

@Service
public class AppConfigService {
	
	@Autowired
	private AppConfigDao appConfigDao;
	
	@Autowired
	private RedisService redisService;
	
	
	public String getAppConfigByKey(String key){
		String result = redisService.get(Constant.APP_CONFIG_KEY_PRE + key);
		if(null != result){
			return result;
		}
		AppConfig config = appConfigDao.getAppConfigByKey(key);
		if(null != config && null != config.getConfigValue()){
			redisService.set(Constant.APP_CONFIG_KEY_PRE + key, config.getConfigValue());
			return config.getConfigValue();
		}
		return null;
	}
	
    public Integer getIntegerAppConfigByKey(String key) {
        String result = redisService.get(Constant.APP_CONFIG_KEY_PRE + key);
        if(!StringUtils.isEmpty(result)){
            return Integer.parseInt(result);
        }
        AppConfig config = appConfigDao.getAppConfigByKey(key);
        if(null != config && !StringUtils.isEmpty(config.getConfigValue())){
        	redisService.set(Constant.APP_CONFIG_KEY_PRE + key, config.getConfigValue());
            return Integer.parseInt(config.getConfigValue());
        }
        return null;
    }
}
