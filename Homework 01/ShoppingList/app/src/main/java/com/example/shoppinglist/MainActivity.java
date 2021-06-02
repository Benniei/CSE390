/*
 * CSE 390: Mobile App Development
 * Name: Bennie Chen
 * Student ID: 112737201
 */

package com.example.shoppinglist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase database;
    private ShoppingAdapter adapter;

    /*
     * This method is called when the app is created
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); // Hides the toolbar on top
        setContentView(R.layout.activity_main);

        ShoppingListSource ds = new ShoppingListSource(this);
        FragmentManager fm = null;
        try {
            ShoppingListDBHelper dbHelper = new ShoppingListDBHelper(this);
            ds.open();
            database = ds.getDatabase();
            RecyclerView recyclerView = findViewById(R.id.ShoppingList);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            fm = getSupportFragmentManager();
            adapter = new ShoppingAdapter(this, ds.getAllItems());
            ds.setmAdapter(adapter);
            recyclerView.setAdapter(adapter);
        } catch(Exception e){
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }

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
        FragmentManager finalFm = fm;
        buttonAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddItemDialog addItemDialog = new AddItemDialog(database, ds);
                addItemDialog.show(finalFm, "AddItem");
            }
        });
    }
}