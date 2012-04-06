/**
 * @Title: CBOrder.java
 * @Package: com.android.cb.support
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-3-16
 */
package com.android.cb.support;

import java.util.Date;
import java.util.LinkedList;

/**
 * @author raiden
 *
 * @Description basic order structure
 */
public class CBOrder {

	private CBId mId = new CBId();
	private String mLocation = "";
	private CBCustomer mCustomer = null;
	private CBTagsSet mDisabledTags;
	private float mDiscount = 1.0f;
	private Date mCreatedTime = new Date();
	private Date mSubmitedTime = new Date();
	private float mSummation = 0.0f;
	private String mMemo = "";
	private String mRecordSavedPath;

	private int mStatus;
	public final int  CBORDER_STATUS_UNKNOWN = 0;
	public final int  CBORDER_STATUS_ADDING = 1;
	public final int  CBORDER_STATUS_EDITING = 2;
	public final int  CBORDER_STATUS_CONFIRMED = 3;

	private class OrderedItem {
		public CBMenuItem item = null;
		public int count = 0;
	}
	private LinkedList<OrderedItem> mOrderedItemsList = new LinkedList<OrderedItem>();

	public CBOrder() {
	}

	public CBOrder(CBOrder order) {
		if (order == null)
			return;

		mId = new CBId(order.getId());
		mLocation = order.getLocation();
		mCustomer = order.getCustomer();
		mDisabledTags = order.getDisabledTags();
		mDiscount = order.getDiscount();
		mCreatedTime = order.getCreateTime();
		mSubmitedTime = order.getSubmitedTime();
		mSummation = order.getSummation();
		mMemo = order.getMemo();
		mRecordSavedPath = order.getRecordSavedPath();
		mStatus = order.getStatus();
	}

	public boolean equals(CBOrder order) {
		return (mId.equals(order.getId()));
	}

	public boolean addItem(CBMenuItem item) {
		return addItem(item,1);
	}

	/**
	 * @Description add item to ordered set, if item exists, update it
	 * @param item
	 * @param count
	 * @return boolean
	 */
	public boolean addItem(CBMenuItem item, int count) {
		if (count <= 0)
			return false;

		OrderedItem orderedItem = new OrderedItem();
		orderedItem.item = item;
		orderedItem.count = count;

		int index = getOrderedItemIndex(item);

		if (index < 0) {
			return mOrderedItemsList.add(orderedItem);
		} else {
			mOrderedItemsList.set(index, orderedItem);
			return true;
		}
	}

	public boolean isItemHasBeenChecked(CBMenuItem item) {
		return (getOrderedItemFromOrderedList(item) != null);
	}

	private OrderedItem getOrderedItemFromOrderedList(CBMenuItem item) {
		if (item == null)
			return null;

		for (int i = 0; i < mOrderedItemsList.size(); ++i) {
			OrderedItem ordered = mOrderedItemsList.get(i);
			if (ordered.item.equals(item))
				return ordered;
		}

		return null;
	}

	public int getTotalItemCheckedCount() {
		if (mOrderedItemsList == null)
			return 0;

		return mOrderedItemsList.size();
	}

	public int getItemCheckedCount(CBMenuItem item) {
		OrderedItem orderedItem = getOrderedItemFromOrderedList(item);
		if (orderedItem == null)
			return 0;

		return orderedItem.count;
	}

	public boolean isItemDisabled(CBMenuItem item) {
		OrderedItem it = getOrderedItemFromOrderedList(item);
		if (it == null)
			return false;

		return (mDisabledTags.getIntersection(it.item.getDish().getTagsSet()) != 0);
	}

	public CBTagsSet getConflictedTagsSet(CBMenuItem item) {
		CBTagsSet res = new CBTagsSet();
		if (item == null)
			return res;

		CBTagsSet dishTags = item.getDish().getTagsSet();
		for (int i = 0; i < dishTags.count(); ++i) {
			String tag = dishTags.get(i);
			if (mDisabledTags.contains(tag))
				res.add(tag);
		}

		return res;
	}

	public int getOrderedItemIndex(CBMenuItem item) {
		if (item == null)
			return -1;

		for (int i = 0; i < mOrderedItemsList.size(); ++i) {
			OrderedItem it = mOrderedItemsList.get(i);
			if (it.item.equals(item))
				return i;
		}

		return -1;
	}

	public boolean removeItem(CBMenuItem item) {
		int index = getOrderedItemIndex(item);
		return removeItem(index);
	}

	public boolean removeItem(int index) {
		if (index < 0 || index >= mOrderedItemsList.size())
			return false;

		mOrderedItemsList.remove(index);
		return true;
	}

	public float getRealSummation() {
		float sum = 0.0f;

		for (int i = 0; i < mOrderedItemsList.size(); ++i) {
			OrderedItem oItem = mOrderedItemsList.get(i);
			if (oItem.item == null)
				continue;
			sum += oItem.item.getDish().getPrice() * (float)oItem.count;
		}

		return sum;
	}

	public void touch() {
		mSubmitedTime = new Date();
	}

	public CBId getId() {
		return mId;
	}

	public void setId(CBId id) {
		this.mId = id;
	}

	public String getLocation() {
		return mLocation;
	}

	public void setLocation(String location) {
		this.mLocation = location;
	}

	public CBMenuItemsSet getOrderedItems() {
		CBMenuItemsSet res = new CBMenuItemsSet();
		for (int i = 0; i < mOrderedItemsList.size(); ++i) {
			OrderedItem oitem = mOrderedItemsList.get(i);
			res.add(oitem.item);
		}

		return res;
	}

	public CBCustomer getCustomer() {
		return mCustomer;
	}

	public void setCustomer(CBCustomer customer) {
		this.mCustomer = customer;
	}

	public void addDisabledTag(String tag) {
		if (mDisabledTags != null || tag != null)
			mDisabledTags.add(tag);
	}

	public CBTagsSet getDisabledTags() {
		return mDisabledTags;
	}

	public void setDisabledTags(CBTagsSet disabledTags) {
		this.mDisabledTags = disabledTags;
	}

	public float getDiscount() {
		return mDiscount;
	}

	public void setDiscount(float disCount) {
		this.mDiscount = disCount;
	}

	public Date getCreateTime() {
		return mCreatedTime;
	}

	public void setCreateTime(Date createTime) {
		this.mCreatedTime = createTime;
	}

	public Date getSubmitedTime() {
		return mSubmitedTime;
	}

	public void setSubmitedTime(Date submitTime) {
		this.mSubmitedTime = submitTime;
	}

	public float getSummation() {
		return mSummation;
	}

	public void setSummation(float summation) {
		this.mSummation = summation;
	}

	public String getMemo() {
		return mMemo;
	}

	public void setMemo(String memo) {
		this.mMemo = memo;
	}

	public String getRecordSavedPath() {
		return mRecordSavedPath;
	}

	public void setRecordSavedPath(String recordSavedPath) {
		this.mRecordSavedPath = recordSavedPath;
	}

	public int getStatus() {
		return mStatus;
	}

	public void setStatus(int status) {
		this.mStatus = status;
	}

}
