package com.chulung.ccache;

import java.util.Map;

public interface Cache {

	boolean put(Object Objectey, Object Objectalue);

	void clear();

	boolean putIfAbsent(Object Objectey, Object Objectalue);

	int getElementCount();

	Map<Object, Element> asMap();

	<V> V get(Object key);

	<V> V remove(Object key);

}
