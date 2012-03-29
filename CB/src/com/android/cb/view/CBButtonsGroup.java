/**
 * @Title: CBButtonsGroup.java
 * @Package: com.android.cb.view
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-3-29
 */
package com.android.cb.view;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * @author raiden
 *
 * @Description CBButton group, in which only one button is on 'on' status at
 * 		one moment
 */
public class CBButtonsGroup extends LinearLayout {

	/**
	 * @Description once button in group clicked, 'onButtonClicked' called
	 * if this function returns true, this button would be turned on and any
	 * others in group would be turned off
	 */
	public interface Callback {
		public boolean onButtonClicked(int index);
	}

	private Callback mCallback = null;
	private ArrayList<CBButton> mButtonList = new ArrayList<CBButton>();

	private class CBButtonClickedListener implements Button.OnClickListener {
		private int mIndex;
		private View mView;

		public CBButtonClickedListener(int index, View view) {
			mIndex = index;
			mView = view;
		}

		public void onClick(View arg0) {
			if (mView != arg0)
				return;
			if (CBButtonsGroup.this.mCallback == null)
				turnButtonOn(mIndex);
			else {
				if (CBButtonsGroup.this.mCallback.onButtonClicked(mIndex)) {
					turnButtonOn(mIndex);
				}
			}
		}
	}

	public CBButtonsGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CBButtonsGroup(Context context) {
		super(context);
	}

	public boolean addButton(CBButton button) {
		if (mButtonList.contains(button) || button == null)
			return false;

		if (!mButtonList.add(button))
			return false;

		int index = mButtonList.size() - 1;
		button.setOnClickListener(new CBButtonClickedListener(index, button));
		this.addView(button);

		return true;
	}

	public boolean removeButton(CBButton button) {
		return mButtonList.remove(button);
	}

	public boolean removeButton(int index) {
		if (index < 0 || index >= mButtonList.size())
			return false;

		return (mButtonList.remove(index) != null);
	}

	public void setCallback(Callback callback) {
		mCallback = callback;
	}

	public int getButtonsCount() {
		return mButtonList.size();
	}

	private void turnButtonOn(int index) {
		if (index < 0 || index >= mButtonList.size())
			return;

		for (int i = 0; i < mButtonList.size(); ++i) {
			CBButton button = mButtonList.get(i);

			if (i == index) {
				button.turnOn();
			} else  {
				button.turnOff();
			}
		} // for
	}

}
