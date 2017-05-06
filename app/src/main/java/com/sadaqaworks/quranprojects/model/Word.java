package com.sadaqaworks.quranprojects.model;

/**
 * Created by Sadmansamee on 7/19/15.
 */
public class Word {
    private long id;
    private long surahId;
    private long verseId;
    private long wordsId;
    private String wordsAr;
    private String translate;
    private String translateEn;
    private String translateBn;
    private String translateIndo;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSurahId() {
        return surahId;
    }

    public void setSurahId(long surahId) {
        this.surahId = surahId;
    }

    public long getVerseId() {
        return verseId;
    }

    public void setVerseId(long verseId) {
        this.verseId = verseId;
    }

    public long getWordsId() {
        return wordsId;
    }

    public void setWordsId(long wordsId) {
        this.wordsId = wordsId;
    }

    public String getWordsAr() {
        return wordsAr;
    }

    public void setWordsAr(String wordsAr) {
        this.wordsAr = wordsAr;
    }

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }

    public String getTranslateEn() {
        return translateEn;
    }

    public void setTranslateEn(String translateEn) {
        this.translateEn = translateEn;
    }

    public String getTranslateBn() {
        return translateBn;
    }

    public void setTranslateBn(String translateBn) {
        this.translateBn = translateBn;
    }

    public String getTranslateIndo() {
        return translateIndo;
    }

    public void setTranslateIndo(String translateIndo) {
        this.translateIndo = translateIndo;
    }
}
