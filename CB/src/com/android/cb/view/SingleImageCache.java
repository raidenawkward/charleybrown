/**
 * @Title: SingleMenuView.java
 * @Package: com.android.cb.view
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-3-20
 */
package com.android.cb.view;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.view.View;

import com.android.cb.support.CBCache;
import com.android.cb.support.CBMenuItem;
import com.android.cb.support.CBMenuItemsSet;

/**
 * @author raiden
 *
 */
public class SingleImageCache extends CBCache<Bitmap> {

	private final int MAX_CACHED_SIZE = 5;

	private View mView = null;
	private CBMenuItemsSet mSourceSet = null;
	private ArrayList<CachingThread> mCachingThreadList = new ArrayList<CachingThread>();

	private float mBitmapWidth = 0;
	private float mBitmapHeight = 0;
	private int mTargetCurrent = 0;

	public SingleImageCache(View view) {
		mView = view;
		for (int i = 0; i < MAX_CACHED_SIZE; ++i) {
			mList.add(null);
			mCachingThreadList.add(null);
		}

		pointToMiddleCachedItem();

		mBitmapWidth = mView.getWidth();
		mBitmapHeight = mView.getHeight();
	}

	private void pointToMiddleCachedItem() {
		mCurrent = getMaxCacheSize() % 2 == 0? getMaxCacheSize() / 2 : getMaxCacheSize() / 2 + 1;
	}

	public void setSourceSet(CBMenuItemsSet set) {
		mSourceSet = set;
	}

	public static Bitmap scaleBitmapToFixView(Bitmap bitmap, float fixWidth, float fixHeight) {
		if (bitmap == null)
			return null;

		float scaleW = fixWidth / (float)bitmap.getWidth();
		float scaleH = fixHeight / (float)bitmap.getHeight();

		Matrix matrix = new Matrix();

		matrix.postScale(scaleW, scaleH);

		return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
	}

	private Bitmap loadBitmap(String path) {
		if (path == null)
			return null;

		Bitmap bitmap = scaleBitmapToFixView(BitmapFactory.decodeFile(path), mBitmapWidth, mBitmapHeight);
		return bitmap;
	}

	@SuppressWarnings("unused")
	private Bitmap loadBitmap(int resourceId) {
		Bitmap bitmap = scaleBitmapToFixView(BitmapFactory.decodeResource(mView.getResources(), resourceId), mBitmapWidth, mBitmapHeight);
		return bitmap;
	}

	private void cacheAllFromCurrentIndex(int index) {
		for (int i = mCurrent + 1, j = 0; i < mList.size(); ++i, ++j) {
			cacheItem(i, index + j);
		}

		for (int i = mCurrent - 1, j = 0; i >= 0; --i, ++j) {
			cacheItem(i, index - j);
		}
	}

	private boolean cacheItem(int cachePos, int targetPos) {
		if (mSourceSet == null)
			return false;
		if (targetPos < 0 || targetPos > mSourceSet.count())
			return false;
		if (cachePos < 0 || cachePos >= mList.size())
			return false;

		CachingThread thread = mCachingThreadList.get(cachePos);
		if (thread != null)
			thread.stop();

		thread = new CachingThread(cachePos, mSourceSet.get(targetPos).getDish().getPicture());
		thread.start();
		mCachingThreadList.set(cachePos, thread);

		return true;
	}

	private void stopAllCachingThreads() {
		for (int i = 0; i < mCachingThreadList.size(); ++i) {
			CachingThread thread = mCachingThreadList.get(i);
			if (thread != null) {
				thread.stop();
				mCachingThreadList.set(i, null);
			}
		}
	}

	private class CachingThread extends Thread {

		private String mPath = "";
		private int mCachingPos = -1;
		private boolean mIsReady = false;

		public CachingThread(int cachingPos, String path) {
			mCachingPos = cachingPos;
			mPath = path;
		}

		@Override
		public void run() {
			Bitmap bitmap = loadBitmap(mPath);
			if (bitmap != null) {
				synchronized(mList) {
					mList.set(mCachingPos, bitmap);
					mIsReady = true;
				}
			}

			super.run();
		}

		@SuppressWarnings("unused")
		public synchronized String getPath() {
			return mPath;
		}

		public synchronized boolean isReady() {
			return mIsReady;
		}

		@SuppressWarnings("unused")
		public synchronized int getCachingPos() {
			return mCachingPos;
		}

	}

	@Override
	public Bitmap getCurrent() {
		return mList.get(mCurrent);
	}

	@Override
	public Bitmap getNext() {
		if (mCurrent + 1 >= 0)
			return null;
		if (mTargetCurrent + 1 >= mSourceSet.count())
			return null;

		CachingThread cachingThread = mCachingThreadList.get(mCurrent + 1);
		if (!cachingThread.isReady()) {
			try {
				cachingThread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		return mList.get(mCurrent + 1);
	}

	@Override
	public Bitmap getPrev() {
		if (mCurrent - 1 < 0)
			return null;
		if (mTargetCurrent - 1 < 0)
			return null;

		CachingThread cachingThread = mCachingThreadList.get(mCurrent - 1);
		if (!cachingThread.isReady()) {
			try {
				cachingThread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		return mList.get(mCurrent - 1);
	}

	@Override
	public int getTotalCachedItemCount() {
		if (mSourceSet == null)
			return 0;
		return mSourceSet.count();
	}

	@Override
	public boolean moveTo(int index) {
		CBMenuItem item = mSourceSet.get(index);
		if (item == null)
			return false;

		clear();

		mTargetCurrent = index;
		mList.set(mCurrent, loadBitmap(item.getDish().getPicture()));

		cacheAllFromCurrentIndex(index);

		return false;
	}

	@Override
	public boolean moveToNext() {
		if (mTargetCurrent + 1 >= mSourceSet.count())
			return false;

		mList.add(null);
		mList.remove(0);
		pointToMiddleCachedItem();

		cacheItem(mList.size() - 1, ++mTargetCurrent);

		return true;
	}

	@Override
	public boolean moveToPrev() {
		if (mTargetCurrent - 1 < 0)
			return false;

		mList.add(0,null);
		mList.remove(mList.size() - 1);
		pointToMiddleCachedItem();

		cacheItem(mList.size() - 1, --mTargetCurrent);

		return true;
	}

	@Override
	public int getMaxCacheSize() {
		return MAX_CACHED_SIZE;
	}

	@Override
	public void clear() {
		stopAllCachingThreads();

		for (int i = 0; i < mList.size(); ++i) {
			mList.set(i, null);
		}

		pointToMiddleCachedItem();
	}


}
