/**
 * @Title: CBMenuGroup.java
 * @Package: com.android.cb.support
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-3-17
 */
package com.android.cb.support;

/**
 * @author raiden
 *
 * @Description logical menu UI item group
 */
public class CBMenuGroup {
	private CBMenuItemsSet mMenuItemsSet = new CBMenuItemsSet();
	private int mCurrentIndex = 0;
	private int mSelectedCount = 0;

	public CBMenuGroup() {
	}

	public CBMenuItemsSet getMenuSet() {
		return mMenuItemsSet;
	}

	public void setMenuSet(CBMenuItemsSet menuSet) {
		this.mMenuItemsSet = new CBMenuItemsSet(menuSet);
	}

	public int getCurrentIndex() {
		return mCurrentIndex;
	}

	public void setCurrentIndex(int currentIndex) {
		this.mCurrentIndex = currentIndex;
	}

	public int getSelectedCount() {
		return mSelectedCount;
	}

	public CBMenuItem next() {
		if (mCurrentIndex == mMenuItemsSet.count() - 1) {
			return null;
		}

		return mMenuItemsSet.get(++mCurrentIndex);
	}

	public CBMenuItem prev() {
		if (mCurrentIndex == 0) {
			return null;
		}

		return mMenuItemsSet.get(--mCurrentIndex);
	}

	public CBMenuItem first() {
		mCurrentIndex = 0;
		return mMenuItemsSet.get(mCurrentIndex);
	}

	public CBMenuItem last() {
		mCurrentIndex = mMenuItemsSet.count() - 1;
		return mMenuItemsSet.get(mCurrentIndex);
	}

	public CBMenuItem current() {
		return mMenuItemsSet.get(mCurrentIndex);
	}

	public CBMenuItem get(int index) {
		return mMenuItemsSet.get(index);
	}

	public CBMenuItem goTo(int index) {
		if (mMenuItemsSet.get(index) != null) {
			mCurrentIndex = index;
			return mMenuItemsSet.get(index);
		}

		return null;
	}

	public int count() {
		return mMenuItemsSet.count();
	}

	public int index() {
		return mCurrentIndex;
	}

	public int getCheckedItemCount() {
		int res = 0;
		for (int i = 0; i < mMenuItemsSet.count(); ++i) {
			if (mMenuItemsSet.get(i).isChecked())
				++res;
		}

		return res;
	}

	public CBMenuItemsSet getCheckedItems() {
		CBMenuItemsSet res = new CBMenuItemsSet();
		for (int i = 0; i < mMenuItemsSet.count(); ++i) {
			CBMenuItem item = mMenuItemsSet.get(i);
			if (item.isChecked())
				res.add(item);
		}

		return res;
	}

}
