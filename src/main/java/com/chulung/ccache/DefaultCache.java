package com.chulung.ccache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.chulung.ccache.builder.CacheConfig;
import com.chulung.ccache.strategy.CacheStrategy;

public final class DefaultCache implements Cache {
	private Map<Object, Element> map = new ConcurrentHashMap<>();
	protected CacheConfig cacheConfig;
	private CacheStrategy cacheStrategy;

	public DefaultCache(CacheConfig cacheConfig, CacheStrategy cacheStrategy) {
		this.cacheConfig = cacheConfig;
		this.cacheStrategy = cacheStrategy;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <V> V get(Object key) {
		Element e = this.map.get(key);
		if (e == null || cacheStrategy.isExpired(e)) {
			return null;
		}
		cacheStrategy.notifyAccess(e);
		return (V) e.getValue();
	}

	@Override
	public boolean put(Object key, Object value) {
		this.cacheStrategy.notifyPut(this);
		return this.map.put(key, new Element(value)) != null;
	}

	@Override
	public void clear() {
		map.clear();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <V> V remove(Object key) {
		Element e = this.map.remove(key);
		return (V) (e == null ? null : e.getValue());
	}

	@Override
	public boolean putIfAbsent(Object key, Object value) {
		return this.map.putIfAbsent(key, new Element(value)) != null;
	}

	@Override
	public int getElementCount() {
		this.cacheStrategy.handlerExpired(this);
		return this.map.size();
	}

	@Override
	public Map<Object, Element> asMap() {
		return this.map;
	}

}
