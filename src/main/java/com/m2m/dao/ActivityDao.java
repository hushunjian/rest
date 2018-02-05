package com.m2m.dao;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.m2m.domain.ActivityExample;
import com.m2m.domain.ActivityWithBLOBs;
import com.m2m.domain.AppLightboxSource;
import com.m2m.domain.AppLightboxSourceExample;
import com.m2m.mapper.ActivityMapper;
import com.m2m.mapper.AppLightboxSourceMapper;

@Repository
public class ActivityDao {

	@Autowired
	private ActivityMapper activityMapper;
	
    @Autowired
    private AppLightboxSourceMapper appLightboxSourceMapper;

	public ActivityWithBLOBs getActivityByCid(long topicId, int type) {
		ActivityExample example = new ActivityExample();
		example.createCriteria().andCidEqualTo(topicId).andTypEqualTo(type).andStatusEqualTo(0);
		List<ActivityWithBLOBs> list = activityMapper.selectByExampleWithBLOBs(example);
		return null != list && list.size() > 0 ? list.get(0) : null;
	}

	public AppLightboxSource getAppLightboxSource(Date nowDate) {
		 AppLightboxSourceExample example = new AppLightboxSourceExample();
         example.createCriteria().andStatusEqualTo(0).andStartTimeLessThan(nowDate).andEndTimeGreaterThan(nowDate);
         List<AppLightboxSource> appLightboxSources = appLightboxSourceMapper.selectByExample(example);
         return appLightboxSources.size()>0 && appLightboxSources != null ?appLightboxSources.get(0):null;
	}
}
