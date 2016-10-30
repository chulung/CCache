package com.chulung.ccache.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import com.chulung.ccache.Cache;
import com.chulung.ccache.annotation.CCache;
import com.chulung.ccache.builder.CacheBuilder;

import java.lang.reflect.Method;

@Aspect
public class CacheAop {
	private Cache cache = CacheBuilder.config(10).addLiveMillesCacheStrategy(30 * 60, true).generateCache();
	
	@Pointcut(value = "@annotation(com.chulung.ccache.annotation.CCache)")
	public void pointcut() {

	}
	@Around("pointcut() ")
	public Object around(ProceedingJoinPoint jp) throws Throwable {
		Method method = ((MethodSignature) jp.getSignature()).getMethod();
		CCache cCache = method.getAnnotation(CCache.class);
		if (cCache.liveSeconds()<=0) {
			throw new IllegalArgumentException("liveSeconds must > 0 !");
		}
		Object value=cache.get(method);
		if (value==null){
			value=jp.proceed();
			cache.put(method,value);
		}
		return value;
	}
}
