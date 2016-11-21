package com.chulung.ccache.builder;

import com.chulung.ccache.Cache;
import com.chulung.ccache.DefaultCache;
import com.chulung.ccache.strategy.CacheStrategy;
import com.chulung.ccache.strategy.LiveMillesCacheStrategy;
import com.chulung.ccache.strategy.LruCacheStrategy;

public final class CacheBuilder {

	private CacheConfig cacheConfig;
	private CacheStrategy cacheStrategy;

	private CacheBuilder() {
	}

	public static CacheBuilder config(int maxCapacity) {
		CacheBuilder cacheBuilder = new CacheBuilder();
		if(maxCapacity <= 0) throw new IllegalArgumentException("maxCapacity must >0");
		cacheBuilder.cacheConfig = new CacheConfig(maxCapacity);
		return cacheBuilder;
	}

	public CacheBuilder addLiveMillesCacheStrategy(long timeToLiveSeconds, boolean isRefreshAfterAccess) {
		if(timeToLiveSeconds <= 0) throw new IllegalArgumentException("timeToLiveSeconds must >0");
		this.cacheStrategy = new LiveMillesCacheStrategy(cacheConfig, timeToLiveSeconds, isRefreshAfterAccess);
		return this;
	}

	public CacheBuilder addLruCacheStrategy() {
		this.cacheStrategy = new LruCacheStrategy(cacheConfig);
		return this;
	}

	public Cache generateCache() {
		if(cacheConfig==null) throw new IllegalArgumentException("cacheConfig can't be null");
		if(cacheStrategy==null) throw new IllegalArgumentException("cacheStrategy can't be null");
		return new DefaultCache(cacheConfig, cacheStrategy);
	}
}
