/**
 * @Title: CBMenuItem.java
 * @Package: com.android.cb.support
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-3-16
 */
package com.android.cb.support;

/**
 * @author raiden
 *
 * @Description logic menu item info set
 */
public class CBMenuItem {
	private int mIndex = -1;
	private CBDish mDish = null;
	private int mCheckedCount = 0;

	CBMenuItem() {
	}

	/**
	 * @Description if checked count is bigger than 0, then return true
	 * @return boolean
	 */
	public boolean isChecked() {
		return (mCheckedCount != 0);
	}

	public int getIndex() {
		return mIndex;
	}

	public void setIndex(int index) {
		this.mIndex = index;
	}

	public CBDish getDish() {
		return mDish;
	}

	public void setDish(CBDish dish) {
		this.mDish = dish;
	}

	public int getCheckedCount() {
		return mCheckedCount;
	}

	public void setCheckedCount(int checkedCount) {
		this.mCheckedCount = checkedCount;
	}

}
