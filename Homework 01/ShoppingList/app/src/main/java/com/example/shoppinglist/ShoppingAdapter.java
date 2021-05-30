package com.example.shoppinglist;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.ShoppingViewHolder>{
    private Context context;
    private Cursor cursor;

    public ShoppingAdapter(Context context, Cursor cursor){
        this.context = context;
        this.cursor = cursor;
    }

    public class ShoppingViewHolder extends RecyclerView.ViewHolder{
        public TextView nameText;
        public TextView costText;
        public CheckBox purchasedBox;
        public Button editButton;
        public ImageView categoryImage;

        public ShoppingViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.item_name);
            costText = itemView.findViewById(R.id.item_price);
            purchasedBox = itemView.findViewById(R.id.item_purchased);
            editButton = itemView.findViewById(R.id.item_edit);
            categoryImage = itemView.findViewById(R.id.item_category_img);
        }
    }

    @NonNull
    @NotNull
    @Override
    public ShoppingAdapter.ShoppingViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ShoppingAdapter.ShoppingViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
