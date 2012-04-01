/**
 * @Title: CBValidityChecker.java
 * @Package: com.android.cb.view
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-4-1
 */
package com.android.cb.view;

import com.android.cb.source.CBSystemInfo;

import android.content.Context;

/**
 * @author raiden
 *
 * @Description class for validity checking
 */
public class CBValidityChecker {

	protected static final String[] ALLOWED_DEVICE_IDS = {
		"352778030577675", /** g1 */
		"123456789012346", /** f7_1 */
	};

	protected static final String[] ALLOWED_MACS = {
		"00:18:41:fd:fd:59", /** g1 */
		"20:59:A0:40:7C:05", /** f7_1 */
	};

	protected static final String[] ALLOWED_ANDROID_SYSTEM_IDS = {
		"200140d98932780b", /** g1 */
		"61e3420362520eb9", /** f7_1 */
	};

	protected static final String[] ALLOWED_SUBSCRIBE_IDS = {
		"460002031727147", /** g1 */
		"012345678912345", /** f7_1 */
	};

	/**
	 * @Description validate device system info; device id, android id and subscribe id
	 * will be checked
	 * @param context from which can get system information
	 * @return boolean
	 */
	public static boolean isValid(Context context) {
		if (checkDeviceId(context) == false)
			return false;

//		if (checkMac(context) == false)
//			return false;

		if (checkAndroidSystemId(context) == false)
			return false;

		if (checkSubscribeId(context) == false)
			return false;

		return true;
	}

	protected static boolean checkDeviceId(Context context) {
		String id = CBSystemInfo.getDeviceId(context);
		return isValueInList(id, ALLOWED_DEVICE_IDS);
	}

	protected static boolean checkMac(Context context) {
		String mac = CBSystemInfo.getMac(context);
		return isValueInList(mac, ALLOWED_MACS);
	}

	protected static boolean checkAndroidSystemId(Context context) {
		String id = CBSystemInfo.getAndroidSystemId(context);
		return isValueInList(id, ALLOWED_ANDROID_SYSTEM_IDS);
	}

	protected static boolean checkSubscribeId(Context context) {
		String id = CBSystemInfo.getSubscriberId(context);
		return isValueInList(id, ALLOWED_SUBSCRIBE_IDS);
	}

	protected static boolean isValueInList(String value, String[] list) {
		if (value == null || list == null)
			return false;

		for (int i = 0; i < list.length; ++i) {
			if (value.equals(list[i]))
				return true;
		}

		return false;
	}

}
