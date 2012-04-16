/**
 * @Title: CBTrialCtrl.java
 * @Package: com.android.cb.source
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-4-16
 */
package com.android.cb.source;

import com.android.cb.R;
import com.android.cb.view.WarningDialog;

import android.content.Context;

/**
 * @author raiden
 *
 * @Description controls for trial application
 */
public class CBTrialCtrl {
	public static final boolean IS_TRIAL_VERSION = false;

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

}
