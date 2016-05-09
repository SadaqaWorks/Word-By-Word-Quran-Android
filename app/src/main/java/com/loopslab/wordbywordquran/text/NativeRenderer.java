package com.loopslab.wordbywordquran.text;

import android.graphics.Bitmap;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class NativeRenderer {

    static {
        System.loadLibrary("render");
    }

    public static native void loadFont(byte[] blob);

    public static native int[] getTextExtent(String text, int fontSize);

    public static native Bitmap renderText(String text, int fontSize);

    public static void loadFont(InputStream is) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            int cc;
            while ((cc = is.read()) != -1) {
                os.write(cc);
            }
            loadFont(os.toByteArray());
            Log.e("Native", "FontLoaded");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadFont(String path) {
        try {
            FileInputStream is = new FileInputStream(path);
            loadFont(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
