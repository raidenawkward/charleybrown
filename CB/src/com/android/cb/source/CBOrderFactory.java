/**
 * @Title: CBOrderFactory.java
 * @Package: com.android.cb.source
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-4-6
 */
package com.android.cb.source;

import com.android.cb.support.CBId;
import com.android.cb.support.CBOrder;

/**
 * @author raiden
 *
 * @Description methods for order object generating
 */
public class CBOrderFactory {

	public static final String CB_ORDER_RECORD_DIR = "/sdcard/orders";

	public static CBOrder newOrder() {
		CBOrder order = new CBOrder();
		return order;
	}

	public static String getOrderRecordPath() {
		return CB_ORDER_RECORD_DIR;
	}

	protected static CBId generateOrderId() {
		return new CBId("");
	}

}
