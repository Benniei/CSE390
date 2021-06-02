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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

public class AddItemDialog extends DialogFragment implements AdapterView.OnItemSelectedListener {
    private SQLiteDatabase database;
    private ShoppingListSource ds;
    private View view;
    private ShoppingItem currentItem;

    public AddItemDialog(SQLiteDatabase database, ShoppingListSource ds){
        this.database = database;
        this.ds = ds;
    }

    /*
     * This method creates the dialog and initializes the Spinner and the Cancel and Save Buttons
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.add_shopping_item, container);

        // Spinner for selecting options that are located in string.xml
        Spinner itemSpinner = view.findViewById(R.id.item_category);
        // Spinner Click Listener
        itemSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.shopping_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemSpinner.setAdapter(adapter);
        itemSpinner.setOnItemSelectedListener(this);
        initTextChangedEvents();

        Bundle bundle = getArguments();
        if(bundle != null)
            initItem(bundle.getInt("itemID"));
        else
            currentItem = new ShoppingItem();

        // Save Button
        Button saveButton = view.findViewById(R.id.buttonSave);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean wasSuccessful;
                currentItem.setCost_temp(((EditText) view.findViewById(R.id.Cost_input)).getText().toString());
                currentItem.setPurchased(((CheckBox) view.findViewById(R.id.Purchased)).isChecked());
                if(currentItem.getName().trim() == ""){
                    Toast.makeText(getContext(), "Please fill in the Name Field", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    if (currentItem.getItemID() == -1)
                        wasSuccessful = ds.insertItem(currentItem);
                    else
                        wasSuccessful = ds.updateItem(currentItem);
                }catch(Exception e){
                    wasSuccessful = false;
                }
                if(!wasSuccessful){
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                }
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

    private void initTextChangedEvents(){
        final EditText etItemName = view.findViewById(R.id.Name_input);
        etItemName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                currentItem.setName(etItemName.getText().toString());
            }
        });
        final EditText etItemDescription = view.findViewById(R.id.Description_input);
        etItemDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                currentItem.setDescription(etItemDescription.getText().toString());
            }
        });
        final Spinner etItemCategory = view.findViewById(R.id.item_category);
        etItemCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentItem.setCategory(etItemCategory.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initItem(int id){
        currentItem = ds.getSpecificItem(id);
        EditText editName = view.findViewById(R.id.Name_input);
        EditText editCost = view.findViewById(R.id.Cost_input);
        EditText editDescription = view.findViewById(R.id.Description_input);
        Spinner editCategory = view.findViewById(R.id.item_category);
        CheckBox editPurchased = view.findViewById(R.id.Purchased);

        editName.setText(currentItem.getName());
        int cost = currentItem.getCost();
        String costFormat = cost/100 + "." + cost%100;
        editCost.setText(costFormat);
        editDescription.setText(currentItem.getDescription());
        if(currentItem.getCategory().equalsIgnoreCase("food"))
            editCategory.setSelection(0);
        else if(currentItem.getCategory().equalsIgnoreCase("electronics"))
            editCategory.setSelection(1);
        else if(currentItem.getCategory().equalsIgnoreCase("books"))
            editCategory.setSelection(2);
        else if(currentItem.getCategory().equalsIgnoreCase("others"))
            editCategory.setSelection(3);
        else
            editCategory.setSelection(0);

        if(currentItem.isPurchased())
            editPurchased.setChecked(true);
        else
            editPurchased.setChecked(false);
    }
}
