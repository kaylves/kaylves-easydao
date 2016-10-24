package com.ycii.core.base.aop;

import org.aspectj.lang.ProceedingJoinPoint;

import com.ycii.core.exception.AopException;

public interface AroundAdvice {
	
	Object doAround(ProceedingJoinPoint processJoin) throws AopException;

}