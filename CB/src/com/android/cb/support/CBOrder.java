/**
 * @Title: CBOrder.java
 * @Package: com.android.cb.support
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-3-16
 */
package com.android.cb.support;

import java.util.Date;
import java.util.List;

/**
 * @author raiden
 *
 * @Description basic order struct
 */
public class CBOrder {

	private String mId = "";
	private String mLocation = "";
	private List<CBMenuItem> mMenuItemList;
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
		mMenuItemList = order.getMenuItemList();
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
		if (item == null)
			return false;
//
//		if (mMenuItemList.contains(item))
//			return false;
//
//		return mMenuItemList.add(item);
		return true;
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

	public List<CBMenuItem> getMenuItemList() {
		return mMenuItemList;
	}

	public void setMenuItemList(List<CBMenuItem> menuItemList) {
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
