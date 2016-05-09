package com.sadaqaworks.quranprojects.model;

/**
 * Created by Sadmansamee on 8/20/15.
 */
public class Tafsir {
    private long tafsirId;
    private long surah_num;
    private long ayah_num;
    private String tafsirText;


    public long getTafsirId() {
        return tafsirId;
    }

    public void setTafsirId(long tafsirId) {
        this.tafsirId = tafsirId;
    }

    public long getSurah_num() {
        return surah_num;
    }

    public void setSurah_num(long surah_num) {
        this.surah_num = surah_num;
    }

    public long getAyah_num() {
        return ayah_num;
    }

    public void setAyah_num(long ayah_num) {
        this.ayah_num = ayah_num;
    }

    public String getTafsirText() {
        return tafsirText;
    }

    public void setTafsirText(String tafsirText) {
        this.tafsirText = tafsirText;
    }
}
