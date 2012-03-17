/**
 * @Title: CBOrder.java
 * @Package: com.android.cb.support
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-3-16
 */
package com.android.cb.support;

import java.util.Date;

/**
 * @author raiden
 *
 * @Description basic order struct
 */
public class CBOrder {

	private String mId = "";
	private String mLocation = "";
	private CBMenuItemsSet mMenuItemList;
	private CBCustomer mCustomer;
	private CBTagsSet mDisabledTags;
	private float mDiscount = 1.0f;
	private Date mCreatedTime = new Date();
	private Date mSubmitedTime = new Date();
	private float mSummation = 0.0f;
	private String mMemo;
	private String mRecordSavedPath;

	private int mStatus;
	public final int  CBORDER_STATUS_UNKNOWN = 0;
	public final int  CBORDER_STATUS_ADDING = 1;
	public final int  CBORDER_STATUS_EDITING = 2;
	public final int  CBORDER_STATUS_CONFIRMED = 3;

	public CBOrder() {
	}

	public CBOrder(CBOrder order) {
		if (order == null)
			return;

		mId = order.getId();
		mLocation = order.getLocation();
		mMenuItemList = new CBMenuItemsSet(order.getMenuItemList());
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

	public boolean addItem(CBMenuItem item) {
		return mMenuItemList.add(item);
	}

	public boolean isItemChecked(CBMenuItem item) {
		return (getItemCheckedCount(item) != 0);
	}

	public int getItemCheckedCount(CBMenuItem item) {
		CBMenuItem it = mMenuItemList.getItem(item);
		if (it == null)
			return 0;

		return it.getCheckedCount();
	}

	public boolean isItemDisabled(CBMenuItem item) {
		CBMenuItem it = mMenuItemList.getItem(item);
		if (it == null)
			return false;

		return (mDisabledTags.getIntersection(item.getDish().getTagsSet()) != 0);
	}

	public int getMenuItemIndex(CBMenuItem item) {
		return mMenuItemList.getIndexOf(item);
	}

	public boolean removeMenuItem(CBMenuItem item) {
		return mMenuItemList.remove(item);
	}

	public boolean removeMenuItem(int index) {
		return mMenuItemList.remove(index);
	}

	public float getRealSummation() {
		float sum = 0.0f;

		for (int i = 0; i < mMenuItemList.count(); ++i) {
			CBMenuItem item = mMenuItemList.getMenuItemsList().get(i);
			sum += item.getDish().getPrice() * item.getCheckedCount();
		}

		return sum;
	}

	public void touch() {
		mSubmitedTime = new Date();
	}

	public String getId() {
		return mId;
	}

	public void setId(String id) {
		this.mId = id;
	}

	public String getLocation() {
		return mLocation;
	}

	public void setLocation(String location) {
		this.mLocation = location;
	}

	public CBMenuItemsSet getMenuItemList() {
		return mMenuItemList;
	}

	public void setMenuItemList(CBMenuItemsSet menuItemList) {
		this.mMenuItemList = menuItemList;
	}

	public CBCustomer getCustomer() {
		return mCustomer;
	}

	public void setCustomer(CBCustomer customer) {
		this.mCustomer = customer;
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
