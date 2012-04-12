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
import com.android.cb.support.CBOrder;
import com.android.cb.support.CBOrdersSet;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author raiden
 *
 * @Description dialog for presenting of ordered list
 */
public class OrdersListDialog extends CBBaseDialog {

	public interface Callback {
		public void onOrdersListRefresh();
		/**
		 * @Description call back function on order item clicked in list
		 * @param order clicked order in list
		 * @return boolean if method returns true, dialog will dismiss
		 */
		public boolean onOrderClickedInList(CBOrder order);
	}
	private Callback mCallback = null;
	private CBOrdersSet mOrdersSet = null;

	private TextView mViewTitle;
	private TextView mViewCount;
	private CBDialogButton mButtonRefresh = null;
//	private CBDialogButton mButtonExport = null;
	private CBDialogButton mButtonCancel = null;
	private CBOrderedItemsSummaryListView mListView;

	public OrdersListDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		initDialog();
	}

	public OrdersListDialog(Context context, int theme) {
		super(context, theme);
		initDialog();
	}

	public OrdersListDialog(Context context) {
		super(context);
		initDialog();
	}

	public void setOrdersSet(CBOrdersSet set) {
		mListView.setOrdersSet(set);
		mViewCount.setText(String.valueOf(set.count()));
		mOrdersSet = set;
	}

	private void initDialog() {
		this.setContentView(R.layout.dialog_ordered_list);
		setCanceledOnTouchOutside(true);

		SimpleDateFormat formatDate = new SimpleDateFormat(this.getContext().getResources().getString(R.string.ordered_list_date_format));

		mViewTitle = (TextView) this.findViewById(R.id.view_title);
		mViewTitle.setText(formatDate.format(new Date()));

		mViewCount = (TextView) this.findViewById(R.id.view_count);

		mListView = (CBOrderedItemsSummaryListView) this.findViewById(R.id.cblistview_orderItemsList);
		mListView.setOnItemClickListener(new ListView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (mCallback != null && mOrdersSet != null) {
					if (mCallback.onOrderClickedInList(mOrdersSet.get(arg2)) == true) {
						dismiss();
					}
				}
			}
		});

		mButtonRefresh = (CBDialogButton) this.findViewById(R.id.button_refresh);
		mButtonRefresh.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				if (mCallback != null)
					mCallback.onOrdersListRefresh();
			}
		});

//		mButtonExport = (CBDialogButton) this.findViewById(R.id.button_export);
//		mButtonExport.setOnClickListener(new Button.OnClickListener() {
//			public void onClick(View v) {
//
//			}
//		});

		mButtonCancel = (CBDialogButton) this.findViewById(R.id.button_quit);
		mButtonCancel.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				dismiss();
			}
		});
	}

	public void setCallback(Callback callback) {
		this.mCallback = callback;
	}

}
