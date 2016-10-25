package com.kaylves.easydao.base.aop;

import org.aspectj.lang.ProceedingJoinPoint;

import com.kaylves.easydao.exception.AopException;

public interface AroundAdvice {
	
	Object doAround(ProceedingJoinPoint processJoin) throws AopException;

}