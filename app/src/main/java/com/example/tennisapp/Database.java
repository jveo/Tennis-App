package com.example.tennisapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import com.example.tennisapp.pojo.racquetTypes;

import java.util.ArrayList;


public class Database extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "tennisClub";

    /* Table Name */
    public static final String TABLE_TENNIS = "tennis";


    /* Column Names */
    public static final String COLUMN_ID = "id";


    /* Tennis Table */
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_WEBSITE = "website";


    public static final String CREATE_TENNIS_TABLE = "CREATE TABLE " +
            TABLE_TENNIS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY," +
            COLUMN_NAME + " TEXT, " + COLUMN_DESCRIPTION + " TEXT, " +
            COLUMN_WEBSITE + " TEXT)";

    public Database(@Nullable Context context) {
        super(context, DATABASE_NAME ,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TENNIS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /* CRUD METHODS*/


    public void addRacquet(racquetTypes racquetTypes){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, racquetTypes.getName());
        values.put(COLUMN_DESCRIPTION, racquetTypes.getDescription());
        values.put(COLUMN_WEBSITE, racquetTypes.getWebsite());
        db.insert(TABLE_TENNIS, null, values);
        db.close();
    }

    public racquetTypes getRacquet(int id){

        SQLiteDatabase db = this.getReadableDatabase();
        racquetTypes racquet = null;
        Cursor cursor = db.query(TABLE_TENNIS,
                new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_DESCRIPTION, COLUMN_WEBSITE}, COLUMN_ID + "= ?",
                new String[]{String.valueOf(id)}, null, null, null);

        if(cursor.moveToFirst()){
            racquet = new racquetTypes(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3)
            );
        }
        db.close();
        return racquet;
    }

    public ArrayList<racquetTypes> getAllRacquets(){
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_TENNIS, null);
            ArrayList<racquetTypes> racquets = new ArrayList<>();
            while(cursor.moveToNext()){
                racquets.add(new racquetTypes(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)
                ));
            }

            db.close();
            return racquets;
    }

    public int updateLocation(racquetTypes rackets){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, rackets.getName());
        values.put(COLUMN_DESCRIPTION, rackets.getDescription());
        values.put(COLUMN_WEBSITE, rackets.getWebsite());

        return db.update(TABLE_TENNIS, values, COLUMN_ID + "=?",
                new String[]{String.valueOf(rackets.getId())});
    }

    public void deleteLocation(int racket){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TENNIS, COLUMN_ID +  "=?",
                new String[]{String.valueOf(racket)});
        db.close();
    }



}
