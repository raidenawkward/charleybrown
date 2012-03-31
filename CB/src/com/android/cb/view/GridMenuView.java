/**
 * @Title: GridMenuView.java
 * @Package: com.android.cb.view
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-3-26
 */
package com.android.cb.view;

import com.android.cb.support.CBMenuItem;
import com.android.cb.support.CBMenuItemsSet;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

/**
 * @author raiden
 *
 * @Description grid menu view
 */
public class GridMenuView extends GridView
		implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

	/**
	 * @Description grid menu control call back
	 */
	public interface Callback {
		public void onItemClicked(CBMenuItem item);
		public boolean onItemLongPressed(CBMenuItem item);
	}

	public final int COLUMN_COUNT = 3;

	private Callback mCallback = null;
	private GridMenuViewAdapter mAdapter = null;

	public GridMenuView(Context context) {
		super(context);
		initMenuView();
	}

	public GridMenuView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initMenuView();
	}

	public GridMenuView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initMenuView();
	}

	private void initMenuView() {
		setNumColumns(COLUMN_COUNT);

		this.setOnItemClickListener(this);
		this.setOnItemLongClickListener(this);
	}

	public void setCallback(Callback callback) {
		mCallback = callback;
	}

	public void setMenuItemSet(CBMenuItemsSet set) {
		if (set == null)
			return;
		if (set.getMenuItemsList() == null)
			return;

		mAdapter = new GridMenuViewAdapter(this.getContext(), set.getMenuItemsList(), this);
		this.setAdapter(mAdapter);
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		CBMenuItem item = (CBMenuItem) arg0.getItemAtPosition(arg2);

		if (mCallback != null) {
			mCallback.onItemClicked(item);
		}
	}

	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		CBMenuItem item = (CBMenuItem) arg0.getItemAtPosition(arg2);
		if (mCallback != null) {
			return mCallback.onItemLongPressed(item);
		}

		return false;
	}
}
