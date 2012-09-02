/**
 * @Title: CBComparator.java
 * @Package: com.android.cb.support
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-3-17
 */
package com.android.cb.support;

/**
 * @author raiden
 *
 * @Description base comparator abstract class
 */
public abstract class CBComparator<TYPE> {

	public final int CB_SORT_DIRECT_ANY = 0;
	public final int CB_SORT_DIRECT_ASCENDING = 1;
	public final int CB_SORT_DIRECT_DESCENDING = 2;

	private int mSortDirect = CB_SORT_DIRECT_ANY;

	public CBComparator() {
	}

	public CBComparator(int direction) {
		mSortDirect = direction;
	}

	public int getSortDirect() {
		return mSortDirect;
	}

	public void setSortDirect(int sortDirect) {
		this.mSortDirect = sortDirect;
	}

	/**
	 * @Description compare 2 items
	 * @param TYPE
	 * @return int if arg0 is bigger (> 0), equal (= 0) or smaller (< 0) than arg1
	 */
	public abstract int compare(TYPE arg0, TYPE arg1);
}
