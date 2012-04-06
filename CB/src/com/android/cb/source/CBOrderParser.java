/**
 * @Title: CBOrderParser.java
 * @Package: com.android.cb.source
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-3-23
 */
package com.android.cb.source;

import java.util.Date;

import com.android.cb.support.CBCustomer;
import com.android.cb.support.CBId;
import com.android.cb.support.CBOrder;

/**
 * @author raiden
 *
 * @Description get order object according to '.xml' file
 */
public class CBOrderParser extends CBXmlParser implements CBXmlParser.Callback {

	private CBOrder mOrder = null;

	public CBOrderParser() {
		super();
		setCallback(this);
	}

	public CBOrderParser(String filePath) {
		super(filePath);
		setCallback(this);
	}

	@Override
	public boolean parse() {
		if (super.parse() == false)
			return false;

		if (mOrder != null)
			mOrder.setRecordSavedPath(this.getFile());
		return true;
	}

	/**
	 * @Description get order object as result of parse
	 * @return CBOrder but returns null if parse failed
	 */
	public CBOrder getOrder() {
		return mOrder;
	}

	public void onTagWithValueDetected(String tag, String value) {
		if (mOrder == null)
			return;

		if (tag.equalsIgnoreCase(CBOrderXmlWriter.TAG_ID)) {
			CBId id = new CBId(value);
			mOrder.setId(id);

		} else if (tag.equalsIgnoreCase(CBOrderXmlWriter.TAG_LOCATION)) {
			mOrder.setLocation(value);

		} else if (tag.equalsIgnoreCase(CBOrderXmlWriter.TAG_CUSTOMER)) {
			CBCustomer customer = new CBCustomer(value);
			mOrder.setCustomer(customer);

		} else if (tag.equalsIgnoreCase(CBOrderXmlWriter.TAG_DISABLED_TAG)) {
			mOrder.addDisabledTag(value);

		} else if (tag.equalsIgnoreCase(CBOrderXmlWriter.TAG_DISCOUNT)) {
			mOrder.setDiscount(Float.valueOf(value));

		} else if (tag.equalsIgnoreCase(CBOrderXmlWriter.TAG_CREATED_TIME)) {
			Date date = new Date(value);
			mOrder.setCreateTime(date);

		} else if (tag.equalsIgnoreCase(CBOrderXmlWriter.TAG_SUBMIT_TIME)) {
			Date date = new Date(value);
			mOrder.setCreateTime(date);

		} else if (tag.equalsIgnoreCase(CBOrderXmlWriter.TAG_SUMMATION)) {
			mOrder.setSummation(Float.valueOf(value));

		} else if (tag.equalsIgnoreCase(CBOrderXmlWriter.TAG_MEMO)) {
			mOrder.setMemo(value);

		} else if (tag.equalsIgnoreCase(CBOrderXmlWriter.TAG_STATUS)) {
			mOrder.setStatus(Integer.valueOf(value));

		}
	}

	public void onStartDocument() {
		mOrder = new CBOrder();
	}

	public void onEndDocument() {

	}

}
