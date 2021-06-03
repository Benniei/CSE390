/*
 * CSE 390: Mobile App Development
 * Name: Bennie Chen
 * Student ID: 112737201
 */

package com.example.shoppinglist;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import androidx.fragment.app.DialogFragment;


public class ShoppingSettingFragment extends DialogFragment  {
    View view;
    /*
     * This method creates the dialog and initializes the Spinner and the Cancel and Save Buttons
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.shopping_settings, container);

        Button cancelButton = view.findViewById(R.id.buttonCancel_settings);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        initSettings();
        initSortByClick();
        initSortOrderClick();


        return view;
    }

    private void initSettings(){
        String sortBy = getContext().getSharedPreferences("MyShoppingListPreferences", Context.MODE_PRIVATE).getString("sortfield", "recent");
        String sortOrder = getContext().getSharedPreferences("MyShoppingListPreferences", Context.MODE_PRIVATE).getString("sortorder", "ASC");

        RadioButton rbRecent = view.findViewById(R.id.radioRecent);
        RadioButton rbName = view.findViewById(R.id.radioName);
        RadioButton rbCost = view.findViewById(R.id.radioCost);
        RadioButton rbCategory = view.findViewById(R.id.radioCategory);
        RadioButton rbPurchased = view.findViewById(R.id.radioPurchased);
        if(sortBy.equalsIgnoreCase("recent"))
            rbRecent.setChecked(true);
        else if(sortBy.equalsIgnoreCase("name"))
            rbName.setChecked(true);
        else if(sortBy.equalsIgnoreCase("cost"))
            rbCost.setChecked(true);
        else if(sortBy.equalsIgnoreCase("category"))
            rbCategory.setChecked(true);
        else if(sortBy.equalsIgnoreCase("purchased"))
            rbPurchased.setChecked(true);

        RadioButton rbAscending = view.findViewById(R.id.radioAscending);
        RadioButton rbDescending = view.findViewById(R.id.radioDescending);
        if(sortOrder.equalsIgnoreCase("ASC"))
            rbAscending.setChecked(true);
        else
            rbDescending.setChecked(true);
    }

    private void initSortByClick(){
        RadioGroup rgSortBy = view.findViewById(R.id.radioGroupSortBy);
        rgSortBy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rbRecent = view.findViewById(R.id.radioRecent);
                RadioButton rbName = view.findViewById(R.id.radioName);
                RadioButton rbCost = view.findViewById(R.id.radioCost);
                RadioButton rbCategory = view.findViewById(R.id.radioCategory);
                if(rbRecent.isChecked())
                    getContext().getSharedPreferences("MyShoppingListPreferences", Context.MODE_PRIVATE).edit().putString("sortfield", "recent").apply();
                else if(rbName.isChecked())
                    getContext().getSharedPreferences("MyShoppingListPreferences", Context.MODE_PRIVATE).edit().putString("sortfield", "name").apply();
                else if(rbCost.isChecked())
                    getContext().getSharedPreferences("MyShoppingListPreferences", Context.MODE_PRIVATE).edit().putString("sortfield", "cost").apply();
                else if(rbCategory.isChecked())
                    getContext().getSharedPreferences("MyShoppingListPreferences", Context.MODE_PRIVATE).edit().putString("sortfield", "category").apply();
                else
                    getContext().getSharedPreferences("MyShoppingListPreferences", Context.MODE_PRIVATE).edit().putString("sortfield", "purchased").apply();
            }
        });
    }

    private void initSortOrderClick() {
        RadioGroup rgSortOrder = view.findViewById(R.id.radioGroupSortOrder);
        rgSortOrder.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                RadioButton rbAscending = view.findViewById(R.id.radioAscending);
                if (rbAscending.isChecked()) {
                    getContext().getSharedPreferences("MyShoppingListPreferences", Context.MODE_PRIVATE).edit().putString("sortorder", "ASC").apply();
                }
                else {
                    getContext().getSharedPreferences("MyShoppingListPreferences", Context.MODE_PRIVATE).edit().putString("sortorder", "DESC").apply();
                }
            }
        });
    }

}
