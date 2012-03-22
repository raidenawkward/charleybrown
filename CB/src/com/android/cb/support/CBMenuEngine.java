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
 * @Description logical menu information & control center
 */
public class CBMenuEngine {
	private CBMenuItemsSet mMenuItemsSet = new CBMenuItemsSet();
	private int mCurrentIndex = 0;
	private int mSelectedCount = 0;

	public CBMenuEngine() {

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

	public boolean gotoNext() {
		if (mCurrentIndex == mMenuItemsSet.count() - 1) {
			return false;
		}

		++mCurrentIndex;
		return true;
	}

	public CBMenuItem next() {
		return mMenuItemsSet.get(mCurrentIndex + 1);
	}

	public boolean gotoPrev() {
		if (mCurrentIndex == 0) {
			return false;
		}

		--mCurrentIndex;
		return true;
	}

	public CBMenuItem prev() {
		return mMenuItemsSet.get(mCurrentIndex - 1);
	}

	public void gotoFirst() {
		mCurrentIndex = 0;
	}

	public void gotoLast() {
		int index = mMenuItemsSet.count() - 1;
		mCurrentIndex = index >= 0? index : 0;
	}

	public CBMenuItem first() {
		return mMenuItemsSet.get(0);
	}

	public CBMenuItem last() {
		return mMenuItemsSet.get(mMenuItemsSet.count() - 1);
	}

	public CBMenuItem current() {
		return mMenuItemsSet.get(mCurrentIndex);
	}

	public CBMenuItem get(int index) {
		return mMenuItemsSet.get(index);
	}

	public boolean goTo(int index) {
		if (mMenuItemsSet.get(index) != null) {
			mCurrentIndex = index;
			return true;
		}

		return false;
	}

	public int count() {
		return mMenuItemsSet.count();
	}

	public int currentIndex() {
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
