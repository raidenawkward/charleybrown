/**
 * @Title: CBSettings.java
 * @Package: com.android.cb.source
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-4-9
 */
package com.android.cb.source;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author raiden
 *
 * @Description global application settings informations
 */
public class CBSettings {

	public static final String CB_SETTINGS_FILE_PATH = "";

	public static final String CB_SETTINGS_SOURCE_DIR = "cb.settings.source.dir";
	public static final String CB_SETTINGS_SOURCE_DIR_DISHES = "cb.settings.source.dir.dishes";
	public static final String CB_SETTINGS_SOURCE_DIR_SETTINGS = "cb.settings.source.dir.settings";
	public static final String CB_SETTINGS_DEVICE_ID = "cb.settings.device.id";
	public static final String CB_SETTINGS_LEFT_BUTTONS_TAGS_FILE = "cb.settings.left.buttons.tags.file";
	public static final String CB_SETTINGS_ORDER_LOCATIONS_FILE = "cb.settings.left.locations.file";


	/**
	 * map for string options
	 */
	protected static HashMap<String, String> SETTINGS_MAP = new HashMap<String, String>();

	static {
		SETTINGS_MAP.put(CB_SETTINGS_DEVICE_ID, "unknown");
		SETTINGS_MAP.put(CB_SETTINGS_SOURCE_DIR, "/sdcard/cb");
		SETTINGS_MAP.put(CB_SETTINGS_SOURCE_DIR_DISHES, "/sdcard/cb/dishes");
		SETTINGS_MAP.put(CB_SETTINGS_SOURCE_DIR_SETTINGS, "/sdcard/cb/settings");
		SETTINGS_MAP.put(CB_SETTINGS_LEFT_BUTTONS_TAGS_FILE, "left_buttons.xml");
		SETTINGS_MAP.put(CB_SETTINGS_ORDER_LOCATIONS_FILE, "order_locations.xml");
	}

	/**
	 * list for tags on left buttons
	 */
	protected static ArrayList<String> LEFT_BUTTONS_TAG_LIST = null;

	/**
	 * list for orders locations
	 */
	protected static ArrayList<String> ORDER_LOCATION_LIST = null;


	public static ArrayList<String> getLeftButtonsTagList(String xmlPath) {
		if (LEFT_BUTTONS_TAG_LIST == null) {
			LEFT_BUTTONS_TAG_LIST = new ArrayList<String>();
		}

		return LEFT_BUTTONS_TAG_LIST;
	}

	public static ArrayList<String> getOrderLocationList(String xmlPath) {
		if (LEFT_BUTTONS_TAG_LIST == null) {
			LEFT_BUTTONS_TAG_LIST = new ArrayList<String>();
		}

		return ORDER_LOCATION_LIST;
	}

	public static void setStringValue(final String name, String value) {
		if (name == null)
			return;

		SETTINGS_MAP.put(name, value);
	}

	public static String getStringValue(final String name) {
		String res = SETTINGS_MAP.get(name);

		return (res == null? new String() : res.toString());
	}

	public static void setIntValue(final String name, int value) {
		setStringValue(name, String.valueOf(value));
	}

	public static int getIntValue(final String name) {
		String str = getStringValue(name);

		return (str.length() > 0 ? Integer.valueOf(str) : 0);
	}

	public static void setFloatValue(final String name, float value) {
		setStringValue(name, String.valueOf(value));
	}

	public static float getFloatValue(final String name) {
		String str = getStringValue(name);

		return (str.length() > 0 ? Float.valueOf(str) : 0.0f);
	}

	public static void setBooleanValue(final String name, boolean value) {
		setStringValue(name, (value == true ? "1" : "0"));
	}

	public static boolean getBooleanValue(final String name) {
		String str = getStringValue(name);
		if (str.length() > 0) {
			if (str.equalsIgnoreCase("true")
					|| str.equalsIgnoreCase("1")
					|| str.equalsIgnoreCase("y")
					|| str.equalsIgnoreCase("yes"))
				return true;
		}

		return false;
	}

}
