/*
 * CSE 390: Mobile App Development
 * Name: Bennie Chen
 * Student ID: 112737201
 * Resource Referenced: https://codinginflow.com/tutorials/android/sqlite-recyclerview/part-3-swipe-to-delete
 */

package com.example.shoppinglist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.ItemTouchHelper;
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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase database;
    private ShoppingAdapter adapter;
    private ShoppingListSource ds;

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
                FragmentManager fm = getSupportFragmentManager();
                ShoppingSettingFragment addItemDialog = new ShoppingSettingFragment();
                addItemDialog.show(fm, "Settings");
            }
        });

        // Add Item Button which creates a dialog to input the item
        buttonAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                AddItemDialog addItemDialog = new AddItemDialog(database, ds);
                addItemDialog.show(fm, "AddItem");
            }
        });
    }

    public void onResume(){
        super.onResume();

        String sortBy = getSharedPreferences("MyShoppingListPreferences", Context.MODE_PRIVATE).getString("sortfield", "recent");
        String sortOrder = getSharedPreferences("MyShoppingListPreferences", Context.MODE_PRIVATE).getString("sortorder", "ASC");

        ds = new ShoppingListSource(this);
        try {
            ShoppingListDBHelper dbHelper = new ShoppingListDBHelper(this);
            ds.open();
            database = ds.getDatabase();
            RecyclerView recyclerView = findViewById(R.id.ShoppingList);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new ShoppingAdapter(this, ds.getAllItems(sortBy, sortOrder), ds, new ShoppingAdapter.ShoppingAdapterListener() {
                @Override
                public void editButtonClick(View v, int position) {
                    ArrayList<ShoppingItem> currentItems = ds.getItems(sortBy, sortOrder);
                    int itemID = currentItems.get(position).getItemID();
                    AddItemDialog addItemDialog = new AddItemDialog(database, ds);
                    Bundle bundle = new Bundle();
                    bundle.putInt("itemID", itemID);
                    addItemDialog.setArguments(bundle);
                    addItemDialog.show(getSupportFragmentManager(), "AddItem");
                }
            });
            ds.setmAdapter(adapter);
            new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                    ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT){

                @Override
                public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
                    ds.removeItem((long) viewHolder.itemView.getTag());
                }
            }).attachToRecyclerView(recyclerView);
            recyclerView.setAdapter(adapter);
        } catch(Exception e){
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }
}