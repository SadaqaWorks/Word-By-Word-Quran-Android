package com.sadaqaworks.quranprojects.activity;

import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.sadaqaworks.quranprojects.R;
import com.sadaqaworks.quranprojects.database.datasource.SurahDataSource;
import com.sadaqaworks.quranprojects.fragment.AyahWordFragment;
import com.sadaqaworks.quranprojects.util.settings.Config;

public class AyahWordActivity extends AppCompatActivity {

  private static final int SCREEN_TIMEOUT = 600;
  public static String surahName;
  // to keep screen on stuff
  static boolean keepScreenOn;
  private final Handler mHandler = new Handler();
  // to keep screen on stuff
  private Runnable clearScreenOn =
      new Runnable() {
        @Override
        public void run() {
          getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
      };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_ayah);

    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    Bundle bundle = this.getIntent().getExtras();
    surahName = bundle.getString(SurahDataSource.SURAH_NAME_TRANSLATE);
    getSupportActionBar().setTitle(surahName);

    if (savedInstanceState == null) {
      FragmentManager fragmentManager = getFragmentManager();

      fragmentManager
          .beginTransaction()
          .replace(R.id.main_container, AyahWordFragment.newInstance(bundle))
          .commit();
    }

    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    keepScreenOn = sharedPreferences.getBoolean(Config.KEEP_SCREEN_ON, Config.defaultKeepScreenOn);
  }

  @Override
  protected void onResume() {
    super.onResume();
    // to keep screen on stuff
    if (keepScreenOn) {
      getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    } else {
      getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
  }

  @Override
  public void onUserInteraction() {
    super.onUserInteraction();
    // to keep screen on stuff
    if (keepScreenOn) {
      mHandler.removeCallbacks(clearScreenOn);
      mHandler.postDelayed(clearScreenOn, SCREEN_TIMEOUT * 1000);
      getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
  }
}
