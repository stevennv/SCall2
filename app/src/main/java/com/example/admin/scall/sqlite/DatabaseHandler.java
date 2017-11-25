package com.example.admin.scall.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.admin.scall.model.InfoStyle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 11/24/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "savestyle";
    private static final String TABLE_CONTACTS = "style";
    private static final String KEY_ID = "id";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_NAME = "name";
    private static final String KEY_FONT = "font";
    private static final String KEY_COLOR = "color";
    private static final String KEY_SIZE = "size";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," + KEY_PHONE + " TEXT,"
                + KEY_NAME + " TEXT" + KEY_FONT + " TEXT" + KEY_COLOR + " INTEGER" + KEY_SIZE + " INTEGER" + ")";
        sqLiteDatabase.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(sqLiteDatabase);
    }

   public void addContact(InfoStyle infoStyle) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PHONE, infoStyle.getPhone()); // Contact Name
        values.put(KEY_NAME, infoStyle.getName()); // Contact Phone
        values.put(KEY_FONT, infoStyle.getFont()); // Contact Phone
        values.put(KEY_COLOR, infoStyle.getColor()); // Contact Phone
        values.put(KEY_SIZE, infoStyle.getSize()); // Contact Phone

        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
    }

   public InfoStyle getContact(String name) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[]{KEY_ID,
                        KEY_PHONE, KEY_NAME, KEY_FONT, KEY_COLOR, KEY_SIZE}, KEY_NAME + "=?",
                new String[]{name}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        InfoStyle info = new InfoStyle(cursor.getString(0),
                cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4));
        // return contact
        return info;
    }

    public List<InfoStyle> getAllContacts() {
        List<InfoStyle> contactList = new ArrayList<InfoStyle>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                InfoStyle info = new InfoStyle();
                info.setId(cursor.getInt(0));
                info.setPhone(cursor.getString(1));
                info.setName(cursor.getString(2));
                info.setFont(cursor.getString(3));
                info.setColor(cursor.getInt(4));
                info.setSize(cursor.getInt(5));
                // Adding contact to list
                contactList.add(info);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    public int updateContact(InfoStyle info) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, info.getName());
        values.put(KEY_PHONE, info.getPhone());
        values.put(KEY_FONT, info.getFont());
        values.put(KEY_COLOR, info.getColor());
        values.put(KEY_SIZE, info.getSize());

        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(info.getId())});
    }

    public void deleteContact(InfoStyle info) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[]{String.valueOf(info.getId())});
        db.close();
    }
}
