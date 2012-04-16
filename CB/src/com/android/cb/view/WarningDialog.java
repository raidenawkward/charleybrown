/**
 * @Title: WarningDialog.java
 * @Package: com.android.cb.view
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-4-16
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
 * @Description confirm dialog with only button 'OK'
 */
public class WarningDialog extends ConfirmDialog {

	public WarningDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		initView();
	}

	public WarningDialog(Context context, int theme) {
		super(context, theme);
		initView();
	}

	public WarningDialog(Context context) {
		super(context, R.style.CBConfirmDialog);
		initView();
	}

	protected void initView() {
		this.setContentView(R.layout.dialog_warning);
		setCanceledOnTouchOutside(false);

		mViewTitle = (TextView) this.findViewById(R.id.view_title);
		mViewMessage = (TextView) this.findViewById(R.id.view_text);

		mButtonConfirm = (CBDialogButton) this.findViewById(R.id.button_ok);
		mButtonConfirm.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				if (getCallback() != null)
					getCallback().onConfirm();
				dismiss();
			}
		});
	}

}
