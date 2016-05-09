package com.sadaqaworks.quranprojects;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.sadaqaworks.quranprojects.database.DatabaseHelper;
import com.sadaqaworks.quranprojects.surah.Surah;
import com.sadaqaworks.quranprojects.surah.SurahDataSource;
import com.sadaqaworks.quranprojects.surah.SurahFragment;
import com.sadaqaworks.quranprojects.various.AboutActivity;
import com.sadaqaworks.quranprojects.various.settings.Config;
import com.sadaqaworks.quranprojects.various.settings.SettingsActivity;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    static String lang;
    SharedPreferences FirstRunPrefs = null;
    SharedPreferences dbVersionPrefs = null;
    SharedPreferences sharedPreferences;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        FirstRunPrefs = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        dbVersionPrefs = getSharedPreferences(getPackageName(), MODE_PRIVATE);

        if ((FirstRunPrefs.getBoolean(Config.FIRST_RUN, false)) || (dbVersionPrefs.getInt(Config.DATABASE_VERSION, 0) == DatabaseHelper.DATABASE_VERSION)) {
            if (savedInstanceState == null) {
                lang = sharedPreferences.getString(Config.LANG, Config.defaultLang);

                if (lang.equals(Config.LANG_BN)) {
                    setLocaleBangla();
                } else {
                    setLocaleEnglish();
                }

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.main_container, SurahFragment.newInstance())
                        .commit();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (DatabaseHelper.DATABASE_VERSION > dbVersionPrefs.getInt(Config.DATABASE_VERSION, 0)) {
            Log.d("MyActivity onResume()", "First Run or dbUpgrade");
            {

                new AsyncInsertData().execute();

            }
        }//checking sharedPrefs finished

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String lang = sp.getString(Config.LANG, Config.defaultLang);
        //to show join group
        if (lang.equals(Config.LANG_BN)) {
            getMenuInflater().inflate(R.menu.menu_main_bn, menu);
        } else {
            getMenuInflater().inflate(R.menu.menu_main, menu);
        }


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_settings:

                Intent intent = new Intent(this, SettingsActivity.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                //MainActivity.this.finish();
                return true;
            case R.id.action_about:
                Intent intentAbout = new Intent(this, AboutActivity.class);
                startActivity(intentAbout);
                return true;

            case R.id.join_group:
                joinGroup();
                return true;
/*
            case R.id.rateUs:
                Intent intentRate = new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(getString(R.string.hisnul_muslim_url)));
                startActivity(intentRate);*/

            default:
                return super.onOptionsItemSelected(item);
        }


    }

    public boolean setLanguage() {

        // CharSequence lang[] = new CharSequence[]{"Bangla", "English","Indonesian"};
        String lang[] = getResources().getStringArray(R.array.lang_names);

        //SharedPreferences.Editor ed = sharedPreferences.edit();
        //ed.clear();
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(getResources().getString(R.string.lang));
        builder.setItems(lang, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // the user clicked on colors[which]
                switch (which) {
                    case 0:
                        SharedPreferences.Editor ed1 = sharedPreferences.edit();
                        ed1.clear();
                        ed1.putString(Config.LANG, Config.LANG_BN);
                        ed1.apply();

                        setLocaleBangla();

                        FragmentManager fragmentManager1 = getFragmentManager();
                        fragmentManager1.beginTransaction()
                                .replace(R.id.main_container, SurahFragment.newInstance())
                                .commit();
                        joinGroup();
                        break;
                    case 1:
                        SharedPreferences.Editor ed2 = sharedPreferences.edit();
                        ed2.clear();
                        ed2.putString(Config.LANG, Config.LANG_EN);
                        ed2.apply();
                        setLocaleEnglish();
                        //recreate();
                        FragmentManager fragmentManager2 = getFragmentManager();
                        fragmentManager2.beginTransaction()
                                .replace(R.id.main_container, SurahFragment.newInstance())
                                .commit();
                        break;
                    case 2:
                        SharedPreferences.Editor ed3 = sharedPreferences.edit();
                        ed3.clear();
                        ed3.putString(Config.LANG, Config.LANG_INDO);
                        ed3.apply();
                        setLocaleEnglish();
                        //recreate();
                        FragmentManager fragmentManager3 = getFragmentManager();
                        fragmentManager3.beginTransaction()
                                .replace(R.id.main_container, SurahFragment.newInstance())
                                .commit();
                        break;
                }

            }

        });
        builder.show();
        return true;
    }

    public void joinGroup() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setCancelable(true);
        builder.setMessage(R.string.joinGroupDesc);
        builder.setTitle(R.string.joinGroupTitle);
        builder.setPositiveButton(R.string.joinGroupTitle, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(getString(R.string.groupLink)));
                startActivity(intent);

            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {

            }
        });

        builder.show();
    }

    public void setLocaleBangla() {
        Locale locale = new Locale(Config.LANG_BN);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getApplicationContext().getResources().getDisplayMetrics());

    }

    public void setLocaleEnglish() {
        Locale locale = new Locale(Config.LANG_EN);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getApplicationContext().getResources().getDisplayMetrics());

    }

    private class AsyncInsertData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {

            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Please wait..");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            Log.d("onInBackground()", "Data Inserting ");
            SurahDataSource surahDataSource = new SurahDataSource(MainActivity.this);
            ArrayList<Surah> surahArrayList = surahDataSource.getEnglishSurahArrayList();

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Log.d("MainActivity", "Data Inserted ");
            //then set 'firstrun' as false, using the following line to edit/commit prefs and set dbversion

            dbVersionPrefs.edit().putInt(Config.DATABASE_VERSION, DatabaseHelper.DATABASE_VERSION).apply();
            progressDialog.dismiss();

            if (FirstRunPrefs.getBoolean(Config.FIRST_RUN, true)) {
                setLanguage();
                FirstRunPrefs.edit().putBoolean(Config.FIRST_RUN, false).apply();
            } else {

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.main_container, SurahFragment.newInstance())
                        .commit();

            }


        }

    }

}
