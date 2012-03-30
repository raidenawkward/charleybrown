/**
 * @Title: LaunchingDialog.java
 * @Package: com.android.cb.view
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-3-30
 */
package com.android.cb.view;

import com.android.cb.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.widget.TextView;

/**
 * @author raiden
 *
 * @Description launching dialog
 */
public class LaunchingDialog extends Dialog {

	/**
	 * @Description will be called every tick
	 */
	public interface Callback {
		public boolean onLaunchingTick();
		public String getCurrentLaunchingText();
	}

	private final int ACTION_TICKING = 0;
	private final int ACTION_DISMISS = 1;

	private String mText = "Launching";
	private Callback mCallback = null;
	private TextView mTextView;
	private ProgressThread mThread;
	private Handler mHandler;

	public LaunchingDialog(Context context, int theme) {
		super(context, theme);
	}

	public LaunchingDialog(Context context) {
		super(context, R.style.CBLaunchingDialog);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.dialog_launching);
		mTextView = (TextView) this.findViewById(R.id.textView1);

		mThread = new ProgressThread();

		mHandler = new Handler() {
			public void handleMessage(Message msg) {
				handlePrograssing(msg.what);
				super.handleMessage(msg);
			}
		};
	}

	protected void handlePrograssing(int action) {
		if (mCallback == null)
			return;

		switch (action) {
		case ACTION_TICKING:
			mText = mCallback.getCurrentLaunchingText();
			if (mText != null)
				mTextView.setText(mText);
			break;
		case ACTION_DISMISS:
			this.dismiss();
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void start() {
		if (mCallback != null)
			mThread.start();
	}

	public Callback getCallback() {
		return mCallback;
	}

	public void setCallback(Callback callback) {
		this.mCallback = callback;
	}

	public String getText() {
		return mText;
	}

	public void setText(String text) {
		this.mText = text;
	}

	private class ProgressThread extends Thread {
		@Override
		public void run() {
			try {
				while(mCallback.onLaunchingTick()) {
					Thread.sleep(500);
					Message message = new Message();
					message.what = ACTION_TICKING;
					mHandler.sendMessage(message);
				}

				Message message = new Message();
				message.what = ACTION_DISMISS;
				mHandler.sendMessage(message);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			super.run();
		}
	}

}
