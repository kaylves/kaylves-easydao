package com.ycii.core.base.aop;

import org.aspectj.lang.JoinPoint;

import com.ycii.core.exception.AopException;

public abstract interface BeforeAdvice
{
  public abstract void doBefore(JoinPoint paramJoinPoint)
    throws AopException;
}