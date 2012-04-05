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
	public boolean loadOrderRecord(String recordPath);
	public boolean saveOrderRecord();
	/**
	 * @Description add or update item record in order
	 * @return boolean returns false if add or update failed
	 */
	public boolean addItemToOrder(CBMenuItem item);
	public boolean removeItemFromOrder(CBMenuItem item);
	public boolean removeItemFromOrder(int index);
}
