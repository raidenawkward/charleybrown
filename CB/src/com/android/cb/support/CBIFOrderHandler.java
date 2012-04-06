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
 * @Description order controls
 */
public interface CBIFOrderHandler {
	public boolean createOrder();
	public boolean loadOrderRecord(CBOrder order);
	public boolean loadOrderRecord(String recordPath);
	public boolean saveOrderRecord();
	/**
	 * @Description add or update item record in order
	 * @return boolean returns false if add or update failed
	 */
	public boolean addItemToOrder(CBMenuItem item, int count);
	public boolean removeItemFromOrder(CBMenuItem item);
	public boolean removeItemFromOrder(int index);
	/**
	 * @Description get the ordered count if item
	 * @return int returns 0 if item is null
	 */
	public int getItemOrederedCount(CBMenuItem item);
}
