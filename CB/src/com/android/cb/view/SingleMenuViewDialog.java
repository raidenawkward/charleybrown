/**
 * @Title: SingleMenuViewDialog.java
 * @Package: com.android.cb.view
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-3-31
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
import android.view.View.OnTouchListener;
import android.widget.ImageView;

/**
 * @author raiden
 *
 * @Description show single image view according to MenuItem
 */
public class SingleMenuViewDialog extends CBBaseDialog implements DishInfoDialog.Callback {

	private CBIFOrderHandler mOrderHandler = null;
	private CBMenuItem mMenuItem = null;
	private ImageView mImageView;

	public SingleMenuViewDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		initView();
	}

	public SingleMenuViewDialog(Context context, int theme) {
		super(context, theme);
		initView();
	}

	public SingleMenuViewDialog(Context context) {
		super(context, R.style.CBSingleViewDialog);
		initView();
	}

	private void initView() {
		setContentView(R.layout.dialog_singleview);
		mImageView = (ImageView) this.findViewById(R.id.imageView);

		mImageView.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				showDishInfoDialog();
				return false;
			}
		});
	}

	public void setMenuItem(CBMenuItem item) {
		if (mImageView == null || item == null)
			return;

		mMenuItem = item;
		CBDish dish = item.getDish();
		Display display = this.getWindow().getWindowManager().getDefaultDisplay();
		float screenWidth = display.getWidth();
		float screenHeight = display.getHeight();
		Bitmap bitmap = CBBitmapFactory.loadScaledBitmap(dish.getPicture(), screenWidth , screenHeight);
		mImageView.setImageBitmap(bitmap);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public void showDishInfoDialog() {
		DishInfoDialog dialog = new DishInfoDialog(SingleMenuViewDialog.this.getContext());
		dialog.setCallback(SingleMenuViewDialog.this);

		if (mMenuItem != null)
			dialog.setMenuItem(mMenuItem);
		dialog.show();
	}

	public void onQuitFromDishInfoDialog() {
		this.dismiss();
	}

	public CBIFOrderHandler getOrderHandler() {
		return mOrderHandler;
	}

	public void setOrderHandler(CBIFOrderHandler orderHandler) {
		this.mOrderHandler = orderHandler;
	}

}
