/**
 * @Title: SingleMenuView.java
 * @Package: com.android.cb.view
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-3-20
 */
package com.android.cb.view;

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
	private float mBitmapWidth = 0;
	private float mBitmapHeight = 0;

	public SingleImageCache(View view) {
		mView = view;
		for (int i = 0; i < MAX_CACHED_SIZE; ++i) {
			mList.add(null);
		}

		pointToMiddleCachedItem();

		mBitmapWidth = mView.getWidth();
		mBitmapHeight = mView.getHeight();
	}

	protected void pointToMiddleCachedItem() {
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

	private Bitmap loadBitmap(int resourceId) {
		Bitmap bitmap = scaleBitmapToFixView(BitmapFactory.decodeResource(mView.getResources(), resourceId), mBitmapWidth, mBitmapHeight);
		return bitmap;
	}

	protected void cacheFromCurrent() {
		
	}

	@Override
	public Bitmap getCurrent() {
		return mList.get(mCurrent);
	}

	@Override
	public Bitmap getNext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bitmap getPrev() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalCachedItemCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean moveTo(int index) {
		CBMenuItem item = mSourceSet.get(index);
		if (item == null)
			return false;

		clear();
		mList.set(mCurrent, loadBitmap(item.getDish().getPicture()));

		cacheFromCurrent();

		return false;
	}

	@Override
	public boolean moveToNext() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean moveToPrev() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getMaxCacheSize() {
		return MAX_CACHED_SIZE;
	}

	@Override
	public void clear() {
		for (int i = 0; i < mList.size(); ++i) {
			mList.set(i, null);
		}

		pointToMiddleCachedItem();
	}


}
