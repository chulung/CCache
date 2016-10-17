package com.chulung.ccache.strategy;

import com.chulung.ccache.Cache;
import com.chulung.ccache.Element;
import com.chulung.ccache.builder.CacheConfig;

public abstract class CacheStrategy {
	protected CacheConfig cacheConfig;

	public CacheStrategy(CacheConfig cacheConfig) {
		this.cacheConfig = cacheConfig;
	}

	public abstract boolean isExpired(Element element);

	public abstract void notifyAccess(Element v);

	public abstract void validateConfig();

	public abstract void notifyPut(Cache cache);

	public void handlerExpired(Cache cache) {
	}

}
