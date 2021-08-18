package com.nihal.visitormanagement;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

/**
 * nihalJain on 15/10/2021
 *
 * Basic Database Class for the application
 *
 * The only class that should use this id AppProvider
 */
class AppDatabase extends SQLiteOpenHelper {

    private static final String TAG = "AppDatabase";

    public static final String DATABASE_NAME = "VisitorMag.db";
    public static final int DATABASE_VERSION = 3;

    //Implement AppDatabase as a Singleton
    private static AppDatabase instance = null;

    private AppDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, "AppDatabase: constructor");
    }

    /**
     * Get an instance of the app's singleton database helper object
     *
     * @param context the content provider context.
     * @return a SQLite database helper object
     */

    static AppDatabase getInstance(Context context) {
        if (instance == null) {
            Log.d(TAG, "getInstance: creating new instance");
            instance = new AppDatabase(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate : starts");
        String sSQL; // use a string variable to facilitate logging
//            sSQL = "CREATE TABLE Visitor (_id INTEGER PRIMARY KEY NOT NULL, Name TEXT NOT NULL, Phone INTEGER, Address TEXT, City TEXT, SortOrder INTEGER);";
        sSQL = "CREATE TABLE " + VisitorContract.TABLE_NAME + " ("
                + VisitorContract.Columns._ID + " INTEGER PRIMARY KEY NOT NULL,"
                + VisitorContract.Columns.VISITOR_NAME + " TEXT NOT NULL,"
                + VisitorContract.Columns.VISITOR_PHONE + " TEXT, "
                + VisitorContract.Columns.VISITOR_ADDRESS + " TEXT, "
                + VisitorContract.Columns.VISITOR_CITY + " TEXT, "
                + VisitorContract.Columns.VISITOR_SORTORDER + " INTEGER);";
        Log.d(TAG, sSQL);
        db.execSQL(sSQL);

        Log.d(TAG, "onCreate: ends");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade: starts");
        switch (oldVersion) {
            case 1:
                // upgrade logic from version 1
                break;
            default:
                throw new IllegalStateException("onUpgrade() with unknown newVersion: " + newVersion);
        }
        Log.d(TAG, "onUpgrade: ends");
    }
}
