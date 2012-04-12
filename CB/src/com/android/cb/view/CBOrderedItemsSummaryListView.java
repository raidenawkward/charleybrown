/**
 * @Title: CBOrderedItemSummaryList.java
 * @Package: com.android.cb.view
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-4-12
 */
package com.android.cb.view;

import java.text.SimpleDateFormat;
import java.util.List;

import com.android.cb.R;
import com.android.cb.support.CBOrder;
import com.android.cb.support.CBOrdersSet;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author raiden
 *
 * @Description total order list item view
 */
public class CBOrderedItemsSummaryListView extends ListView {

	private CBOrderedItemsSummaryListView.ListAdapter mAdapter = null;

	public CBOrderedItemsSummaryListView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	public CBOrderedItemsSummaryListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CBOrderedItemsSummaryListView(Context context) {
		super(context);
	}

	public void setOrdersSet(CBOrdersSet set) {
		if (set == null)
			return;

		mAdapter = new ListAdapter(this.getContext(), set.getSet(), this);
		this.setAdapter(mAdapter);
	}

	public class ListItemView {
		private TextView mViewLocation = null;
		private TextView mViewDate = null;
		private TextView mViewCount = null;
		private TextView mViewPrice = null;

		private View mBaseView = null;

		public ListItemView(View baseView) {
			mBaseView = baseView;
		}

		public TextView getViewLocation() {
			return mViewLocation;
		}

		public void setViewLocation(int id) {
			mViewLocation = (TextView) mBaseView.findViewById(id);
		}

		public TextView getViewDate() {
			return mViewDate;
		}

		public void setViewDate(int id) {
			mViewDate = (TextView) mBaseView.findViewById(id);
		}

		public TextView getViewCount() {
			return mViewCount;
		}

		public void setViewCount(int id) {
			mViewCount = (TextView) mBaseView.findViewById(id);
		}

		public TextView getViewPrice() {
			return mViewPrice;
		}

		public void setViewPrice(int id) {
			mViewPrice = (TextView) mBaseView.findViewById(id);
		}

		public View getBaseView() {
			return mBaseView;
		}

		public void setBaseView(View baseView) {
			this.mBaseView = baseView;
		}

	} // class ListItemView

	public class ListAdapter extends ArrayAdapter<CBOrder> {

		private LayoutInflater mInflater;
		private static final int sListItemSource = R.layout.ordered_list_item;

		public ListAdapter(Context context, List<CBOrder> objects, CBOrderedItemsSummaryListView listView) {
			super(context, 0, objects);
			mInflater = LayoutInflater.from(CBOrderedItemsSummaryListView.this.getContext());
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = mInflater.inflate(sListItemSource, null, false);
			}

			final CBOrder orderedItem = this.getItem(position);

			ListItemView listItemView = (ListItemView) convertView.getTag();
			if (listItemView == null) {
				listItemView = new ListItemView(convertView);
				listItemView.setViewLocation(R.id.view_location);
				listItemView.setViewDate(R.id.view_date);
				listItemView.setViewCount(R.id.view_count);
				listItemView.setViewPrice(R.id.view_price);

				convertView.setTag(listItemView);
			}

			listItemView.getViewLocation().setText(orderedItem.getLocation());

			SimpleDateFormat formatDate = new SimpleDateFormat(this.getContext().getResources().getString(R.string.ordered_time_format));
			listItemView.getViewDate().setText(formatDate.format(orderedItem.getSubmitedTime()));

			listItemView.getViewPrice().setText(String.valueOf(orderedItem.getRealSummation()));
			listItemView.getViewCount().setText(orderedItem.getTotalItemCheckedCount());

			return convertView;
		}

	} // class CBAdapter

}
