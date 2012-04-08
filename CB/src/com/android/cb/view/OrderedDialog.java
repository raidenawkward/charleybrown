/**
 * @Title: OrderedDialog.java
 * @Package: com.android.cb.view
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-4-7
 */
package com.android.cb.view;

import com.android.cb.R;
import com.android.cb.support.CBIFOrderHandler;
import com.android.cb.support.CBMenuItem;
import com.android.cb.support.CBOrder;

import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author raiden
 *
 * @Description dialog in which ordered result shows
 */
public class OrderedDialog extends CBBaseDialog
								implements CBOrderedListView.Callback, OrderingDialog.Callback {

	private final float DIALOG_SIZE_SCALE_RATE_WIDTH = 0.55f;
	private final float DIALOG_SIZE_SCALE_RATE_HEIGHT = 0.90f;

	private CBOrder mOrder = null;
	private CBIFOrderHandler mOrderHandler = null;

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
		mListView.setCallback(this);

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

		mViewCount.setText(getContext().getResources().getString(R.string.ordered_dialog_label_count)
						+ String.valueOf(order.getTotalItemCheckedCount()));
		mViewPrice.setText(getContext().getResources().getString(R.string.ordered_dialog_label_price)
						+ String.valueOf(order.getRealSummation())
						+ getContext().getResources().getString(R.string.ordered_dialog_price_tail));

		mListView.setOrder(order);
		mOrder = order;
	}

	public void submit() {
		if (mOrder.getTotalItemCheckedCount() <= 0) {
			String message = OrderedDialog.this.getContext().getResources().getString(R.string.ordered_dialog_submit_warning_empty);;
			Toast.makeText(OrderedDialog.this.getContext(), message, 0).show();
			return;
		}

		ConfirmDialog dialog = new ConfirmDialog(this.getContext());
		dialog.setCallback(new ConfirmDialog.Callback() {
			public void onConfirm() {

				String message = null;
				if (mOrderHandler.saveOrderRecord() == true) {
					message = OrderedDialog.this.getContext().getResources().getString(R.string.ordered_dialog_submit_succeed);
				} else {
					message = OrderedDialog.this.getContext().getResources().getString(R.string.ordered_dialog_submit_failed);
				}

				Toast.makeText(OrderedDialog.this.getContext(), message, 0).show();
				OrderedDialog.this.dismiss();
			}

			public void onCancel() {

			}
		});
		dialog.setMessage(getContext().getResources().getString(R.string.ordered_dialog_submit_message));
		dialog.show();
	}

	public CBOrder getOrder() {
		return mOrder;
	}

	private void showOrderingDialog(CBOrder.OrderedItem item) {
		OrderingDialog dialog = new OrderingDialog(this.getContext());
		dialog.setOrderHandler(mOrderHandler);
		dialog.setMenuItem(item.item);
		dialog.setCallback(this);
		dialog.show();
	}

	public void onOrderedItemEditInList(CBOrder.OrderedItem item) {
		if (item == null || mOrderHandler == null)
			return;

		showOrderingDialog(item);
	}

	public void onOrderedItemRemoveInList(CBOrder.OrderedItem item) {
		if (item == null || mOrderHandler == null)
			return;

		final CBOrder.OrderedItem itemToRemove = item;

		ConfirmDialog dialog = new ConfirmDialog(this.getContext());
		dialog.setCallback(new ConfirmDialog.Callback() {
			public void onConfirm() {
				mOrderHandler.removeItemFromOrder(itemToRemove.item);
				onOrderedItemListUpdate();
			}

			public void onCancel() {
			}
		});
		dialog.setMessage(getContext().getResources().getString(R.string.ordered_dialog_remove_message)
					+ item.item.getDish().getName()
					+ "?");
		dialog.show();
	}

	public void onOrderedItemListUpdate() {
		setOrder(mOrder);
	}

	public CBIFOrderHandler getOrderHandler() {
		return mOrderHandler;
	}

	public void setOrderHandler(CBIFOrderHandler orderHandler) {
		this.mOrderHandler = orderHandler;
	}

	public void onItemAddedToOrder(boolean succeed) {
		if (succeed == true)
			onOrderedItemListUpdate();
	}

	public void onItemDeletedFromOrder(boolean succeed) {
		if (succeed == true)
			onOrderedItemListUpdate();
	}

	public boolean onItemDeletingFromOrder(CBMenuItem item) {
		Toast.makeText(this.getContext(), R.string.ordering_count_cannot_be_zero, 0).show();
		return false;
	}

}
