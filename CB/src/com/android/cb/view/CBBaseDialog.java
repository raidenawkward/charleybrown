/**
 * @Title: CBBaseDialog.java
 * @Package: com.android.cb.view
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-3-28
 */
package com.android.cb.view;

import com.android.cb.R;

import android.app.Dialog;
import android.content.Context;

/**
 * @author raiden
 *
 * @Description base dialog define
 */
public class CBBaseDialog extends Dialog {

	public CBBaseDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	public CBBaseDialog(Context context, int theme) {
		super(context, theme);
	}

	public CBBaseDialog(Context context) {
		super(context, R.style.CBBaseDialog);
	}

}
