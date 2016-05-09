package com.sadaqaworks.quranprojects.model;

/**
 * Created by Sadmansamee on 7/19/15.
 */
public class Surah {
    private Long surah_id;
    private Long surah_no;
    private String surah_name_english;
    private String surah_name_arabic;
    private String surah_name_translate;

    private String surah_mean_english;
    private Long surah_ayah_number;
    private String surah_type;

    public Long getSurah_id() {
        return surah_id;
    }

    public void setSurah_id(Long surah_id) {
        this.surah_id = surah_id;
    }

    public Long getSurah_ayah_number() {
        return surah_ayah_number;
    }

    public void setSurah_ayah_number(Long surah_ayah_number) {
        this.surah_ayah_number = surah_ayah_number;
    }

    public Long getSurah_no() {
        return surah_no;
    }

    public void setSurah_no(Long surah_no) {
        this.surah_no = surah_no;
    }

    public String getSurah_name_translate() {
        return surah_name_translate;
    }

    public void setSurah_name_translate(String surah_name_translate) {
        this.surah_name_translate = surah_name_translate;
    }

    //   public String getSurah_name_english() {
    //       return surah_name_english;
    //   }

    // public void setSurah_name_english(String surah_name_english) {
    //      this.surah_name_english = surah_name_english;
    //   }

    public String getSurah_type() {
        return surah_type;
    }

    public void setSurah_type(String surah_type) {
        this.surah_type = surah_type;
    }

    public String getSurah_mean_english() {
        return surah_mean_english;
    }

    public void setSurah_mean_english(String surah_mean_english) {
        this.surah_mean_english = surah_mean_english;
    }

    public String getSurah_name_arabic() {
        return surah_name_arabic;
    }

    public void setSurah_name_arabic(String surah_name_arabic) {
        this.surah_name_arabic = surah_name_arabic;
    }

}
