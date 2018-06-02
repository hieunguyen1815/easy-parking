package app.android.easygroup.easyparking.services.internal.volley;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import app.android.easygroup.easyparking.application.EasyParkingApplication;

public class VolleyService {

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private static VolleyService instance;

    private VolleyService() {
        mRequestQueue = getRequestQueue();

        mImageLoader = new ImageLoader(mRequestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }

    public static synchronized VolleyService getInstance() {
        if (instance == null) {
            instance = new VolleyService();
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(EasyParkingApplication.getAppContext());
        }
        return mRequestQueue;
    }

    public <T> Request<T> addToRequestQueue(Request<T> req) {
        return getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }
}
