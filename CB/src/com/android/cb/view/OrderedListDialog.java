/**
 * @Title: OrderedListDialog.java
 * @Package: com.android.cb.view
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-4-12
 */
package com.android.cb.view;

import com.android.cb.R;

import android.content.Context;
import android.widget.TextView;

/**
 * @author raiden
 *
 * @Description dialog for presenting of ordered list
 */
public class OrderedListDialog extends CBBaseDialog {

	private TextView mViewTitle;
	private TextView mViewCount;

	public OrderedListDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		initDialog();
	}

	public OrderedListDialog(Context context, int theme) {
		super(context, theme);
		initDialog();
	}

	public OrderedListDialog(Context context) {
		super(context);
		initDialog();
	}

	private void initDialog() {
		this.setContentView(R.layout.dialog_ordered);
		setCanceledOnTouchOutside(true);

		mViewTitle = (TextView) this.findViewById(R.id.view_title);
		mViewCount = (TextView) this.findViewById(R.id.view_count);
	}

}
