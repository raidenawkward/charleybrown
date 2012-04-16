/**
 * @Title: CBTrialCtrl.java
 * @Package: com.android.cb.source
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-4-16
 */
package com.android.cb.source;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

import com.android.cb.R;
import com.android.cb.support.CBDish;
import com.android.cb.support.CBMenuItem;
import com.android.cb.support.CBMenuItemsSet;
import com.android.cb.view.WarningDialog;

import android.content.Context;
import android.content.res.XmlResourceParser;

/**
 * @author raiden
 *
 * @Description controls for trial application
 */
public class CBTrialCtrl {
	private static final boolean IS_TRIAL_VERSION = false;

	public static boolean isTrialVersion() {
		return IS_TRIAL_VERSION;
	}

	public static void showTrialWarningDialog(Context context) {
		if (context == null)
			return;

		WarningDialog dialog = new WarningDialog(context);
		dialog.setTitle(R.string.warning_trial_title);
		dialog.setMessage(context.getResources().getString(R.string.warning_trial_message));

		dialog.show();
	}

	public static CBMenuItemsSet loadTrialDishes(Context context) {
		CBMenuItemsSet res = new CBMenuItemsSet();
		CBDish newDish = null;

		try {
			XmlResourceParser parser = context.getResources().getXml(R.xml.dishes);
			int eventType = parser.getEventType();
			while (eventType != XmlResourceParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlResourceParser.START_TAG:
					newDish = new CBDish();
					for (int i = 0; i < parser.getAttributeCount(); ++i) {
						if (CBDish.setDishAttr(newDish, parser.getAttributeName(i), parser.getAttributeValue(i))
									== true) {
							CBMenuItem item = new CBMenuItem(newDish);
							res.add(item);
						}
					}
					break;
				case XmlResourceParser.END_TAG:
					newDish = null;
					break;
				}

				eventType = parser.next();
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return res;
	}

}
