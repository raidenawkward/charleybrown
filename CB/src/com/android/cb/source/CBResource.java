/**
 * @Title: CBResource.java
 * @Package: com.android.cb.source
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-4-12
 */
package com.android.cb.source;

import android.content.Context;

import com.android.cb.support.CBMenuEngine;

/**
 * @author raiden
 *
 * @Description global resource keeper
 */
public class CBResource {

	/**
	 * golbal menu engine resource
	 */
	public static CBMenuEngine menuEngine = null;

	/**
	 * global context of CBActivity
	 */
	public static Context contextCBActivity = null;

	/**
	 * global context of CBGridViewActivity
	 */
	public static Context contextGridViewActivity = null;
}
