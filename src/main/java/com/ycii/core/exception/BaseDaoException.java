package com.ycii.core.exception;

import org.springframework.dao.DataAccessException;
/**
 * 
 * Copyright(c) 2013 hofort. All Rights Reserved.
 * Compiler: JDK1.6.0_23
 * @author Kaylves
 * @create_date 2014-6-15 下午08:44:01
 * @version 1.0
 * @update_user Kaylves
 * @update_date 2014-6-15 下午08:44:01
 * @description 此处写类的描述
 */
public class BaseDaoException extends DataAccessException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BaseDaoException(String message) {
		super(message);
	}

	public BaseDaoException(String message, Throwable cause) {
		super(message, cause);
	}

	public BaseDaoException(String message, String[] args) {
		super(message);
	}

	public BaseDaoException(String message, String[] args, boolean isI18nArgs) {
		super(message);
	}

}