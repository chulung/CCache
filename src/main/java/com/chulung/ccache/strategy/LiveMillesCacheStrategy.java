package com.chulung.ccache.strategy;

import com.chulung.ccache.Cache;
import com.chulung.ccache.Element;
import com.chulung.ccache.builder.CacheConfig;
import com.chulung.ccache.exception.CapacityOutOfBoundsException;
import com.chulung.common.util.Preconditions;

public class LiveMillesCacheStrategy extends CacheStrategy {
	private long timeToLiveMilles;
	private boolean isRefreshAfterAccess;

	public LiveMillesCacheStrategy(CacheConfig cacheConfig, long timeToLiveSeconds, boolean isRefreshAfterAccess) {
		super(cacheConfig);
		this.timeToLiveMilles = timeToLiveSeconds * 1000;
		this.isRefreshAfterAccess = isRefreshAfterAccess;
	}

	public long getTimeToLiveMilles() {
		return timeToLiveMilles;
	}

	public void setTimeToLiveMilles(long timeToLiveMilles) {
		this.timeToLiveMilles = timeToLiveMilles;
	}

	@Override
	public boolean isExpired(Element element) {
		return (element.getCreateMills() + this.timeToLiveMilles) < System.currentTimeMillis();
	}

	@Override
	public void validateConfig() {
		Preconditions.checkState(timeToLiveMilles <= 0, "timeToLiveMilles must > 0 !");
	}

	public boolean isRefreshAfterAccess() {
		return isRefreshAfterAccess;
	}

	public void setRefreshAfterAccess(boolean isRefreshAfterAccess) {
		this.isRefreshAfterAccess = isRefreshAfterAccess;
	}

	@Override
	public void notifyAccess(Element v) {
		if (isRefreshAfterAccess) {
			v.setCreateMills(System.currentTimeMillis());
		}
	}
	@Override
	public void notifyPut(Cache cache) {
		if (cache.getElementCount() >= this.cacheConfig.getMaxCapacity()) {
			handlerExpired(cache);
			if (cache.getElementCount() >= this.cacheConfig.getMaxCapacity())
				throw new CapacityOutOfBoundsException();
		}
	}
	@Override
	public void handlerExpired(final Cache cache) {
		cache.asMap().forEach((k, v) -> {
			if (isExpired(v)) {
				cache.remove(k);
			}
		});
	}

}
