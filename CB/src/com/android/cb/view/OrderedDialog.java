/**
 * @Title: OrderedDialog.java
 * @Package: com.android.cb.view
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-4-7
 */
package com.android.cb.view;

import com.android.cb.R;
import com.android.cb.support.CBOrder;

import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.Window;

/**
 * @author raiden
 *
 * @Description dialog in which ordered result shows
 */
public class OrderedDialog extends CBBaseDialog {

	private final float DIALOG_SIZE_SCALE_RATE_WIDTH = 0.50f;
	private final float DIALOG_SIZE_SCALE_RATE_HEIGHT = 0.85f;

	private CBOrderedListView mListView;

	public OrderedDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		initDialog();
	}

	public OrderedDialog(Context context, int theme) {
		super(context, theme);
		initDialog();
	}

	public OrderedDialog(Context context) {
		super(context);
		initDialog();
	}

	private void initDialog() {
		this.setContentView(R.layout.dialog_ordered);
		setCanceledOnTouchOutside(true);

		Window window = this.getWindow();
		window.setGravity(Gravity.CENTER);

		android.view.WindowManager.LayoutParams params = window.getAttributes();
		Display display = window.getWindowManager().getDefaultDisplay();
		params.width= (int) (display.getWidth() * DIALOG_SIZE_SCALE_RATE_WIDTH);
		params.height = (int) (display.getHeight() * DIALOG_SIZE_SCALE_RATE_HEIGHT);
		window.setAttributes(params);

		mListView = (CBOrderedListView) this.findViewById(R.id.cblistview_orderItemsList);
	}

	public void setOrder(CBOrder order) {
		mListView.setOrder(order);
	}

}
