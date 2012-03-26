package com.android.cb;

import com.android.cb.source.CBPathWalker;
import com.android.cb.support.CBDish;
import com.android.cb.support.CBId;
import com.android.cb.support.CBMenuEngine;
import com.android.cb.support.CBMenuItem;
import com.android.cb.support.CBMenuItemsSet;
import com.android.cb.support.CBOrder;
import com.android.cb.view.SingleMenuView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class CBActivity extends Activity {
	LinearLayout mLayoutMain;
	CBMenuEngine mMenuEngine;
	SingleMenuView mSingleView;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// testing for singleview
		mLayoutMain = (LinearLayout) findViewById(R.id.mainview);
		mSingleView = new SingleMenuView(this);
		testingSingleViewPrepare();
		mLayoutMain.addView(mSingleView);
    }

    private void testingSingleViewPrepare() {
		CBMenuItemsSet set = new CBMenuItemsSet();
		for (int i = 1; i < 11; ++i) {
			CBId id = new CBId();
			id.setId(String.valueOf(i));

			CBDish dish = new CBDish();
			String image = "/sdcard/image/img" + String.valueOf(i) + ".jpg";
			Log.d("## ", "loading: " +image);
			dish.setPicture(image);
			dish.setId(id);

			CBMenuItem item = new CBMenuItem();
			item.setDish(dish);
			set.add(item);
		}

		CBOrder order = new CBOrder();
		mMenuEngine = new CBMenuEngine();
		mMenuEngine.setMenuSet(set);
		mMenuEngine.setOrder(order);

		mSingleView.setMenuEngine(mMenuEngine);
	}

    private class TestPathWalker implements CBPathWalker.Callback {

		public boolean onDirDetected(String dir, int depth) {
			Log.d("dir found", dir + ", depth: " + depth);
			return true;
		}

		public boolean onFileDetected(String file, int depth) {
			Log.d("file found", file + ", depth: " + depth);
			return true;
		}

    }

	@SuppressWarnings("unused")
	private void testPathWalker() {
		CBPathWalker walker = new CBPathWalker(new TestPathWalker());
		walker.setRoot("/sdcard");
		walker.go();
	}

}