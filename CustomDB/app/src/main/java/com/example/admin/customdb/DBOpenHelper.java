package com.example.admin.customdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by admin on 2017-12-26.
 */

public class DBOpenHelper {
    private static final String DB_NAME = DataBase._TABLENAME + ".db";
    private static final int DB_VERSION = 1;
    public static SQLiteDatabase mDB;
    private DBHelper mDBHelper;
    private Context mCtx;

    public DBOpenHelper(Context context) {
        this.mCtx = context;
    }

    public DBOpenHelper open() throws SQLiteException {
        mDBHelper = new DBHelper(mCtx, DB_NAME, null, DB_VERSION);
        mDB = mDBHelper.getWritableDatabase(); // DB READ, WRITE 권한 부여
        return this;
    }

    public void close() {
        mDB.close();
    }

    public long insert(String title, String time, String genre, String period, String link, String day) {
        ContentValues values = new ContentValues();
        values.put(DataBase.TITLE, title);
        values.put(DataBase.TIME, time);
        values.put(DataBase.GENRE, genre);
        values.put(DataBase.PERIOD, period);
        values.put(DataBase.LINK, link);
        values.put(DataBase.DAY, day);

        return mDB.insert(DataBase._TABLENAME, null, values);
    }

    public boolean update(int id, String title, String time, String genre, String period, String link, String day) {
        ContentValues values = new ContentValues();
        values.put(DataBase.TITLE, title);
        values.put(DataBase.TIME, time);
        values.put(DataBase.GENRE, genre);
        values.put(DataBase.PERIOD, period);
        values.put(DataBase.LINK, link);
        values.put(DataBase.DAY, day);

        return mDB.update(DataBase._TABLENAME, values, "_id=" + id, null) > 0;
    }
    //아이디로 삭제
    public boolean delete(int id) {
        return mDB.delete(DataBase._TABLENAME, "_id=" + id, null) > 0;
    }
    //타이틀로 삭제
    public boolean delete(String title){
        return mDB.delete(DataBase._TABLENAME, "title=" + title, null) > 0;
    }
    //모든 컬럼 가져오기
    public Cursor getAll() {
        return mDB.query(DataBase._TABLENAME, null, null, null, null, null, null);
    }
    //아이디로 특정 컬럼 가져오기
    public Cursor getColumn(long id) {
        Cursor c = mDB.query(DataBase._TABLENAME, null, "_id=" + id, null, null, null, null);
        if(c != null && c.getCount() != 0) {
            c.moveToFirst();
        }
        return c;
    }
    //타이틀로 특정 컬럼 가져오기
    public Cursor getColum(String title) {
        Cursor c = mDB.rawQuery("SELECT * FROM " + DataBase._TABLENAME + " WHERE TITLE = " + title + ";", null); // rawQuery는 일반적인 조회 sql
        return c;
    }

    private class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
            super(context, name, factory, version, errorHandler);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DataBase._CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DataBase._TABLENAME);
            onCreate(db);
        }
    }
}
