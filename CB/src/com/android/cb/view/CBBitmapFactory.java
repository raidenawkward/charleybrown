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

	public static Bitmap loadScaledBitmap(String path, float fixWidth, float fixHeight) {
		Bitmap bitmap = loadBitmap(path);
		if (bitmap == null)
			return null;

		return scaleBitmapToFixView(bitmap, fixWidth, fixHeight);
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

}
