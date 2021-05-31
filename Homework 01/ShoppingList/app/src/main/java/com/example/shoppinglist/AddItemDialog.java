/*
 * CSE 390: Mobile App Development
 * Name: Bennie Chen
 * Student ID: 112737201
 */

package com.example.shoppinglist;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
                insertItem();
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

    public boolean insertItem(){
        return false;
    }

    public boolean updateItem(){
        return false;
    }
}
