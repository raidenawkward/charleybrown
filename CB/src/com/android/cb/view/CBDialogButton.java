/**
 * @Title: CBDialogButton.java
 * @Package: com.android.cb.view
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-4-5
 */
package com.android.cb.view;

import com.android.cb.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

/**
 * @author raiden
 *
 * @Description dialog button with regular button images
 */
public class CBDialogButton extends CBButton {

	public CBDialogButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initButton();
	}

	public CBDialogButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		initButton();
	}

	public CBDialogButton(Context context, Drawable drawableOn,
			Drawable drawableOff) {
		super(context, drawableOn, drawableOff);
		initButton();
	}

	public CBDialogButton(Context context, int drawableResourceOn,
			int drawableResourceOff) {
		super(context, drawableResourceOn, drawableResourceOff);
	}

	public CBDialogButton(Context context) {
		super(context);
		initButton();
	}

	private void initButton() {
		this.setDrawableOn(R.drawable.button_orange);
		this.setDrawableOff(R.drawable.button_gray);
		this.turnOff();
	}

}
