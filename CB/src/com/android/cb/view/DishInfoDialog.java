/**
 * @Title: DishInfoDialog.java
 * @Package: com.android.cb.view
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-4-5
 */
package com.android.cb.view;

import com.android.cb.R;
import com.android.cb.support.CBDish;
import com.android.cb.support.CBMenuItem;

import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author raiden
 *
 * @Description dialog shows dish information
 */
public class DishInfoDialog extends CBBaseDialog {

	public interface Callback {
		public void onQuitFromDishInfoDialog();
	}

	private Callback mCallback = null;

	private final float DIALOG_SIZE_ZOOMING_RATE_X = 0.40f;
	private final float DIALOG_SIZE_ZOOMING_RATE_Y = 0.65f;

	private TextView mViewName;
	private TextView mViewPrice;
	private TextView mViewSummary;
	private TextView mViewDetail;
	private CBDialogButton mButtonQuit;

	public DishInfoDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		initDialog();
	}

	public DishInfoDialog(Context context, int theme) {
		super(context, theme);
		initDialog();
	}

	public DishInfoDialog(Context context) {
		super(context, R.style.CBTranslucentDialog);
		initDialog();
	}

	private void initDialog() {
		this.setContentView(R.layout.dialog_dishinfo);
		setCanceledOnTouchOutside(true);

		Window window = this.getWindow();
		window.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);

		android.view.WindowManager.LayoutParams params = window.getAttributes();
		Display display = window.getWindowManager().getDefaultDisplay();
		params.width= (int) (display.getWidth() * DIALOG_SIZE_ZOOMING_RATE_X);
		params.height = (int) (display.getHeight() * DIALOG_SIZE_ZOOMING_RATE_Y);
		params.x = 20;
		window.setAttributes(params);

		mViewName = (TextView) this.findViewById(R.id.view_name);
		mViewPrice = (TextView) this.findViewById(R.id.view_price);
		mViewSummary = (TextView) this.findViewById(R.id.view_summary);
		mViewDetail = (TextView) this.findViewById(R.id.view_detail);
		mButtonQuit = (CBDialogButton) this.findViewById(R.id.button_quit);

		mButtonQuit.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				if (mCallback != null)
					mCallback.onQuitFromDishInfoDialog();
				DishInfoDialog.this.dismiss();
			}
		});
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public void setDialogGravity(int gravity, int offset) {
		Window window = this.getWindow();
		window.setGravity(gravity);

		android.view.WindowManager.LayoutParams params = window.getAttributes();
		params.x = offset;
		window.setAttributes(params);
	}

	public void setMenuItem(CBMenuItem item) {
		if (item == null)
			return;

		CBDish dish = item.getDish();
		if (dish == null)
			return;

		mViewName.setText(dish.getName());
		mViewPrice.setText(mContext.getString(R.string.dishinfo_price_prefix) + dish.getPrice() + mContext.getString(R.string.dishinfo_price_rear));
		mViewSummary.setText(dish.getSummarize());
		mViewDetail.setText(dish.getDetail());
	}

	public void setCallback(Callback callback) {
		this.mCallback = callback;
	}

}
