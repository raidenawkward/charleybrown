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
 * @Description ctrls for signle menu ui
 */
public interface CBIFSingleMenuHandler extends CBIFCommonMenuHandler {
	public boolean gotoNextItem();
	public boolean gotoPrevItem();
	public void currentItemTouched();
}
