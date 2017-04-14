package com.sadaqaworks.quranprojects.model;

import java.util.ArrayList;

/**
 * Created by Sadmansamee on 7/19/15.
 */
public class AyahWord {
    private ArrayList<Word> word;
    private String quranArabic;
    private String quranTranslate;
    private Long quranVerseId;

    public ArrayList<Word> getWord() {
        return word;
    }

    public void setWord(ArrayList<Word> word) {
        this.word = word;
    }

    public String getQuranArabic() {
        return quranArabic;
    }

    public void setQuranArabic(String quranArabic) {
        this.quranArabic = quranArabic;
    }

    public String getQuranTranslate() {
        return quranTranslate;
    }

    public void setQuranTranslate(String quranTranslate) {
        this.quranTranslate = quranTranslate;
    }

    public Long getQuranVerseId() {
        return quranVerseId;
    }

    public void setQuranVerseId(Long quranVerseId) {
        this.quranVerseId = quranVerseId;
    }
}
