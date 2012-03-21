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
	private CBId mId = new CBId();
	private String mName = "";
	private int mNumOfPeople = 0;

	public CBCustomer() {
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
}
