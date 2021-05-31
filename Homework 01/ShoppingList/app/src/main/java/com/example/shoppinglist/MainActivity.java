/*
 * CSE 390: Mobile App Development
 * Name: Bennie Chen
 * Student ID: 112737201
 */

package com.example.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivity extends AppCompatActivity {
    // TODO: Implement ShoppingAdapter.java + Implement Database and Adapter in the main method
    /*
     * This method is called when the app is created
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); // Hides the toolbar on top
        setContentView(R.layout.activity_main);

        // Buttons on the homepage
        ImageButton buttonSettings = findViewById(R.id.settings_button);
        FloatingActionButton buttonAddItem = findViewById(R.id.add_item);

        // Settings Button initializing the setting which allows for sorting oft he Recycler List
        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // Add Item Button which creates a dialog to input the item
        buttonAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                AddItemDialog addItemDialog = new AddItemDialog();
                addItemDialog.show(fm, "AddItem");
            }
        });
    }

    private Cursor getAllItems(){
        return null;
    }
}