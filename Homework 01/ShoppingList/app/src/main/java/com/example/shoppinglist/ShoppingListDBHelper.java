/*
 * CSE 390: Mobile App Development
 * Name: Bennie Chen
 * Student ID: 112737201
 */

package com.example.shoppinglist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.shoppinglist.ShoppingListContact.*;

public class ShoppingListDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "shoppingList.db";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE_CONTACT = "CREATE TABLE " +
            ShoppingListEntry.TABLE_NAME + " (" +
            ShoppingListEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ShoppingListEntry.COLUMN_CATEGORY + " TEXT NOT NULL, " +
            ShoppingListEntry.COLUMN_NAME + " TEXT NOT NULL, " +
            ShoppingListEntry.COLUMN_COST + " INTEGER NOT NULL, " +
            ShoppingListEntry.COLUMN_DESCRIPTION + " TEXT, " +
            ShoppingListEntry.COLUMN_PURCHASED + " INTEGER NOT NULL" +
            ");";

    /**
     * Constructor for the DB helper
     * @param context parent context
     */
    public ShoppingListDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This method creates the database
     * @param db database to be created
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CONTACT);
    }

    /**
     * This method is used to make changes to the database
     * @param db The new database
     * @param oldVersion The version of the current database
     * @param newVersion The Versio of the new database
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +  ShoppingListEntry.TABLE_NAME);
        onCreate(db);
    }
}
