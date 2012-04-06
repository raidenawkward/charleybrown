/**
 * @Title: CBOrderingDialog.java
 * @Package: com.android.cb.view
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-4-6
 */
package com.android.cb.view;

import com.android.cb.R;
import com.android.cb.support.CBDish;
import com.android.cb.support.CBIFOrderHandler;
import com.android.cb.support.CBMenuItem;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @author raiden
 *
 * @Description ordering dish with count in this dialog
 */
public class OrderingDialog extends CBBaseDialog {

	public static final int MAX_ITEM_COUNT = 50;

	CBMenuItem mMenuItem = null;
	CBIFOrderHandler mOrderHandler = null;

	private TextView mViewName;
	private EditText mEditCount;
	private CBDialogButton mButtonIncrease;
	private CBDialogButton mButtonDecrease;
	private CBDialogButton mButtonOk;
	private CBDialogButton mButtonClear;
	private CBDialogButton mButtonQuit;

	public OrderingDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		initView();
	}

	public OrderingDialog(Context context, int theme) {
		super(context, theme);
		initView();
	}

	public OrderingDialog(Context context) {
		super(context, R.style.CBTranslucentDialog);
		initView();
	}

	private void initView() {
		this.setContentView(R.layout.dialog_ordering);
		setCanceledOnTouchOutside(true);

		mViewName = (TextView) this.findViewById(R.id.view_name);

		mEditCount = (EditText) this.findViewById(R.id.edit_count);
		mEditCount.selectAll();

		mButtonIncrease = (CBDialogButton) this.findViewById(R.id.button_increase);
		mButtonIncrease.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				increaseCount();
			}
		});

		mButtonDecrease = (CBDialogButton) this.findViewById(R.id.button_decrease);
		mButtonDecrease.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				decreaseCount();
			}
		});

		mButtonOk = (CBDialogButton) this.findViewById(R.id.button_ok);
		mButtonOk.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				submit();
			}
		});

		mButtonClear = (CBDialogButton) this.findViewById(R.id.button_clear);
		mButtonClear.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				clear();
			}
		});

		mButtonQuit = (CBDialogButton) this.findViewById(R.id.button_quit);
		mButtonQuit.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				OrderingDialog.this.dismiss();
			}
		});
	}

	public void setMenuItem(CBMenuItem item) {
		if (item == null)
			return;

		CBDish dish = item.getDish();
		mViewName.setText(dish.getName());
		mMenuItem = item;
	}

	public void increaseCount() {
		int count = Integer.valueOf(mEditCount.getText().toString().trim());
		if (count >= MAX_ITEM_COUNT)
			return;

		mEditCount.setText(String.valueOf(++count));
		mEditCount.selectAll();
	}

	public void decreaseCount() {
		int count = Integer.valueOf(mEditCount.getText().toString().trim());
		if (count <= 0)
			return;

		mEditCount.setText(String.valueOf(--count));
		mEditCount.selectAll();
	}

	public void clear() {
		mEditCount.setText(String.valueOf(0));
		mEditCount.selectAll();
	}

	public void submit() {
		if (mMenuItem == null)
			return;
	}

	public void setOrderHandler(CBIFOrderHandler handler) {
		mOrderHandler = handler;
	}

}
