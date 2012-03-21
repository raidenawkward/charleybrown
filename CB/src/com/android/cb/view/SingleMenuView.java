/**
 * @Title: SingleMenuView.java
 * @Package: com.android.cb.view
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-3-17
 */
package com.android.cb.view;

import com.android.cb.support.CBDish;
import com.android.cb.support.CBIFCommonMenuHandler;
import com.android.cb.support.CBIFSingleMenuHandler;
import com.android.cb.support.CBId;
import com.android.cb.support.CBMenuItem;
import com.android.cb.support.CBMenuItemsSet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
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
	SurfaceHolder.Callback, OnGestureListener, android.view.View.OnTouchListener,
	CBIFCommonMenuHandler, CBIFSingleMenuHandler {

	/** for current scrolling directions */
	private final int SCROLLING_UNKNOWN = 0;
	private final int SCROLLING_LEFT = 1;
	private final int SCROLLING_RIGHT = 2;
	private int mScrollDirection = SCROLLING_UNKNOWN;

	/** for paging directions */
	private final int PAGING_UNKNOWN = 0;
	private final int PAGING_PREV = 1;
	private final int PAGING_NEXT = 2;
	private int mPagingDirection = PAGING_UNKNOWN;

	private boolean mHasScrolled = false;
	private SurfaceHolder mSurfaceHolder;
	private GestureDetector mGuestureDetctor;

	private float mSplitLineX = 0;

	/** for data source */
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
	}

	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {

	}

	public void surfaceCreated(SurfaceHolder arg0) {

		mImageCache = new SingleImageCache(this);

		// for testing

		CBMenuItemsSet set = new CBMenuItemsSet();
		for (int i = 1; i < 11; ++i) {
			CBId id = new CBId();
			id.setId(String.valueOf(i));

			CBDish dish = new CBDish();
			String image = "/sdcard/image/img" + String.valueOf(i) + ".jpg";
			Log.d("## ", "loading: " +image);
			dish.setPicture(image);
			dish.setId(id);

			CBMenuItem item = new CBMenuItem();
			item.setDish(dish);
			set.add(item);
		}

		this.setMenuItemsSet(set);


		mImageCache.moveTo(0);
		mSplitLineX = 0;
		draw2SpitedBitmaps(mSplitLineX,mImageCache.getCurrent(),mImageCache.getCurrent());
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
			if (mPagingDirection == PAGING_UNKNOWN) {
				mPagingDirection = PAGING_NEXT;
			}
		} else if (e1.getX() < e2.getX()){
			mScrollDirection = SCROLLING_LEFT;
			if (mPagingDirection == PAGING_UNKNOWN) {
				mPagingDirection = PAGING_PREV;
			}
		} else {
			mScrollDirection = SCROLLING_UNKNOWN;
		}

		if (mScrollDirection != SCROLLING_UNKNOWN) {
			mSplitLineX = e2.getX();

			switch(mScrollDirection) {
			case SCROLLING_RIGHT:
				mHasScrolled = draw2SpitedBitmaps(mSplitLineX, mImageCache.getCurrent(), mImageCache.getNext());
				break;
			case SCROLLING_LEFT:
				mHasScrolled = draw2SpitedBitmaps(mSplitLineX, mImageCache.getPrev(), mImageCache.getCurrent());
				break;
			}
			return mHasScrolled;
		}

		return false;
	}

	public void onShowPress(MotionEvent e) {
//		Toast.makeText(this.getContext(), new String("show press"), 0).show();
	}

	public boolean onSingleTapUp(MotionEvent e) {
		Toast.makeText(this.getContext(), new String("tapping: " + mImageCache.getCurrentIndexInSet()), 0).show();
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
//				Toast.makeText(this.getContext(), new String("up after scrolled"), 0).show();
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
		Log.d("##", "finishScroll, current: " + mImageCache.getCurrentIndexInSet());
		finishAnimation(mScrollDirection);
		mHasScrolled = false;
		mSplitLineX = 0;

		switch (mScrollDirection) {
		case SCROLLING_LEFT:
			gotoPrevItem();
			break;
		case SCROLLING_RIGHT:
			gotoNextItem();
			break;
		}
		mScrollDirection = SCROLLING_UNKNOWN;
		mPagingDirection = PAGING_UNKNOWN;
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
				draw2SpitedBitmaps(start + s, mImageCache.getPrev(), mImageCache.getCurrent());
				time++;
			}
			draw2SpitedBitmaps(this.getWidth(), mImageCache.getPrev(), mImageCache.getCurrent());
			break;
		case SCROLLING_RIGHT:
			while (mSplitLineX > 0) {
				s = (speed + time * accelerate / 2) * time;
				mSplitLineX -= s;
				draw2SpitedBitmaps(start - s,  mImageCache.getCurrent(), mImageCache.getNext());
				time++;
			}
			draw2SpitedBitmaps(0, mImageCache.getCurrent(), mImageCache.getNext());
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
