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
import android.util.Log;
import android.view.View;

import com.android.cb.support.CBCache;
import com.android.cb.support.CBMenuEngine;
import com.android.cb.support.CBMenuItem;

/**
 * @author raiden
 *
 */
public class SingleImageCache extends CBCache<Bitmap> {

	private final int MAX_CACHED_SIZE = 5;

	private View mView = null;
	private CBMenuEngine mMenuEngine = null;

	private float mBitmapWidth = 0;
	private float mBitmapHeight = 0;

	private class CachedItem {
		public int globalIndex = -1;
		public Bitmap bitmap = null;
	}

	private ArrayList<CachedItem> mCachedItems = new ArrayList<CachedItem>();
	private ArrayList<SingleImageCachingThread> mImageCachingThreadList = new ArrayList<SingleImageCachingThread>();


	public SingleImageCache(View view) {
		mView = view;
		for (int i = 0; i < MAX_CACHED_SIZE; ++i) {
			mCachedItems.add(null);
			mImageCachingThreadList.add(null);
		}

		mBitmapWidth = mView.getWidth();
		mBitmapHeight = mView.getHeight();
	}

	public int getCurrentIndexInSet() {
		if (mMenuEngine == null)
			return -1;
		return mMenuEngine.currentIndex();
	}

	public void setMenuEngine(CBMenuEngine engine) {
		mMenuEngine = engine;
	}

	private static int getAbs(int i) {
		if (i < 0)
			return i * (-1);
		return i;
	}

	private int getCachedItemToEliminate() {
		int biggestIndex = 0;
		int biggestDistance = -1;
		for (int i = 0; i < mCachedItems.size(); ++i) {
			if (mCachedItems.get(i) == null)
				return i;
			int distance = getAbs(mCachedItems.get(i).globalIndex - mMenuEngine.currentIndex());
			if (distance >= biggestDistance) {
				biggestIndex = i;
				biggestDistance = distance;
			}
		}

		return biggestIndex;
	}

	private CachedItem getCachedItem(int globalIndex) {

		if (globalIndex < 0 || globalIndex >= mMenuEngine.count())
			return null;

		for (int i = 0; i < mCachedItems.size(); ++i) {
			CachedItem item = mCachedItems.get(i);
			if (item == null)
				continue;
			if (item.globalIndex == globalIndex) {
				return item;
			}
		}

		Log.d("##", "not found in cache");
		return loadItem(globalIndex);
	}

	private boolean isIndexedItemInCache(int globalIndex) {
		for (int i = 0; i < mCachedItems.size(); ++i) {
			CachedItem item = mCachedItems.get(i);
			if (item != null) {
				if (item.globalIndex == globalIndex)
					return true;
			}
		}
		return false;
	}

	private void cacheItem(int globalIndex) {
		if (mMenuEngine == null)
			return;

		if (isIndexedItemInCache(globalIndex))
			return;

		CBMenuItem item = mMenuEngine.get(globalIndex);
		if (item == null)
			return;

		int eIndex = getCachedItemToEliminate();
		SingleImageCachingThread thread = mImageCachingThreadList.get(eIndex);
		if (thread != null) {
			if (!thread.isReady())
				mImageCachingThreadList.get(eIndex).stop();
		}

		thread = new SingleImageCachingThread(eIndex, globalIndex, item.getDish().getPicture());
		thread.start();
		mImageCachingThreadList.set(eIndex, thread);
	}

	private CachedItem loadItem(int globalIndex) {
		CBMenuItem item = mMenuEngine.get(globalIndex);
		if (item == null)
			return null;

		int eIndex = getCachedItemToEliminate();

		Bitmap bitmap = loadBitmap(item.getDish().getPicture());
		if (bitmap == null)
			return null;
		CachedItem cItem = new CachedItem();
		cItem.globalIndex = globalIndex;
		cItem.bitmap = bitmap;
		mCachedItems.set(eIndex, cItem);
//		if (oldOne != null) {
//			oldOne.bitmap.recycle();
//		}

		return cItem;
	}

	private class SingleImageCachingThread extends Thread {

		private String mPath = "";
		private int mCachingPos = -1;
		private int mGloabalPos = -1;
		private boolean mIsReady = false;

		public SingleImageCachingThread(int cachingPos, int globalPos, String path) {
			mCachingPos = cachingPos;
			mGloabalPos = globalPos;
			mPath = path;
		}

		@Override
		public void run() {
			Bitmap bitmap = loadBitmap(mPath);
			if (bitmap != null) {
				synchronized(mCachedItems) {
					CachedItem cItem = new CachedItem();
					cItem.globalIndex = mGloabalPos;
					cItem.bitmap = bitmap;
					mCachedItems.set(mCachingPos, cItem);
				}
			}
			refreshStatus();
			mIsReady = true;
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

	private void cacheAllFromCurrentIndex(int globalIndex) {
		cacheItem(globalIndex);
		for (int i = 0; i < this.mCachedItems.size() / 2; ++i) {
			if (globalIndex + i < mMenuEngine.count()) {
				cacheItem(globalIndex + i);
			}

			if (globalIndex - i >= 0) {
				cacheItem(globalIndex - i);
			}
		}

	}

	private void stopAllCachingThreads() {
		for (int i = 0; i < this.mImageCachingThreadList.size(); ++i) {
			SingleImageCachingThread thread = mImageCachingThreadList.get(i);
			if (thread != null) {
				thread.stop();
				mImageCachingThreadList.set(i, null);
			}
		}
	}

	private void refreshStatus() {
		for (int i = 0; i < mImageCachingThreadList.size(); ++i) {
			SingleImageCachingThread thread = mImageCachingThreadList.get(i);
			if (thread != null) {
				if (!thread.isReady()) {
					mStatus = CBCACHE_STATUS_LOADING;
					return;
				}
			} else {
				mStatus = CBCACHE_STATUS_UNKNOWN;
				return;
			}
		}

		mStatus = CBCACHE_STATUS_READY;
	}

	@Override
	public Bitmap getCurrent() {
		Log.d("current", "getting " + mMenuEngine.currentIndex() + "/" + mMenuEngine.count());
		CachedItem item = getCachedItem(mMenuEngine.currentIndex());
		if (item == null)
			return null;

		return item.bitmap;
	}

	@Override
	public Bitmap getNext() {
		Log.d("next", "getting " + (mMenuEngine.currentIndex() + 1) + "/" + mMenuEngine.count());
		CachedItem item = getCachedItem(mMenuEngine.currentIndex() + 1);
		if (item == null)
			return null;

		return item.bitmap;
	}

	@Override
	public Bitmap getPrev() {
		Log.d("prev", "getting " + (mMenuEngine.currentIndex() - 1) + "/" + mMenuEngine.count());
		CachedItem item = getCachedItem(mMenuEngine.currentIndex() - 1);
		if (item == null)
			return null;

		return item.bitmap;
	}

	@Override
	public int getTotalCachedItemCount() {
		if (mMenuEngine == null)
			return 0;
		return mMenuEngine.count();
	}

	@Override
	public boolean moveTo(int index) {
		CBMenuItem item = mMenuEngine.get(index);
		if (item == null)
			return false;

		if (loadItem(index) != null) {
			cacheAllFromCurrentIndex(index);
			return mMenuEngine.goTo(index);
		}

		return false;
	}

	@Override
	public boolean moveToNext() {
		Log.d("pos moved","moveToNext");
		if (mMenuEngine.currentIndex() + 1 >= mMenuEngine.count())
			return false;

		mMenuEngine.gotoNext();
		cacheItem(mMenuEngine.getCurrentIndex() + 1);

		return true;
	}

	@Override
	public boolean moveToPrev() {
		Log.d("pos moved","moveToPrev");
		if (mMenuEngine.getCurrentIndex() - 1 < 0)
			return false;

		mMenuEngine.gotoPrev();
		cacheItem(mMenuEngine.getCurrentIndex() - 1);

		return true;
	}

	@Override
	public int getMaxCacheSize() {
		return MAX_CACHED_SIZE;
	}

	@Override
	public void clear() {
		stopAllCachingThreads();

		for (int i = 0; i < mCachedItems.size(); ++i) {
//			CachedItem item = mCachedItems.get(i);
//			if (item != null) {
//				item.bitmap.recycle();
//			}
			mCachedItems.set(i, null);
		}
	}

	@Override
	public int getCachedSize() {
		return mCachedItems.size();
	}


}
