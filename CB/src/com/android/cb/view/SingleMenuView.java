/**
 * @Title: SingleMenuView.java
 * @Package: com.android.cb.view
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-3-17
 */
package com.android.cb.view;

import com.android.cb.support.CBIFCommonMenuHandler;
import com.android.cb.support.CBIFSingleMenuHandler;
import com.android.cb.support.CBMenuEngine;
import com.android.cb.support.CBMenuItem;

import android.content.Context;
import android.graphics.Bitmap;
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

	private boolean mPageTurningEnabled = true;

	/** for paging directions */
	private final int PAGING_UNKNOWN = 0;
	private final int PAGING_PREV = 1;
	private final int PAGING_NEXT = 2;
	private int mPagingDirection = PAGING_UNKNOWN;

	/** for gestures */
	private boolean mHasScrolled = false;
	private SurfaceHolder mSurfaceHolder;
	private GestureDetector mGuestureDetctor;

	/** for paging animations */
	private float mSplitLineX = 0;
	private final float PAGING_ACCELERATE = 32.0f;

	/** for data source */
	private CBMenuEngine mMenuEngine = null;
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
		if (this.mPageTurningEnabled)
			mImageCache.updateViewInfo();
	}

	public void surfaceCreated(SurfaceHolder arg0) {
		if (this.mPageTurningEnabled) {
			mImageCache.updateViewInfo();
			mSplitLineX = 0;

			drawBitmap(mImageCache.getCurrent());
		} else {
			drawBitmap(CBBitmapFactory.loadBitmap(mMenuEngine.getCurrentItem().getDish().getPicture()));
		}
	}

	public void surfaceDestroyed(SurfaceHolder arg0) {
		if (this.mPageTurningEnabled) {
			mImageCache.clear();
		}
	}

	public boolean onDown(MotionEvent e) {
		return mPageTurningEnabled;
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
			if (mPagingDirection == PAGING_UNKNOWN) {
				mPagingDirection = PAGING_NEXT;
			}
		} else if (e1.getX() < e2.getX()){
			if (mPagingDirection == PAGING_UNKNOWN) {
				mPagingDirection = PAGING_PREV;
			}
		} else {
			mPagingDirection = PAGING_UNKNOWN;
		}

		if (mPagingDirection != PAGING_UNKNOWN) {
			mSplitLineX = e2.getX();
			Bitmap pl = null;
			Bitmap pr = null;

			switch(mPagingDirection) {
			case PAGING_NEXT:
				pl = mImageCache.getCurrent();
				pr = mImageCache.getNext();
				break;
			case PAGING_PREV:
				pl = mImageCache.getPrev();
				pr = mImageCache.getCurrent();
				break;
			}

			if (pl != null && pr != null) {
				mHasScrolled = draw2SpitedBitmaps(mSplitLineX, pl, pr);
				return mHasScrolled;
			}
		}

		mSplitLineX = 0;
		mHasScrolled = false;
		mPagingDirection = PAGING_UNKNOWN;

		return false;
	}

	public void onShowPress(MotionEvent e) {
//		Toast.makeText(this.getContext(), new String("show press"), 0).show();
	}

	public boolean onSingleTapUp(MotionEvent e) {
		currentItemTouched();
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
			}
			break;
		}

		return this.mGuestureDetctor.onTouchEvent(event);
	}

	/**
	 * @Description draw one bitmap to view
	 * @param @param bitmap
	 * @return boolean
	 */
	protected boolean drawBitmap(Bitmap bitmap) {
		synchronized (mSurfaceHolder) {
			Canvas canvas = mSurfaceHolder.lockCanvas();
			canvas.drawColor(Color.BLACK);

			if (bitmap == null)
				return false;

			Paint paint = new Paint();
			canvas.drawBitmap(bitmap,
						new Rect(0,0,bitmap.getWidth(),bitmap.getHeight()),
						new Rect(0,0,this.getWidth(),this.getHeight()), paint);

			onDraw(canvas);
			mSurfaceHolder.unlockCanvasAndPost(canvas);
		}

		return true;
	}
	/**
	 * @Description draw 2 bitmaps with position offset separator
	 * @param offset x separator position
	 * @param pl bitmap in left
	 * @param pr bitmap in right
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
		doPagingAnimation(mSplitLineX, mPagingDirection, PAGING_ACCELERATE);

		mHasScrolled = false;
		mSplitLineX = 0;

		switch (mPagingDirection) {
		case PAGING_PREV:
			mImageCache.moveToPrev();
			break;
		case PAGING_NEXT:
			mImageCache.moveToNext();
			break;
		}

		mPagingDirection = PAGING_UNKNOWN;
	}

	/**
	 * @Description animation page on given direction from splited position
	 * @param splitX page split position
	 * @param pagingDirection could be PAGING_PREV, PAGING_PREV, PAGING_UNKNOWN
	 * @param accelerate pages move acceleration
	 * @return boolean return false if null bitmap gotten from image cache
	 */
	protected boolean doPagingAnimation(float splitX, int pagingDirection, float accelerate) {
		int speed = 32;
		int time = 1;
		float distance = 0;
		float start = splitX;
		float splitLineX = splitX;

		Bitmap pl = null;
		Bitmap pr = null;

		switch (pagingDirection) {
		case PAGING_PREV:
			pl = mImageCache.getPrev();
			pr = mImageCache.getCurrent();
			if (pl == null || pr == null)
				return false;

			while (splitLineX < this.getWidth()) {
				distance = (speed + time * accelerate / 2) * time;
				splitLineX += distance;
				draw2SpitedBitmaps(start + distance, pl, pr);
				time++;
			}
			draw2SpitedBitmaps(this.getWidth(), pl, pr);
			break;
		case PAGING_NEXT:
			pl = mImageCache.getCurrent();
			pr = mImageCache.getNext();
			if (pl == null || pr == null)
				return false;

			while (splitLineX > 0) {
				distance = (speed + time * accelerate / 2) * time;
				splitLineX -= distance;
				draw2SpitedBitmaps(start - distance,  pl, pr);
				time++;
			}
			draw2SpitedBitmaps(0, pl, pr);
			break;
		case PAGING_UNKNOWN:
		default:
				return false;
		}

		return true;
	}

	public boolean isPageTurningEnabled() {
		return mPageTurningEnabled;
	}

	public void setPageTurningEnabled(boolean enabled) {
		mPageTurningEnabled = enabled;

		if (this.mPageTurningEnabled) {
			mImageCache.updateViewInfo();
			mSplitLineX = 0;
		}
	}

	public CBMenuEngine getMenuEngine() {
		return mMenuEngine;
	}

	public void setMenuEngine(CBMenuEngine engine) {
		mMenuEngine = engine;
		loadMenuItems();
	}

	public boolean gotoNextItem() {
		if (mPageTurningEnabled == false)
			return false;

		boolean res = doPagingAnimation(getWidth() / 2, PAGING_NEXT, PAGING_ACCELERATE);
		if (res)
			mImageCache.moveToNext();

		return res;
	}

	public boolean gotoPrevItem() {
		if (mPageTurningEnabled == false)
			return false;

		boolean res = doPagingAnimation(getWidth() / 2, PAGING_PREV, PAGING_ACCELERATE);
		if (res)
			mImageCache.moveToPrev();
		return res;
	}

	public boolean gotoItem(int index) {
		if (mPageTurningEnabled) {
			return mImageCache.moveTo(index);
		} else {
			return mMenuEngine.gotoPos(index);
		}
	}

	public boolean gotoItem(CBMenuItem item) {
		if (item == null)
			return false;

		return gotoItem(mMenuEngine.getMenuItemIndex(item));
	}

	public void currentItemTouched() {
		String start = "";
		if (mMenuEngine.isCurrentItemChecked()) {
			start = "Disordered: ";
			mMenuEngine.disOrderCurrentItem();
		} else {
			start = "Ordered: ";
			mMenuEngine.orderCurrentItem(1);
		}
		Toast.makeText(this.getContext(), new String(start + mMenuEngine.getCurrentIndex()
				+ ", total ordered count: " + mMenuEngine.getTotalItemCheckedCount()), 0).show();
	}

	public int loadMenuItems() {
		if (mMenuEngine == null)
			return 0;

		mImageCache.setMenuEngine(mMenuEngine);

		return mMenuEngine.getMenuItemcount();
	}

	public void refresh() {
		mMenuEngine.gotoFirst();
	}

}
