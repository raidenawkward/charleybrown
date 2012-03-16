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
	private String mId = "";
	private String mName = "";
	private int mNumOfPeople = 0;

	CBCustomer() {
	}

	public String getId() {
		return mId;
	}

	public void setId(String id) {
		this.mId = id;
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

}
