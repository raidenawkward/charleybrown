/**
 * @Title: CBIFSetHandler.java
 * @Package: com.android.cb.support
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-3-17
 */
package com.android.cb.support;

/**
 * @author raiden
 *
 * @Description operations for any kinds of CBSet
 */
public interface CBIFSetHandler<TYPE> {
	boolean add(TYPE item);
	TYPE get(int index);
	/**
	 * @Description get the real item in set from passed item
	 * @param @param item fake item
	 * @return boolean
	 */
	TYPE get(TYPE item);
	boolean update(TYPE item);
	boolean update(int index, TYPE item);
	boolean remove(TYPE item);
	boolean remove(int index);
	int getIndexOf(TYPE item);
	int count();
	boolean contains(TYPE item);
}
