package com.castle.repo.domain;

/**
 * 可锁定的，如为true，则不可删除
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午6:09:00
 */
public interface Lockedable {
	public boolean isLocked();

	public void setLocked(boolean locked);

	/**
	 * 标识为已锁定
	 */
	public void markLocked();
}