package com.sadaqaworks.quranprojects.database.datasource;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sadaqaworks.quranprojects.database.DatabaseHelper;
import com.sadaqaworks.quranprojects.model.Surah;

import java.util.ArrayList;

/** Created by Sadmansamee on 7/19/15. */
public class SurahDataSource {

  public static final String SURAH_TABLE_NAME = "surah_name";
  public static final String SURAH_ID = "_id";
  public static final String SURAH_ID_TAG = "surah_id";
  public static final String SURAH_NO = "surah_no";
  public static final String SURAH_NAME_ARABIC = "name_arabic";
  public static final String SURAH_NAME_ENGLISH = "name_english";
  public static final String SURAH_NAME_TRANSLATE = "name_translate";
  public static final String SURAH_NAME_BANGLA = "name_bangla";
  public static final String SURAH_MEAN_ENGLISH = "surah_mean_english";
  public static final String SURAH_ARTI_NAMA = "arti_nama";
  public static final String SURAH_AYAH_NUMBER = "ayah_number";
  public static final String SURAH_TYPE = "type";
  private static Cursor cursor;
  private DatabaseHelper databaseHelper;

  public SurahDataSource(Context context) {
    databaseHelper = new DatabaseHelper(context);
  }

  public ArrayList<Surah> getEnglishSurahArrayList() {

    ArrayList<Surah> surahArrayList = new ArrayList<>();
    SQLiteDatabase db = databaseHelper.getReadableDatabase();
    cursor =
        db.rawQuery(
            "SELECT surah_name._id,surah_name.name_arabic,surah_name.name_english,surah_name.ayah_number FROM surah_name",
            null);

    cursor.moveToFirst();
    while (!cursor.isAfterLast()) {
      Surah surah = new Surah();
      surah.setId(cursor.getLong(cursor.getColumnIndex(SURAH_ID)));
      surah.setNameArabic(cursor.getString(cursor.getColumnIndex(SURAH_NAME_ARABIC)));
      surah.setNameTranslate(cursor.getString(cursor.getColumnIndex(SURAH_NAME_ENGLISH)));
      surah.setAyahNumber(cursor.getLong(cursor.getColumnIndex(SURAH_AYAH_NUMBER)));
      surahArrayList.add(surah);
      cursor.moveToNext();
    }
    cursor.close();
    db.close();
    return surahArrayList;
  }

  public ArrayList<Surah> getBanglaSurahArrayList() {

    long banglaStart = 28;

    ArrayList<Surah> surahArrayList = new ArrayList<>();
    SQLiteDatabase db = databaseHelper.getReadableDatabase();
    cursor =
        db.rawQuery(
            "SELECT surah_name._id,surah_name.name_arabic,surah_name.name_bangla,surah_name.ayah_number FROM surah_name",
            null); // where surah_name._id >= " + banglaStart

    cursor.moveToFirst();
    while (!cursor.isAfterLast()) {
      Surah surah = new Surah();
      surah.setId(cursor.getLong(cursor.getColumnIndex(SURAH_ID)));
      surah.setNameArabic(cursor.getString(cursor.getColumnIndex(SURAH_NAME_ARABIC)));
      surah.setNameTranslate(cursor.getString(cursor.getColumnIndex(SURAH_NAME_BANGLA)));
      surah.setAyahNumber(cursor.getLong(cursor.getColumnIndex(SURAH_AYAH_NUMBER)));
      surahArrayList.add(surah);
      cursor.moveToNext();
    }
    cursor.close();
    db.close();
    return surahArrayList;
  }

  public ArrayList<Surah> getIndonesianSurahArrayList() {

    ArrayList<Surah> surahArrayList = new ArrayList<>();
    SQLiteDatabase db = databaseHelper.getReadableDatabase();
    cursor =
        db.rawQuery(
            "SELECT surah_name._id,surah_name.name_arabic,surah_name.arti_nama,surah_name.ayah_number FROM surah_name",
            null);

    cursor.moveToFirst();
    while (!cursor.isAfterLast()) {
      Surah surah = new Surah();
      surah.setId(cursor.getLong(cursor.getColumnIndex(SURAH_ID)));
      surah.setNameArabic(cursor.getString(cursor.getColumnIndex(SURAH_NAME_ARABIC)));
      surah.setNameTranslate(cursor.getString(cursor.getColumnIndex(SURAH_ARTI_NAMA)));
      surah.setAyahNumber(cursor.getLong(cursor.getColumnIndex(SURAH_AYAH_NUMBER)));
      surahArrayList.add(surah);
      cursor.moveToNext();
    }
    cursor.close();
    db.close();
    return surahArrayList;
  }
}
