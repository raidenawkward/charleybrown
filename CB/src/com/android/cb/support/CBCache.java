/**
 * @Title: CBCache.java
 * @Package: com.android.cb.support
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-3-20
 */
package com.android.cb.support;

import java.util.ArrayList;

/**
 * @author raiden
 *
 */
public abstract class CBCache<T> {

	public final int CBCACHE_STATUS_UNKNOWN = 0;
	public final int CBCACHE_STATUS_READY = 1;
	public final int CBCACHE_STATUS_LOADING = 2;

	protected ArrayList<T> mList;
	protected int mCurrent = 0;
	protected int mMaxCacheSize = 0;
	protected int mStatus = CBCACHE_STATUS_UNKNOWN;


	public int getStatus() {
		return mStatus;
	}

	public int getCachedSize() {
		return mList.size();
	}

	public int getMaxCacheSize() {
		return mMaxCacheSize;
	}

	public void setMaxCacheSize(int size) {
		mMaxCacheSize = size;
	}

	public int getCurrentIndex() {
		return mCurrent;
	}

	public abstract T getCurrent();
	public abstract T getNext();
	public abstract T getPrev();
	public abstract int getTotalCachedItemCount();
	public abstract boolean moveTo(int index);
	public abstract boolean moveToNext();
	public abstract boolean moveToPrev();

}
