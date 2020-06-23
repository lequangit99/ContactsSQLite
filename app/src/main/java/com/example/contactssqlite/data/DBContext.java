package com.example.contactssqlite.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.contactssqlite.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class DBContext extends SQLiteOpenHelper {
    private final String TAG = "DBManager";
    private static final String DATABASE_NAME = "contacts_manager";
    private static final String TABLE_NAME = "contacts";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String PHONE_NUMBER = "phone";
    private static final String EMAIL = "email";
    private static final String BIRTHDAY = "birthday";
    private static int VERSION = 1;

    private Context context;

    private String SQLQuery = "CREATE TABLE " + TABLE_NAME + " (" +
            ID + " integer primary key, " +
            NAME + " TEXT, " +
            PHONE_NUMBER + " TEXT, " +
            EMAIL + " TEXT, " +
            BIRTHDAY + " TEXT)";

    public DBContext(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQLQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, contact.getmName());
        values.put(PHONE_NUMBER, contact.getmPhoneNumber());
        values.put(EMAIL, contact.getmEmail());
        values.put(BIRTHDAY, contact.getmBirthday());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public List<Contact> getAllContact() {
        List<Contact> listContact = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setmId(cursor.getInt(0));
                contact.setmName(cursor.getString(1)+"");
                contact.setmPhoneNumber(cursor.getString(2));
                contact.setmEmail(cursor.getString(3));
                contact.setmBirthday(cursor.getString(4));
                listContact.add(contact);

            } while (cursor.moveToNext());
        }
        db.close();
        return listContact;
    }
    public int updateContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME,contact.getmName());
        contentValues.put(PHONE_NUMBER,contact.getmPhoneNumber());
        contentValues.put(BIRTHDAY,contact.getmBirthday());
        contentValues.put(EMAIL,contact.getmEmail());
        return db.update(TABLE_NAME,contentValues,ID+"=?",new String[]{String.valueOf(contact.getmId())});
    }
    public int deleteContact(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,ID+"=?",new String[] {String.valueOf(id)});
    }
}
