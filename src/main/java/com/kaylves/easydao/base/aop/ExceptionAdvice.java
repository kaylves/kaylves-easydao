package com.kaylves.easydao.base.aop;

import org.aspectj.lang.JoinPoint;

import com.kaylves.easydao.exception.AopException;

public abstract interface ExceptionAdvice
{
  public abstract void doThrowing(JoinPoint paramJoinPoint, Throwable paramThrowable)
    throws AopException;
}