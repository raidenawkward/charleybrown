/**
 * @Title: CBIFOrderHandler.java
 * @Package: com.android.cb.support
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-3-18
 */
package com.android.cb.support;

/**
 * @author raiden
 *
 * @Description order ctrls
 */
public interface CBIFOrderHandler {
	public boolean loadRecord(String recordPath);
	public boolean saveRecord();
	public boolean addItemToRecord(CBMenuItem item);
	public boolean removeFromRecord(CBMenuItem item);
	public boolean removeFromRecord(int index);
}
