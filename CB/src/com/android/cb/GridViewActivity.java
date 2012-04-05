/**
 * @Title: GridViewActivity.java
 * @Package: com.android.cb
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-3-29
 */
package com.android.cb;

import com.android.cb.source.CBDishesScanner;
import com.android.cb.support.CBDish;
import com.android.cb.support.CBId;
import com.android.cb.support.CBMenuEngine;
import com.android.cb.support.CBMenuItem;
import com.android.cb.support.CBMenuItemsSet;
import com.android.cb.support.CBOrder;
import com.android.cb.support.CBTagsSet;
import com.android.cb.view.CBButton;
import com.android.cb.view.CBButtonsGroup;
import com.android.cb.view.GridMenuView;
import com.android.cb.view.LaunchingDialog;
import com.android.cb.view.PreviewDialog;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/**
 * @author raiden
 *
 * @Description grid style activity
 */
public class GridViewActivity extends Activity implements CBButtonsGroup.Callback,
	LaunchingDialog.Callback,
	GridMenuView.Callback {

	public static final String DISHES_DIR = "/sdcard/dishes";

	private CBMenuEngine mMenuEngine;
	private GridMenuView mGridView;
	private CBButtonsGroup mButtonsGruop;
	private CBTagsSet mContainedTags;
	private LaunchingDialog mLaunchingDialog = null;
	private boolean mIsInitDone = false;


	public GridViewActivity() {
		super();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.main_grid_menu_view);
		super.onCreate(savedInstanceState);

		InitAsyncTask initTask = new InitAsyncTask();
		initTask.execute((Object)null);
	}

	private void initMenuEngine() {
		CBDishesScanner scanner = new CBDishesScanner(DISHES_DIR);
		CBMenuItemsSet set = scanner.scan();

		CBOrder order = new CBOrder();
		mMenuEngine = new CBMenuEngine();
		mMenuEngine.setMenuSet(set);
		mMenuEngine.setOrder(order);
	}

	@SuppressWarnings("unused")
	private void initTestingMenuEngine() {
		CBMenuItemsSet set = new CBMenuItemsSet();
		for (int i = 1; i <= 242; ++i) {
			CBId id = new CBId();
			id.setId(String.valueOf(i));

			CBDish dish = new CBDish();
			String image = "/sdcard/image/img" + String.valueOf(i) + ".jpg";
			dish.setPicture(image);
			dish.setThumb(image);
			dish.setId(id);
			dish.setName("img" + String.valueOf(i));
			if (i % 2 == 0) {
				dish.addTag("even");
			} else {
				dish.addTag("odd");
			}
			dish.addTag("dish");

			CBMenuItem item = new CBMenuItem();
			item.setDish(dish);
			set.add(item);
		}

		CBOrder order = new CBOrder();
		mMenuEngine = new CBMenuEngine();
		mMenuEngine.setMenuSet(set);
		mMenuEngine.setOrder(order);
	}

	private void initButtonGroups() {
		mButtonsGruop = (CBButtonsGroup) this.findViewById(R.id.buttonsGroup);
		mButtonsGruop.setCallback(this);
		mButtonsGruop.setButtonsMargins(5);

		mContainedTags = mMenuEngine.getContainedTags();
		for (int i = 0; i < mContainedTags.count(); ++i) {
			CBButton button = new CBButton(mButtonsGruop.getContext(), R.drawable.button_orange, R.drawable.button_gray);
			button.setText(mContainedTags.get(i));
			mButtonsGruop.addButton(button);
		}
	}

	private void initGridMenuView() {
		mGridView = (GridMenuView) this.findViewById(R.id.gridMenuView);
		mGridView.setCallback(this);

		if (mContainedTags.count() > 0) {
			mGridView.setMenuItemSet(mMenuEngine.getMenuItemsSetWithTag(mContainedTags.get(0)));
		} else {
			mGridView.setMenuItemSet(mMenuEngine.getMenuSet());
		}
	}

	public void onButtonInGroupClicked(int index) {
		String tagSelected = mContainedTags.get(index);
		mGridView.setMenuItemSet(mMenuEngine.getMenuItemsSetWithTag(tagSelected));
	}

	private void showLaunchingDialog() {
		if (mLaunchingDialog == null)
			mLaunchingDialog = new LaunchingDialog(this);
		mLaunchingDialog.setCallback(this);
		mLaunchingDialog.show();
		mLaunchingDialog.start();
	}

	public boolean onLaunchingTick() {
		return !mIsInitDone;
	}

	private static String sLaunchingText = "Launching";
	public String getCurrentLaunchingText() {
		sLaunchingText += ".";
		if (sLaunchingText.length() > 12)
			sLaunchingText = "Launching";
		return sLaunchingText;
	}

	public void onItemClicked(CBMenuItem item) {
		PreviewDialog dialog = new PreviewDialog(this);
		dialog.setMenuItem(item);
		dialog.show();
	}

	public boolean onItemLongPressed(CBMenuItem item) {
		return false;
	}

	private class InitAsyncTask extends AsyncTask<Object, Integer, Boolean> {

		@Override
		protected Boolean doInBackground(Object... params) {
			// fake delay for engine initializing
//			try {
//				Thread.sleep(5000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}

			initMenuEngine();
			return Boolean.TRUE;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			initButtonGroups();
			initGridMenuView();

			mIsInitDone = true;
			if (mLaunchingDialog != null)
				mLaunchingDialog.dismiss();

			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			showLaunchingDialog();
			super.onPreExecute();
		}

	} // InitAsyncTask

}
