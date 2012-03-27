/**
 * @Title: CBListViewAdapter.java
 * @Package: com.android.cb.view
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-3-26
 */
package com.android.cb.view;

import java.util.ArrayList;
import java.util.List;

import com.android.cb.support.CBMenuItem;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

/**
 * @author raiden
 *
 * @Description base menu list view adapter
 */
public abstract class CBListViewAdapter extends ArrayAdapter<CBMenuItem> {
	private AbsListView mListView = null;
    private CBAsyncImageLoader mAsyncImageLoader = null;
    private int mListItemSource = 0;

	public CBListViewAdapter(Context context, int textViewResourceId,
			List<CBMenuItem> objects) {
		super(context, textViewResourceId, objects);

	}

	public void setListView(AbsListView listView) {
		mListView = listView;
	}

	public AbsListView getListView() {
		return mListView;
	}

	public void setAsyncImageLoader(CBAsyncImageLoader loader) {
		mAsyncImageLoader = loader;
	}

	public CBAsyncImageLoader getAsyncImageLoader() {
		return mAsyncImageLoader;
	}

	public void setListItemSource(int source) {
		mListItemSource = source;
	}

	public int getListItemSource() {
		return mListItemSource;
	}

	public ArrayList<String> getImagePathList() {
		ArrayList<String> res = new ArrayList<String>();

		for (int i = 0; i < this.getCount(); ++i) {
			CBMenuItem item = this.getItem(i);
			String s = item.getDish().getThumb();
			res.add(s);
		}

		return res;
	}

	public Drawable loadDrawable(String imageUrl) {
		if (mAsyncImageLoader == null || mListView == null)
			return null;

		Drawable res = mAsyncImageLoader.loadDrawable(imageUrl,
				new CBAsyncImageLoader.Callback() {
					public void onImageLoaded(Drawable imageDrawable, String imageUrl) {
						ImageView imageViewByTag = (ImageView) mListView.findViewWithTag(imageUrl);
						if (imageViewByTag != null) {
		                    imageViewByTag.setImageDrawable(imageDrawable);
		                }
					}
				}
		);

		return res;
	}

	public abstract View getView(int position, View convertView, ViewGroup parent);

}
