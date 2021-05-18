package com.esandmongodb.posterapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import com.esandmongodb.posterapp.filter.OperationInterceptor;
@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Autowired
	private OperationInterceptor operationInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		WebMvcConfigurer.super.addInterceptors(registry);
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("language");
		registry.addInterceptor(localeChangeInterceptor);
		registry.addInterceptor(this.operationInterceptor).excludePathPatterns("/**/login", "/**/logout");
	}

}
