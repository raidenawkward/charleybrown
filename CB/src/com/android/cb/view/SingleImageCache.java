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

	/**
	 * @Description this method must be called after geometry of view changed, to changed
	 * cached images scale accordingly
	 */
	public void updateViewInfo() {
		mBitmapWidth = mView.getWidth();
		mBitmapHeight = mView.getHeight();
		cacheAllFromCurrentIndex(mMenuEngine.getCurrentIndex());
	}

	public int getCurrentIndexInSet() {
		if (mMenuEngine == null)
			return -1;
		return mMenuEngine.getCurrentIndex();
	}

	public void setMenuEngine(CBMenuEngine engine) {
		mMenuEngine = engine;
	}

	private static final int getAbs(int i) {
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
			int distance = getAbs(mCachedItems.get(i).globalIndex - mMenuEngine.getCurrentIndex());
			if (distance >= biggestDistance) {
				biggestIndex = i;
				biggestDistance = distance;
			}
		}

		return biggestIndex;
	}

	private boolean isCacheThreadReady(int cachingPos) {
		for (int i = 0; i < mImageCachingThreadList.size(); ++i) {
			SingleImageCachingThread thread = mImageCachingThreadList.get(i);
			if (thread == null)
				continue;
			if (thread.getCachingPos() == cachingPos) {
				if (thread.isReady())
					return true;
				else
					return false;
			}
		}
		return false;
	}

	private void joinCachingThread(int cachingPos) {
		for (int i = 0; i < mImageCachingThreadList.size(); ++i) {
			SingleImageCachingThread thread = mImageCachingThreadList.get(i);
			if (thread == null)
				continue;
			if (thread.getCachingPos() == cachingPos) {
				try {
					thread.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private CachedItem getCachedItem(int globalIndex) {

		if (globalIndex < 0 || globalIndex >= mMenuEngine.getMenuItemcount())
			return null;

		for (int i = 0; i < mCachedItems.size(); ++i) {
			CachedItem item = mCachedItems.get(i);
			if (item == null)
				continue;
			if (item.globalIndex == globalIndex) {
				if (isCacheThreadReady(i))
					return item;
				else {
					joinCachingThread(i);
					return item;
				}
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

		CBMenuItem item = mMenuEngine.getItem(globalIndex);
		if (item == null)
			return;

		int eIndex = getCachedItemToEliminate();
		SingleImageCachingThread thread = mImageCachingThreadList.get(eIndex);
		if (thread != null) {
			if (!thread.isReady()) {
				// it is needed to stop the running thread, but it looks like bad to use 'stop'
				// leave it here
				//thread.stop();
			}
		}

		thread = new SingleImageCachingThread(eIndex, globalIndex, item.getDish().getPicture());
		thread.start();
		mImageCachingThreadList.set(eIndex, thread);
	}

	private CachedItem loadItem(int globalIndex) {
		CBMenuItem item = mMenuEngine.getItem(globalIndex);
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
					mIsReady = true;
				}
			}
			refreshStatus();
			super.run();
		}

		@SuppressWarnings("unused")
		public synchronized String getPath() {
			return mPath;
		}

		public synchronized boolean isReady() {
			return mIsReady;
		}

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
			if (globalIndex + i < mMenuEngine.getMenuItemcount()) {
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
		Log.d("current", "getting " + mMenuEngine.getCurrentIndex() + "/" + mMenuEngine.getMenuItemcount());
		CachedItem item = getCachedItem(mMenuEngine.getCurrentIndex());
		if (item == null)
			return null;

		return item.bitmap;
	}

	@Override
	public Bitmap getNext() {
		Log.d("next", "getting " + (mMenuEngine.getCurrentIndex() + 1) + "/" + mMenuEngine.getMenuItemcount());
		CachedItem item = getCachedItem(mMenuEngine.getCurrentIndex() + 1);
		if (item == null)
			return null;

		return item.bitmap;
	}

	@Override
	public Bitmap getPrev() {
		Log.d("prev", "getting " + (mMenuEngine.getCurrentIndex() - 1) + "/" + mMenuEngine.getMenuItemcount());
		CachedItem item = getCachedItem(mMenuEngine.getCurrentIndex() - 1);
		if (item == null)
			return null;

		return item.bitmap;
	}

	@Override
	public int getTotalCachedItemCount() {
		if (mMenuEngine == null)
			return 0;
		return mMenuEngine.getMenuItemcount();
	}

	@Override
	public boolean moveTo(int index) {
		CBMenuItem item = mMenuEngine.getItem(index);
		if (item == null)
			return false;

		if (loadItem(index) != null) {
			cacheAllFromCurrentIndex(index);
			return mMenuEngine.gotoPos(index);
		}

		return false;
	}

	@Override
	public boolean moveToNext() {
		Log.d("pos moved","moveToNext");
		if (mMenuEngine.getCurrentIndex() + 1 >= mMenuEngine.getMenuItemcount())
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
