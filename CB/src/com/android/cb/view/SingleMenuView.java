/**
 * @Title: SingleMenuView.java
 * @Package: com.android.cb.view
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-3-17
 */
package com.android.cb.view;

import java.util.ArrayList;

import com.android.cb.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
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
/**
 * @author huangtao
 *
 */
/**
 * @author huangtao
 *
 */
/**
 * @author huangtao
 *
 */
public class SingleMenuView extends SurfaceView implements
	SurfaceHolder.Callback, OnGestureListener, android.view.View.OnTouchListener, Runnable {

	private SurfaceHolder mSurfaceHolder;
//	private TestMovingRunnable mTestMovingRunnable;
//	private Thread mTestDrawingTrhead;
	private GestureDetector mGuestureDetctor;
	private ArrayList<Bitmap> mPictures;
	private float mSplitLineX = 0;

	private void initSurfaceHolder() {
		mSplitLineX = 0;
		mSurfaceHolder = getHolder();
		mSurfaceHolder.addCallback(this);

		mGuestureDetctor = new GestureDetector(this);


		Bitmap map1 = BitmapFactory.decodeResource(getResources(), R.drawable.img0001);
		Bitmap map2 = BitmapFactory.decodeResource(getResources(), R.drawable.img0001);
		mPictures = new ArrayList<Bitmap>();

		mPictures.add(scaleBitmapToFixView(map1));
		mPictures.add(scaleBitmapToFixView(map2));
	}

	private Bitmap scaleBitmapToFixView(Bitmap bitmap) {
		if (bitmap == null)
			return null;

		float scaleW = getWidth() / bitmap.getWidth();
		float scaleH = getHeight() / bitmap.getHeight();

		Matrix matrix = new Matrix();
		matrix.postScale(scaleW, scaleH);

		return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
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
//		mTestMovingRunnable = new TestMovingRunnable();
//		mTestDrawingTrhead = new Thread(mTestMovingRunnable);
//		mTestDrawingTrhead.start();
		mSplitLineX = 0;
		draw2SpitedBitmap(mSplitLineX,mPictures.get(0),mPictures.get(1));
	}

	public void surfaceDestroyed(SurfaceHolder arg0) {
//		while (true) {
//			try {
//				mTestDrawingTrhead.join();
//				break ;
//			}
//			catch(Exception ex){
//
//			}
//		}
	}

//	class TestMovingRunnable implements Runnable {
//		private int mX = 40;
//		private int mY = 60;
//		private int mW = 80;
//		private int mH = 80;
//
//		private int mSignX = 1;
//		private int mSignY = 1;
//		private final int MOVESTEP = 2;
//
//		private RectF mRect;
//		private Paint mPaint;
//		public TestMovingRunnable() {
//			mRect = new RectF(mX,mY,mW,mH);
//			mPaint = new Paint();
//			mPaint.setColor(Color.BLUE);
//		}
//
//		public void run() {
//			while(true) {
//				synchronized (mSurfaceHolder) {
//					try {
//						Canvas canvas = mSurfaceHolder.lockCanvas();
//						canvas.drawColor(Color.BLACK);
//
//						mX += MOVESTEP * mSignX;
//						mY += MOVESTEP * mSignY;
//						if (mX < 0 || mX + mW > SingleMenuView.this.getWidth())
//							mSignX *= -1;
//						if (mY < 0 || mY + mH > SingleMenuView.this.getHeight())
//							mSignY *= -1;
//
//						mRect.set(mX, mY, mX + mW, mY + mH);
//						mPaint.setColor(Color.BLUE);
//						canvas.drawRect(mRect, mPaint);
//						onDraw(canvas);
//						mSurfaceHolder.unlockCanvasAndPost(canvas);
//						Thread.sleep(1);
//
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				} // sync
//			} // while
//		}
//
//	}

	public boolean onDown(MotionEvent e) {
		return true; // 'true' means a lot ...
	}

	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		Toast.makeText(this.getContext(), new String("fling"), 0).show();
		return false;
	}

	public void onLongPress(MotionEvent e) {

	}

	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		mSplitLineX = e2.getX();
		draw2SpitedBitmap(mSplitLineX,mPictures.get(0),mPictures.get(1));
		return false;
	}

	public void onShowPress(MotionEvent e) {
	}

	public boolean onSingleTapUp(MotionEvent e) {
		Toast.makeText(this.getContext(), new String("singleTapUp"), 0).show();
		return false;
	}

	public boolean onTouch(View v, MotionEvent event) {
		Toast.makeText(this.getContext(), new String("touched"), 0).show();
		return false;
	}

	public void run() {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return this.mGuestureDetctor.onTouchEvent(event);
	}

	protected void draw2SpitedBitmap(float offset, Bitmap pl, Bitmap pr) {
		synchronized (mSurfaceHolder) {
			if (pl == null || pr == null)
				return;
			if (offset < 0 || offset > this.getWidth())
				return;

			Toast.makeText(this.getContext(), new String("p: " + pl.getWidth() + "x" + pl.getHeight() + " s: " + this.getWidth() + "x" + this.getHeight()), 0).show();
			Canvas canvas = mSurfaceHolder.lockCanvas();
			canvas.drawColor(Color.BLACK);

			Paint paint = new Paint();

			int off = (int) offset;

			canvas.drawBitmap(pl,
					new Rect(pl.getWidth() - off, 0, pl.getWidth(), pl.getHeight()),
					new RectF(0, 0, off, this.getHeight()),
					paint);

			canvas.drawBitmap(pr,
					new Rect(0, 0, this.getWidth() - off, pr.getHeight()),
					new RectF(off, 0, this.getWidth(), this.getHeight()),
					paint);

			onDraw(canvas);

			mSurfaceHolder.unlockCanvasAndPost(canvas);
		}
	}

}
