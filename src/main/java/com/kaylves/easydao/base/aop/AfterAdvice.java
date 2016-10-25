package com.kaylves.easydao.base.aop;

import org.aspectj.lang.JoinPoint;

import com.kaylves.easydao.exception.AopException;

public interface AfterAdvice {
	
	void doAfter(JoinPoint joinPoint, Object obj) throws AopException;

}