/**
 * @Title: CBListViewAdapter.java
 * @Package: com.android.cb.view
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-3-26
 */
package com.android.cb.view;

import java.util.List;

import com.android.cb.support.CBMenuItem;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;

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

	public abstract View getView(int position, View convertView, ViewGroup parent);

}
