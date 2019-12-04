package com.sadaqaworks.quranprojects.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.sadaqaworks.quranprojects.R;
import com.sadaqaworks.quranprojects.activity.TafsirActivity;
import com.sadaqaworks.quranprojects.database.datasource.AyahWordDataSource;
import com.sadaqaworks.quranprojects.database.datasource.CorpusDataSource;
import com.sadaqaworks.quranprojects.model.AyahWord;
import com.sadaqaworks.quranprojects.model.Corpus;
import com.sadaqaworks.quranprojects.model.Word;
import com.sadaqaworks.quranprojects.util.settings.Config;
import com.sadaqaworks.quranprojects.view.layout.FlowLayout;

import java.util.ArrayList;

/** Created by Sadmansamee on 7/19/15. */
public class AyahWordAdapter extends RecyclerView.Adapter<AyahWordAdapter.AyahViewHolder> {

  static boolean showTranslation;
  static boolean wordByWord;

  static int fontSizeArabic;
  static int fontSizeTranslation;
  static Typeface corpusTypeface;
  static String[] corpusArabicTypeArray;
  public Context context;
  long surah_id;
  private ArrayList<AyahWord> ayahWordArrayList;

  public AyahWordAdapter(ArrayList<AyahWord> ayahWordArrayList, Context context, long surah_id) {

    this.ayahWordArrayList = ayahWordArrayList;
    this.context = context;
    this.surah_id = surah_id;

    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    showTranslation =
        sharedPreferences.getBoolean(Config.SHOW_TRANSLATION, Config.defaultShowTranslation);
    wordByWord = sharedPreferences.getBoolean(Config.WORD_BY_WORD, Config.defaultWordByWord);
    fontSizeArabic =
        Integer.parseInt(
            sharedPreferences.getString(Config.FONT_SIZE_ARABIC, Config.defaultFontSizeArabic));
    fontSizeTranslation =
        Integer.parseInt(
            sharedPreferences.getString(
                Config.FONT_SIZE_TRANSLATION, Config.defaultFontSizeTranslation));
    corpusTypeface = Typeface.createFromAsset(context.getResources().getAssets(), "amiri.ttf");
  }

  @Override
  public int getItemCount() {
    return ayahWordArrayList.size();
  }

  @Override
  public long getItemId(int position) {

    AyahWord ayahWord = ayahWordArrayList.get(position);
    long itemId = 1;

    for (Word word : ayahWord.getWord()) {
      itemId = word.getVerseId();
    }
    return itemId;
  }

  @Override
  public AyahWordAdapter.AyahViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    View view =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.row_ayah_word, parent, false);
    AyahWordAdapter.AyahViewHolder viewHolder = new AyahWordAdapter.AyahViewHolder(view);
    return viewHolder;
  }

  @Override
  public void onBindViewHolder(AyahWordAdapter.AyahViewHolder holder, int position) {

    final AyahWord ayahWord = ayahWordArrayList.get(position);

    // holder.verse_idTextView.setText("\uFD3F" + intToArabic(ayahWord.getQuranVerseId()) +
    // "\uFD3E");
    holder.verse_idTextView.setText("(" + ayahWord.getQuranVerseId() + ")");

    if (wordByWord) {
      final LayoutInflater inflater =
          (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      holder.flow_word_by_word.removeAllViews();

      for (final Word word : ayahWord.getWord()) {

        final View view = inflater.inflate(R.layout.word_by_word, null);
        final TextView arabic = view.findViewById(R.id.word_arabic_textView);
        final TextView translation = view.findViewById(R.id.word_trans_textView);
        arabic.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSizeArabic);
        translation.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSizeTranslation);
        arabic.setText(fixArabic(word.getWordsAr()));
        translation.setText(word.getTranslate());
        holder.flow_word_by_word.addView(view);

        // corpus
        view.setOnClickListener(
            new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.corpus_layout);
                dialog.setTitle(fixArabic(word.getWordsAr()));

                Corpus corpus;
                CorpusDataSource corpusDataSource = new CorpusDataSource(context);
                corpus =
                    corpusDataSource.getCorpusBySurahAyahWord(
                        surah_id, word.getVerseId(), word.getWordsId());
                Log.e(
                    "arabic",
                    corpus.getArabic1()
                        + corpus.getArabic2()
                        + corpus.getArabic3()
                        + corpus.getArabic4()
                        + corpus.getArabic5());

                final TextView corpus_arabic =
                    dialog.findViewById(R.id.corpus_word_arabic_textView);
                final TextView corpus_translation =
                    dialog.findViewById(R.id.corpus_word_trans_textView);
                corpus_arabic.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSizeArabic);
                corpus_translation.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSizeTranslation);
                corpus_arabic.setText(fixArabic(word.getWordsAr()));
                corpus_translation.setText(word.getTranslate());

                TextView rootArabicTextView = dialog.findViewById(R.id.rootArabicTextView);
                TextView rootTextView = dialog.findViewById(R.id.rootTextView);
                try {

                  rootArabicTextView.setText(getRootInArabic(corpus.getRoot()));
                  rootArabicTextView.setVisibility(View.VISIBLE);
                  rootTextView.setVisibility(View.VISIBLE);

                } catch (StringIndexOutOfBoundsException e) {

                  e.printStackTrace();
                }

                // if(corpus.getArabic1() != null )
                //  {
                final TextView arabic1TextView = dialog.findViewById(R.id.arabic1TextView);
                final TextView arabic1TypeTextView = dialog.findViewById(R.id.arabic1TypeTextView);
                arabic1TextView.setTypeface(corpusTypeface);
                arabic1TextView.setVisibility(View.VISIBLE);
                String arabic1 = new String(getUnicodeString(corpus.getArabic1()));
                arabic1TextView.setText(arabic1);
                arabic1TypeTextView.setText(getCorpusArabicType(corpus.getWord_type_id1()));
                //   }
                if (!corpus.getArabic2().equals("")) {
                  final TextView arabic2TextView = dialog.findViewById(R.id.arabic2TextView);
                  final TextView arabic2TypeTextView =
                      dialog.findViewById(R.id.arabic2TypeTextView);
                  arabic2TextView.setTypeface(corpusTypeface);
                  arabic2TextView.setVisibility(View.VISIBLE);
                  String arabic2 = new String(getUnicodeString(corpus.getArabic2()));
                  arabic2TextView.setText(arabic2);
                  arabic2TypeTextView.setText(getCorpusArabicType(corpus.getWord_type_id2()));
                }

                if (!corpus.getArabic3().equals("")) {
                  final TextView arabic3TextView = dialog.findViewById(R.id.arabic3TextView);
                  final TextView arabic3TypeTextView =
                      dialog.findViewById(R.id.arabic3TypeTextView);
                  arabic3TextView.setTypeface(corpusTypeface);
                  arabic3TextView.setVisibility(View.VISIBLE);
                  String arabic3 = new String(getUnicodeString(corpus.getArabic3()));
                  arabic3TextView.setText(arabic3);
                  arabic3TypeTextView.setText(getCorpusArabicType(corpus.getWord_type_id3()));
                }

                if (!corpus.getArabic4().equals("")) {
                  final TextView arabic4TextView = dialog.findViewById(R.id.arabic4TextView);
                  final TextView arabic4TypeTextView =
                      dialog.findViewById(R.id.arabic4TypeTextView);
                  arabic4TextView.setTypeface(corpusTypeface);
                  arabic4TextView.setVisibility(View.VISIBLE);
                  String arabic4 = new String(getUnicodeString(corpus.getArabic4()));
                  arabic4TextView.setText(arabic4);
                  arabic4TypeTextView.setText(getCorpusArabicType(corpus.getWord_type_id4()));
                }

                if (!corpus.getArabic5().equals("")) {
                  final TextView arabic5TextView = dialog.findViewById(R.id.arabic5TextView);
                  final TextView arabic5TypeTextView =
                      dialog.findViewById(R.id.arabic5TypeTextView);
                  arabic5TextView.setTypeface(corpusTypeface);
                  arabic5TextView.setVisibility(View.VISIBLE);
                  String arabic5 = new String(getUnicodeString(corpus.getArabic5()));
                  arabic5TextView.setText(arabic5);
                  arabic5TypeTextView.setText(getCorpusArabicType(corpus.getWord_type_id5()));
                }
                dialog.show();
              }
            });
      }

      holder.flow_word_by_word.setVisibility(View.VISIBLE);
      holder.arabic_textView.setVisibility(View.GONE);

    } else {
      holder.flow_word_by_word.setVisibility(View.GONE);
      holder.arabic_textView.setText(ayahWord.getQuranArabic());
      // holder.arabic_textView.setTypeface(typeface);
      holder.arabic_textView.setTextSize(fontSizeArabic);
      holder.arabic_textView.setVisibility(View.VISIBLE);
    }

    if (showTranslation) {
      holder.translate_textView.setText(ayahWord.getQuranTranslate());
      holder.translate_textView.setTextSize(fontSizeTranslation);
      holder.translate_textView.setVisibility(View.VISIBLE);
      /*To show tafsir
      holder.translate_textView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {

              startTafsirActivity(surah_id, ayahWord.getQuranVerseId());

          }
      });*/
    }

    if (position % 2 == 0) {
      holder.flow_word_by_word.setBackgroundColor(ContextCompat.getColor(context, R.color.mushaf2));
      holder.verse_idTextView.setBackgroundColor(ContextCompat.getColor(context, R.color.mushaf2));
      holder.arabic_textView.setBackgroundColor(ContextCompat.getColor(context, R.color.mushaf2));
      holder.translate_textView.setBackgroundColor(
          ContextCompat.getColor(context, R.color.mushaf2));

    } else {

      holder.flow_word_by_word.setBackgroundColor(ContextCompat.getColor(context, R.color.mushaf3));
      holder.verse_idTextView.setBackgroundColor(ContextCompat.getColor(context, R.color.mushaf3));
      holder.translate_textView.setBackgroundColor(
          ContextCompat.getColor(context, R.color.mushaf3));
      holder.arabic_textView.setBackgroundColor(ContextCompat.getColor(context, R.color.mushaf3));
    }
  }

  public void startTafsirActivity(long surah_id, long verse_id) {
    Intent intent = new Intent(context, TafsirActivity.class);
    Bundle bundle = new Bundle();
    bundle.putLong(AyahWordDataSource.AYAHWORD_SURAH_ID, surah_id);
    bundle.putLong(AyahWordDataSource.AYAHWORD_VERSE_ID, verse_id);
    intent.putExtras(bundle);
    context.startActivity(intent);
  }

  private String fixArabic(String s) {
    // Add sukun on mem | nun
    s = s.replaceAll("([\u0645\u0646])([ \u0627-\u064A]|$)", "$1\u0652$2");
    // Tatweel + Hamza Above (joining chairless hamza) => Yeh With Hamza Above
    s = s.replaceAll("\u0640\u0654", "\u0626");
    return s;
  }

  // converts int to arabic alphabet
  private String intToArabic(long n) {
    StringBuffer sb = new StringBuffer(Long.toString(n));
    for (int i = 0; i < sb.length(); i++) {
      char ch = sb.charAt(i);
      ch += '\u0660' - '0';
      sb.setCharAt(i, ch);
    }
    return sb.reverse().toString();
  }

  public char[] getUnicodeString(String arString) {
    int charlength = arString.length() / 4;
    char[] arrayOfChar = new char[charlength];
    for (int position = 0; position < charlength; position++) {
      arrayOfChar[position] =
          ((char) Integer.parseInt(arString.substring(position * 4, 4 + position * 4), 16));
    }
    return arrayOfChar;
  }

  private int getStringInt(SharedPreferences sp, String key, int defValue) {
    return Integer.parseInt(sp.getString(key, Integer.toString(defValue)));
  }

  public String getRootInArabic(String rootEnglsih) {
    String rootArabic = " ";
    String rootLetter0 = getEnglishToArabicLetter(Character.toString(rootEnglsih.charAt(0)));
    String rootLetter1 = getEnglishToArabicLetter(Character.toString(rootEnglsih.charAt(1)));
    String rootLetter2 = getEnglishToArabicLetter(Character.toString(rootEnglsih.charAt(2)));

    rootArabic = rootLetter0 + rootLetter1 + rootLetter2;

    return rootArabic;
  }

  public String getEnglishToArabicLetter(String englishLetter) {
    String arabicLetter = "";

    switch (englishLetter) {
      case "A":
        arabicLetter = "أ";
        break;
      case "a":
        arabicLetter = "ا";
        break;

      case "b":
        arabicLetter = "ب";
        break;

      case "t":
        arabicLetter = "ت";
        break;

      case "v":
        arabicLetter = "ث";
        break;

      case "j":
        arabicLetter = "ج";
        break;

      case "H":
        arabicLetter = "ح";
        break;

      case "x":
        arabicLetter = "خ";
        break;

      case "d":
        arabicLetter = "د";
        break;

      case "*":
        arabicLetter = "ذ";
        break;

      case "r":
        arabicLetter = "ر";
        break;

      case "z":
        arabicLetter = "ز";
        break;

      case "s":
        arabicLetter = "س";
        break;

      case "$":
        arabicLetter = "ش";
        break;

      case "S":
        arabicLetter = "ص";
        break;

      case "D":
        arabicLetter = "ض";
        break;

      case "T":
        arabicLetter = "ط";
        break;

      case "Z":
        arabicLetter = "ظ";
        break;

      case "E":
        arabicLetter = "ع";
        break;

      case "g":
        arabicLetter = "غ";
        break;

      case "f":
        arabicLetter = "ف";
        break;

      case "q":
        arabicLetter = "ق";
        break;
      case "k":
        arabicLetter = "ك";
        break;

      case "l":
        arabicLetter = "ل";
        break;

      case "I":
        arabicLetter = "ل";
        break;

      case "m":
        arabicLetter = "م";
        break;

      case "n":
        arabicLetter = "ن";
        break;

      case "h":
        arabicLetter = "ه";
        break;

      case "w":
        arabicLetter = "و";
        break;

      case "y":
        arabicLetter = "ي";
        break;
    }

    return arabicLetter;
  }

  public String getCorpusArabicType(long typeId) {
    int typeIdInt = (int) typeId - 1;
    corpusArabicTypeArray = context.getResources().getStringArray(R.array.corpus_word_type);
    String corpusArabicType = "";
    try {
      corpusArabicType = corpusArabicTypeArray[typeIdInt];
    } catch (ArrayIndexOutOfBoundsException e) {
      e.printStackTrace();
    }

    return corpusArabicType;
  }

  public static class AyahViewHolder extends RecyclerView.ViewHolder {

    public TextView verse_idTextView;
    public FlowLayout flow_word_by_word;
    public TextView translate_textView;
    public TextView arabic_textView;

    public AyahViewHolder(View view) {
      super(view);
      verse_idTextView = view.findViewById(R.id.verse_id_textView);
      flow_word_by_word = view.findViewById(R.id.flow_word_by_word);
      translate_textView = view.findViewById(R.id.translate_textView);
      arabic_textView = view.findViewById(R.id.arabic_textView);
    }
  }
}
