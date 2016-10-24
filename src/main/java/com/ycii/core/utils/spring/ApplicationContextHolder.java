
package com.ycii.core.utils.spring;

import javax.annotation.PostConstruct;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationObjectSupport;

/****************************************************************
 * Copyright(c) 2013 hofort. All Rights Reserved.
 * Title: OaApplicationContextHolder
 * Compiler: JDK1.6.0_23
 * @author Grahor
 * @create_date 2013-9-23 下午11:39:59
 * @update_user Grahor
 * @update_date 2013-9-23 下午11:39:59
 * @description the application context holder
 ****************************************************************/
public class ApplicationContextHolder extends WebApplicationObjectSupport{
	
	private static ApplicationContext context;
	
	@PostConstruct
	public void init(){
		context = getApplicationContext();
	}
	
	/**
	 * @time 2013-9-23  下午11:43:40
	 * @return ApplicationContext
	 * @description get application context
	 * @since 1.0
	 */
	public static ApplicationContext getOaApplicationContext(){
		return context;
	}
	
	/**
	 * @time 2013-9-23  下午11:45:22
	 * @param <T>
	 * @param clazz
	 * @return T
	 * @description get bean for giving clazz
	 * @since 1.0
	 */
	public static <T> T getBean(Class<T> clazz){
		return context.getBean(clazz);
	}

}
