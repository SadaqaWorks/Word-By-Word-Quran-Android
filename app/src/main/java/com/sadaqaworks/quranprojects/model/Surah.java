package com.sadaqaworks.quranprojects.model;

/**
 * Created by Sadmansamee on 7/19/15.
 */
public class Surah {
    private Long id;
    private Long no;
    private String nameEnglish;
    private String nameArabic;
    private String nameTranslate;

    private String meanEnglish;
    private Long ayahNumber;
    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNo() {
        return no;
    }

    public void setNo(Long no) {
        this.no = no;
    }

    public String getNameEnglish() {
        return nameEnglish;
    }

    public void setNameEnglish(String nameEnglish) {
        this.nameEnglish = nameEnglish;
    }

    public String getNameArabic() {
        return nameArabic;
    }

    public void setNameArabic(String nameArabic) {
        this.nameArabic = nameArabic;
    }

    public String getNameTranslate() {
        return nameTranslate;
    }

    public void setNameTranslate(String nameTranslate) {
        this.nameTranslate = nameTranslate;
    }

    public String getMeanEnglish() {
        return meanEnglish;
    }

    public void setMeanEnglish(String meanEnglish) {
        this.meanEnglish = meanEnglish;
    }

    public Long getAyahNumber() {
        return ayahNumber;
    }

    public void setAyahNumber(Long ayahNumber) {
        this.ayahNumber = ayahNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
