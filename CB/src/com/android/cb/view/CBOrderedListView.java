/**
 * @Title: CBOrderedList.java
 * @Package: com.android.cb.view
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-4-7
 */
package com.android.cb.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * @author raiden
 *
 * @Description list view to show ordered items
 */
public class CBOrderedListView extends ListView {

	public CBOrderedListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public CBOrderedListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CBOrderedListView(Context context) {
		super(context);
	}

}
