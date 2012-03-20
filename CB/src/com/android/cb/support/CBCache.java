/**
 * @Title: CBCache.java
 * @Package: com.android.cb.support
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-3-20
 */
package com.android.cb.support;

/**
 * @author raiden
 *
 */
public abstract class CBCache<T> {

	public final int CBCACHE_STATUS_UNKNOWN = 0;
	public final int CBCACHE_STATUS_READY = 1;
	public final int CBCACHE_STATUS_LOADING = 2;

	protected int mStatus = CBCACHE_STATUS_UNKNOWN;


	public int getStatus() {
		return mStatus;
	}

	public abstract int getCachedSize();
	public abstract T getCurrent();
	public abstract T getNext();
	public abstract T getPrev();
	public abstract void clear();
	public abstract int getMaxCacheSize();
	public abstract int getTotalCachedItemCount();
	public abstract boolean moveTo(int index);
	public abstract boolean moveToNext();
	public abstract boolean moveToPrev();

}
