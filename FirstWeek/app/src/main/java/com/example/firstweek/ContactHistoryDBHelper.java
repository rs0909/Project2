package com.example.firstweek;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.CallLog;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

public class ContactHistoryDBHelper extends SQLiteOpenHelper {
    private Context context;

    public ContactHistoryDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(" CREATE TABLE CONTACT_HISTORY_TABLE ( ");
        stringBuffer.append(" _ID INTEGER PRIMARY KEY AUTOINCREMENT, ");
        stringBuffer.append(" NAME TEXT, ");
        stringBuffer.append(" PHONE TEXT, ");
        stringBuffer.append(" COUNT INTEGER ) ");

        db.execSQL(stringBuffer.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS CONTACT_HISTORY_TABLE");
        onCreate(db);
    }
}


class SettingContactHistoryDB{
    private Context context;
    final static String dbname = "CONTACT_HISTORY.db";
    private ContactHistoryDBHelper  contactHistoryDBHelper;

    public SettingContactHistoryDB(Context context) {
        this.context = context;
        contactHistoryDBHelper = new ContactHistoryDBHelper(context, dbname, null, 1);
        contactHistoryDBHelper.getWritableDatabase().execSQL("DELETE FROM CONTACT_HISTORY_TABLE;");
        settingDB();

    }

    public void settingDB(){
        int permissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CALL_LOG);
        if(permissionCheck == PackageManager.PERMISSION_GRANTED){
            Cursor cursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, CallLog.Calls.DEFAULT_SORT_ORDER);

            if(cursor.getCount() > 0){
                while(cursor.moveToNext()){
                    String name = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
                    String phoneNumber = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_FORMATTED_NUMBER));
                    settingDBLine(name, phoneNumber);
                }
            }
        }
    }

    public void settingDBLine(String name, String phoneNumber){
        SQLiteDatabase db;
        int defaultCount = 1;
        int count = 1;
        String sql;
        Object[] objects;

        db = contactHistoryDBHelper.getWritableDatabase();


        count = checkCount(name, phoneNumber);
        if( count == 0){
            sql = String.format("INSERT INTO CONTACT_HISTORY_TABLE ( NAME, PHONE, COUNT ) VALUES(?, ?, ?);");
            objects = new Object[]{name, phoneNumber, defaultCount};
        }else{

            db.execSQL("DELETE FROM CONTACT_HISTORY_TABLE WHERE PHONE = '" + phoneNumber + "';");
            sql = String.format("INSERT INTO CONTACT_HISTORY_TABLE ( NAME, PHONE, COUNT ) VALUES (?, ?, ?);");
            objects = new Object[]{name, phoneNumber, count+1};
        }

        db.execSQL(sql, objects);
    }

    public int checkCount(String name, String phone){
        SQLiteDatabase db = contactHistoryDBHelper.getReadableDatabase();
        String sql = "SELECT * FROM CONTACT_HISTORY_TABLE;";
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                if(cursor.getString(1).equals(name) && cursor.getString(2).equals(phone)){
                    return cursor.getInt(3);
                }
            }
        }
        return 0;
    }
}