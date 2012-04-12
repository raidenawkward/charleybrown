/**
 * @Title: OrderedListDialog.java
 * @Package: com.android.cb.view
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-4-12
 */
package com.android.cb.view;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.android.cb.R;
import com.android.cb.support.CBOrdersSet;

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
	private CBOrderedItemsSummaryListView mListView;

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

	public void setOrdersSet(CBOrdersSet set) {
		mListView.setOrdersSet(set);
	}

	private void initDialog() {
		this.setContentView(R.layout.dialog_ordered);
		setCanceledOnTouchOutside(true);

		SimpleDateFormat formatDate = new SimpleDateFormat(this.getContext().getResources().getString(R.string.ordered_time_format));

		mViewTitle = (TextView) this.findViewById(R.id.view_title);
		mViewTitle.setText(formatDate.format(new Date()));

		mViewCount = (TextView) this.findViewById(R.id.view_count);
		mListView = (CBOrderedItemsSummaryListView) this.findViewById(R.id.cblistview_orderItemsList);
	}

}
