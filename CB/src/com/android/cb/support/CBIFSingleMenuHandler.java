/**
 * @Title: CBIFSingleMenuHandler.java
 * @Package: com.android.cb.support
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-3-18
 */
package com.android.cb.support;

/**
 * @author raiden
 *
 * @Description ctrls for single menu UI
 */
public interface CBIFSingleMenuHandler extends CBIFCommonMenuHandler {
	public boolean gotoItem(int index);
	public boolean gotoItem(CBMenuItem item);
	public boolean gotoNextItem();
	public boolean gotoPrevItem();
	public void currentItemTouched();
}
