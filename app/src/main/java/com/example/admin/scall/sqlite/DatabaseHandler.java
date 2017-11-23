package com.example.admin.scall.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.admin.scall.model.Contact;
import com.example.admin.scall.model.InfoStyle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 11/23/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "MakeYourStyle";

    // Contacts table name
    private static final String TABLE_NAME = "style";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_FONT = "font";
    private static final String KEY_COLOR = "color";
    private static final String KEY_SIZE = "size";
    private static final String[] COLUMNS = {KEY_ID, KEY_NAME, KEY_FONT, KEY_COLOR, KEY_SIZE};

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_FONT + " TEXT" + KEY_COLOR + " INTEGER" + KEY_SIZE + " INTEGER" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */
    public void addStyle(InfoStyle infoStyle) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, infoStyle.getId());
        values.put(KEY_NAME, infoStyle.getName());
        values.put(KEY_FONT, infoStyle.getFont());
        values.put(KEY_COLOR, infoStyle.getColorName());
        values.put(KEY_SIZE, infoStyle.getSize());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public InfoStyle searchById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_ID,
                        KEY_NAME, KEY_FONT, KEY_COLOR, KEY_SIZE}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        InfoStyle infoStyle = new InfoStyle(Integer.parseInt(cursor.getString(0)), cursor.getString(1)
                , cursor.getString(2), Integer.parseInt(cursor.getString(3)), Integer.parseInt(cursor.getString(4)));
        return infoStyle;
    }

    public InfoStyle search2(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =
                db.query(TABLE_NAME, // a. table
                        COLUMNS, // b. column names
                        " id = ?", // c. selections
                        new String[]{String.valueOf(id)}, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit
        if (cursor != null)
            cursor.moveToFirst();
        InfoStyle infoStyle = new InfoStyle();
        infoStyle.setId(cursor.getInt(0));
        infoStyle.setName(cursor.getString(1));
        infoStyle.setFont(cursor.getString(2));
        infoStyle.setColorName(cursor.getInt(3));
        infoStyle.setSize(cursor.getInt(4));
        return infoStyle;
    }

    public List<InfoStyle> getAllList() {
        List<InfoStyle> list = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                InfoStyle style = new InfoStyle();
                style.setId(Integer.parseInt(cursor.getString(0)));
                style.setName(cursor.getString(1));
                style.setFont(cursor.getString(2));
                style.setColorName(Integer.parseInt(cursor.getString(3)));
                style.setSize(Integer.parseInt(cursor.getString(4)));
                // Adding contact to list
                list.add(style);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public int updateStyle(InfoStyle infoStyle) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, infoStyle.getName());
        values.put(KEY_FONT, infoStyle.getFont());
        values.put(KEY_COLOR, infoStyle.getColorName());
        values.put(KEY_SIZE, infoStyle.getSize());
        return db.update(TABLE_NAME, values, KEY_ID + " = ?",
                new String[]{String.valueOf(infoStyle.getId())});
    }

    public void deleteStyle(InfoStyle infoStyle) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + " = ?",
                new String[]{String.valueOf(infoStyle.getId())});
        db.close();
    }

}