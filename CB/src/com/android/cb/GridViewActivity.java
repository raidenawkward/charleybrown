/**
 * @Title: GridViewActivity.java
 * @Package: com.android.cb
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-3-29
 */
package com.android.cb;

import com.android.cb.support.CBDish;
import com.android.cb.support.CBId;
import com.android.cb.support.CBMenuEngine;
import com.android.cb.support.CBMenuItem;
import com.android.cb.support.CBMenuItemsSet;
import com.android.cb.support.CBOrder;
import com.android.cb.view.CBButton;
import com.android.cb.view.CBButtonsGroup;
import com.android.cb.view.GridMenuView;
import com.android.cb.view.LaunchingDialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/**
 * @author raiden
 *
 * @Description grid style activity
 */
public class GridViewActivity extends Activity implements CBButtonsGroup.Callback, LaunchingDialog.Callback {

	private CBMenuEngine mMenuEngine;
	private GridMenuView mGridView;
	private CBButtonsGroup mButtonsGruop;
	private LaunchingDialog mLaunchingDialog;

	public GridViewActivity() {
		super();

		initMenuEngine();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		showLaunchingDialog();

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.main_grid_menu_view);

		initButtonGroups();
		initGridMenuView();

		super.onCreate(savedInstanceState);
	}

	private void initMenuEngine() {
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
		mButtonsGruop.setButtonsMargins(5);

		for (int i = 0; i < 6; ++i) {
			CBButton button = new CBButton(mButtonsGruop.getContext(), R.drawable.button_orange, R.drawable.button_gray);
			button.setText("this is button " + i);
			mButtonsGruop.addButton(button);
		}
	}

	private void initGridMenuView() {
		mGridView = (GridMenuView) this.findViewById(R.id.gridMenuView);
		mGridView.setMenuItemSet(mMenuEngine.getMenuSet());
	}

	public boolean onButtonInGroupClicked(int index) {
		switch (index) {
		default:
			break;
		}
//		mGridView.setMenuItemSet(mMenuEngine.getMenuItemsSetWithTag(sCurrentTag));
		return true;
	}

	private void showLaunchingDialog() {
		if (mLaunchingDialog == null)
			mLaunchingDialog = new LaunchingDialog(this);
		mLaunchingDialog.setCallback(this);
		mLaunchingDialog.show();
		mLaunchingDialog.start();
	}

	private static int sTimes = 12;
	public boolean onLaunchingTick() {
		if (--sTimes < 0) {
			return false;
		}

		return true;
	}

	private static String sLaunchingText = "Launching";
	public String getCurrentLaunchingText() {
		sLaunchingText += ".";
		if (sLaunchingText.length() > 12)
			sLaunchingText = "Launching";
		return sLaunchingText;
	}

}
