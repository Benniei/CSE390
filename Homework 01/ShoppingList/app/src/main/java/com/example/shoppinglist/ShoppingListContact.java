/*
 * CSE 390: Mobile App Development
 * Name: Bennie Chen
 * Student ID: 112737201
 */

package com.example.shoppinglist;

import android.provider.BaseColumns;

/*
 * This class is used to store the number of databases that exist
 * Source: https://codinginflow.com/tutorials/android/sqlite-recyclerview/part-2-cursoradapter
 */
public class ShoppingListContact {

    private ShoppingListContact(){}

    /*
     * This class stores the constants that be being used in the SHopping List Database
     */
    public static final class ShoppingListEntry implements BaseColumns {
        public static final String TABLE_NAME = "shoppingList";
        public static final String COLUMN_CATEGORY = "category";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_COST = "cost";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_PURCHASED = "purchased";
    }
}
