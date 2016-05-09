package com.sadaqaworks.quranprojects.ayah.ayahword;

/**
 * Created by Sadmansamee on 7/19/15.
 */
public class Word {
    private long AyahWord_id;
    private long AyahWord_surah_id;
    private long AyahWord_verse_id;
    private long AyahWord_words_id;
    private String AyahWord_words_ar;
    private String AyahWord_translate;
    private String AyahWord_translate_en;
    private String AyahWord_translate_bn;
    private String AyahWord_translate_indo;

    public long getAyahWord_id() {
        return AyahWord_id;
    }

    public void setAyahWord_id(long ayahWord_id) {
        AyahWord_id = ayahWord_id;
    }

    public long getAyahWord_surah_id() {
        return AyahWord_surah_id;
    }

    public void setAyahWord_surah_id(long ayahWord_surah_id) {
        AyahWord_surah_id = ayahWord_surah_id;
    }

    public long getAyahWord_verse_id() {
        return AyahWord_verse_id;
    }

    public void setAyahWord_verse_id(long ayahWord_verse_id) {
        AyahWord_verse_id = ayahWord_verse_id;
    }

    public long getAyahWord_words_id() {
        return AyahWord_words_id;
    }

    public void setAyahWord_words_id(long ayahWord_words_id) {
        AyahWord_words_id = ayahWord_words_id;
    }

    public String getAyahWord_words_ar() {
        return AyahWord_words_ar;
    }

    public void setAyahWord_words_ar(String ayahWord_words_ar) {
        AyahWord_words_ar = ayahWord_words_ar;
    }

    public String getAyahWord_translate() {
        return AyahWord_translate;
    }

    public void setAyahWord_translate(String ayahWord_translate) {
        AyahWord_translate = ayahWord_translate;
    }


    public String getAyahWord_translate_en() {
        return AyahWord_translate_en;
    }

    public void setAyahWord_translate_en(String ayahWord_translate_en) {
        AyahWord_translate_en = ayahWord_translate_en;
    }

    public String getAyahWord_translate_bn() {
        return AyahWord_translate_bn;
    }

    public void setAyahWord_translate_bn(String ayahWord_translate_bn) {
        AyahWord_translate_bn = ayahWord_translate_bn;
    }

    public String getAyahWord_translate_indo() {
        return AyahWord_translate_indo;
    }

    public void setAyahWord_translate_indo(String ayahWord_translate_indo) {
        AyahWord_translate_indo = ayahWord_translate_indo;
    }
}
