 package com.example.filloutform;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "FillOutForm.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table FormDetails(name TEXT primary key, gender TEXT, number TEXT, program TEXT, city TEXT, country TEXT, school TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists FormDetails");
    }

    public boolean insertData(String name, String gender, String number, String program, String city, String country, String school)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("name", name);
        contentValues.put("gender", gender);
        contentValues.put("number", number);
        contentValues.put("program", program);
        contentValues.put("city", city);
        contentValues.put("country", country);
        contentValues.put("school", school);

        long result = db.insert("FormDetails", null, contentValues);

        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean updateData(String name, String gender, String number, String program, String city, String country, String school)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        //No name because it's a primary key
        contentValues.put("gender", gender);
        contentValues.put("number", number);
        contentValues.put("program", program);
        contentValues.put("city", city);
        contentValues.put("country", country);
        contentValues.put("school", school);

        Cursor cursor = db.rawQuery("Select * from FormDetails where name = ?", new String[]{name});

        if(cursor.getCount() > 0) {
            long result = db.update("FormDetails", contentValues, "name=?", new String[] {name});

            if (result == -1)
                return false;
            else
                return true;
        }
        else
            return false;
    }

    public boolean deleteData(String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from FormDetails where name = ?", new String[]{name});

        if(cursor.getCount() > 0) {
            long result = db.delete("FormDetails", "name=?", new String[]{name});

            if (result == -1)
                return false;
            else
                return true;
        }
        else
            return false;
    }

    public Cursor getData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from FormDetails", null);

        return cursor;
    }
}
