package com.kaylves.easydao.base.aop;

import org.aspectj.lang.JoinPoint;

import com.kaylves.easydao.exception.AopException;

public abstract interface BeforeAdvice
{
  public abstract void doBefore(JoinPoint paramJoinPoint)
    throws AopException;
}