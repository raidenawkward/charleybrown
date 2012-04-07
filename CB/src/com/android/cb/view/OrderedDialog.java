/**
 * @Title: OrderedDialog.java
 * @Package: com.android.cb.view
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-4-7
 */
package com.android.cb.view;

import android.content.Context;

/**
 * @author raiden
 *
 * @Description dialog in which ordered result shows
 */
public class OrderedDialog extends CBBaseDialog {

	public OrderedDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	public OrderedDialog(Context context, int theme) {
		super(context, theme);
	}

	public OrderedDialog(Context context) {
		super(context);
	}

}
