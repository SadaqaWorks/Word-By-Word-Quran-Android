package com.sadaqaworks.quranprojects;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.sadaqaworks.quranprojects.util.settings.Config;


/**
 * Created by Sadmansamee on 7/27/15.
 */
public class App extends Application {
    public static App app;
    final public Config config = new Config();
    SharedPreferences sharedPreferences;
    private String loadedFont = "1";
    private String loadedFontSize = "1";

    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        app = this;
        config.load(this);
        Log.e("app ", "onCreate");
        //loadFont();
    }

//    public boolean loadFont() {
//        if (!loadedFont.equals(config.fontArabic) || !loadedFontSize.equals(config.fontSizeArabic)) {
//
//            try {
//
//                Log.e("App: config.fontArabic", loadedFont + " " + config.fontArabic + " size: " + loadedFontSize + config.fontSizeArabic);
//                NativeRenderer.loadFont(this.getResources().getAssets().open(config.fontArabic));
//                FontCache.getInstance().clearCache();
//                loadedFont = config.fontArabic;
//                loadedFontSize = config.fontSizeArabic;
//            } catch (IOException e) {
//                Log.e("app", "LoadFont Failed");
//                e.printStackTrace();
//                loadedFont = "1";
//                loadedFontSize = "1";
//                return false;
//            }
//        }
//        Log.e("app", "LoadFont");
//        return true;
//    }
}
