/**
 * @Title: SingleMenuView.java
 * @Package: com.android.cb.view
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-3-17
 */
package com.android.cb.view;

import java.util.ArrayList;

import com.android.cb.R;
import com.android.cb.support.CBIFCommonMenuHandler;
import com.android.cb.support.CBIFSingleMenuHandler;
import com.android.cb.support.CBMenuItemsSet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
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
public class SingleMenuView extends SurfaceView implements
	SurfaceHolder.Callback, OnGestureListener, android.view.View.OnTouchListener,
	CBIFCommonMenuHandler, CBIFSingleMenuHandler {

	/** for ui */
	private final int SCROLLING_UNKNOWN = 0;
	private final int SCROLLING_LEFT = 1;
	private final int SCROLLING_RIGHT = 2;
	private int mScrollDirection = SCROLLING_UNKNOWN;
	private boolean mHasScrolled = false;
	private SurfaceHolder mSurfaceHolder;
	private GestureDetector mGuestureDetctor;
	private float mSplitLineX = 0;

	/** for data source */
	private ArrayList<Bitmap> mPictures;
	private CBMenuItemsSet mMenuItemSet = null;
	private SingleImageCache mImageCache = null;


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

	private void initView() {
		mSplitLineX = 0;
		mSurfaceHolder = getHolder();
		mSurfaceHolder.addCallback(this);

		mGuestureDetctor = new GestureDetector(this);

		mImageCache = new SingleImageCache(this);
	}

	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {

	}

	public void surfaceCreated(SurfaceHolder arg0) {
		Bitmap map1 = BitmapFactory.decodeResource(getResources(), R.drawable.img0001);
		Bitmap map2 = BitmapFactory.decodeResource(getResources(), R.drawable.img0030);
		mPictures = new ArrayList<Bitmap>();

		mPictures.add(SingleImageCache.scaleBitmapToFixView(map1, getWidth(), getHeight()));
		mPictures.add(SingleImageCache.scaleBitmapToFixView(map2, getWidth(), getHeight()));

		mImageCache.moveTo(0);

		mSplitLineX = 0;
		draw2SpitedBitmaps(mSplitLineX,mPictures.get(0),mPictures.get(1));
	}

	public void surfaceDestroyed(SurfaceHolder arg0) {
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

		switch (mScrollDirection) {
		case SCROLLING_LEFT:
			gotoNextItem();
			break;
		case SCROLLING_RIGHT:
			gotoPrevItem();
			break;
		}
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

	public CBMenuItemsSet getMenuItemsSet() {
		return mMenuItemSet;
	}

	public void setMenuItemsSet(CBMenuItemsSet set) {
		mMenuItemSet = set;
		loadMenuItems();
	}

	public boolean gotoNextItem() {
		if (!mImageCache.moveToNext())
			return false;

		return true;
	}

	public boolean gotoPrevItem() {
		if (!mImageCache.moveToPrev())
			return false;

		return true;
	}

	public void currentItemTouched() {
		// TODO Auto-generated method stub
	}

	public int loadMenuItems() {
		if (mMenuItemSet == null)
			return 0;

		mImageCache.setSourceSet(mMenuItemSet);

		return mMenuItemSet.count();
	}

	public void refresh() {
		// TODO Auto-generated method stub
	}

}
