package com.android.cb;

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
				Intent intent = new Intent();
				intent.setClass(CBActivity.this, GridViewActivity.class);
				CBActivity.this.startActivity(intent);
			}

		});

//		mLayoutMain = (LinearLayout) findViewById(R.id.mainview);

		// testing for singleview
//		mSingleView = new SingleMenuView(this);
//		mSingleView.setMenuEngine(mMenuEngine);
//		mLayoutMain.addView(mSingleView);
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
//
//	private void testButtonGroup() {
//		CBButtonsGroup buttonGroup = (CBButtonsGroup) this.findViewById(R.id.buttonsGroup);
//
//		for (int i = 0; i < 6; ++i) {
//			CBButton button = new CBButton(buttonGroup.getContext(), R.drawable.button_orange, R.drawable.button_gray);
//			button.setText("this is button " + i + "with a long name");
//			buttonGroup.addButton(button);
//		}
//	}
//
//	private static String sCurrentTag = "odd";
//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		switch(keyCode) {
//		case KeyEvent.KEYCODE_MENU:
//			if (sCurrentTag == "odd")
//				sCurrentTag = "even";
//			else
//				sCurrentTag = "odd";
//			mGridView.setMenuItemSet(mMenuEngine.getMenuItemsSetWithTag(sCurrentTag));
//			break;
//		}
//
//		return super.onKeyDown(keyCode, event);
//	}

}