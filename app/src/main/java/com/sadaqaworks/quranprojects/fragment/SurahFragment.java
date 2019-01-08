package com.sadaqaworks.quranprojects.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sadaqaworks.quranprojects.R;
import com.sadaqaworks.quranprojects.activity.AyahWordActivity;
import com.sadaqaworks.quranprojects.adapter.SurahAdapter;
import com.sadaqaworks.quranprojects.database.datasource.SurahDataSource;
import com.sadaqaworks.quranprojects.intrface.OnItemClickListener;
import com.sadaqaworks.quranprojects.model.Surah;
import com.sadaqaworks.quranprojects.util.settings.Config;

import java.util.ArrayList;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/** A simple {@link Fragment} subclass. */
public class SurahFragment extends Fragment {

  static String lang;
  private ArrayList<Surah> surahArrayList;
  private RecyclerView mRecyclerView;
  private SurahAdapter surahAdapter;
  private RecyclerView.LayoutManager mLayoutManager;

  public SurahFragment() {
    // Required empty public constructor
  }

  public static SurahFragment newInstance() {
    SurahFragment surahFragment = new SurahFragment();
    return surahFragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
    lang = sp.getString(Config.LANG, Config.defaultLang);
    surahArrayList = getSurahArrayList();
  }

  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_surah, container, false);
    mRecyclerView = view.findViewById(R.id.recycler_surah_view);
    surahAdapter = new SurahAdapter(surahArrayList, getActivity());

    return view;
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    // set Adapter with Animation
    //  SlideInLeftAnimationAdapter slideInLeftAnimationAdapter = new
    // SlideInLeftAnimationAdapter(surahAdapter);
    //  slideInLeftAnimationAdapter.setInterpolator(new OvershootInterpolator());
    //  slideInLeftAnimationAdapter.setFirstOnly(false);
    mRecyclerView.setAdapter(surahAdapter);

    mRecyclerView.setHasFixedSize(true);
    // use a linear layout manager
    mLayoutManager = new LinearLayoutManager(getActivity());
    mRecyclerView.setLayoutManager(mLayoutManager);
    mRecyclerView.setItemAnimator(new DefaultItemAnimator());

    surahAdapter.SetOnItemClickListener(
        new OnItemClickListener() {
          @Override
          public void onItemClick(View v, int position) {
            Surah surah = (Surah) surahAdapter.getItem(position);

            long surah_id = surah.getId(); // mRecyclerView.getAdapter().getItemId(position);
            long ayah_number = surah.getAyahNumber();
            String surah_name = surah.getNameTranslate();

            Log.d("SurahFragment", "ID: " + surah_id + " Surah Name: " + surah_name);

            Bundle dataBundle = new Bundle();
            dataBundle.putLong(SurahDataSource.SURAH_ID_TAG, surah_id);
            dataBundle.putLong(SurahDataSource.SURAH_AYAH_NUMBER, ayah_number);
            dataBundle.putString(SurahDataSource.SURAH_NAME_TRANSLATE, surah_name);

            Intent intent = new Intent(getActivity(), AyahWordActivity.class);
            intent.putExtras(dataBundle);
            startActivity(intent);
          }
        });

    /* mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {


           long id =  mRecyclerView.getAdapter().getItemId(position);

            Log.d("SurahFragment", "position: " + id);
        }

        @Override
        public void onItemLongClick(View view, int position) {

        }
    }));*/

  }

  private ArrayList<Surah> getSurahArrayList() {
    ArrayList<Surah> surahArrayList = new ArrayList<Surah>();
    SurahDataSource surahDataSource = new SurahDataSource(getActivity());

    switch (lang) {
      case Config.LANG_BN:
        surahArrayList = surahDataSource.getBanglaSurahArrayList();
        break;
      case Config.LANG_INDO:
        surahArrayList = surahDataSource.getIndonesianSurahArrayList();
        break;
      case Config.LANG_EN:
        surahArrayList = surahDataSource.getEnglishSurahArrayList();
        break;
    }

    return surahArrayList;
  }
}
