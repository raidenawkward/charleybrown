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
	private CBDish mDish;

	public CBMenuItem() {
		mDish = new CBDish();
	}

	public CBMenuItem(CBDish dish) {
		if (dish != null)
			mDish = dish;
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

	public boolean isTagContained(String tag) {
		return mDish.isTagContained(tag);
	}

	public boolean isTagsSetContained(CBTagsSet set) {
		return mDish.isTagsSetContained(set);
	}

	public boolean equals(CBMenuItem item) {
		if (item == null)
			return false;

		if (mDish == null) {
			if (item.getDish() == null)
				return true;
		}

		return mDish.equals(item.getDish());
	}
}
