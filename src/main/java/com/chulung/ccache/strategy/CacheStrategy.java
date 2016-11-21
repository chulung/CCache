package com.chulung.ccache.strategy;

import com.chulung.ccache.Cache;
import com.chulung.ccache.Element;
import com.chulung.ccache.builder.CacheConfig;

/**
 * 缓存策略接口
 */
public abstract class CacheStrategy {
	protected CacheConfig cacheConfig;

	public CacheStrategy(CacheConfig cacheConfig) {
		this.cacheConfig = cacheConfig;
	}

	/**
	 *根据策略判断缓存是否过期
	 * @param element
	 * @return
	 */
	public abstract boolean isExpired(Element element);

	/**
	 * 在元素被访问时调用
	 * @param v
	 */
	public abstract void notifyAccess(Element v);

	/**
	 * 在元素被添加时调用
	 * @param cache
	 */
	public abstract void notifyPut(Cache cache);

	/**
	 * 处理过期元素
	 * @param cache
	 */
	public void handlerExpired(Cache cache) {
	}

}
