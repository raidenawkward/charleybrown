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
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

/**
 * @author raiden
 *
 * @Description single menu view
 */
public class SingleMenuView extends SurfaceView implements
	SurfaceHolder.Callback,OnGestureListener, android.view.View.OnTouchListener {

	private SurfaceHolder mSurfaceHolder;
	private TestMovingRunnable mTestMovingRunnable;
	private Thread mTestDrawingTrhead;
	private GestureDetector mGuestureDetctor;

	private void initSurfaceHolder() {
		mSurfaceHolder = getHolder();
		mSurfaceHolder.addCallback(this);

		mGuestureDetctor = new GestureDetector(this);
//		 BitmapFactory
	}

	public SingleMenuView(Context context) {
		super(context);
		initSurfaceHolder();
	}

	public SingleMenuView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initSurfaceHolder();
	}

	public SingleMenuView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initSurfaceHolder();
	}

	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
	}

	public void surfaceCreated(SurfaceHolder arg0) {
		mTestMovingRunnable = new TestMovingRunnable();
		mTestDrawingTrhead = new Thread(mTestMovingRunnable);
		mTestDrawingTrhead.start();
	}

	public void surfaceDestroyed(SurfaceHolder arg0) {
		while (true) {
			try {
				mTestDrawingTrhead.join();
				break ;
			}
			catch(Exception ex){

			}
		}
	}

	class TestMovingRunnable implements Runnable {
		private int mX = 40;
		private int mY = 60;
		private int mW = 80;
		private int mH = 80;

		private int mSignX = 1;
		private int mSignY = 1;
		private final int MOVESTEP = 2;

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
						canvas.drawColor(Color.BLACK);

						mX += MOVESTEP * mSignX;
						mY += MOVESTEP * mSignY;
						if (mX < 0 || mX + mW > SingleMenuView.this.getWidth())
							mSignX *= -1;
						if (mY < 0 || mY + mH > SingleMenuView.this.getHeight())
							mSignY *= -1;

						mRect.set(mX, mY, mX + mW, mY + mH);
						mPaint.setColor(Color.BLUE);
						canvas.drawRect(mRect, mPaint);
						onDraw(canvas);
						mSurfaceHolder.unlockCanvasAndPost(canvas);
						Thread.sleep(1);

					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} // sync
			} // while
		}

	}

	public boolean onDown(MotionEvent e) {
		Toast.makeText(this.getContext(), new String("down"), 0).show();
		return true;
	}

	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		return false;
	}

	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean onTouch(View v, MotionEvent event) {
		return this.mGuestureDetctor.onTouchEvent(event);
	}

}
