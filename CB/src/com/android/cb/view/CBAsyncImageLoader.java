/**
 * @Title: CBAsyncImageLoader.java
 * @Package: com.android.cb.view
 * @Author: Raiden Awkward<raiden.ht@gmail.com>
 * @Date: 2012-3-26
 */
package com.android.cb.view;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * @author raiden
 *
 * @Description async image loader, used in image adapter
 */
public class CBAsyncImageLoader {

	/**
	 * @Description callback used in loadDrawable
	 */
	public interface Callback {
		public void onImageLoaded(Drawable imageDrawable, String imageUrl);
    }

	private HashMap<String, SoftReference<Drawable>> mImagesMap = new HashMap<String, SoftReference<Drawable>>();
//	private HashMap<String, Drawable> mImagesMap = new HashMap<String, Drawable>();
	private ArrayList<String> mImagePathList = null;

	/**
	 * cache while loading
	 */
	public CBAsyncImageLoader() {

	}

	/**
	 * @param imagePathList
	 * cache before loading
	 */
	public CBAsyncImageLoader(ArrayList<String> imagePathList) {
		setImagePathList(imagePathList);

		if (mImagePathList == null)
			return;

		new Thread() {
            @Override
            public void run() {
			for (int i = 0; i < mImagePathList.size(); ++i) {
				String path = mImagePathList.get(i);
				Drawable drawable = loadDrawableFromLocalUrl(path);
				mImagesMap.put(path, new SoftReference<Drawable>(drawable));
//				mImagesMap.put(path, drawable);
				}
			}
        }.start();
	}

	public void setImagePathList(ArrayList<String> imagePathList) {
		this.mImagePathList = imagePathList;
	}

	public ArrayList<String> getImagePathList() {
		return mImagePathList;
	}

	public Drawable loadDrawable(final String imageUrl, final Callback callback) {
		if (mImagesMap.containsKey(imageUrl)) {
            SoftReference<Drawable> softReference = mImagesMap.get(imageUrl);
            Drawable drawable = softReference.get();
//			Drawable drawable = mImagesMap.get(imageUrl);
            if (drawable != null) {
                return drawable;
            }
            Log.d("==-==", "empty: " + imageUrl);
        }

		Log.d("=====", "not found: " + imageUrl);
		final Handler handler = new Handler() {
            public void handleMessage(Message message) {
				if (callback != null)
					callback.onImageLoaded((Drawable) message.obj, imageUrl);
            }
        };

        new Thread() {
            @Override
            public void run() {
                Drawable drawable = loadDrawableFromLocalUrl(imageUrl);
                mImagesMap.put(imageUrl, new SoftReference<Drawable>(drawable));
//                mImagesMap.put(imageUrl, drawable);
                Message message = handler.obtainMessage(0, drawable);
                handler.sendMessage(message);
            }
        }.start();

		return null;
	}

	protected static Drawable loadDrawableFromLocalUrl(String imageUrl) {
		Options options=new Options();
        options.inSampleSize=2;
        Bitmap bitmap=BitmapFactory.decodeFile(imageUrl, options);

        Drawable drawable=new BitmapDrawable(bitmap);

        return drawable;
	}

	protected static Drawable loadDrawableFromNetUrl(String imageUrl) {
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