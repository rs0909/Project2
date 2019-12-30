package com.example.realone;

import android.util.Log;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;

public class ContactItem implements Serializable{
    private String user_phNumber, user_Name;
    private long photo_id=0, person_id=0;
    private int id;
    public ContactItem(){}
    public long getPhoto_id(){
        return photo_id;
    }
    public long getPerson_id(){
        return person_id;
    }
    public void setPhoto_id(long id){
        this.photo_id = id;
    }
    public void setPerson_id(long id){
        this.person_id = id;
    }
    public String getUser_phNumber(){
        return user_phNumber;
    }
    public String getUser_Name(){
        return user_Name;
    }
    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return id;
    }
    public void setUser_phNumber(String string){
        this.user_phNumber = string;
    }
    public void setUser_Name(String string){
        this.user_Name = string;
    }
    @Override
    public String toString() {
        return this.user_phNumber;
    }
    @Override
    public int hashCode() {
        return getPhNumberChanged().hashCode();
    }
    public String getPhNumberChanged(){
        return user_phNumber.replace("-", "");
    }
    @Override
    public boolean equals(Object o) {
        if (o instanceof ContactItem)
            return getPhNumberChanged().equals(((ContactItem) o).getPhNumberChanged());
        return false;
    }


public ArrayList<ContactItem> getContactList(Context context) {
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection = new String[]{
        ContactsContract.CommonDataKinds.Phone.NUMBER,
        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
        ContactsContract.Contacts.PHOTO_ID,
        ContactsContract.Contacts._ID
        };
        String[] selectionArgs = null;
        String sortOrder = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
            + " COLLATE LOCALIZED ASC";
        Cursor cursor = context.getContentResolver().query(uri, projection, null,
                selectionArgs, sortOrder);
        LinkedHashSet<ContactItem> hashlist = new LinkedHashSet<>();
        if (cursor.moveToFirst()) {
            do {
                long photo_id = cursor.getLong(2);
                long person_id = cursor.getLong(3);
                ContactItem contactItem = new ContactItem();
                contactItem.setUser_phNumber(cursor.getString(0));
                contactItem.setUser_Name(cursor.getString(1));
                contactItem.setPhoto_id(photo_id);
                contactItem.setPerson_id(person_id);

                hashlist.add(contactItem);
                } while (cursor.moveToNext());
            }
            ArrayList<ContactItem> contactItems = new ArrayList<>(hashlist);
            for (int i = 0; i < contactItems.size(); i++) {
                contactItems.get(i).setId(i);
            }
            return contactItems;
}

public Bitmap loadContactPhoto(ContentResolver cr, long id, long photo_id) {
    Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, id);
    InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(cr, uri);
    if (input != null)
        return resizingBitmap(BitmapFactory.decodeStream(input));
    else
        Log.d("PHOTO", "first try failed to load photo");
    byte[] photoBytes = null;
    Uri photoUri = ContentUris.withAppendedId(ContactsContract.Data.CONTENT_URI, photo_id);
    Cursor c = cr.query(photoUri, new String[]{ContactsContract.CommonDataKinds.Photo.PHOTO}, null, null, null);
    try {
        if (c.moveToFirst())
            photoBytes = c.getBlob(0);
    } catch (Exception e) {
        // TODO: handle exception
        e.printStackTrace();
    } finally {
        c.close();
    }
    if (photoBytes != null)
        return resizingBitmap(BitmapFactory.decodeByteArray(photoBytes, 0, photoBytes.length));
    else
        Log.d("PHOTO", "second try also failed");
    return null;
}
    public Bitmap resizingBitmap(Bitmap oBitmap) {
        if (oBitmap == null)
            return null;
        float width = oBitmap.getWidth();
        float height = oBitmap.getHeight();
        float resizing_size = 120;
        Bitmap rBitmap = null;
        if (width > resizing_size) {
            float mWidth = (float) (width / 100);
            float fScale = (float) (resizing_size / mWidth);
            width *= (fScale / 100);
            height *= (fScale / 100);
        } else if (height > resizing_size) {
            float mHeight = (float) (height / 100);
            float fScale = (float) (resizing_size / mHeight);
            width *= (fScale / 100);
            height *= (fScale / 100);
        }
        Log.d("tag","rBitmap : " + width + ", " + height);
        rBitmap = Bitmap.createScaledBitmap(oBitmap, (int) width, (int) height, true);
        return rBitmap;
    }

}
