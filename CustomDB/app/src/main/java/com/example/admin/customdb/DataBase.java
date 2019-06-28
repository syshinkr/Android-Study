package com.example.admin.customdb;

import android.provider.BaseColumns;

/**
 * Created by admin on 2017-12-26.
 */

public final class DataBase implements BaseColumns {
        public static final String TITLE = "title";
        public static final String TIME = "time";
        public static final String GENRE = "genre";
        public static final String PERIOD = "period";
        public static final String LINK = "link";
        public static final String DAY = "day";
        public static final String _TABLENAME = "info";
        public static final String _CREATE =
                "CREATE TABLE " + _TABLENAME
                + "("
                + _ID + "INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TITLE + "TEXT NOT NULL, "
                + TIME + "TEXT NOT NULL, "
                + GENRE + "TEXT NOT NULL, "
                + PERIOD + "TEXT NOT NULL, "
                + LINK + "TEXT NOT NULL, "
                + DAY + "TEXT NOT NULL"
                + ");";
}
