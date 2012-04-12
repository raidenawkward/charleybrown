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
public class GridMenuViewAdapter extends CBImageViewAdapterBase {

	private GridMenuView mMenuView;
	private LayoutInflater mInflater;

	public GridMenuViewAdapter(Context context,
			List<CBMenuItem> objects, GridMenuView gridMenuView) {
		super(context, objects);
		mMenuView = gridMenuView;

//		CBAsyncImageLoader loader = new CBAsyncImageLoader(this.getImagePathList());
		CBAsyncImageLoader loader = new CBAsyncImageLoader();
		setAsyncImageLoader(loader);

		setListView(gridMenuView);
		setListItemSource(R.layout.main_grid_menu_view_list_item);

		mInflater = LayoutInflater.from(mMenuView.getContext());
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = mInflater.inflate(getListItemSource(), null, false);
		}

		GridItemView viewHolder = (GridItemView) convertView.getTag();
		if (viewHolder == null) {
			viewHolder = new GridItemView(convertView);
			viewHolder.setTextView(R.id.listitem_base_text);
			viewHolder.setImageView(R.id.listitem_base_image);
			viewHolder.setIconView(R.id.listitem_imageView_icon);
			convertView.setTag(viewHolder);
		}

		CBMenuItem menuItem = getItem(position);
		String imageUrl = menuItem.getDish().getThumb();
		ImageView imageView = viewHolder.getImageView();
		imageView.setTag(imageUrl);

		Drawable drawableImage = loadDrawable(imageUrl);

		if (drawableImage == null) {
			imageView.setImageResource(R.drawable.ic_launcher);
		} else {
			imageView.setImageDrawable(drawableImage);
		}

		TextView textView = viewHolder.getTextView();
		textView.setText(menuItem.getDish().getName());
		viewHolder.setIcon(menuItem.getIcon());

		return convertView;
	}

	private class GridItemView {
		private TextView mTextView = null;
		private ImageView mImageView = null;
		private ImageView mIconView = null;

		private View mBaseView;

		public GridItemView(View baseView) {
			if (baseView == null)
				return;
			mBaseView = baseView;
		}

		public void setTextView(int id) {
			mTextView = (TextView) mBaseView.findViewById(id);
		}

		public TextView getTextView() {
			return mTextView;
		}

		public void setImageView(int id) {
			mImageView = (ImageView) mBaseView.findViewById(id);
		}

		public ImageView getImageView() {
			return mImageView;
		}

		public void setIconView(int id) {
			mIconView = (ImageView) mBaseView.findViewById(id);
		}

		public void setIcon(int id) {
			if (id <= 0) {
				mIconView.setBackgroundDrawable(null);
				return;
			}

			mIconView.setBackgroundResource(id);
		}

	} // class ItemView

}
