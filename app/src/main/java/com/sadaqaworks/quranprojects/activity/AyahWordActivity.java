package com.sadaqaworks.quranprojects.activity;

import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;

import com.sadaqaworks.quranprojects.R;
import com.sadaqaworks.quranprojects.database.datasource.SurahDataSource;
import com.sadaqaworks.quranprojects.fragment.AyahWordFragment;
import com.sadaqaworks.quranprojects.util.settings.Config;

public class AyahWordActivity extends AppCompatActivity {

    final private static int SCREEN_TIMEOUT = 600;
    static public String surahName;
    //to keep screen on stuff
    static boolean keepScreenOn;
    final private Handler mHandler = new Handler();
    //to keep screen on stuff
    private Runnable clearScreenOn = new Runnable() {
        @Override
        public void run() {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayah);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Bundle bundle = this.getIntent().getExtras();
        surahName = bundle.getString(SurahDataSource.SURAH_NAME_TRANSLATE);
        getSupportActionBar().setTitle(surahName);

        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getFragmentManager();

            fragmentManager.beginTransaction()
                    .replace(R.id.main_container, AyahWordFragment.newInstance(bundle))
                    .commit();
        }

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        keepScreenOn = sharedPreferences.getBoolean(Config.KEEP_SCREEN_ON, Config.defaultKeepScreenOn);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //to keep screen on stuff
        if (keepScreenOn) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }


    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        //to keep screen on stuff
        if (keepScreenOn) {
            mHandler.removeCallbacks(clearScreenOn);
            mHandler.postDelayed(clearScreenOn, SCREEN_TIMEOUT * 1000);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }



    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ayah, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
