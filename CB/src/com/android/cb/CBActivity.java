package com.android.cb;

import java.util.ArrayList;

import com.android.cb.source.CBSettings;
import com.android.cb.source.CBValidityChecker;
import com.android.cb.view.CBDialogButton;
import com.android.cb.view.ConfirmDialog;
import com.android.cb.view.OrderPrepareDialog;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class CBActivity extends Activity {

	private CBDialogButton mButtonNewOrder;
	private CBDialogButton mButtonOrdersList;
	private CBDialogButton mButtonQuit;

	private boolean mIsValidDevice = false;
	private Intent mGridViewActivityIntent = null;

	public CBActivity() {
		super();

		CBSettings.load();
	}

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

		mIsValidDevice = CBValidityChecker.isValid(CBActivity.this);

		mGridViewActivityIntent = new Intent();
		mGridViewActivityIntent.setClass(CBActivity.this, GridViewActivity.class);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_manage);

		mButtonNewOrder = (CBDialogButton) this.findViewById(R.id.button_newOrder);
		mButtonNewOrder.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				if (mIsValidDevice == false) {
					showValidateCheckingFailedDialog();
					return;
				}

				showOrderPrepareDialog();
			}
		});

		mButtonOrdersList = (CBDialogButton) this.findViewById(R.id.button_ordersList);
		mButtonOrdersList.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				if (mIsValidDevice == false) {
					showValidateCheckingFailedDialog();
					return;
				}
				openGridViewActivity(null);
			}
		});

		mButtonQuit = (CBDialogButton) this.findViewById(R.id.button_quit);
		mButtonQuit.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				CBSettings.save();
				finish();
			}
		});
    }

	private void openGridViewActivity() {
		if (mGridViewActivityIntent != null)
			CBActivity.this.startActivity(mGridViewActivityIntent);
    }

	private void openGridViewActivity(String orderRecordPath) {
		if (orderRecordPath == null) {
			openGridViewActivity();
			return;
		}

		mGridViewActivityIntent.putExtra(GridViewActivity.INTENT_ORDER_RECORD_PATH, orderRecordPath);

		CBActivity.this.startActivity(mGridViewActivityIntent);
    }

	private void showOrderPrepareDialog() {
		OrderPrepareDialog dialog = new OrderPrepareDialog(this);

		ArrayList<String> locationList = CBSettings.getOrderLocationList();
		if (locationList == null) {
			locationList = new ArrayList<String>();
			for (int i = 0; i < 15; ++i)
				locationList.add("testing location " + i);
		}

		dialog.setCallback(new OrderPrepareDialog.Callback() {

			public void onSubmit() {
				openGridViewActivity();
			}

			public void onSelectedItemChanged(String item) {
				mGridViewActivityIntent.putExtra(GridViewActivity.INTENT_ORDER_LOCATION, item);
			}

			public void onCancel() {

			}
		});

		dialog.setContentList(locationList);
		dialog.setSelectedItem(0);
		dialog.show();
	}

	private void showValidateCheckingFailedDialog() {
		ConfirmDialog dialog = new ConfirmDialog(this);
		dialog.setTitle(R.string.confirm_dialog_title_warning);
		dialog.setMessage(this.getResources().getString(R.string.managing_warning_validate_failed));
		dialog.setCancelButtonText(this.getResources().getString(R.string.confirm_dialog_exit));
		dialog.setCallback(new ConfirmDialog.Callback() {
			public void onConfirm() {

			}

			public void onCancel() {
				CBActivity.this.finish();
			}
		});

		dialog.show();
	}

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