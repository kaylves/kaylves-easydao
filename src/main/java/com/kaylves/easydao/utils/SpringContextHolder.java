package com.kaylves.easydao.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
/**
 * 
 * Copyright(c) 2013 hofort. All Rights Reserved.
 * Compiler: JDK1.6.0_23
 * @author Kaylves
 * @create_date 2014-6-15 下午06:46:31
 * @version 1.0
 * @update_user Kaylves
 * @update_date 2014-6-15 下午06:46:31
 * @description 此处写类的描述
 */
@SuppressWarnings({"unchecked","rawtypes"})
public class SpringContextHolder implements ApplicationContextAware {
	private static ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContextHolder.applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		checkApplicationContext();
		return applicationContext;
	}

	private static void checkApplicationContext() {
		if (applicationContext == null){
			throw new IllegalStateException("applicaitonContext未注入,请在applicationContext.xml中定义SpringContextHolder");
		}
	}

	public static <T> T getBean(String beanName) {
		checkApplicationContext();
		return (T) applicationContext.getBean(beanName);
	}

	public static <T> T getBean(Class<T> clazz) {
		checkApplicationContext();
		return (T) applicationContext.getBeansOfType(clazz);
	}

	public static Map<String, Object> getAllBeans() {
		String[] beanNames = applicationContext.getBeanDefinitionNames();
		Map beanMap = new HashMap();
		String[] arrayOfString1 = beanNames;
		int j = beanNames.length;
		for (int i = 0; i < j; i++) {
			String beanName = arrayOfString1[i];
			if (beanName.indexOf("ProxyTemplate") != -1)
				continue;
			Object bean = applicationContext.getBean(beanName);
			beanMap.put(beanName, bean);
		}
		return beanMap;
	}
}