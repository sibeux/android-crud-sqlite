package com.hacktiv8.crudsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    //Database Version
    private static final int DB_VERSION = 1;

    //Database Name
    private static final String DB_NAME = "WORLD";

    //Country Table Name
    private static final String TABLE_COUNTRY = "country";

    private static final String KEY_ID = "id";
    private static final String COUNTRY_NAME = "country_name";
    private static final String POPULATION = "population";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tableCountry = " CREATE TABLE "+TABLE_COUNTRY+" ("+
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COUNTRY_NAME + " TEXT, "+
                POPULATION + " LONG ) ";

        db.execSQL(tableCountry);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS "+TABLE_COUNTRY);
        onCreate(db);
    }

    public List<Country> getAllCountries(){

        List<Country> countryList = new ArrayList<>();

        String allCountry = "SELECT * FROM "+TABLE_COUNTRY;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(allCountry, null);

        if(cursor.moveToFirst()){
            do{
                Country country = new Country();
                country.setId(Integer.parseInt(cursor.getString(0)));
                country.setCountryName(cursor.getString(1));
                country.setPopulation(cursor.getLong(2));

                countryList.add(country);

            }while (cursor.moveToNext());
        }

        return countryList;

    }

    void addCountry(Country country){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COUNTRY_NAME, country.getCountryName());
        values.put(POPULATION, country.getPopulation());

        Log.i("DBHELPER ",country.getCountryName()+ " - "+country.getPopulation());

        db.insert(TABLE_COUNTRY, null, values);
        db.close();

    }

}
