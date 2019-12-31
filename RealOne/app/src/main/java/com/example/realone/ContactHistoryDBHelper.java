package com.example.realone;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

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
        stringBuffer.append(" NAME TEXT, ");
        stringBuffer.append(" PHONE TEXT, ");
        stringBuffer.append(" COUNT INTEGER, ");
        stringBuffer.append(" PHOTO INTEGER ) ");

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
    private boolean haveNoCallLog = false;

    //연락처를 읽어서 데이터베이스에 저장한다.
    public SettingContactHistoryDB(Context context) {
        this.context = context;
        contactHistoryDBHelper = new ContactHistoryDBHelper(context, dbname, null, 1);
        contactHistoryDBHelper.getWritableDatabase().execSQL("DELETE FROM CONTACT_HISTORY_TABLE;");
        settingDB();
    }

    public boolean getHaveNoCallLog(){
        return haveNoCallLog;
    }

    public void settingDB(){
        int permissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CALL_LOG);
        if(permissionCheck == PackageManager.PERMISSION_GRANTED){
            Cursor cursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, CallLog.Calls.DEFAULT_SORT_ORDER);
            if(cursor.getCount() > 0){
                while(cursor.moveToNext()){
                    String name = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
                    String phoneNumber = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_FORMATTED_NUMBER));
                    if(phoneNumber != null){
                        settingDBLine(name, phoneNumber);
                    }
                }
            }else{
                haveNoCallLog = true;
                return;
            }
        }
    }

    public void settingDBLine(String name, String phoneNumber){
        SQLiteDatabase db;
        int defaultCount = 1;
        int count = 1;
        String sql;
        String name1 = null;
        String phoneNumber1 = null;
        Object[] objects;
        long photoId = 0;
        Cursor contactCurcor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.Contacts.PHOTO_ID, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME}, null, null, null);
        if(contactCurcor.moveToFirst()){
            do{
                if(phoneNumber.equals( contactCurcor.getString(0))){
                    photoId = contactCurcor.getLong(1);
                    name1 = contactCurcor.getString(2);
                    phoneNumber1 = contactCurcor.getString(0);
                    break;
                }
            }while (contactCurcor.moveToNext());
        }
        if(name1 == null){
            return;
        }
        db = contactHistoryDBHelper.getWritableDatabase();

        count = checkCount(name1, phoneNumber1);
        if( count == 0){
            sql = String.format("INSERT INTO CONTACT_HISTORY_TABLE ( NAME, PHONE, COUNT, PHOTO ) VALUES(?, ?, ?, ?);");
            objects = new Object[]{name, phoneNumber, defaultCount, photoId};
        }else{

            db.execSQL("DELETE FROM CONTACT_HISTORY_TABLE WHERE PHONE = '" + phoneNumber + "';");
            sql = String.format("INSERT INTO CONTACT_HISTORY_TABLE ( NAME, PHONE, COUNT, PHOTO ) VALUES (?, ?, ?, ?);");
            objects = new Object[]{name, phoneNumber, count+1, photoId};
        }

        db.execSQL(sql, objects);
    }

    public int checkCount(String name, String phone){
        SQLiteDatabase db = contactHistoryDBHelper.getReadableDatabase();
        String sql = "SELECT * FROM CONTACT_HISTORY_TABLE;";
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                if(cursor.getString(0).equals(name) && cursor.getString(1).equals(phone)){
                    return cursor.getInt(2);
                }
            }
        }
        return 0;
    }

    //개인 연락처에서 통화를 많이한 친구들을 골라냄
    public ArrayList<Contact> getFriendsArray(SQLiteDatabase db){
        ArrayList<Contact> arrayList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM CONTACT_HISTORY_TABLE order by COUNT desc", null);
        if(cursor.moveToFirst()){
            do{
                Contact contact = new Contact();
                contact.setName(cursor.getString(0));
                contact.setPhoneNumber(cursor.getString(1));
                contact.setPhotoId(cursor.getLong(3));
                arrayList.add(contact);
            }while(cursor.moveToNext());
        }
        return arrayList;
    }

    //내가 통화를 많이한 친구들과 가입해있는 친구들을 비교하여 통화를 많이하고 가입도 되어있는 친구들만 골라낸다.
    public ArrayList<Contact> getJoinedFriendArray(ArrayList<Contact> joinedPeopleArray){
        SQLiteDatabase db = contactHistoryDBHelper.getReadableDatabase();
        ArrayList<Contact> friendArray = getFriendsArray(db);
        ArrayList<Contact> joinedFriedArray = new ArrayList<>();
        for(int i = 0; i < friendArray.size(); i++){
            for(int j = 0; j < joinedPeopleArray.size(); j++){
                if(friendArray.get(i).getPhoneNumber().equals(joinedPeopleArray.get(j).getPhoneNumber())){
                    joinedFriedArray.add(friendArray.get(i));
                    j = joinedPeopleArray.size()-1;
                }
            }
        }
        return joinedFriedArray;
    }
}