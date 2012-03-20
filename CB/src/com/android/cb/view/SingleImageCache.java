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

	private float mBitmapWidth = 0;
	private float mBitmapHeight = 0;
	private int mTargetCurrent = 0;

	private class CachedItem {
		public int globalIndex = -1;
		public Bitmap bitmap = null;
		public int priority = MAX_CACHED_SIZE;
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
		return mTargetCurrent;
	}

	public void setSourceSet(CBMenuItemsSet set) {
		mSourceSet = set;
	}

	private int getCachedItemToEliminate() {
		int smallestIndex = 0;
		int smallestValue = MAX_CACHED_SIZE;
		for (int i = 0; i < mCachedItems.size(); ++i) {
			if (mCachedItems.get(i) == null)
				return i;
			if (mCachedItems.get(i).priority < smallestValue)
				smallestIndex = i;
		}

		return smallestIndex;
	}

	private CachedItem getCachedItem(int globalIndex) {
		Log.d("##", "getting " + globalIndex + "/" + mSourceSet.count());

		if (globalIndex < 0 || globalIndex >= mSourceSet.count())
			return null;

		for (int i = 0; i < mCachedItems.size(); ++i) {
			CachedItem item = mCachedItems.get(i);
			if (item == null)
				continue;
			if (item.globalIndex == globalIndex) {
				if (item.priority > 0)
					--item.priority;
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
		if (mSourceSet == null)
			return;

		if (isIndexedItemInCache(globalIndex))
			return;

		CBMenuItem item = mSourceSet.get(globalIndex);
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
		CBMenuItem item = mSourceSet.get(globalIndex);
		if (item == null)
			return null;

		int eIndex = getCachedItemToEliminate();

		Bitmap bitmap = loadBitmap(item.getDish().getPicture());
		if (bitmap == null)
			return null;
		CachedItem cItem = new CachedItem();
		cItem.globalIndex = globalIndex;
		cItem.bitmap = bitmap;
		CachedItem oldOne = mCachedItems.set(eIndex, cItem);
		if (oldOne != null) {
			oldOne.bitmap.recycle();
		}

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

//		BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
//		bitmapOptions.inSampleSize = 4;
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
			if (globalIndex + i < mSourceSet.count()) {
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
		CachedItem item = getCachedItem(mTargetCurrent);
		if (item == null)
			return null;

		return item.bitmap;
	}

	@Override
	public Bitmap getNext() {
		CachedItem item = getCachedItem(mTargetCurrent + 1);
		if (item == null)
			return null;

		return item.bitmap;
	}

	@Override
	public Bitmap getPrev() {
		CachedItem item = getCachedItem(mTargetCurrent - 1);
		if (item == null)
			return null;

		return item.bitmap;
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
//
//		int eIndex = getCachedItemToEliminate();
//
//		Bitmap bitmap = loadBitmap(item.getDish().getPicture());
//		if (bitmap == null)
//			return false;
//		CachedItem cItem = new CachedItem();
//		cItem.globalIndex = index;
//		cItem.bitmap = bitmap;
//		mCachedItems.set(eIndex, cItem);
		if (loadItem(index) != null) {
			cacheAllFromCurrentIndex(index);
			return true;
		}

		return false;
	}

	@Override
	public boolean moveToNext() {
		if (mTargetCurrent + 1 >= mSourceSet.count())
			return false;

		++mTargetCurrent;
		cacheItem(mTargetCurrent + 1);

		return true;
	}

	@Override
	public boolean moveToPrev() {
		if (mTargetCurrent - 1 < 0)
			return false;

		--mTargetCurrent;
		cacheItem(mTargetCurrent - 1);

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
			CachedItem item = mCachedItems.get(i);
			if (item != null) {
				item.bitmap.recycle();
			}
			mCachedItems.set(i, null);
		}
	}

	@Override
	public int getCachedSize() {
		return mCachedItems.size();
	}


}
