package com.ycii.core.base.aop;

import org.aspectj.lang.JoinPoint;

import com.ycii.core.exception.AopException;

public abstract interface ExceptionAdvice
{
  public abstract void doThrowing(JoinPoint paramJoinPoint, Throwable paramThrowable)
    throws AopException;
}