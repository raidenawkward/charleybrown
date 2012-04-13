/**
 * @Title: CBOrderXmlWriter.java
 * @Package: com.android.cb.source
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-4-6
 */
package com.android.cb.source;

import java.util.LinkedList;

import com.android.cb.support.CBCustomer;
import com.android.cb.support.CBDish;
import com.android.cb.support.CBOrder;
import com.android.cb.support.CBTagsSet;
import com.android.cb.support.CBOrder.OrderedItem;

/**
 * @author raiden
 *
 * @Description order xml writer
 */
public class CBOrderXmlWriter {

	public static final String TAG_ORDER = "Order";
	public static final String TAG_LOCATION = "Location";
	public static final String TAG_CUSTOMER = "Customer";
	public static final String TAG_ID = "Id";
	public static final String TAG_DISABLED_TAGS = "DisabledTags";
	public static final String TAG_DISABLED_TAG = "DisabledTag";
	public static final String TAG_DISCOUNT = "Discount";
	public static final String TAG_CREATED_TIME = "CreatedTime";
	public static final String TAG_SUBMIT_TIME = "SubmitTime";
	public static final String TAG_SUMMATION = "Summation";
	public static final String TAG_MEMO = "Memo";
	public static final String TAG_STATUS = "Status";
	public static final String TAG_DISHES = "Dishes";
	public static final String TAG_DISH = "Dish";
	public static final String TAG_ATTR_COUNT = "count";
	public static final String TAG_ATTR_ID = "id";

	public static boolean writeOrderRecord(CBOrder order, String path) {
		if (order == null || path == null)
			return false;

		CBXmlWriter writter = new CBXmlWriter(path, CBSettings.getStringValue(CBSettings.CB_SETTINGS_XML_ENCODING));
		if (writter.open() == false)
			return false;

		if (writter.writeXmlHeader() == false)
			return false;

		writter.writeStartTag(TAG_ORDER, 0);
		writter.writeOneLineTags(TAG_ID, null, order.getId().toString(), 1);
		writter.writeOneLineTags(TAG_LOCATION, null, order.getLocation(), 1);
		CBCustomer customer = order.getCustomer();
		if (customer != null)
			writter.writeOneLineTags(TAG_CUSTOMER, null, order.getCustomer().toString(), 1);

		writter.writeStartTag(TAG_DISABLED_TAGS, 1);
		CBTagsSet disabledTagsSet = order.getDisabledTags();
		if (disabledTagsSet != null) {
			for (int i = 0; i < disabledTagsSet.count(); ++i) {
				String tag = disabledTagsSet.get(i);
				writter.writeOneLineTags(TAG_DISABLED_TAG, null, tag, 2);
			}
		}
		writter.writeEndTag(TAG_DISABLED_TAGS, 1);

		writter.writeOneLineTags(TAG_DISCOUNT, null, String.valueOf(order.getDiscount()), 1);
		writter.writeOneLineTags(TAG_CREATED_TIME, null, String.valueOf(order.getCreateTime().getTime()), 1);
		writter.writeOneLineTags(TAG_SUBMIT_TIME, null, String.valueOf(order.getSubmitedTime().getTime()), 1);
		writter.writeOneLineTags(TAG_SUMMATION, null, String.valueOf(order.getSummation()), 1);
		writter.writeOneLineTags(TAG_MEMO, null, order.getMemo(), 1);
		writter.writeOneLineTags(TAG_STATUS, null, String.valueOf(order.getStatus()), 1);

		writter.writeStartTag(TAG_DISHES, 1);
		LinkedList<OrderedItem> dishes = order.getOrderedItemsWithCount();
		for (int i = 0; i < dishes.size(); ++i) {
			OrderedItem item = dishes.get(i);
			CBDish dish = item.item.getDish();
			String attr = "";
			attr += TAG_ATTR_ID + "=\"" + dish.getId().toString() + "\"";
			attr += " " + TAG_ATTR_COUNT + "=\"" + String.valueOf(item.count) + "\"";
			writter.writeOneLineTags(TAG_DISH, attr, String.valueOf(dish.getName()), 2);
		}
		writter.writeEndTag(TAG_DISHES, 1);

		writter.writeEndTag(TAG_ORDER, 0);

		writter.close();
		return true;
	}

}
