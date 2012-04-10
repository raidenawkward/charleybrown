/**
 * @Title: OrderPrepareDialog.java
 * @Package: com.android.cb.view
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-4-10
 */
package com.android.cb.view;

import java.util.List;

import com.android.cb.R;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

/**
 * @author raiden
 *
 * @Description dialog for order preparing
 */
public class OrderPrepareDialog extends CBBaseDialog {

	public interface Callback {
		public void onSubmit();
		public void onCancel();
		public void onSelectedItemChanged(String item);
	}
	private Callback mCallback = null;

	private Spinner mSpinnerLocation;
	private CBDialogButton mButtonConfirm;
	private CBDialogButton mButtonCancel;

	private ArrayAdapter<String> mAdapter = null;

	public OrderPrepareDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		initView();
	}

	public OrderPrepareDialog(Context context, int theme) {
		super(context, theme);
		initView();
	}

	public OrderPrepareDialog(Context context) {
		super(context);
		initView();
	}

	private void initView() {
		this.setContentView(R.layout.dialog_order_prepare);
		setCanceledOnTouchOutside(false);

		mSpinnerLocation = (Spinner) this.findViewById(R.id.spinner_location);
		mSpinnerLocation.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if (mCallback != null || mAdapter != null)
					mCallback.onSelectedItemChanged(mAdapter.getItem(arg2));
			}

			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

		mButtonConfirm = (CBDialogButton) this.findViewById(R.id.button_ok);
		mButtonConfirm.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				dismiss();
				if (mCallback != null)
					mCallback.onSubmit();
			}
		});

		mButtonCancel = (CBDialogButton) this.findViewById(R.id.button_cancel);
		mButtonCancel.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				dismiss();
				if (mCallback != null)
					mCallback.onCancel();
			}
		});
	}

	public void setContentList(List<String> list) {
		mAdapter = new ArrayAdapter<String>(this.getContext(),
					android.R.layout.simple_spinner_item,
					list);
		mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSpinnerLocation.setAdapter(mAdapter);
	}

	public int getSelectedIndex() {
		return mSpinnerLocation.getSelectedItemPosition();
	}

	public String getSelectedItem() {
		if (mAdapter == null)
			return null;

		return mAdapter.getItem(getSelectedIndex());
	}

	public void setSelectedItem(String item) {
		int index = -1;

		for (int i = 0; i < mAdapter.getCount(); ++i) {
			if (mAdapter.getItem(i).equals(item)) {
				index = i;
				break;
			}
		}

		setSelectedItem(index);
	}

	public boolean setSelectedItem(int index) {
		if (mAdapter == null)
			return false;

		if (index < 0 || index >= mAdapter.getCount())
			return false;

		mSpinnerLocation.setSelection(index);

		return true;
	}

	public void setCallback(Callback callback) {
		this.mCallback = callback;
	}

}
