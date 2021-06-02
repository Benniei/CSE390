/*
 * CSE 390: Mobile App Development
 * Name: Bennie Chen
 * Student ID: 112737201
 */

package com.example.shoppinglist;

import android.app.Dialog;
import android.content.ContentValues;
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
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.ShoppingViewHolder>{
    private final Context context;
    private Cursor cursor;
    private ShoppingListSource ds;
    public ShoppingAdapterListener onClickListener;
    private boolean isVisible;

    public ShoppingAdapter(Context context, Cursor cursor, ShoppingListSource ds, ShoppingAdapterListener listener){
        this.context = context;
        this.cursor = cursor;
        this.ds = ds;
        onClickListener = listener;
        isVisible = false;
    }

    public interface ShoppingAdapterListener {
        void editButtonClick(View v, int position);
    }

    public class ShoppingViewHolder extends RecyclerView.ViewHolder{
        public TextView nameText;
        public TextView costText;
        public CheckBox purchasedBox;
        public ImageView categoryImage;
        public TextView descriptionText;
        public LinearLayout container;
        public ShoppingViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.item_name);
            costText = itemView.findViewById(R.id.item_price);
            purchasedBox = itemView.findViewById(R.id.item_purchased);
            categoryImage = itemView.findViewById(R.id.item_category_img);
            descriptionText = itemView.findViewById(R.id.item_description);
            container = itemView.findViewById(R.id.outer_container);
            Button editButton = itemView.findViewById(R.id.item_edit);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isVisible = !isVisible;
                    if(isVisible){
                        descriptionText.setVisibility(View.VISIBLE);
                    }
                    else{
                        descriptionText.setVisibility(View.GONE);
                    }
                }
            });

            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.editButtonClick(v, getAdapterPosition());
                }
            });
            purchasedBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<ShoppingItem> currentItems = ds.getItems();
                    int qPosition = getAdapterPosition();
                    ShoppingItem item = currentItems.get(qPosition);
                    if(!ds.updatePurchased(item))
                        Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
                }
            });
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
        int cost = cursor.getInt(cursor.getColumnIndex(ShoppingListContact.ShoppingListEntry.COLUMN_COST));
        String description = cursor.getString(cursor.getColumnIndex(ShoppingListContact.ShoppingListEntry.COLUMN_DESCRIPTION));
        String category = cursor.getString(cursor.getColumnIndex(ShoppingListContact.ShoppingListEntry.COLUMN_CATEGORY));
        int purchased = cursor.getInt(cursor.getColumnIndex(ShoppingListContact.ShoppingListEntry.COLUMN_PURCHASED));

        // Set the image
        if(category.equals("Food")){
            Drawable d = context.getResources().getDrawable(R.drawable.items_food);
            holder.categoryImage.setImageDrawable(d);
            holder.container.setBackgroundColor(context.getResources().getColor(R.color.food));
        }
        else if(category.equals("Electronics")){
            Drawable d = context.getResources().getDrawable(R.drawable.items_electronics);
            holder.categoryImage.setImageDrawable(d);
            holder.container.setBackgroundColor(context.getResources().getColor(R.color.electronics));
        }
        else if(category.equals("Books")){
            Drawable d = context.getResources().getDrawable(R.drawable.items_books);
            holder.categoryImage.setImageDrawable(d);
            holder.container.setBackgroundColor(context.getResources().getColor(R.color.books));
        }
        else if(category.equals("Others")){
            Drawable d = context.getResources().getDrawable(R.drawable.item_others);
            holder.categoryImage.setImageDrawable(d);
            holder.container.setBackgroundColor(context.getResources().getColor(R.color.others));
        }

        holder.nameText.setText(name);
        String costFormat = cost/100 + "." + cost%100;
        holder.costText.setText(costFormat);
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
