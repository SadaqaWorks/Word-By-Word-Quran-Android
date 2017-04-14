package com.sadaqaworks.quranprojects.database.datasource;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sadaqaworks.quranprojects.database.DatabaseHelper;
import com.sadaqaworks.quranprojects.model.Corpus;

/**
 * Created by Sadmansamee on 8/10/15.
 */
public class CorpusDataSource {


    public final static String CORPUS_PAUSE_UNICODE = "pause_unicode";
    public final static String CORPUS_PAUSE_TYPE_ID = "pause_type_id";
    public final static String CORPUS_WORD_TYPE_ID1 = "word_type_id1";
    public final static String CORPUS_ID = "_id";
    public final static String CORPUS_SURAH_ID = "surah";
    public final static String CORPUS_AYAH_ID = "ayah";
    public final static String CORPUS_WORD_ID = "word";
    public final static String CORPUS_ARABIC1 = "arabic1";
    public final static String CORPUS_ROOT = "root";
    public final static String CORPUS_WORD_COUNT = "word_count";
    public final static String CORPUS_ARABIC2 = "arabic2";
    public final static String CORPUS_ARABIC3 = "arabic3";
    public final static String CORPUS_ARABIC4 = "arabic4";
    public final static String CORPUS_ARABIC5 = "arabic5";
    public final static String CORPUS_WORD_TYPE_ID2 = "word_type_id2";
    public final static String CORPUS_WORD_TYPE_ID3 = "word_type_id3";
    public final static String CORPUS_WORD_TYPE_ID4 = "word_type_id4";
    public final static String CORPUS_WORD_TYPE_ID5 = "word_type_id5";

    private static Cursor cursor;
    private DatabaseHelper databaseHelper;

    public CorpusDataSource(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public Corpus getCorpusById(long id) {
        Corpus corpus = new Corpus();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT quran_corpus.root,quran_corpus.arabic1,quran_corpus.arabic2,quran_corpus.arabic3,quran_corpus.arabic4,quran_corpus.arabic5 ,quran_corpus.word_type_id1,quran_corpus.word_type_id2 from quran_corpus where quran_corpus._id = " + id, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            corpus.setRoot(cursor.getString(cursor.getColumnIndex(CORPUS_ROOT)));
            corpus.setArabic1(cursor.getString(cursor.getColumnIndex(CORPUS_ARABIC1)));
            corpus.setArabic2(cursor.getString(cursor.getColumnIndex(CORPUS_ARABIC2)));
            corpus.setArabic3(cursor.getString(cursor.getColumnIndex(CORPUS_ARABIC3)));
            corpus.setArabic4(cursor.getString(cursor.getColumnIndex(CORPUS_ARABIC4)));
            corpus.setArabic5(cursor.getString(cursor.getColumnIndex(CORPUS_ARABIC5)));
            corpus.setWord_type_id1(cursor.getLong(cursor.getColumnIndex(CORPUS_WORD_TYPE_ID1)));
            corpus.setWord_type_id2(cursor.getLong(cursor.getColumnIndex(CORPUS_WORD_TYPE_ID2)));
            corpus.setWord_type_id3(cursor.getLong(cursor.getColumnIndex(CORPUS_WORD_TYPE_ID3)));
            corpus.setWord_type_id4(cursor.getLong(cursor.getColumnIndex(CORPUS_WORD_TYPE_ID4)));
            corpus.setWord_type_id5(cursor.getLong(cursor.getColumnIndex(CORPUS_WORD_TYPE_ID5)));
            cursor.moveToNext();
        }

        cursor.close();
        db.close();
        return corpus;
    }

    public Corpus getCorpusBySurahAyahWord(long surah, long ayah, long word) {
        Corpus corpus = new Corpus();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT quran_corpus.root,quran_corpus.arabic1,quran_corpus.arabic2,quran_corpus.arabic3,quran_corpus.arabic4,quran_corpus.arabic5,quran_corpus.word_type_id1,quran_corpus.word_type_id2,quran_corpus.word_type_id3,quran_corpus.word_type_id4,quran_corpus.word_type_id5 from quran_corpus where quran_corpus.surah = " + surah + " and quran_corpus.ayah = " + ayah + " and quran_corpus.word = " + word, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            corpus.setRoot(cursor.getString(cursor.getColumnIndex(CORPUS_ROOT)));
            corpus.setArabic1(cursor.getString(cursor.getColumnIndex(CORPUS_ARABIC1)));
            corpus.setArabic2(cursor.getString(cursor.getColumnIndex(CORPUS_ARABIC2)));
            corpus.setArabic3(cursor.getString(cursor.getColumnIndex(CORPUS_ARABIC3)));
            corpus.setArabic4(cursor.getString(cursor.getColumnIndex(CORPUS_ARABIC4)));
            corpus.setArabic5(cursor.getString(cursor.getColumnIndex(CORPUS_ARABIC5)));
            corpus.setWord_type_id1(cursor.getLong(cursor.getColumnIndex(CORPUS_WORD_TYPE_ID1)));
            corpus.setWord_type_id2(cursor.getLong(cursor.getColumnIndex(CORPUS_WORD_TYPE_ID2)));
            corpus.setWord_type_id3(cursor.getLong(cursor.getColumnIndex(CORPUS_WORD_TYPE_ID3)));
            corpus.setWord_type_id4(cursor.getLong(cursor.getColumnIndex(CORPUS_WORD_TYPE_ID4)));
            corpus.setWord_type_id5(cursor.getLong(cursor.getColumnIndex(CORPUS_WORD_TYPE_ID5)));
            cursor.moveToNext();
        }

        cursor.close();
        db.close();
        return corpus;
    }

}
