package com.example.firstweek;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import java.util.ArrayList;

public class ContactList {

    private Context context;

    public ContactList(Context context) {
        this.context = context;
    }

    public ArrayList<Contact> getContactList(){
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

        String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.CONTACT_ID, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};
        String[] selectionArgs = null;
        String sortOrder = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " COLLATE LOCALIZED ASC";

        Cursor contactCursor = context.getContentResolver().query(uri, projection, null, selectionArgs, sortOrder);
        ArrayList<Contact> contactList = new ArrayList<>();

        if(contactCursor.moveToFirst()){
            do {
                String phoneNumber = contactCursor.getString(1).replaceAll("-", "");

                phoneNumber = phoneNumber.substring(0, 3) + "-" + phoneNumber.substring(3, 7) + "-" + phoneNumber.substring(6);

                Contact contact = new Contact();
                contact.setId(contactCursor.getLong(0));
                contact.setPhoneNumber(phoneNumber);
                contact.setName(contactCursor.getString(2));
                contactList.add(contact);
            }while (contactCursor.moveToNext());
        }
        return contactList;
    }
}

class Contact{
    private long Id;
    private String phoneNumber;
    private String name;

    public void setId(long Id){
        this.Id = Id;
    }
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }
    public void setName(String name){
        this.name = name;
    }

    public long getId() {
        return Id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getName() {
        return name;
    }
}