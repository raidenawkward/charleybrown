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

	public static final int CHECK_DEVICE = 0;
	public static final int CHECK_DEVICE_ID = 1;
	public static final int CHECK_DEVICE_MAC = 2;
	public static final int CHECK_DEVICE_SYSTEM_ID = 3;
	public static final int CHECK_DEVICE_SUBSCRIBE_ID = 4;
	public static final int CHECK_DEVICE_LAST_INFO = 5;

	protected static final String[] DEVICE_INFO_G1 = {
		"g1",
		"352778030577675",
		"00:18:41:fd:fd:59",
		"200140d98932780b",
		"460002031727147",
	};

	protected static final String[] DEVICE_INFO_F7 = {
		"f7",
		"123456789012346",
		"20:59:A0:40:7C:05",
		"61e3420362520eb9",
		"012345678912345",
	};

	protected static final String[] DEVICE_INFO_ACER_ZZY = {
		"acer",
		"",
		"14:DA:E9:23:E1:68",
		"70c516644ad8fe61",
		"",
	};

	protected static String[][] ALLOWED_DEVICES = {
		DEVICE_INFO_G1,
		DEVICE_INFO_F7,
		DEVICE_INFO_ACER_ZZY,
	};

	/**
	 * @Description validate device system info; device id, android id and subscribe id
	 * will be checked
	 * @param context from which can get system information
	 * @return boolean
	 */
	public static boolean isValid(Context context) {
		String systemId = CBSystemInfo.getAndroidSystemId(context);
		String[] info = getDeviceIndexBySystemId(systemId);

		if (info == null)
			return false;

		String deviceId = CBSystemInfo.getDeviceId(context);
		if (deviceId == null)
			deviceId = "";
		String mac = CBSystemInfo.getMac(context);
		if (mac == null)
			mac = "";
		String subscriberId = CBSystemInfo.getSubscriberId(context);
		if (subscriberId == null)
			subscriberId = "";

		for (int i = CHECK_DEVICE_ID; i < CHECK_DEVICE_LAST_INFO; ++i) {
			switch (i) {
			case CHECK_DEVICE_ID:
				if (deviceId.equals(info[i]) == false)
					return false;
				break;
			case CHECK_DEVICE_MAC:
				if (mac.equals(info[i]) == false);
				break;
			case CHECK_DEVICE_SYSTEM_ID:
				break;
			case CHECK_DEVICE_SUBSCRIBE_ID:
				if (subscriberId.equals(info[i]) == false)
					return false;
				break;
			default:
					break;
			}
		}

		return true;
	}

	protected static String[] getDeviceIndexBySystemId(String systemId) {
		if (systemId == null)
			return null;

		for (int i = 0; i < ALLOWED_DEVICES.length; ++i) {
			String[] deviceInfo = ALLOWED_DEVICES[i];
			if (deviceInfo == null)
				continue;

			if (deviceInfo.length < CHECK_DEVICE_LAST_INFO)
				continue;

			if (deviceInfo[CHECK_DEVICE_SYSTEM_ID].equals(systemId))
				return deviceInfo;
		}

		return null;
	}

}
