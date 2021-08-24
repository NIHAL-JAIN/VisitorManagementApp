package com.nihal.visitormanagement;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Provider for the TaskTimer app. This is the only that knows about{@link AppDatabase}.
 */

public class AppProvider extends ContentProvider {
    Context context;
    private static final String TAG = "AppProvider";
    private  AppDatabase mOpenHelper;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    static final String CONTENT_AUTHORITY = "com.nihal.visitormanagement.provider";
    public static final Uri CONTENT_AUTHORITY_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    private static final int VISITOR = 100;
    private static final int VISITOR_ID = 101;


    private static UriMatcher buildUriMatcher(){
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        //eg. content://com.nihal.visitormanagement.provider/Visitor
//        if(true)
        matcher.addURI(CONTENT_AUTHORITY,VisitorContract.TABLE_NAME , VISITOR);
        //eg. content://com.nihal.visitormanagement.provider/Visitor/8
//        else
        matcher.addURI(CONTENT_AUTHORITY,VisitorContract.TABLE_NAME + "/#", VISITOR_ID);

        return matcher;

    }

    @Override
    public boolean onCreate() {
        //this.context=getContext();
        return false;
    }

   public AppProvider(){

    }

   public AppProvider(Context context){
        this.context=context;
    }

    @Nullable
    @Override
    public Cursor query( Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.d(TAG,"query: called with URI" + uri);
        final int match = sUriMatcher.match(uri);
        Log.d(TAG,"query: match is " + match);
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        switch (match) {
            case VISITOR:
                queryBuilder.setTables(VisitorContract.TABLE_NAME);
                break;
            case VISITOR_ID:
                queryBuilder.setTables(VisitorContract.TABLE_NAME);
                long visitorId = VisitorContract.getVisitorId(uri);
                queryBuilder.appendWhere(VisitorContract.Columns._ID + " = " + visitorId);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);

        }
        mOpenHelper = AppDatabase.getInstance(getContext());
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
//        return queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        Cursor cursor = queryBuilder.query(db,projection,selection,selectionArgs,null,null,sortOrder);
        Log.d(TAG,"query : rows in returned cursor = " + cursor.getCount()); // TODO remove this line

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType( Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match){
            case VISITOR:
                return VisitorContract.CONTENT_TYPE;
            case VISITOR_ID:
                return VisitorContract.CONTENT_ITEM_TYPE;

            default:
                throw new IllegalArgumentException("unknown Uri: " +uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Log.d(TAG,"Entering insert, called with uri: " + uri);
        final int match = sUriMatcher.match(uri);
        Log.d(TAG,"match is" +match);

        final SQLiteDatabase db;
        Uri returnUri;
        long recordId;

        switch (match) {
            case VISITOR:
                db = mOpenHelper.getWritableDatabase();
                recordId = db.insert(VisitorContract.TABLE_NAME,null,values);
                if(recordId >=0){
                    returnUri = VisitorContract.buildVisitorUri(recordId);
                }else {
                    throw new android.database.SQLException("Failed to insert into" + uri.toString());
                }
                break;

                default:
                    throw new IllegalArgumentException("Unknown uri: " +uri);
        }
        if(recordId >= 0) {
            //something was inserted
            Log.d(TAG,"insert: Setting notifyChanged with" + uri);
            getContext().getContentResolver().notifyChange(uri,null);
        }else {
            Log.d(TAG,"insert: nothing inserted");

        }
        Log.d(TAG, "Exiting insert, returning " + returnUri);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.d(TAG, "delete called with uri " + uri);
        final int match = sUriMatcher.match(uri);
        Log.d(TAG, "match is" + match);

        final SQLiteDatabase db;
        int count;

        String selectionCriteria;

        switch (match) {
            case VISITOR:
                db = mOpenHelper.getWritableDatabase();
                count = db.delete(VisitorContract.TABLE_NAME, selection, selectionArgs);
                break;

            case VISITOR_ID:
                db = mOpenHelper.getWritableDatabase();
                long visitorId = VisitorContract.getVisitorId(uri);
                selectionCriteria = VisitorContract.Columns._ID + " = " + visitorId;
                if ((selection != null) && (selection.length() > 0)) {
                    selectionCriteria += "AND (" + selection + ")";
                }
                count = db.delete(VisitorContract.TABLE_NAME, selectionCriteria, selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown uri: " + uri);
        }
        if(count >0){
            //something was deleted
            Log.d(TAG,"delete: Setting notifyChange with" + uri);
            getContext().getContentResolver().notifyChange(uri, null);
        }else{
            Log.d(TAG,"delete: nothing deleted");
        }
        Log.d(TAG,"Exiting delete, returning " + count);
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {

        Log.d(TAG, "update called with uri " + uri);
        final int match = sUriMatcher.match(uri);
        Log.d(TAG, "match is" + match);

        SQLiteDatabase db;
        int count;

        String selectionCriteria;

        switch (match) {
            case VISITOR:
                mOpenHelper = AppDatabase.getInstance(getContext());
                db = mOpenHelper.getWritableDatabase();
                count = db.update(VisitorContract.TABLE_NAME, values, selection, selectionArgs);
                break;

            case VISITOR_ID:
                db = mOpenHelper.getWritableDatabase();
                long visitorId = VisitorContract.getVisitorId(uri);
                selectionCriteria = VisitorContract.Columns._ID + " = " + visitorId;
                if ((selection != null) && (selection.length() > 0)) {
                    selectionCriteria += "AND (" + selection + ")";
                }
                count = db.update(VisitorContract.TABLE_NAME, values, selectionCriteria, selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown uri: " + uri);
        }
        if(count >0){
            //something was updated
            Log.d(TAG,"update: Setting notifyChange with" + uri);
            context.getContentResolver().notifyChange(uri, null);
        }else{
            Log.d(TAG,"update: nothing update");

        }
        Log.d(TAG,"Exiting update, returning " + count);
        return count;

    }
}
