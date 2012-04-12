/**
 * @Title: CBOrderFactory.java
 * @Package: com.android.cb.source
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-4-6
 */
package com.android.cb.source;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.android.cb.support.CBId;
import com.android.cb.support.CBMenuEngine;
import com.android.cb.support.CBMenuItemsSet;
import com.android.cb.support.CBOrder;
import com.android.cb.support.CBOrdersSet;

/**
 * @author raiden
 *
 * @Description methods for order object generating
 */
public class CBOrderFactory {

	public static final String CB_ORDER_PATH_SPLITOR = "/";
	public static final String CB_ORDER_RECORD_EXT = ".xml";
	public static final String CB_ORDER_RECORD_FILE_FORMAT = "hhmmss";
	public static final String CB_ORDER_RECORD_DIR_FORMAT = "yyyyMMdd";

	public static Date sCurrentTime = new Date();

	public static CBOrder newOrder() {
		refreshCurrentTime();

		CBOrder order = new CBOrder();
		order.setId(generateOrderId());
		order.setCreateTime(sCurrentTime);
		order.setSubmitedTime(sCurrentTime);
		order.setRecordSavedPath(getOrderRecordPath(sCurrentTime));
		order.setStatus(CBOrder.CBORDER_STATUS_ADDING);

		return order;
	}

	public static boolean saveOrder(CBOrder order) {
		if (order == null)
			return false;

		refreshCurrentTime();
		order.setSubmitedTime(sCurrentTime);
		order.getRealSummation();
		order.setStatus(CBOrder.CBORDER_STATUS_CONFIRMED);

		return CBOrderXmlWriter.writeOrderRecord(order, order.getRecordSavedPath());
	}

	public static CBOrder loadOrder(String path, CBMenuEngine menuEngine) {
		if (path == null)
			return null;

		CBOrderParser parser = new CBOrderParser(path);
		parser.setMenuEngine(menuEngine);
		if (parser.parse() == false)
			return null;

		return parser.getOrder();
	}

	public static CBOrdersSet loadOrdersByDate(Date date, CBMenuItemsSet set) {
		CBOrdersSet res = new CBOrdersSet();

//		String homeDir = generateOrderDir(date);

		return res;
	}

	public static void refreshCurrentTime() {
		sCurrentTime = new Date();
	}

	public static String getOrderRecordPath(Date date) {
		String res = generateOrderDir(date);
		if (res == null)
			return null;

		res += CB_ORDER_PATH_SPLITOR;
		res += generateOrderFileName(date);

		return res;
	}

	protected static CBId generateOrderId() {
		SimpleDateFormat format = new SimpleDateFormat(CB_ORDER_RECORD_DIR_FORMAT + CB_ORDER_RECORD_FILE_FORMAT);
		String id = format.format(sCurrentTime);
		return new CBId(id);
	}

	public static String generateOrderFileName(Date date) {
		SimpleDateFormat format = new SimpleDateFormat(CB_ORDER_RECORD_FILE_FORMAT);
		String res = format.format(date);

		res += CB_ORDER_RECORD_EXT;

		return res;
	}

	public static String generateOrderDir(Date date) {
		String res = CBSettings.getStringValue(CBSettings.CB_SETTINGS_SOURCE_DIR_ORDERS) + CB_ORDER_PATH_SPLITOR;
		SimpleDateFormat format = new SimpleDateFormat(CB_ORDER_RECORD_DIR_FORMAT);
		res += format.format(date);

		File dir = new File(res);
		dir.mkdirs();

		return res;
	}

	public static boolean exportOrderToZip(String zipPath) {
		refreshCurrentTime();
		return CBZipFactory.zipDir(generateOrderDir(sCurrentTime), zipPath);
	}

}
