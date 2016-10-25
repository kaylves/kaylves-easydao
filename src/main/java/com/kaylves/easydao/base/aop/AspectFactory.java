package com.kaylves.easydao.base.aop;

import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaylves.easydao.exception.AopException;
import com.kaylves.easydao.utils.SpringContextHolder;

@SuppressWarnings( "rawtypes" )
public class AspectFactory {
	protected Logger logger = LoggerFactory.getLogger(AspectFactory.class);
	private Map<String, String> adviceMap;

	private String getBeanName(String methodInfo) {
		if (this.adviceMap == null)
			return null;
        Iterator keyIt = this.adviceMap.keySet().iterator();
		while (keyIt.hasNext()) {
			String regex = (String) keyIt.next();
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(methodInfo);
			if (m.matches()) {
				return (String) this.adviceMap.get(regex);
			}
		}
		return null;
	}

	public void doAfter(JoinPoint jp, Object retVal) throws AopException {
		if (this.adviceMap == null)
			return;
		String className = jp.getTarget().getClass().getName();
		String methodName = jp.getSignature().getName();
		String beanName = getBeanName(className + "." + methodName);
		if (beanName == null)
			return;
		Object aspect = SpringContextHolder.getBean(beanName);
		if (aspect == null)
			throw new AopException("AOP配置错误！Bean" + beanName + "未配置！");
		if (!(aspect instanceof AfterAdvice))
			return;
		AfterAdvice advice = (AfterAdvice) aspect;
		advice.doAfter(jp, retVal);
	}

	public Object doAround(ProceedingJoinPoint pjp) throws AopException {
		if (this.adviceMap == null) {
			Object retVal = null;
			try {
				retVal = pjp.proceed();
			} catch (Throwable cause) {
				throw new AopException(cause.getLocalizedMessage(), cause);
			}
			return retVal;
		}
		String className = pjp.getTarget().getClass().getName();
		String methodName = pjp.getSignature().getName();
		String beanName = getBeanName(className + "." + methodName);
		if (beanName == null) {
			Object retVal = null;
			try {
				retVal = pjp.proceed();
			} catch (Throwable cause) {
				throw new AopException(cause.getLocalizedMessage(), cause);
			}
			return retVal;
		}
		Object aspect = SpringContextHolder.getBean(beanName);
		if (aspect == null)
			throw new AopException("AOP配置错误！Bean" + beanName + "未配置！");
		if (!(aspect instanceof AroundAdvice)) {
			Object retVal = null;
			try {
				retVal = pjp.proceed();
			} catch (Throwable cause) {
				throw new AopException(cause.getLocalizedMessage(), cause);
			}
			return retVal;
		}
		AroundAdvice advice = (AroundAdvice) aspect;
		return advice.doAround(pjp);
	}

	public void doBefore(JoinPoint jp) throws AopException {
		if (this.adviceMap == null)
			return;
		String className = jp.getTarget().getClass().getName();
		String methodName = jp.getSignature().getName();
		String beanName = getBeanName(className + "." + methodName);
		if (beanName == null) {
			return;
		}
		Object aspect = SpringContextHolder.getBean(beanName);
		if (aspect == null)
			throw new AopException("AOP配置错误！Bean" + beanName + "未配置！");
		if (!(aspect instanceof BeforeAdvice))
			return;
		BeforeAdvice advice = (BeforeAdvice) aspect;
		advice.doBefore(jp);
	}

	public void doThrowing(JoinPoint jp, Throwable ex) throws AopException {
		String className = jp.getTarget().getClass().getName();
		String methodName = jp.getSignature().getName();
		String beanName = getBeanName(className + "." + methodName);

		if (beanName == null) {
			ex.printStackTrace();
			this.logger.error(ex.getCause().getLocalizedMessage(), ex
					.getCause());
		}
		Object aspect = SpringContextHolder.getBean(beanName);
		if (aspect == null)
			throw new AopException("AOP配置错误！Bean" + beanName + "未配置！");
		if (!(aspect instanceof ExceptionAdvice)) {
			this.logger.error(ex.getLocalizedMessage(), ex.getCause());
		}
		ExceptionAdvice advice = (ExceptionAdvice) SpringContextHolder
				.getBean(beanName);
		advice.doThrowing(jp, ex);
	}

	public Map<String, String> getAdviceMap() {
		return this.adviceMap;
	}

	public void setAdviceMap(Map<String, String> adviceMap) {
		this.adviceMap = adviceMap;
	}
}