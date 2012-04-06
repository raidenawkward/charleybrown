/**
 * @Title: PreviewDialog.java
 * @Package: com.android.cb.view
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-3-28
 */
package com.android.cb.view;

import com.android.cb.R;
import com.android.cb.support.CBDish;
import com.android.cb.support.CBIFOrderHandler;
import com.android.cb.support.CBMenuItem;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author raiden
 *
 * @Description dialog with image preview and dish detail
 */
public class PreviewDialog extends CBBaseDialog implements OrderingDialog.Callback {

	public final float SCALED_RATE = 0.60f;

	private ImageView mImageView = null;
	private TextView mTextViewName = null;
	private TextView mTextViewPrice = null;
	private TextView mTextViewSummary = null;
	private TextView mTextViewDetail = null;
	private CBDialogButton mButtonOrder = null;
	private CBDialogButton mButtonDetail = null;
	private CBDialogButton mButtonQuit = null;

	private CBIFOrderHandler mOrderHandler = null;
	private CBMenuItem mMenuItem = null;

	public PreviewDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		initView();
	}

	public PreviewDialog(Context context, int theme) {
		super(context, theme);
		initView();
	}

	public PreviewDialog(Context context) {
		super(context);
		initView();
	}

	private void initView() {
		this.setContentView(R.layout.dialog_preview);
		setCanceledOnTouchOutside(true);

		mImageView = (ImageView) this.findViewById(R.id.imageView);
		mImageView.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				return false;
			}
		});

		mTextViewName = (TextView) this.findViewById(R.id.textView_name);
		mTextViewPrice = (TextView) this.findViewById(R.id.textView_price);
		mTextViewSummary = (TextView) this.findViewById(R.id.textView_summary);
//		mTextViewDetail = (TextView) this.findViewById(R.id.textView_detail);

		mButtonOrder = (CBDialogButton) this.findViewById(R.id.button_order);
		mButtonOrder.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				showOrdingDialog();
			}
		});

		mButtonDetail = (CBDialogButton) this.findViewById(R.id.button_detail);
		mButtonDetail.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				showSingleMenuViewDialog();
			}
		});

		mButtonQuit = (CBDialogButton) this.findViewById(R.id.button_quit);
		mButtonQuit.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				PreviewDialog.this.dismiss();
			}
		});
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	private void showSingleMenuViewDialog() {
		if (mMenuItem == null)
			return;

		SingleMenuViewDialog dialog = new SingleMenuViewDialog(PreviewDialog.this.getContext());
		dialog.setMenuItem(mMenuItem);
		dialog.setOrderHandler(mOrderHandler);
		dialog.show();
		dialog.showDishInfoDialog();
	}

	public CBMenuItem getMenuItem() {
		return mMenuItem;
	}

	public void setMenuItem(CBMenuItem item) {
		mMenuItem = item;
		if (item == null)
			return;

		CBDish dish = item.getDish();
		if (dish == null)
			return;

		if (mImageView != null) {
			Display display = this.getWindow().getWindowManager().getDefaultDisplay();
			float screenWidth = display.getWidth();
			float screenHeight = display.getHeight();
			Bitmap bitmap = CBBitmapFactory.loadScaledBitmap(dish.getPicture(), screenWidth * SCALED_RATE, screenHeight * SCALED_RATE);
			mImageView.setImageBitmap(bitmap);
		}

		if (mTextViewName != null) {
			mTextViewName.setText(dish.getName());
		}

		if (mTextViewPrice != null) {
			mTextViewPrice.setText(getContext().getResources().getString(R.string.preview_price_prefix)
						+ String.valueOf(dish.getPrice())
						+ getContext().getResources().getString(R.string.preview_price_rear));
		}

		if (mTextViewSummary != null) {
			mTextViewSummary.setText(dish.getSummarize());
		}

		if (mTextViewDetail != null) {
			mTextViewDetail.setText(dish.getDetail());
		}
	}

	public CBIFOrderHandler getOrderHandler() {
		return mOrderHandler;
	}

	public void setOrderHandler(CBIFOrderHandler orderHandler) {
		this.mOrderHandler = orderHandler;
	}

	public void showOrdingDialog() {
		OrderingDialog dialog = new OrderingDialog(this.getContext());
		dialog.setOrderHandler(mOrderHandler);
		dialog.setMenuItem(mMenuItem);
		dialog.setCallback(this);
		dialog.show();
	}

	public void onItemAddingToOrder(boolean succeed) {
		int stringId = (succeed ? R.string.ordering_adding_succeed : R.string.ordering_adding_failed);
		String str = this.getContext().getResources().getString(stringId);
		Toast.makeText(this.getContext(), str, 0).show();
	}

	public void onItemDeletingFromOrder(boolean succeed) {
		int stringId = (succeed ? R.string.ordering_deleting_succeed : R.string.ordering_deleting_falied);
		String str = this.getContext().getResources().getString(stringId);
		Toast.makeText(this.getContext(), str, 0).show();
	}

}
