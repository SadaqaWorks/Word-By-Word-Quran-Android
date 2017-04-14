package com.sadaqaworks.quranprojects.database.datasource;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sadaqaworks.quranprojects.database.DatabaseHelper;
import com.sadaqaworks.quranprojects.model.Tafsir;

import java.util.ArrayList;

/**
 * Created by Sadmansamee on 8/20/15.
 */
public class TafsirKathirEnglishDataSource {

    public static final String TAFSIR_KATHIR_ENGLSIH_TABLE = "tafsir_ibne_kathir_english";
    public static final String TAFSIR_KATHIR_ENGLSIH_ID = "_id";
    public static final String TAFSIR_KATHIR_ENGLSIH_SURAH_NUM = "surah_num";
    public static final String TAFSIR_KATHIR_ENGLSIH_AYAH_NUM = "ayah_num";
    public static final String TAFSIR_KATHIR_ENGLSIH_TAFSIR_TEXT = "tafsir_text";


    public static final String TAFSIR_KATHIR_ENGLSIH_SEGMENT_TABLE = "tafsir_ibne_kathir_english_segment";
    public static final String TAFSIR_KATHIR_ENGLSIH_SEGMENT_ID_ = "_id";
    public static final String TAFSIR_KATHIR_ENGLSIH_SEGMENT_SURAH_NUM_ = "surah_num";
    public static final String TAFSIR_KATHIR_ENGLSIH_SEGMENT_AYAH_BEGIN_ = "ayah_begin";
    public static final String TAFSIR_KATHIR_ENGLSIH_SEGMENT_AYAH_END = "ayah_end";

    private static Cursor cursor;
    private DatabaseHelper databaseHelper;

    public TafsirKathirEnglishDataSource(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public ArrayList<Tafsir> getTafsirKathirEnglishArrayList(long surah_id, long verse_id) {
        ArrayList<Tafsir> tafsirArrayList = new ArrayList<>();

        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        cursor = db.rawQuery("select tafsir_ibne_kathir_english.tafsir_text,tafsir_ibne_kathir_english.ayah_num," +
                "tafsir_ibne_kathir_english.surah_num from tafsir_ibne_kathir_english \n" +
                "where tafsir_ibne_kathir_english.surah_num = " + surah_id + " and tafsir_ibne_kathir_english.ayah_num >= " +
                "(select tafsir_ibne_kathir_english_segment.ayah_begin\n" +
                "from tafsir_ibne_kathir_english_segment where  tafsir_ibne_kathir_english_segment.surah_num=  " + surah_id + " and " + verse_id + " " +
                "between tafsir_ibne_kathir_english_segment.ayah_begin and tafsir_ibne_kathir_english_segment.ayah_end) \n" +
                "and tafsir_ibne_kathir_english.ayah_num <= (select tafsir_ibne_kathir_english_segment.ayah_end from " +
                "tafsir_ibne_kathir_english_segment \n" +
                "where  tafsir_ibne_kathir_english_segment.surah_num = " + surah_id + " and " + verse_id + " between" +
                " tafsir_ibne_kathir_english_segment.ayah_begin " +
                "and tafsir_ibne_kathir_english_segment.ayah_end)", null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Tafsir tafsir = new Tafsir();
            tafsir.setTafsirText(cursor.getString(cursor.getColumnIndex(TAFSIR_KATHIR_ENGLSIH_TAFSIR_TEXT)));
            tafsirArrayList.add(tafsir);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return tafsirArrayList;
    }

}
