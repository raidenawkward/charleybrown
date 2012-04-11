/**
 * @Title: ConfirmDialog.java
 * @Package: com.android.cb.view
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-4-8
 */
package com.android.cb.view;

import com.android.cb.R;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author raiden
 *
 * @Description confirm dialog with button 'OK' and 'Cancel'
 */
public class ConfirmDialog extends CBBaseDialog {

	public interface Callback {
		public void onConfirm();
		public void onCancel();
	}
	private Callback mCallback = null;

	private TextView mViewTitle;
	private TextView mViewMessage;
	private CBDialogButton mButtonConfirm;
	private CBDialogButton mButtonCancel;

	public ConfirmDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		initView();
	}

	public ConfirmDialog(Context context, int theme) {
		super(context, theme);
		initView();
	}

	public ConfirmDialog(Context context) {
		super(context, R.style.CBConfirmDialog);
		initView();
	}

	private void initView() {
		this.setContentView(R.layout.dialog_confirm);
		setCanceledOnTouchOutside(false);

		mViewTitle = (TextView) this.findViewById(R.id.view_title);
		mViewMessage = (TextView) this.findViewById(R.id.view_text);

		mButtonConfirm = (CBDialogButton) this.findViewById(R.id.button_ok);
		mButtonConfirm.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				if (mCallback != null)
					mCallback.onConfirm();
				dismiss();
			}
		});

		mButtonCancel = (CBDialogButton) this.findViewById(R.id.button_cancel);
		mButtonCancel.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				if (mCallback != null)
					mCallback.onCancel();
				dismiss();
			}
		});
	}

	public void setTitle(String str) {
		mViewTitle.setText(str);
	}

	public void setTitle(int strId) {
		String str = this.getContext().getResources().getString(strId);
		if (str != null)
			setTitle(str);
	}

	public void setMessage(String str) {
		mViewMessage.setText(str);
	}

	public void setConfirmButtonText(String text) {
		if (text != null)
			mButtonConfirm.setText(text);
	}

	public void setCancelButtonText(String text) {
		if (text != null)
			mButtonCancel.setText(text);
	}

	public Callback getCallback() {
		return mCallback;
	}

	public void setCallback(Callback callback) {
		this.mCallback = callback;
	}

}
