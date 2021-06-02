/*
 * CSE 390: Mobile App Development
 * Name: Bennie Chen
 * Student ID: 112737201
 */

package com.example.shoppinglist;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.ShoppingViewHolder>{
    private final Context context;
    private Cursor cursor;

    public ShoppingAdapter(Context context, Cursor cursor){
        this.context = context;
        this.cursor = cursor;
    }

    public class ShoppingViewHolder extends RecyclerView.ViewHolder{
        public TextView nameText;
        public TextView costText;
        public CheckBox purchasedBox;
        public ImageView categoryImage;
        public TextView descriptionText;
        public ShoppingViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.item_name);
            costText = itemView.findViewById(R.id.item_price);
            purchasedBox = itemView.findViewById(R.id.item_purchased);
            categoryImage = itemView.findViewById(R.id.item_category_img);
            descriptionText = itemView.findViewById(R.id.item_description);

        }

        public TextView getNameText() {
            return nameText;
        }

        public TextView getCostText() {
            return costText;
        }

        public CheckBox getPurchasedBox() {
            return purchasedBox;
        }

        public ImageView getCategoryImage() {
            return categoryImage;
        }

        public TextView getDescriptionText() {
            return descriptionText;
        }
    }

    @NonNull
    @NotNull
    @Override
    public ShoppingAdapter.ShoppingViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_item, parent, false);
        return new ShoppingViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ShoppingAdapter.ShoppingViewHolder holder, int position) {
        if (!cursor.moveToPosition(position)) {
            return;
        }

        String name = cursor.getString(cursor.getColumnIndex(ShoppingListContact.ShoppingListEntry.COLUMN_NAME));
        String cost = cursor.getString(cursor.getColumnIndex(ShoppingListContact.ShoppingListEntry.COLUMN_COST));
        String description = cursor.getString(cursor.getColumnIndex(ShoppingListContact.ShoppingListEntry.COLUMN_DESCRIPTION));
        String category = cursor.getString(cursor.getColumnIndex(ShoppingListContact.ShoppingListEntry.COLUMN_CATEGORY));
        int purchased = cursor.getInt(cursor.getColumnIndex(ShoppingListContact.ShoppingListEntry.COLUMN_PURCHASED));

        // Set the image
        if(category.equals("Food")){
            Drawable d = context.getResources().getDrawable(R.drawable.items_food);
            holder.categoryImage.setImageDrawable(d);
        }
        else if(category.equals("Electronics")){
            Drawable d = context.getResources().getDrawable(R.drawable.items_electronics);
            holder.categoryImage.setImageDrawable(d);
        }
        else if(category.equals("Books")){
            Drawable d = context.getResources().getDrawable(R.drawable.items_books);
            holder.categoryImage.setImageDrawable(d);
        }
        else if(category.equals("Others")){
            Drawable d = context.getResources().getDrawable(R.drawable.item_others);
            holder.categoryImage.setImageDrawable(d);
        }

        holder.nameText.setText(name);
        holder.costText.setText(cost);
        holder.descriptionText.setText(description);

        if(purchased == 1)
            holder.purchasedBox.setChecked(true);
        else if(purchased == 0)
            holder.purchasedBox.setChecked(false);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public void swapCursor(Cursor newCursor){
        if(cursor != null)
            cursor.close();

        cursor = newCursor;

        if(newCursor != null)
            notifyDataSetChanged();
    }
}
