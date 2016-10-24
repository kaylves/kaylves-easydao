package com.ycii.core.exception;
/**
 * Copyright(c) 2013 hofort. All Rights Reserved.
 * Compiler: JDK1.6.0_23
 * @author Kaylves
 * @create_date 2014-6-16 上午09:15:47
 * @version 1.0
 * @update_user Kaylves
 * @update_date 2014-6-16 上午09:15:47
 * @description 此处写类的描述
 */
public class AopException extends Exception {
	private static final long serialVersionUID = 1L;

	public AopException(String error) {
		super(error);
	}

	public AopException(String error, Throwable cause) {
		super(error, cause);
	}
}