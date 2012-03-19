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
import android.util.Log;
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
	SurfaceHolder.Callback, OnGestureListener, android.view.View.OnTouchListener {

	private final int SCROLLING_UNKNOWN = 0;
	private final int SCROLLING_LEFT = 1;
	private final int SCROLLING_RIGHT = 2;
	private int mScrollDirection = SCROLLING_UNKNOWN;

	private SurfaceHolder mSurfaceHolder;
//	private TestMovingRunnable mTestMovingRunnable;
//	private Thread mTestDrawingTrhead;
	private GestureDetector mGuestureDetctor;
	private ArrayList<Bitmap> mPictures;
	private float mSplitLineX = 0;

	private boolean mHasScrolled = false;

	private void initView() {
		mSplitLineX = 0;
		mSurfaceHolder = getHolder();
		mSurfaceHolder.addCallback(this);

		mGuestureDetctor = new GestureDetector(this);
	}

	private Bitmap scaleBitmapToFixView(Bitmap bitmap) {
		if (bitmap == null)
			return null;

		float scaleW = (float)getWidth() / (float)bitmap.getWidth();
		float scaleH = (float)getHeight() / (float)bitmap.getHeight();

		Matrix matrix = new Matrix();
		Log.d("***" , "sw: " + scaleW + ", sh: " + scaleH + ", iw: " + (float)getWidth() + ", ih: " + (float)getHeight());

		matrix.postScale(scaleW, scaleH);

		return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
	}

	public SingleMenuView(Context context) {
		super(context);
		initView();
	}

	public SingleMenuView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
	}

	public SingleMenuView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {

	}

	public void surfaceCreated(SurfaceHolder arg0) {
//		mTestMovingRunnable = new TestMovingRunnable();
//		mTestDrawingTrhead = new Thread(mTestMovingRunnable);
//		mTestDrawingTrhead.start();

		Bitmap map1 = BitmapFactory.decodeResource(getResources(), R.drawable.img0001);
		Bitmap map2 = BitmapFactory.decodeResource(getResources(), R.drawable.img0030);
		mPictures = new ArrayList<Bitmap>();

		mPictures.add(scaleBitmapToFixView(map1));
		mPictures.add(scaleBitmapToFixView(map2));

		mSplitLineX = 0;
		draw2SpitedBitmaps(mSplitLineX,mPictures.get(0),mPictures.get(1));
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

	public boolean onDown(MotionEvent e) {
		return true; // 'true' means a lot ...
	}

	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		return false;
	}

	public void onLongPress(MotionEvent e) {

	}

	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		if (e1.getX() > e2.getX()) {
			mScrollDirection = SCROLLING_RIGHT;
		} else if (e1.getX() < e2.getX()){
			mScrollDirection = SCROLLING_LEFT;
		} else {
			mScrollDirection = SCROLLING_UNKNOWN;
		}

		if (mScrollDirection != SCROLLING_UNKNOWN) {
			mSplitLineX = e2.getX();
			mHasScrolled = draw2SpitedBitmaps(mSplitLineX,mPictures.get(0),mPictures.get(1));
			return mHasScrolled;
		}

		return false;
	}

	public void onShowPress(MotionEvent e) {
		Toast.makeText(this.getContext(), new String("show press"), 0).show();
	}

	public boolean onSingleTapUp(MotionEvent e) {
		Toast.makeText(this.getContext(), new String("singleTapUp"), 0).show();
		return false;
	}

	public boolean onTouch(View v, MotionEvent event) {
		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_UP:
			if (mHasScrolled) {
				finishScroll();
				Toast.makeText(this.getContext(), new String("up after scrolled"), 0).show();
			}
			break;
		}

		return this.mGuestureDetctor.onTouchEvent(event);
	}

	/**
	 * @Description draw 2 bitmaps with position offset separator
	 * @param @param offset x separator position
	 * @param @param pl bitmap in left
	 * @param @param pr bitmap in right
	 * @return boolean if the pictures have been scrolled
	 */
	protected boolean draw2SpitedBitmaps(float offset, Bitmap pl, Bitmap pr) {
		synchronized (mSurfaceHolder) {
			if (pl == null || pr == null)
				return false;
			if (offset < 0 || offset > this.getWidth())
				return false;

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
			return true;
		}
	}

	protected void finishScroll() {
		finishAnimation(mScrollDirection);
		mHasScrolled = false;
		mSplitLineX = 0;
		mScrollDirection = SCROLLING_UNKNOWN;
	}

	protected void finishAnimation(int direction) {
		int speed = 32;
		float accelerate = 32.0f;
		int time = 1;
		float s = 0;
		float start = mSplitLineX;

		switch (direction) {
		case SCROLLING_LEFT:
			while (mSplitLineX < this.getWidth()) {
				s = (speed + time * accelerate / 2) * time;
				mSplitLineX += s;
				draw2SpitedBitmaps(start + s, mPictures.get(0), mPictures.get(1));
				time++;
			}
			draw2SpitedBitmaps(this.getWidth(), mPictures.get(0), mPictures.get(1));
			break;
		case SCROLLING_RIGHT:
			while (mSplitLineX > 0) {
				s = (speed + time * accelerate / 2) * time;
				mSplitLineX -= s;
				draw2SpitedBitmaps(start - s, mPictures.get(0), mPictures.get(1));
				time++;
			}
			draw2SpitedBitmaps(0, mPictures.get(0), mPictures.get(1));
			break;
		default:
				break;
		}

		mSplitLineX = 0;
	}

}
