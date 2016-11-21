package com.chulung.ccache.strategy;

import java.util.Map.Entry;

import com.chulung.ccache.Cache;
import com.chulung.ccache.Element;
import com.chulung.ccache.builder.CacheConfig;

/**
 * 最近最少使用策略
 */
public class LruCacheStrategy extends CacheStrategy {

	public LruCacheStrategy(CacheConfig cacheConfig) {
		super(cacheConfig);
	}

	@Override
	public boolean isExpired(Element element) {
		return false;
	}

	@Override
	public void notifyAccess(Element v) {
		v.setLastAccessMills(System.currentTimeMillis());
	}

	@Override
	public void notifyPut(Cache cache) {
		if (cache.getElementCount() >= this.cacheConfig.getMaxCapacity()) {
			Entry<Object, Element> lowest = null;
			for (Entry<Object, Element> e : cache.asMap().entrySet()) {
				if (lowest == null) {
					lowest = e;
				} else {
					lowest = e.getValue().getLastAccessMills() < lowest.getValue().getLastAccessMills() ? e : lowest;
				}
			}
			cache.remove(lowest.getKey());
		}
	}

}
