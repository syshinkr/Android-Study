package personal.project.sampledb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by admin on 2017-12-18.
 */

public class DBOpenHelper {
    private static final String DATABASE_NAME =  DataBases.CreateDB._TABLENAME + ".db";
    private static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase mDB;
    private DatabaseHelper mDBHelper;
    private Context mCtx;

    private class DatabaseHelper extends SQLiteOpenHelper{

        //생성자
        public DatabaseHelper(Context context, String name,
                              SQLiteDatabase.CursorFactory factory, int version){
            super(context, name, factory, version);
        }

        //최초 DB 생성 시 호출
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DataBases.CreateDB._CREATE);
        }

        //버전 업데이트 시 DB 재생성
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DataBases.CreateDB._TABLENAME);
            onCreate(db);
        }
    }

    public DBOpenHelper(Context context) {
        this.mCtx = context;
    }

    public DBOpenHelper open() throws SQLException {
        mDBHelper = new DatabaseHelper(mCtx, DATABASE_NAME, null, DATABASE_VERSION);
        mDB = mDBHelper.getWritableDatabase(); //DB 읽거나 쓸 수 있는 권한 부여
        return this;
    }
    public void close() {
        mDB.close();
    }

    public long insertColumn(String name, String contact, String email) {
        ContentValues values = new ContentValues();
        values.put(DataBases.CreateDB.NAME, name);
        values.put(DataBases.CreateDB.CONTACT, contact);
        values.put(DataBases.CreateDB.EMAIL, email);

        return mDB.insert(DataBases.CreateDB._TABLENAME, null, values);
    }

    public boolean updatedColumn(long id, String name, String contact, String email){
        ContentValues values = new ContentValues();
        values.put(DataBases.CreateDB.NAME, name);
        values.put(DataBases.CreateDB.CONTACT, contact);
//        values.put(DataBases.CreateDB.EMAIL, email);

        return mDB.update(DataBases.CreateDB._TABLENAME, values, "_id="+id, null) > 0;
    }

    public boolean deleteColumn(long id){
        return mDB.delete(DataBases.CreateDB._TABLENAME, "_id="+id, null) > 0;
    }

    public boolean deleteColumn(String number) {
        return mDB.delete(DataBases.CreateDB._TABLENAME, "_contact="+number, null) > 0;
    }

    //select all
    public Cursor getAllColumns() {
        return mDB.query(DataBases.CreateDB._TABLENAME, null, null, null, null, null, null);
    }

    // ID로 컬럼 가져오기
    public Cursor getColumn(long id) {
        Cursor c = mDB.query(DataBases.CreateDB._TABLENAME, null, "_id="+id, null, null, null, null);
        if (c != null && c.getCount() != 0) {
            c.moveToFirst();
        }
        return c;
    }

    public Cursor getMatchName(String name) {
        Cursor c = mDB.rawQuery("select * from " + DataBases.CreateDB._TABLENAME + "where name=" + "" + name + "", null);
        return c;
    }
}
