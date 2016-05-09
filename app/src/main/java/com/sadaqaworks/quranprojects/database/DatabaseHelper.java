package com.sadaqaworks.quranprojects.database;

import android.content.Context;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by Sadmansamee on 7/19/15.
 */
public class DatabaseHelper extends SQLiteAssetHelper {

    public static final String DATABASE_NAME = "wordbyword.db";
    public static final int DATABASE_VERSION = 8;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.setForcedUpgrade(); //just because it is read only database so ForceUpgrade change or remove it is not read only
        Log.d("DatabaseHelper  ", "constructor");
    }

}
