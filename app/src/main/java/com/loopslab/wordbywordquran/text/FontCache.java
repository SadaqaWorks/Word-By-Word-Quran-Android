package com.loopslab.wordbywordquran.text;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

public class FontCache {
    final private static int BITMAP_CACHE_SIZE = 256;
    final private static int EXTENT_CACHE_SIZE = 1024;
    private static FontCache mInstance;
    private LruCache<String, Bitmap> mBitmapCache;
    private LruCache<String, int[]> mExtentCache;

    private FontCache() {
        mBitmapCache = new LruCache<String, Bitmap>(BITMAP_CACHE_SIZE);
        mExtentCache = new LruCache<String, int[]>(EXTENT_CACHE_SIZE);
    }

    public static FontCache getInstance() {
        if (mInstance == null) {
            mInstance = new FontCache();
        }
        return mInstance;
    }

    public void putBitmap(String text, Bitmap bitmap) {
        mBitmapCache.put(text, bitmap);
    }

    public Bitmap getBitmap(String text) {
        return mBitmapCache.get(text);
    }

    public void putExtent(String text, int[] extent) {
        mExtentCache.put(text, extent);
    }

    public int[] getExtent(String text) {
        return mExtentCache.get(text);
    }

    public void clearCache() {
        mBitmapCache.evictAll();
        mExtentCache.evictAll();
    }
}
