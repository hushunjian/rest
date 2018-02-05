package com.m2m.mapstruct;

import java.util.Date;

public class TimeTransform {

	public long date2Long(Date date){
		if(null == date){
			return 0;
		}
		return date.getTime();
	}
	
	public int currentSeconds(Date date){
		if(null == date){
			return 0;
		}
		return (int)(System.currentTimeMillis()-date.getTime())/1000;
	}
}
