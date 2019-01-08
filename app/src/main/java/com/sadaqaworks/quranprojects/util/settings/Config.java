package com.sadaqaworks.quranprojects.util.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/** Created by Sadmansamee on 7/25/15. */
public class Config {

  public static final int FONT_QALAM_MAJEED = 0;
  public static final int FONT_HAFS = 1;
  public static final int FONT_NOOREHUDA = 2;
  public static final int FONT_ME_QURAN = 3;
  public static final int FONT_MAX = 3;

  public static final String LANG = "lang";
  public static final String LANG_BN = "bn";
  public static final String LANG_EN = "en";
  public static final String LANG_INDO = "indo";
  public static final String SHOW_TRANSLATION = "showTranslation";
  public static final String WORD_BY_WORD = "wordByWord";
  public static final String KEEP_SCREEN_ON = "keepScreenOn";
  public static final String ARABIC_FONT = "arabicFont";
  public static final String FONT_SIZE_ARABIC = "fontSizeArabic";
  public static final String FONT_SIZE_TRANSLATION = "fontSizeTranslation";
  public static final String FIRST_RUN = "firstRun";
  public static final String DATABASE_VERSION = "dbVersion";

  public static final String defaultLang = "en";
  public static final boolean defaultShowTranslation = true;
  public static final boolean defaultWordByWord = true;
  public static final boolean defaultKeepScreenOn = true;
  public static final String defaultArabicFont = "PDMS_IslamicFont.ttf";
  public static final String defaultFontSizeArabic = "30";
  public static final String defaultFontSizeTranslation = "14";

  // public String lang;
  public boolean rtl;
  public boolean showTranslation;
  public boolean wordByWord;
  public boolean fullWidth;
  public boolean keepScreenOn;
  public boolean enableAnalytics;
  public String fontArabic;
  public String fontSizeArabic;
  public int fontSizeTranslation;

  public void load(Context context) {
    Log.d("Config", "Load");
    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
    try {
      loadDefault();
      fontArabic = sp.getString(Config.ARABIC_FONT, Config.defaultArabicFont);
      fontSizeArabic = sp.getString(Config.FONT_SIZE_ARABIC, Config.defaultFontSizeArabic);
      Log.d("Config", "Loading Custom");

    } catch (Exception e) {
      loadDefault();
      Log.d("Config", "Exception Loading Defaults");
    }
  }

  public void loadDefault() {
    fontArabic = defaultArabicFont;
    fontSizeArabic = defaultFontSizeArabic;
  }

  /*public void save(Context context) {
      Log.d("Config","Save");
      SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
      SharedPreferences.Editor ed = sp.edit();
      ed.clear();
      ed.putString(LANG, lang);
      ed.putBoolean(SHOW_TRANSLATION, showTranslation);
      ed.putBoolean(WORD_BY_WORD, wordByWord);
      ed.putBoolean(KEEP_SCREEN_ON, keepScreenOn);
      ed.putString(FONT_SIZE_ARABIC, "" + fontSizeArabic);
      ed.putString(FONT_SIZE_TRANSLATION, "" + fontSizeTranslation);
      ed.commit();
  }*/
  private int getStringInt(SharedPreferences sp, String key, int defValue) {
    return Integer.parseInt(sp.getString(key, Integer.toString(defValue)));
  }

  /*  public boolean loadFont() {
      if (loadedFont != Config.fontArabic) {
          String name;
          switch (config.fontArabic) {
              case Config.FONT_NASKH:
                  name = "naskh.otf";
                  break;
              case Config.FONT_NOOREHUDA:
                  name = "noorehuda.ttf";
                  break;
              case Config.FONT_ME_QURAN:
                  name = "me_quran.ttf";
                  break;
              default:
                  name = "qalam.ttf";
          }
          try {
              NativeRenderer.loadFont(getAssets().open(name));
              loadedFont = config.fontArabic;
          } catch (IOException e) {
              e.printStackTrace();
              loadedFont = -1;
              return false;
          }
      }
      return true;
  }*/

}
