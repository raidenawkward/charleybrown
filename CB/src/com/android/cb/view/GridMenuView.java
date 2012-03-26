/**
 * @Title: GridMenuView.java
 * @Package: com.android.cb.view
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-3-26
 */
package com.android.cb.view;

import java.util.List;

import com.android.cb.R;
import com.android.cb.support.CBMenuEngine;
import com.android.cb.support.CBMenuItem;
import com.android.cb.support.CBMenuItemsSet;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author raiden
 *
 * @Description grid menu view
 */
public class GridMenuView extends GridView {

	/** for data source */
	private CBMenuEngine mMenuEngine = null;
	private Adapter mAdapter = null;

	public GridMenuView(Context context) {
		super(context);

	}

	public GridMenuView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

	}

	public GridMenuView(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	public CBMenuEngine getMenuEngine() {
		return mMenuEngine;
	}

	public void setMenuEngine(CBMenuEngine menuEngine) {
		mMenuEngine = menuEngine;

		if (mMenuEngine != null) {
			CBMenuItemsSet set = mMenuEngine.getMenuSet();

			mAdapter = new Adapter(getContext(), 0, set.getMenuItemsList(), R.layout.listitem_base);
			this.setAdapter(mAdapter);
		}
	}

	private class Adapter extends CBListViewAdapter {

		public Adapter(Context context, int textViewResourceId,
				List<CBMenuItem> objects, int listItemSource) {
			super(context, textViewResourceId, objects);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (getListView() == null || getListItemSource() == 0
					|| getAsyncImageLoader() == null)
			return null;

			View rowView = convertView;
			ItemView viewItem = new ItemView(GridMenuView.this,
					R.id.listitem_base_text,
					R.id.listitem_base_image);
			if (rowView == null) {
				LayoutInflater inflater = LayoutInflater.from(GridMenuView.this.getContext());
				rowView = inflater.inflate(getListItemSource(), null);
				rowView.setTag(viewItem);
			} else {
				viewItem = (ItemView) rowView.getTag();
			}

			CBMenuItem menuItem = getItem(position);
			String imageUrl = menuItem.getDish().getPicture();
			ImageView imageView = viewItem.getImageView();
			imageView.setTag(imageUrl);

			Drawable drawableImage = loadDrawable(imageUrl);

			if (drawableImage == null) {
				imageView.setImageResource(R.drawable.ic_launcher);
			} else {
				imageView.setImageDrawable(drawableImage);
			}

			TextView textView = viewItem.getTextView();
			textView.setText(menuItem.getDish().getName());

			return rowView;
		}

		private class ItemView {
			private TextView mTextView = null;
			private ImageView mImageView = null;

			public ItemView(View baseView, int textViewId, int imageViewId) {
				if (baseView == null)
					return;
				mTextView = (TextView) baseView.findViewById(textViewId);
				mImageView = (ImageView) baseView.findViewById(imageViewId);
			}

			public TextView getTextView() {
				return mTextView;
			}

			public ImageView getImageView() {
				return mImageView;
			}
		} // class ItemView

	} // class Adapter

}
