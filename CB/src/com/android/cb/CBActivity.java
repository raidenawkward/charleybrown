package com.android.cb;

import com.android.cb.view.CBValidityChecker;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class CBActivity extends Activity {
//	LinearLayout mLayoutMain;
//	CBMenuEngine mMenuEngine;
//	SingleMenuView mSingleView;
//	GridMenuView mGridView;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Button buttonStart = (Button) this.findViewById(R.id.button_start);
		buttonStart.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View arg0) {
				openGridViewActivity();
			}

		});

		if (CBValidityChecker.isValid(this) == false) {
			buttonStart.setText("Invalid!");
		}

//		mLayoutMain = (LinearLayout) findViewById(R.id.mainview);

		// testing for db
//		testDB();

		// testing for singleview
//		mSingleView = new SingleMenuView(this);
//		mSingleView.setMenuEngine(mMenuEngine);
//		mLayoutMain.addView(mSingleView);
    }

    private void openGridViewActivity() {
		Intent intent = new Intent();
		intent.setClass(CBActivity.this, GridViewActivity.class);
		CBActivity.this.startActivity(intent);
    }

//    private void testingMenuEnginePrepare() {
//		CBMenuItemsSet set = new CBMenuItemsSet();
//		for (int i = 1; i <= 242; ++i) {
//			CBId id = new CBId();
//			id.setId(String.valueOf(i));
//
//			CBDish dish = new CBDish();
//			String image = "/sdcard/image/img" + String.valueOf(i) + ".jpg";
//			Log.d("## ", "loading: " +image);
//			dish.setPicture(image);
//			dish.setThumb(image);
//			dish.setId(id);
//			dish.setName("img" + String.valueOf(i));
//			if (i % 2 == 0) {
//				dish.addTag("even");
//			} else {
//				dish.addTag("odd");
//			}
//
//			CBMenuItem item = new CBMenuItem();
//			item.setIndex(i - 1);
//			item.setDish(dish);
//			set.add(item);
//		}
//
//		CBOrder order = new CBOrder();
//		mMenuEngine = new CBMenuEngine();
//		mMenuEngine.setMenuSet(set);
//		mMenuEngine.setOrder(order);
//	}
//
//    private class TestPathWalker implements CBPathWalker.Callback {
//
//		public boolean onDirDetected(String dir, int depth) {
//			Log.d("dir found", dir + ", depth: " + depth);
//			return true;
//		}
//
//		public boolean onFileDetected(String file, int depth) {
//			Log.d("file found", file + ", depth: " + depth);
//			return true;
//		}
//
//    }
//
//	@SuppressWarnings("unused")
//	private void testPathWalker() {
//		CBPathWalker walker = new CBPathWalker(new TestPathWalker());
//		walker.setRoot("/sdcard");
//		walker.go();
//	}

//	private void testDB() {
//		File dbDir = getDir("db", Context.MODE_PRIVATE);
//		dbDir.mkdirs();
//		File dbFile = new File(dbDir, "cbdb.sqllite");
//		Log.d("-DB-", "DB created on " + dbFile.getAbsolutePath());
//		CBDB db = new CBDB(dbFile.getAbsolutePath());
//
////		db.addMenuItemsToDB(mMenuEngine.getMenuSet());
//
//		CBMenuItemsSet set = db.loadMenuItemsSet();
//		for (int i = 0; i < set.count(); ++i) {
//			Log.d("-DB-", "item loaded: " + set.get(i).getDish().getPicture());
//		}
//
//		db.close();
//	}

}