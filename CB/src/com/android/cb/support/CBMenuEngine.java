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
	private CBOrder mCurrentOrder = null;
	private int mCurrentIndex = 0;

	public CBMenuEngine() {

	}

	/** methods for menu items and positions */

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

	public boolean gotoNext() {
		if (mCurrentIndex == mMenuItemsSet.count() - 1) {
			return false;
		}

		++mCurrentIndex;
		return true;
	}

	public boolean gotoPrev() {
		if (mCurrentIndex == 0) {
			return false;
		}

		--mCurrentIndex;
		return true;
	}

	public void gotoFirst() {
		mCurrentIndex = 0;
	}

	public void gotoLast() {
		int index = mMenuItemsSet.count() - 1;
		mCurrentIndex = index >= 0? index : 0;
	}

	public boolean gotoPos(int index) {
		if (mMenuItemsSet.get(index) != null) {
			mCurrentIndex = index;
			return true;
		}

		return false;
	}

	public CBMenuItem getNextItem() {
		return mMenuItemsSet.get(mCurrentIndex + 1);
	}

	public CBMenuItem getPrevItem() {
		return mMenuItemsSet.get(mCurrentIndex - 1);
	}

	public CBMenuItem getFirstItem() {
		return mMenuItemsSet.get(0);
	}

	public CBMenuItem getLastItem() {
		return mMenuItemsSet.get(mMenuItemsSet.count() - 1);
	}

	public CBMenuItem getCurrentItem() {
		return mMenuItemsSet.get(mCurrentIndex);
	}

	public CBMenuItem getItem(int index) {
		return mMenuItemsSet.get(index);
	}

	public int getMenuItemcount() {
		return mMenuItemsSet.count();
	}

	/** methods for orders */

	public int getCurrentItemCheckedCount() {
		if (mCurrentOrder == null)
			return 0;

		return mCurrentOrder.getItemCheckedCount(getCurrentItem());
	}
}
