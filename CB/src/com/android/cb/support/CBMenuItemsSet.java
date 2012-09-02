/**
 * @Title: CBMenuItemsSet.java
 * @Package: com.android.cb.support
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-3-17
 */
package com.android.cb.support;

import java.util.ArrayList;

/**
 * @author raiden
 *
 * @Description menu item set
 */
public class CBMenuItemsSet implements CBIFSetHandler<CBMenuItem> {
	private ArrayList<CBMenuItem> mMenuItemList = new ArrayList<CBMenuItem>();

	public CBMenuItemsSet() {
	}

	public CBMenuItemsSet(CBMenuItemsSet set) {
		mMenuItemList = set.getMenuItemsList();
	}

	public CBMenuItem getItem(int index) {
		if (index <= 0 || index >= mMenuItemList.size())
			return null;

		return mMenuItemList.get(index);
	}

	/**
	 * @Description select real item in set by fake item
	 * @param @param item
	 * @return CBMenuItem if not found, returns null
	 */
	public CBMenuItem getItem(CBMenuItem item) {
		if (item == null)
			return null;

		for (int i = 0; i < mMenuItemList.size(); ++i) {
			CBMenuItem it = mMenuItemList.get(i);
			if (it.getDish().equals(item.getDish()))
				return it;
		}

		return null;
	}

	public CBMenuItem getItemById(CBId id) {
		for (int i = 0; i < count(); ++i) {
			CBMenuItem item = get(i);
			if (item.getDish().getId().equals(id))
				return item;
		}

		return null;
	}

	/**
	 * @Description adding item to set if item is not already in set
	 * @param @param item
	 * @return boolean will return false if item exists in mMenuItemList
	 */
	public boolean add(CBMenuItem item) {
		if (item == null)
			return false;

		if (contains(item))
			return false;

		mMenuItemList.add(item);
		return true;
	}

	/**
	 * @Description adding item to set and put it in a right place
	 * @param @param item
	 * @return boolean will return false if item exists in mMenuItemList
	 */
	public boolean add(CBMenuItem item, CBComparator<CBMenuItem> comparator) {
		if (item == null)
			return false;

		if (contains(item))
			return false;

		for (int i = 0; i < mMenuItemList.size(); ++i) {
			CBMenuItem current = mMenuItemList.get(i);
			int compareRes = comparator.compare(item, current);
			if (compareRes < 0) {
				mMenuItemList.add(i, item);
				return true;
			}
		}

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
			if (mMenuItemList.get(i).getDish().equals(item.getDish())) {
				return update(i,item);
			}
		}

		return false;
	}

	public boolean update(int index,  CBMenuItem item) {
		if (index < 0 || index >= mMenuItemList.size()
				|| item == null)
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
			CBMenuItem item = set.getMenuItemsList().get(i);
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
			if (mMenuItemList.get(i).getDish().equals(item.getDish()))
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

	public void clear() {
		mMenuItemList.clear();
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
			if (it.getDish().equals(item.getDish()))
				return true;
		}

		return false;
	}

	public int count() {
		return mMenuItemList.size();
	}

	public ArrayList<CBMenuItem> getMenuItemsList() {
		return mMenuItemList;
	}

	public void setMenuItemsList(ArrayList<CBMenuItem> itemList) {
		this.mMenuItemList = itemList;
	}

	public CBMenuItem get(int index) {
		if (index < 0 || index >= mMenuItemList.size())
			return null;

		return mMenuItemList.get(index);
	}

	public CBMenuItem get(CBMenuItem item) {
		if (item == null)
			return null;

		for (int i = 0; i < mMenuItemList.size(); ++i) {
			CBMenuItem it = mMenuItemList.get(i);
			if (it.equals(item))
				return it;
		}

		return null;
	}

}
