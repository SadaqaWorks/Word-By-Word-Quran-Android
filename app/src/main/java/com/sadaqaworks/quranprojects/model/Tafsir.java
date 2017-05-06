package com.sadaqaworks.quranprojects.model;

/**
 * Created by Sadmansamee on 8/20/15.
 */
public class Tafsir {
    private long tafsirId;
    private long surahNum;
    private long ayahNum;
    private String tafsirText;


    public long getTafsirId() {
        return tafsirId;
    }

    public void setTafsirId(long tafsirId) {
        this.tafsirId = tafsirId;
    }


    public long getSurahNum() {
        return surahNum;
    }

    public void setSurahNum(long surahNum) {
        this.surahNum = surahNum;
    }

    public long getAyahNum() {
        return ayahNum;
    }

    public void setAyahNum(long ayahNum) {
        this.ayahNum = ayahNum;
    }

    public String getTafsirText() {
        return tafsirText;
    }

    public void setTafsirText(String tafsirText) {
        this.tafsirText = tafsirText;
    }
}
