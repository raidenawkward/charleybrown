package com.android.cb;

import java.io.File;

import com.android.cb.source.CBDB;
import com.android.cb.source.CBPathWalker;
import com.android.cb.support.CBDish;
import com.android.cb.support.CBId;
import com.android.cb.support.CBMenuEngine;
import com.android.cb.support.CBMenuItem;
import com.android.cb.support.CBMenuItemsSet;
import com.android.cb.support.CBOrder;
import com.android.cb.view.GridMenuView;
import com.android.cb.view.SingleMenuView;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class CBActivity extends Activity {
	LinearLayout mLayoutMain;
	CBMenuEngine mMenuEngine;
	SingleMenuView mSingleView;
	GridMenuView mGridView;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		testingMenuEnginePrepare();

		mLayoutMain = (LinearLayout) findViewById(R.id.mainview);

		// testing for db
		testDB();

		// testing for singleview
//		mSingleView = new SingleMenuView(this);
//		mSingleView.setMenuEngine(mMenuEngine);
//		mLayoutMain.addView(mSingleView);

		// testing for gridview
		mGridView = new GridMenuView(this);
		mGridView.setMenuItemSet(mMenuEngine.getMenuSet());
		mGridView.setMenuItemSet(mMenuEngine.getMenuItemsSetWithTag(sCurrentTag));
		mLayoutMain.addView(mGridView);
    }

    private void testingMenuEnginePrepare() {
		CBMenuItemsSet set = new CBMenuItemsSet();
		for (int i = 1; i <= 242; ++i) {
			CBId id = new CBId();
			id.setId(String.valueOf(i));

			CBDish dish = new CBDish();
			String image = "/sdcard/image/img" + String.valueOf(i) + ".jpg";
			Log.d("## ", "loading: " +image);
			dish.setPicture(image);
			dish.setThumb(image);
			dish.setId(id);
			dish.setName("img" + String.valueOf(i));
			if (i % 2 == 0) {
				dish.addTag("even");
			} else {
				dish.addTag("odd");
			}

			CBMenuItem item = new CBMenuItem();
			item.setIndex(i - 1);
			item.setDish(dish);
			set.add(item);
		}

		CBOrder order = new CBOrder();
		mMenuEngine = new CBMenuEngine();
		mMenuEngine.setMenuSet(set);
		mMenuEngine.setOrder(order);
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

	private static String sCurrentTag = "odd";
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch(keyCode) {
		case KeyEvent.KEYCODE_MENU:
			if (sCurrentTag == "odd")
				sCurrentTag = "even";
			else
				sCurrentTag = "odd";
			mGridView.setMenuItemSet(mMenuEngine.getMenuItemsSetWithTag(sCurrentTag));
			break;
		}

		return super.onKeyDown(keyCode, event);
	}

	private void testDB() {
		File dbDir = getDir("db", Context.MODE_PRIVATE);
		dbDir.mkdirs();
		File dbFile = new File(dbDir, "cbdb.sqllite");
		Log.d("-DB-", "DB created on " + dbFile.getAbsolutePath());
		CBDB db = new CBDB(dbFile.getAbsolutePath());

		db.close();
	}

}