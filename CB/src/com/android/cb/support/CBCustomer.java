/**
 * @Title: CBCustomer.java
 * @Package: com.android.cb.support
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-3-16
 */
package com.android.cb.support;

/**
 * @author raiden
 *
 * @Description customer info
 */
public class CBCustomer {
	public static final String VALUE_SPLITER = "__";
	private CBId mId = new CBId();
	private String mName = "";
	private int mNumOfPeople = 0;

	public CBCustomer() {

	}

	public CBCustomer(String string) {
		if (string == null)
			return;

		String[] list = string.split(VALUE_SPLITER);
		if (list.length < 3)
			return;

		mId = new CBId(list[0]);
		mName = new String(list[1]);
		mNumOfPeople = Integer.valueOf(list[2]);
	}

	public CBId getId() {
		return mId;
	}

	public void setId(CBId id) {
		this.mId.setId(id);
	}

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		this.mName = name;
	}

	public int getNumOfPeople() {
		return mNumOfPeople;
	}

	public void setNumOfPeople(int numOfPeople) {
		this.mNumOfPeople = numOfPeople;
	}

	public boolean equals(CBCustomer customer) {
		if (customer == null)
			return false;

		if (mId == null) {
			if (customer.getId() != null)
				return false;
		}

		if (mName != customer.getName())
			return false;

		if (mNumOfPeople != customer.getNumOfPeople())
			return false;

		return true;
	}

	public String toString() {
		return mId.toString() + VALUE_SPLITER + mName + VALUE_SPLITER + mNumOfPeople;
	}

}
