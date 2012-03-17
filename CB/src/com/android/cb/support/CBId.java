/**
 * @Title: CBId.java
 * @Package: com.android.cb.support
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-3-17
 */
package com.android.cb.support;

/**
 * @author raiden
 *
 * @Description general id defination
 */
public class CBId {
	String mId = new String();

	public CBId() {
	}

	public CBId(String id) {
		mId = id;
	}

	public CBId(CBId id) {
		mId = id.getId();
	}

	public String getId() {
		return mId;
	}

	public void setId(String id) {
		this.mId = id;
	}

	public void setId(CBId id) {
		this.mId = id.getId();
	}

	public int compare(CBId id) {
		return mId.compareTo(id.getId());
	}

	public boolean equals(CBId id) {
		return mId.equals(id.getId());
	}

	public String toString() {
		return mId;
	}
}
