/**
 * @Title: SingleMenuView.java
 * @Package: com.android.cb.view
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-3-17
 */
package com.android.cb.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * @author raiden
 *
 * @Description single menu view
 */
public class SingleMenuView extends SurfaceView implements SurfaceHolder.Callback {

	private SurfaceHolder mSurfaceHolder;
	private TestMovingRunnable mTestMovingRunnable;
	private Thread mTestDrawingTrhead;

	private void initSurfaceHolder() {
		mSurfaceHolder = getHolder();
		mSurfaceHolder.addCallback(this);
	}

	public SingleMenuView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initSurfaceHolder();
	}

	public SingleMenuView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		initSurfaceHolder();
	}

	public SingleMenuView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initSurfaceHolder();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return mTestMovingRunnable.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return mTestMovingRunnable.onKeyUp(keyCode, event);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
	}

	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
//		new Thread(new TestDrawingThread()).start();
		mTestMovingRunnable = new TestMovingRunnable();
		mTestDrawingTrhead = new Thread(mTestMovingRunnable);
		mTestDrawingTrhead.start();
	}

	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
//		mTestDrawingTrhead.stop();
	}

	class TestDrawingRunnable implements Runnable {  
        public void run() {
            Canvas canvas = mSurfaceHolder.lockCanvas(null);
            Paint mPaint = new Paint();
            mPaint.setColor(Color.BLUE);
            canvas.drawRect(new RectF(40,60,80,80), mPaint);
            mSurfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

	class TestMovingRunnable implements Runnable {
		private int mX = 40;
		private int mY = 60;
		private int mW = 80;
		private int mH = 80;

		private RectF mRect;
		private Paint mPaint;
		public TestMovingRunnable() {
			mRect = new RectF(mX,mY,mW,mH);
			mPaint = new Paint();
			mPaint.setColor(Color.BLUE);
		}

		public void run() {
			while(true) {
				synchronized (mSurfaceHolder) {
					try {
						Canvas canvas = mSurfaceHolder.lockCanvas();
						mX += 5;
						mY += 5;
						mRect.set(mX, mY, mX + mW, mY + mH);
						canvas.drawColor(Color.BLACK);
						canvas.drawRect(mRect, mPaint);
						onDraw(canvas);
						mSurfaceHolder.unlockCanvasAndPost(canvas);
						Thread.sleep(250);
						String msg = new String();
						msg += "# painting : " + mX + "," + mY + "," + mW + "," + mH;
						Log.d("************************* ", msg);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}

		private final int mMoveStep = 30;

		public boolean onKeyDown(int keyCode, KeyEvent event) {
			synchronized (mSurfaceHolder) {
				Log.d("************************* keyCode", String.valueOf(keyCode));
				switch (keyCode) {
				case KeyEvent.KEYCODE_DPAD_UP:
				case KeyEvent.KEYCODE_K:
					mY -= mMoveStep;
					if (mY < 0)
						mY = 0;
					break;
				case KeyEvent.KEYCODE_DPAD_DOWN:
				case KeyEvent.KEYCODE_J:
					mY += mMoveStep;
					break;
				case KeyEvent.KEYCODE_DPAD_LEFT:
				case KeyEvent.KEYCODE_H:
					mX -= mMoveStep;
					if (mX < 0)
						mX = 0;
					break;
				case KeyEvent.KEYCODE_DPAD_RIGHT:
				case KeyEvent.KEYCODE_L:
					mX += mMoveStep;
					break;
				default:
					return false;
				}
			} // synchronized

			return true;
		}

		public boolean onKeyUp(int keyCode, KeyEvent event) {
			return true;
		}


	}

}
