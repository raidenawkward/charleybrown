/**
 * @Title: CBAsyncImageLoader.java
 * @Package: com.android.cb.view
 * @Author: raiden
 * @Date: 2012-3-26
 */
package com.android.cb.view;

import java.lang.ref.SoftReference;
import java.util.HashMap;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;

/**
 * @author raiden
 *
 * @Description async image loader, used in image adapter
 */
public class CBAsyncImageLoader {

	/**
	 * @author raiden
	 *
	 * @Description callback used in loadDrawable
	 */
	public interface Callback {
		public void onImageLoaded(Drawable imageDrawable, String imageUrl);
    }

	private HashMap<String, SoftReference<Drawable>> mImagesMap = new HashMap<String, SoftReference<Drawable>>();

	public CBAsyncImageLoader() {

	}

	public Drawable loadDrawable(final String imageUrl, final Callback callback) {
		if (mImagesMap.containsKey(imageUrl)) {
            SoftReference<Drawable> softReference = mImagesMap.get(imageUrl);
            Drawable drawable = softReference.get();
            if (drawable != null) {
                return drawable;
            }
        }

		final Handler handler = new Handler() {
            public void handleMessage(Message message) {
				if (callback != null)
					callback.onImageLoaded((Drawable) message.obj, imageUrl);
            }
        };

        new Thread() {
            @Override
            public void run() {
                Drawable drawable = loadDrawableFromUrl(imageUrl);
                mImagesMap.put(imageUrl, new SoftReference<Drawable>(drawable));
                Message message = handler.obtainMessage(0, drawable);
                handler.sendMessage(message);
            }
        }.start();

		return null;
	}

	protected static Drawable loadDrawableFromUrl(String imageUrl) {
		Options options=new Options();
        options.inSampleSize=2;
        Bitmap bitmap=BitmapFactory.decodeFile(imageUrl, options);

        Drawable drawable=new BitmapDrawable(bitmap);

        return drawable;
	}
}
