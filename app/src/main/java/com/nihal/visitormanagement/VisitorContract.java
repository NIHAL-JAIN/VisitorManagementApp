package com.nihal.visitormanagement;
/*
* Class to declare fields
 */

import static com.nihal.visitormanagement.AppProvider.CONTENT_AUTHORITY;
import static com.nihal.visitormanagement.AppProvider.CONTENT_AUTHORITY_URI;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class VisitorContract {
    static final String TABLE_NAME = "Visitor";

    //Visitor Fields
    public static class Columns {

        //base columns is a abstract class
        public static final String _ID = BaseColumns._ID;
        public static final String VISITOR_NAME = "Name";
        public static final String VISITOR_PHONE = "Phone";
        public static final String VISITOR_ADDRESS = "Address";
        public static final String VISITOR_CITY = "City";
        public static final String VISITOR_SORTORDER = "SortOrder";
        public static String VISITOR_STATUS = "Status";

        private Columns(){
            //private Constructor to prevent instantiation
        }

    }

    /**
     * The URI to access the Visitor Table
     */
    public static final Uri CONTENT_URI = Uri.withAppendedPath(CONTENT_AUTHORITY_URI, TABLE_NAME);

    static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;
    static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;

    public static Uri buildVisitorUri(long visitorId){
        return ContentUris.withAppendedId(CONTENT_URI, visitorId);

    }
    public static long getVisitorId(Uri uri){
        return ContentUris.parseId(uri);
    }
}
