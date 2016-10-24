package com.ycii.core.base.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ycii.core.base.dao.JEasyDao;
import com.ycii.core.base.service.BaseService;

/**
 * Copyright(c) 2013 hofort. All Rights Reserved.
 * Compiler: JDK1.6.0_23
 * @author Kaylves
 * @create_date 2014-6-15 下午08:16:39
 * @version 1.0
 * @update_user Kaylves
 * @update_date 2014-6-15 下午08:16:39
 * @description 此处写类的描述
 */
public class BaseServiceImpl implements BaseService {
	
	protected Logger logger = LoggerFactory.getLogger(super.getClass());
	
	protected JEasyDao baseDao;

	public void setBaseDao(JEasyDao baseDao) {
		this.baseDao = baseDao;
	}

}