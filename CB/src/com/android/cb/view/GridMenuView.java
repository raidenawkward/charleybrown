/**
 * @Title: GridMenuView.java
 * @Package: com.android.cb.view
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-3-26
 */
package com.android.cb.view;

import com.android.cb.support.CBMenuItem;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

/**
 * @author raiden
 *
 * @Description grid menu view
 */
public class GridMenuView extends GridView {

	public final int COLUMN_COUNT = 3;

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

		this.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				CBMenuItem item = (CBMenuItem) arg0.getItemAtPosition(arg2);
				Toast.makeText(GridMenuView.this.getContext(), item.getDish().getThumb(), 0).show();
			}
		});
	}
}
