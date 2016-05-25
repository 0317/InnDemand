package demand.inn.com.inndemand.utils;

import android.graphics.Bitmap;
import com.android.volley.toolbox.ImageLoader.ImageCache;

import java.lang.ref.WeakReference;
import java.util.Hashtable;

/**
 * Created by TP151 on 6/1/14.
 */
public class JabongImageCache implements ImageCache {
    private static final int IMAGE_CACHE_SIZE = 10;

    private Hashtable<String, WeakReference<Bitmap>> mBitmapStore = new Hashtable<String, WeakReference<Bitmap>>(IMAGE_CACHE_SIZE);
    private static JabongImageCache mImageCache;

    public synchronized static JabongImageCache getImageCache() {
        if (null == mImageCache) {
            mImageCache = new JabongImageCache();
        }
        return mImageCache;
    }

    private JabongImageCache() {
    }

    @Override
    public Bitmap getBitmap(String url) {
        WeakReference<Bitmap> bmp = mBitmapStore.get(url);
        return null == bmp ? null : bmp.get();
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        mBitmapStore.put(url, new WeakReference<Bitmap>(bitmap));
    }
}
