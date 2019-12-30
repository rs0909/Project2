package com.example.realone;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
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
        String sortOrder = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " COLLATE LOCALIZED ASC";

        Cursor contactCursor = context.getContentResolver().query(uri, projection, null, null, sortOrder);
        ArrayList<Contact> contactList = new ArrayList<>();

        if(contactCursor.moveToFirst()){
            do {
                String phoneNumber = contactCursor.getString(1).replaceAll("-", "");

                phoneNumber = phoneNumber.substring(0, 3) + "-" + phoneNumber.substring(3, 7) + "-" + phoneNumber.substring(7);

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

class Contact implements Parcelable {
    private long Id;
    private String phoneNumber;
    private String name;
    private Uri photo;

    public Contact() {
    }

    protected Contact(Parcel in) {
        Id = in.readLong();
        phoneNumber = in.readString();
        name = in.readString();
        photo = in.readParcelable(Uri.class.getClassLoader());
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    public void setId(long Id){
        this.Id = Id;
    }
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setPhoto(Uri uri){this.photo = uri;}

    public long getId() {
        return Id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getName() {
        return name;
    }

    public Uri getPhoto(){return photo;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(Id);
        parcel.writeString(phoneNumber);
        parcel.writeString(name);
        parcel.writeParcelable(photo, i);
    }
}