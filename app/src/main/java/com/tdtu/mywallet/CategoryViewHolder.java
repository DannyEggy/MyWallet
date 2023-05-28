package com.tdtu.mywallet;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryViewHolder extends RecyclerView.ViewHolder {

    public ImageView icon_category;
    public TextView name_category;

    public CardView cardView;

    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);

        this.icon_category = itemView.findViewById(R.id.icon_category);
        this.name_category = itemView.findViewById(R.id.name_category);
        this.cardView = itemView.findViewById(R.id.color_category);

    }
}
