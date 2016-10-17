package com.chulung.ccache;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.chulung.ccache.builder.CacheBuilder;

public class DefaultCacheTest {
	private static final Integer v = 1;
	private Cache cache;

	@Before
	public void setUp() {
		cache = CacheBuilder.config(1).addLruCacheStrategy().generateCache();
		cache.put("k", v);
	}

	@Test
	public void testGetAndPutLM() throws InterruptedException {
		cache = CacheBuilder.config(10).addLiveMillesCacheStrategy(1, true).generateCache();
		cache.put("k", v);
		assertEquals(v, cache.get("k"));
		Thread.sleep(2000);
		assertNull(cache.get("k"));
	}

	@Test
	public void testGetAndPutLRU() throws InterruptedException {
		cache = CacheBuilder.config(1).addLruCacheStrategy().generateCache();
		cache.put("k", v);
		assertEquals(v, cache.get("k"));
		cache.put("k2", v);
		Thread.sleep(2000);
		assertNull(cache.get("k"));
		assertEquals(v, cache.get("k2"));
	}

	@Test
	public void testClear() {
		assertNotNull(cache.get("k"));
		cache.clear();
		assertNull(cache.get("k"));
	}

	@Test
	public void testRemove() {
		assertNotNull(cache.get("k"));
		cache.remove("k");
		assertNull(cache.get("k"));
	}

	@Test
	public void testGetElementCount() {
		assertEquals(1, cache.getElementCount());
	}

	@Test
	public void testAsMap() {
		assertEquals(1, cache.asMap().size());
	}

}
