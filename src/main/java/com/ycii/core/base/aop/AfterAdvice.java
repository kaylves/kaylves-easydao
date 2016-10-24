package com.ycii.core.base.aop;

import org.aspectj.lang.JoinPoint;

import com.ycii.core.exception.AopException;

public interface AfterAdvice {
	
	void doAfter(JoinPoint joinPoint, Object obj) throws AopException;

}