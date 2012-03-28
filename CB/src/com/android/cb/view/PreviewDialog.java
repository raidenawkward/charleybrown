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
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author raiden
 *
 * @Description dialog with image preview and dish detail
 */
public class PreviewDialog extends CBBaseDialog {

	ImageView mImageView = null;
	TextView mTextViewName = null;
	TextView mTextViewPrice = null;
	TextView mTextViewSummary = null;
	TextView mTextViewDetail = null;

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
		this.setContentView(R.layout.preview_dialog);
		mImageView = (ImageView) this.findViewById(R.id.imageView);
		mTextViewName = (TextView) this.findViewById(R.id.textView_name);
		mTextViewPrice = (TextView) this.findViewById(R.id.textView_price);
//		mTextViewSummary = (TextView) this.findViewById(R.id.textView_summary);
		mTextViewDetail = (TextView) this.findViewById(R.id.textView_detail);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public void setMenuItem(CBMenuItem item) {
		if (item == null)
			return;
		CBDish dish = item.getDish();
		if (dish == null)
			return;

		if (mImageView != null) {
			Bitmap bitmap = BitmapFactory.decodeFile(dish.getPicture());
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
