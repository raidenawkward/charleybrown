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
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author raiden
 *
 * @Description dialog in which ordered result shows
 */
public class OrderedDialog extends CBBaseDialog {

	private final float DIALOG_SIZE_SCALE_RATE_WIDTH = 0.50f;
	private final float DIALOG_SIZE_SCALE_RATE_HEIGHT = 0.85f;

	private CBOrder mOrder = null;

	private CBOrderedListView mListView;
	private TextView mViewHeader;
	private TextView mViewTitle;
	private TextView mViewCount;
	private TextView mViewPrice;
	private CBDialogButton mButtonSubmit;
	private CBDialogButton mButtonQuit;

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

		mViewHeader = (TextView) this.findViewById(R.id.view_header);
		mViewTitle = (TextView) this.findViewById(R.id.view_title);
		mViewCount = (TextView) this.findViewById(R.id.view_count);
		mViewPrice = (TextView) this.findViewById(R.id.view_price);

		mButtonSubmit = (CBDialogButton) this.findViewById(R.id.button_submit);
		mButtonSubmit.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				submit();
			}
		});

		mButtonQuit = (CBDialogButton) this.findViewById(R.id.button_quit);
		mButtonQuit.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				OrderedDialog.this.dismiss();
			}
		});
	}

	public void setOrder(CBOrder order) {
		mViewHeader.setText(order.getId().toString());
		mViewTitle.setText(order.getCreateTime().toString());

		mViewCount.setText(getContext().getResources().getString(R.string.ordering_dialog_label_count)
						+ String.valueOf(order.getTotalItemCheckedCount()));
		mViewPrice.setText(getContext().getResources().getString(R.string.ordering_dialog_label_price)
						+ String.valueOf(order.getRealSummation())
						+ getContext().getResources().getString(R.string.ordering_dialog_price_tail));

		mListView.setOrder(order);
		mOrder = order;
	}

	public void submit() {

	}

	public CBOrder getOrder() {
		return mOrder;
	}

}
