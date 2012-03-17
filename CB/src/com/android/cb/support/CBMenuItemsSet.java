/**
 * @Title: CBMenuItemsSet.java
 * @Package: com.android.cb.support
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-3-17
 */
package com.android.cb.support;

import java.util.List;

/**
 * @author raiden
 *
 * @Description menu item set
 */
public class CBMenuItemsSet {
	private List<CBMenuItem> mMenuItemList;

	public CBMenuItemsSet() {
	}

	public CBMenuItemsSet(CBMenuItemsSet set) {
		mMenuItemList = set.getMenuList();
	}

	/**
	 * @Description adding item to set if item is not already in set
	 * @param @param item
	 * @return boolean will return false if item exists in mMenuItemList
	 */
	boolean add(CBMenuItem item) {
		if (item == null)
			return false;

		if (contains(item))
			return false;

		mMenuItemList.add(item);
		return true;
	}

	/**
	 * @Description update item in set
	 * @param @param item
	 * @return boolean returns false if item is null or item is not in set
	 */
	public boolean update(CBMenuItem item) {
		if (item == null)
			return false;

		for (int i = 0; i < mMenuItemList.size(); ++i) {
			if (mMenuItemList.get(i).getDish().getId() == item.getDish().getId()) {
				return update(i,item);
			}
		}

		return false;
	}

	public boolean update(int index,  CBMenuItem item) {
		if (index < 0 || index >= mMenuItemList.size())
			return false;

		mMenuItemList.set(index, item);

		return true;
	}

	/**
	 * @Description combine with new set
	 * @param @param set
	 * @return int items count both in src set and target set
	 */
	public int combine(CBMenuItemsSet set) {
		if (set == null)
			return 0;

		int res = 0;
		for (int i = 0; i < set.count(); ++i) {
			CBMenuItem item = set.getMenuList().get(i);
			if (!add(item)) {
				++res;
			}
		}

		return res;
	}

	/**
	 * @Description get item index in set by dish->id
	 * @param @param item
	 * @param @return
	 * @return int
	 */
	public int getIndexOf(CBMenuItem item) {
		for (int i = 0; i < mMenuItemList.size(); ++i) {
			if (mMenuItemList.get(i).getDish().getId() == item.getDish().getId())
				return i;
		}
		return -1;
	}

	public boolean remove(CBMenuItem item) {
		if (item == null)
			return false;

		return remove(getIndexOf(item));
	}

	public boolean remove(int index) {
		if (index < 0 || index >= mMenuItemList.size())
			return false;

		mMenuItemList.remove(index);
		return true;
	}

	/**
	 * @Description judging if item is in set with 'item->dish->id'
	 * @param @param item
	 * @return boolean
	 */
	public boolean contains(CBMenuItem item) {
		if (item == null)
			return false;

		for (int i = 0; i < mMenuItemList.size(); ++i) {
			CBMenuItem it = mMenuItemList.get(i);
			if (it.getDish().getId() == item.getDish().getId())
				return true;
		}

		return false;
	}

	public int count() {
		return mMenuItemList.size();
	}

	public List<CBMenuItem> getMenuList() {
		return mMenuItemList;
	}

	public void setMenuList(List<CBMenuItem> itemList) {
		this.mMenuItemList = itemList;
	}
}
