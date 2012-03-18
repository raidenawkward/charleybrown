/**
 * @Title: CBIFGroupMenuHandler.java
 * @Package: com.android.cb.support
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-3-18
 */
package com.android.cb.support;

/**
 * @author raiden
 *
 * @Description ctrls for group menu ui
 */
public interface CBIFGroupMenuHandler extends CBIFCommonMenuHandler {
	public boolean moveUp();
	public boolean moveDown();
	public boolean moveLeft();
	public boolean moveRight();
	public boolean slideUp();
	public boolean slideDown();
	public boolean slideLeft();
	public boolean slideRight();
	public void onItemClicked(int index);
	public void onItemLongPressed(int index);
}
