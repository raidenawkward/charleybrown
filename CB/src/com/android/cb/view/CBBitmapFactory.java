/**
 * @Title: CBBitmapFactory.java
 * @Package: com.android.cb.view
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-3-28
 */
package com.android.cb.view;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import com.android.cb.support.CBMenuItem;
import com.android.cb.support.CBMenuItemsSet;
import com.android.cb.support.CBTagsSet;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * @author raiden
 *
 * @Description bitmap methods class
 */
public class CBBitmapFactory {

	public static Bitmap scaleBitmapToFixView(Bitmap bitmap, float fixWidth, float fixHeight) {
		if (bitmap == null)
			return null;

		float scaleW = fixWidth / (float)bitmap.getWidth();
		float scaleH = fixHeight / (float)bitmap.getHeight();

		Matrix matrix = new Matrix();

		matrix.postScale(scaleW, scaleH);

		return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
	}

	public static Bitmap loadBitmap(String path) {
		if (path == null)
			return null;

		Bitmap bitmap = BitmapFactory.decodeFile(path);
		return bitmap;
	}

	public static Bitmap loadBitmap(Resources resource, int id) {
		if (id <= 0 || resource == null)
			return null;

		return BitmapFactory.decodeResource(resource, id);
	}

	public static Bitmap loadScaledBitmap(String path, float fixWidth, float fixHeight) {
		Bitmap bitmap = loadBitmap(path);
		if (bitmap == null)
			return null;

		return scaleBitmapToFixView(bitmap, fixWidth, fixHeight);
	}

	public static Bitmap loadScaledBitmapFromResourceField(String field, Context context, float fixWidth, float fixHeight) {
		Resources res = context.getResources();
		int id = res.getIdentifier(field, "drawable", context.getPackageName());
		if (id <= 0)
			return null;

		Bitmap bitmap = loadBitmap(res, id);
		if (bitmap == null)
			return null;

		return scaleBitmapToFixView(bitmap, fixWidth, fixHeight);
	}

	public static Drawable loadDrawableFromResourceField(String field, Context context) {
		if (field == null || context == null)
			return null;

		Resources res = context.getResources();
		int id = res.getIdentifier(field, "drawable", context.getPackageName());
		if (id <= 0)
			return null;

		Bitmap bitmap = loadBitmap(res, id);
		if (bitmap == null)
			return null;

		return new BitmapDrawable(bitmap);
	}

	public static Drawable loadDrawableFromLocalUrl(String imageUrl) {
		Options options=new Options();
		options.inSampleSize=2;
		Bitmap bitmap=BitmapFactory.decodeFile(imageUrl, options);

		Drawable drawable=new BitmapDrawable(bitmap);

		return drawable;
	}

	public static Drawable loadDrawableFromNetUrl(String imageUrl) {
		URL url;
		InputStream iStream = null;
		try {
			url = new URL(imageUrl);
			iStream = (InputStream) url.getContent();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Drawable drawable = Drawable.createFromStream(iStream, "src");
		return drawable;
	}

	public static void setMenuItemsSetIcons(CBMenuItemsSet set, Context context) {
		if (set == null || context == null)
			return;

		for (int i = 0; i < set.count(); ++i) {
			setMenuItemIcon(set.get(i), context);
		}
	}

	public static void setMenuItemIcon(CBMenuItem item, Context context) {
		if (item == null || context == null)
			return;

		CBTagsSet tagsSet = item.getDish().getTagsSet();
		item.setIcon(getIconByTags(tagsSet, context));
	}

	public static int getIconByTags(CBTagsSet set, Context context) {
		if (set == null || context == null)
			return -1;

		int res = -1;
		for (int i = 0; i < set.count(); ++i) {
//			String tag = set.get(i);
//			if (context.getResources().getString(R.string.retained_tag_new).equalsIgnoreCase(tag)) {
//				res = R.drawable.icon_new;
//				break;
//			} else if (context.getResources().getString(R.string.retained_tag_hot).equalsIgnoreCase(tag)) {
//				res = R.drawable.icon_hot;
//				break;
//			} else if (context.getResources().getString(R.string.retained_tag_recommend).equalsIgnoreCase(tag)) {
//				res = R.drawable.icon_recommend;
//				break;
//			}
		}

		return res;
	}

}
