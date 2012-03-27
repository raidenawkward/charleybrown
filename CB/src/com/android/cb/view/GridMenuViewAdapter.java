/**
 * @Title: GridMenuViewAdapter.java
 * @Package: com.android.cb.view
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-3-26
 */
package com.android.cb.view;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.cb.R;
import com.android.cb.support.CBMenuItem;


/**
 * @author raiden
 *
 * @Description grid menu view adapter, used in GridMenuView
 */
public class GridMenuViewAdapter extends CBListViewAdapter {

	GridMenuView mMenuView;

	public GridMenuViewAdapter(Context context, int textViewResourceId,
			List<CBMenuItem> objects, GridMenuView gridMenuView) {
		super(context, textViewResourceId, objects);
		mMenuView = gridMenuView;

//		CBAsyncImageLoader loader = new CBAsyncImageLoader(this.getImagePathList());
		CBAsyncImageLoader loader = new CBAsyncImageLoader();
		setAsyncImageLoader(loader);

		setListView(gridMenuView);
		setListItemSource(R.layout.listitem_base);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (getListView() == null || getListItemSource() == 0
				|| getAsyncImageLoader() == null)
			return new View(this.getContext());

		View rowView = convertView;
		ItemView viewItem;
		if (rowView == null) {
			LayoutInflater inflater = LayoutInflater.from(mMenuView.getContext());
			rowView = inflater.inflate(getListItemSource(), null);
			viewItem = new ItemView(rowView,
					R.id.listitem_base_text,
					R.id.listitem_base_image);
			rowView.setTag(viewItem);
		} else {
			viewItem = (ItemView) rowView.getTag();
		}

		CBMenuItem menuItem = getItem(position);
		String imageUrl = menuItem.getDish().getThumb();
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

}
