package com.chulung.ccache;

public class Element {
	private Object value;
	private int hitCount;
	private long createMills;
	private long lastAccessMills;

	public Element(Object v) {
		this.setCreateMills(System.currentTimeMillis());
		this.value = v;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public int getHitCount() {
		return hitCount;
	}

	public void setHitCount(int hitCount) {
		this.hitCount = hitCount;
	}

	public long getLastAccessMills() {
		return lastAccessMills;
	}

	public void setLastAccessMills(long lastAccessMills) {
		this.lastAccessMills = lastAccessMills;
	}

	public long getCreateMills() {
		return createMills;
	}

	public void setCreateMills(long createMills) {
		this.createMills = createMills;
	}

}