/**
 * @Title: CBOrdersSet.java
 * @Package: com.android.cb.support
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-3-17
 */
package com.android.cb.support;

import java.util.List;

/**
 * @author raiden
 *
 * @Description set of orders
 */
public class CBOrdersSet {

	public final int CBORDER_SORT_NO_SORT= 0;
	public final int CBORDER_SORT_BY_ID = 1;
	public final int CBORDER_SORT_BY_TIME = 2;

	private List<CBOrder> mSet;

	public CBOrdersSet() {
	}

	public int getIndexOf(CBOrder item) {
		if (item == null)
			return -1;

		for (int i = 0; i < mSet.size(); ++i) {
			if (mSet.get(i).equals(item))
				return i;
		}

		return -1;
	}

	public CBOrder get(CBOrder item) {
		if (item == null)
			return null;

		for (int i = 0; i < mSet.size(); ++i) {
			CBOrder it = mSet.get(i);
			if (it.equals(item))
				return it;
		}

		return null;
	}

	public boolean add(CBOrder item) {
		if (item == null)
			return false;

		if (contains(item))
			return false;

		mSet.add(item);
		return true;
	}

	public boolean update(CBOrder item) {
		if (item == null)
			return false;

		for (int i = 0; i < mSet.size(); ++i) {
			CBOrder it = mSet.get(i);
			if (it.equals(item)) {
				return update(i,item);
			}
		}

		return false;
	}

	public boolean update(int index, CBOrder item) {
		if (index < 0 || index >= mSet.size()
				|| item == null)
			return false;

		mSet.set(index, item);
		return true;
	}

	public int combine(CBOrdersSet set) {
		if (set == null)
			return 0;

		int res = 0;

		return res;
	}

	public boolean remove(CBOrder item) {
		if (item == null)
			return false;

		return true;
	}

	public boolean remove(int index) {
		if (index < 0 || index >= mSet.size())
			return false;

		return true;
	}

	public boolean contains(CBOrder item) {
		if (item == null)
			return false;

		for (int i = 0; i < mSet.size(); ++i) {
			if (mSet.get(i).equals(item))
				return true;
		}

		return false;
	}

	public int count() {
		return mSet.size();
	}

	public List<CBOrder> getSet() {
		return mSet;
	}

	public void setSet(List<CBOrder> set) {
		this.mSet = set;
	}

}
