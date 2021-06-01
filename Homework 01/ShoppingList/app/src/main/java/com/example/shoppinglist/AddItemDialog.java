/*
 * CSE 390: Mobile App Development
 * Name: Bennie Chen
 * Student ID: 112737201
 */

package com.example.shoppinglist;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

public class AddItemDialog extends DialogFragment implements AdapterView.OnItemSelectedListener {
    private SQLiteDatabase database;
    public AddItemDialog(SQLiteDatabase database){
        this.database = database;
    }

    /*
     * This method creates the dialog and initializes the Spinner and the Cancel and Save Buttons
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.add_shopping_item, container);

        // Spinner for selecting options that are located in string.xml
        Spinner itemSpinner = view.findViewById(R.id.item_category);
        // Spinner Click Listener
        itemSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.shopping_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemSpinner.setAdapter(adapter);
        itemSpinner.setOnItemSelectedListener(this);

        // Save Button
        Button saveButton = view.findViewById(R.id.buttonSave);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ((EditText) view.findViewById(R.id.Name_input)).getText().toString();
                String cost = ((EditText) view.findViewById(R.id.Cost_input)).getText().toString();
                String category = ((Spinner) view.findViewById(R.id.item_category)).getSelectedItem().toString();
                String description = ((EditText) view.findViewById(R.id.Description_input)).getText().toString();
                boolean purchase = ((CheckBox) view.findViewById(R.id.Purchased)).isChecked();

                ShoppingItem s = new ShoppingItem();

                s.setName(name);
                s.setCost(cost);
                s.setCategory(category);
                s.setDescription(description);
                s.setPurchased(purchase);

                insertItem(s); // TODO: replace shopping item
                getDialog().dismiss();
            }
        });
        // Cancel Button
        Button cancelButton = view.findViewById(R.id.buttonCancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        return view;
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
            Long id = (long) s.getItemID();
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
        } catch(Exception e){
            // no nothing
        }
        return didSucceed;
    }

    public boolean updateItem(){
        return false;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // DO NOTHING
        // String text = parent.getItemAtPosition(position).toString();
        //Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Do nothing
    }
}
