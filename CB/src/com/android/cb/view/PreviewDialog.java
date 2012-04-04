/**
 * @Title: PreviewDialog.java
 * @Package: com.android.cb.view
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-3-28
 */
package com.android.cb.view;

import com.android.cb.R;
import com.android.cb.support.CBDish;
import com.android.cb.support.CBMenuItem;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author raiden
 *
 * @Description dialog with image preview and dish detail
 */
public class PreviewDialog extends CBBaseDialog {

	public interface Callback {
		/**
		 * @Description asked whether there is a next action on item
		 * @param item
		 * @return boolean if returns false, this dialog would be
		 * dismissed
		 */
		public boolean onImageClickedInPreviewDialog(CBMenuItem item);
	}

	public final float SCALED_RATE = 0.60f;

	private ImageView mImageView = null;
	private TextView mTextViewName = null;
	private TextView mTextViewPrice = null;
	private TextView mTextViewSummary = null;
	private TextView mTextViewDetail = null;

	Callback mCallback = null;
	CBMenuItem mMenuItem = null;

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
		mImageView = (ImageView) this.findViewById(R.id.imageView);
		mImageView.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (mCallback != null) {
					if (!mCallback.onImageClickedInPreviewDialog(mMenuItem)) {
						PreviewDialog.this.dismiss();
					}
				}
				return false;
			}
		});

		mTextViewName = (TextView) this.findViewById(R.id.textView_name);
		mTextViewPrice = (TextView) this.findViewById(R.id.textView_price);
//		mTextViewSummary = (TextView) this.findViewById(R.id.textView_summary);
		mTextViewDetail = (TextView) this.findViewById(R.id.textView_detail);

		setCanceledOnTouchOutside(true);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public void setCallback(Callback callback) {
		mCallback = callback;
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
			mTextViewPrice.setText(String.valueOf(dish.getPrice()));
		}

		if (mTextViewSummary != null) {
			mTextViewDetail.setText(dish.getSummarize());
		}

		if (mTextViewDetail != null) {
			mTextViewDetail.setText(dish.getDetail());
		}
	}

}
