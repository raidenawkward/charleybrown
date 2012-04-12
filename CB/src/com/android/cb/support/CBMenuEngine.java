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
 * @Description logical menu information & order control center
 */
public class CBMenuEngine {
	private CBMenuItemsSet mMenuItemsSet = new CBMenuItemsSet();
	private CBOrder mCurrentOrder = null;
	private int mCurrentIndex = 0;

	public CBMenuEngine() {

	}

	/** methods for positions **/

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

	/** methods for menu items **/


	public CBMenuItem getItem(int index) {
		return mMenuItemsSet.get(index);
	}

	public CBMenuItem getItemById(CBId id) {
		return mMenuItemsSet.getItemById(id);
	}

	public int getMenuItemcount() {
		return mMenuItemsSet.count();
	}

	public CBMenuItemsSet getMenuSet() {
		return mMenuItemsSet;
	}

	public void setMenuSet(CBMenuItemsSet menuSet) {
		this.mMenuItemsSet = new CBMenuItemsSet(menuSet);
	}

	public CBMenuItemsSet getMenuItemsSetWithTags(CBTagsSet tagSet) {
		if (tagSet == null)
			return null;

		CBMenuItemsSet res = new CBMenuItemsSet();

		for (int i = 0; i < mMenuItemsSet.count(); ++i) {
			CBMenuItem item = mMenuItemsSet.get(i);
			if (item.isTagsSetContained(tagSet)) {
				res.add(item);
			}
		}

		return res;
	}

	public CBMenuItemsSet getMenuItemsSetWithTag(String tag) {
		if (tag == null)
			return null;

		CBMenuItemsSet res = new CBMenuItemsSet();

		for (int i = 0; i < mMenuItemsSet.count(); ++i) {
			CBMenuItem item = mMenuItemsSet.get(i);
			if (item.isTagContained(tag)) {
				res.add(item);
			}
		}

		return res;
	}

	/**
	 * @Description get tags contained by all dishes in engine source,
	 * each tag only add once
	 * @return CBTagsSet
	 */
	public CBTagsSet getContainedTags() {
		CBTagsSet set = new CBTagsSet();

		for (int i = 0; i < mMenuItemsSet.count(); ++i) {
			CBMenuItem item = mMenuItemsSet.get(i);
			set.combine(item.getDish().getTagsSet());
		}

		return set;
	}


	/** methods for orders **/


	public void setOrder(CBOrder order) {
		if (order == null)
			mCurrentOrder = new CBOrder();
		else
			mCurrentOrder = order;
	}

	public CBOrder getOrder() {
		return mCurrentOrder;
	}

	public int getTotalItemCheckedCount() {
		return mCurrentOrder.getTotalItemCheckedCount();
	}

	public int getCurrentItemCheckedCount() {
		return getIndexedItemCheckedCount(mCurrentIndex);
	}

	public int getIndexedItemCheckedCount(int index) {
		if (mCurrentOrder == null)
			return 0;

		CBMenuItem indexedItem = this.getItem(index);

		return mCurrentOrder.getItemCheckedCount(indexedItem);
	}

	public boolean isCurrentItemChecked() {
		return (getCurrentItemCheckedCount() != 0);
	}

	public boolean isIndexedItemChecked(int index) {
		return (getIndexedItemCheckedCount(index) != 0);
	}

	public int getMenuItemIndex(CBMenuItem item) {
		return this.mMenuItemsSet.getIndexOf(item);
	}

	/**
	 * @Description order / disorder item
	 * @param index
	 * @param count if item exists, item will be updated, or if
	 * count <= 0, item will be removed
	 * @return boolean
	 */
	public boolean orderIndexedItem(int index, int count) {
		if (mCurrentOrder == null)
			return false;

		CBMenuItem indexedItem = this.getItem(index);
		if (indexedItem == null)
			return false;

		if (count > 0)
			return mCurrentOrder.addItem(indexedItem, count);
		else
			return mCurrentOrder.removeItem(indexedItem);
	}

	public boolean orderCurrentItem(int count) {
		return orderIndexedItem(mCurrentIndex, count);
	}

	public boolean disOrderIndexedItem(int index) {
		if (mCurrentOrder == null)
			return false;

		CBMenuItem indexedItem = this.getItem(index);
		if (indexedItem == null)
			return false;

		return mCurrentOrder.removeItem(indexedItem);
	}

	public boolean disOrderCurrentItem() {
		return disOrderIndexedItem(mCurrentIndex);
	}

	public CBTagsSet getConflictedTagSetOfIndexedItem(int index) {
		if (mCurrentOrder == null)
			return null;

		CBMenuItem indexedItem = this.getItem(index);
		return mCurrentOrder.getConflictedTagsSet(indexedItem);
	}

	public CBTagsSet getConflictedTagSetOfCurrentItem() {
		if (mCurrentOrder == null)
			return null;

		return mCurrentOrder.getConflictedTagsSet(getCurrentItem());
	}
}
