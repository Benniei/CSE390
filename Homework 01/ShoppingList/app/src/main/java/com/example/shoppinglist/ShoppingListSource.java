package com.example.shoppinglist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class ShoppingListSource {
    private SQLiteDatabase database;
    private ShoppingListDBHelper dbHelper;
    private ShoppingAdapter mAdapter;
    private Context context;

    public ShoppingListSource(Context context){
        this.context = context;
        dbHelper = new ShoppingListDBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public void setmAdapter(ShoppingAdapter mAdapter) {
        this.mAdapter = mAdapter;
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }

    public int getLastContactID(){
        int lastID;
        try{
            String query = "Select MAX(_id) from " + ShoppingListContact.ShoppingListEntry.TABLE_NAME;
            Cursor cursor = database.rawQuery(query, null);

            cursor.moveToFirst();
            lastID = cursor.getInt(0);
            cursor.close();
        }catch(Exception e){
            lastID = -1;
        }
        return lastID;
    }

    public boolean insertItem(ShoppingItem s){
        boolean didSucceed = false;
        try{
            ContentValues initialValue = new ContentValues();

            initialValue.put(ShoppingListContact.ShoppingListEntry.COLUMN_NAME, s.getName());
            initialValue.put(ShoppingListContact.ShoppingListEntry.COLUMN_CATEGORY, s.getCategory());
            initialValue.put(ShoppingListContact.ShoppingListEntry.COLUMN_COST, s.getCost());
            initialValue.put(ShoppingListContact.ShoppingListEntry.COLUMN_DESCRIPTION, s.getDescription());
            if(s.isPurchased())
                initialValue.put(ShoppingListContact.ShoppingListEntry.COLUMN_PURCHASED, 1); // boolean as int
            else
                initialValue.put(ShoppingListContact.ShoppingListEntry.COLUMN_PURCHASED, 0); // boolean as int

            didSucceed = database.insert(ShoppingListContact.ShoppingListEntry.TABLE_NAME, null, initialValue) > 0;
            String sortBy =  context.getSharedPreferences("MyShoppingListPreferences", Context.MODE_PRIVATE).getString("sortfield", "recent");
            String sortOrder = context.getSharedPreferences("MyShoppingListPreferences", Context.MODE_PRIVATE).getString("sortorder", "ASC");
            mAdapter.swapCursor(getAllItems(sortBy, sortOrder));
        } catch(Exception e){
            // no nothing
        }
        return didSucceed;
    }

    public boolean updateItem(ShoppingItem s){
        boolean didSucceed = false;
        try{
            Long id = (long) s.getItemID();
            ContentValues updateValue = new ContentValues();

            updateValue.put(ShoppingListContact.ShoppingListEntry.COLUMN_NAME, s.getName());
            updateValue.put(ShoppingListContact.ShoppingListEntry.COLUMN_CATEGORY, s.getCategory());
            updateValue.put(ShoppingListContact.ShoppingListEntry.COLUMN_COST, s.getCost());
            updateValue.put(ShoppingListContact.ShoppingListEntry.COLUMN_DESCRIPTION, s.getDescription());
            if(s.isPurchased())
                updateValue.put(ShoppingListContact.ShoppingListEntry.COLUMN_PURCHASED, 1); // boolean as int
            else
                updateValue.put(ShoppingListContact.ShoppingListEntry.COLUMN_PURCHASED, 0); // boolean as int

            didSucceed = database.update(ShoppingListContact.ShoppingListEntry.TABLE_NAME, updateValue, "_id=" + id, null) > 0;
            String sortBy =  context.getSharedPreferences("MyShoppingListPreferences", Context.MODE_PRIVATE).getString("sortfield", "recent");
            String sortOrder = context.getSharedPreferences("MyShoppingListPreferences", Context.MODE_PRIVATE).getString("sortorder", "ASC");
            mAdapter.swapCursor(getAllItems(sortBy, sortOrder));
        } catch(Exception e){
            // no nothing
        }
        return didSucceed;
    }

    public Cursor getAllItems(String sortBy, String sortOrder){
        if(sortBy.equalsIgnoreCase("recent"))
            sortBy = "_id";
        return database.query(ShoppingListContact.ShoppingListEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                sortBy + " " + sortOrder);
    }

    public ShoppingItem getSpecificItem(int shoppingID){
        String sortBy =  context.getSharedPreferences("MyShoppingListPreferences", Context.MODE_PRIVATE).getString("sortfield", "recent");
        String sortOrder = context.getSharedPreferences("MyShoppingListPreferences", Context.MODE_PRIVATE).getString("sortorder", "ASC");
        ArrayList<ShoppingItem> items = getItems(sortBy, sortOrder);
        for (ShoppingItem i: items) {
            if (i.getItemID() == shoppingID)
                return i;
        }
        return new ShoppingItem();
    }

    public boolean updatePurchased(ShoppingItem item){
        boolean isSuccess;
        ContentValues cv = new ContentValues();
        if(!item.isPurchased()) {
            item.setPurchased(true);
            cv.put(ShoppingListContact.ShoppingListEntry.COLUMN_PURCHASED, 1);
        }
        else {
            item.setPurchased(false);
            cv.put(ShoppingListContact.ShoppingListEntry.COLUMN_PURCHASED, 0);
        }
        isSuccess = database.update(ShoppingListContact.ShoppingListEntry.TABLE_NAME, cv, "_id= " + item.getItemID(), null) > 0;
        return isSuccess;
    }

    public ArrayList<ShoppingItem> getItems(String sortBy, String sortOrder){
        ArrayList<ShoppingItem> items = new ArrayList<>();
        try{
            String query = "SELECT * FROM " + ShoppingListContact.ShoppingListEntry.TABLE_NAME + " ORDER BY " + sortBy + " " + sortOrder;
            Cursor cursor = database.rawQuery(query, null);

            ShoppingItem newItem;
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                newItem = new ShoppingItem();
                newItem.setItemID(cursor.getInt(0));
                newItem.setCategory(cursor.getString(1));
                newItem.setName(cursor.getString(2));
                newItem.setCost(cursor.getInt(3));
                newItem.setDescription(cursor.getString(4));
                if(cursor.getInt(5) == 0)
                    newItem.setPurchased(false);
                else
                    newItem.setPurchased(true);
                items.add(newItem);
                cursor.moveToNext();
            }
        }catch(Exception e){
            items = new ArrayList<ShoppingItem>();
        }
        return items;
    }
    public void removeItem(long id){
        database.delete(ShoppingListContact.ShoppingListEntry.TABLE_NAME,
                ShoppingListContact.ShoppingListEntry.ID + "=" + id, null);
        String sortBy =  context.getSharedPreferences("MyShoppingListPreferences", Context.MODE_PRIVATE).getString("sortfield", "recent");
        String sortOrder = context.getSharedPreferences("MyShoppingListPreferences", Context.MODE_PRIVATE).getString("sortorder", "ASC");
        mAdapter.swapCursor(getAllItems(sortBy, sortOrder));
    }

}
