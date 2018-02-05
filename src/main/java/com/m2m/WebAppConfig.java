package com.m2m;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.m2m.interceptor.UserSecurityInterceptor;

@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {

	@Bean
	public UserSecurityInterceptor getUserSecurityInterceptor() {
		return new UserSecurityInterceptor();
	}
	
	/**
	 * 添加过滤器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {  
        registry.addInterceptor(getUserSecurityInterceptor()).addPathPatterns("/**");
    }
	
	/**
	 * 增加fastjson的解析器
	 */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters){
		super.configureMessageConverters(converters);
		FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
		fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
		converters.add(fastJsonHttpMessageConverter);
	}
}
