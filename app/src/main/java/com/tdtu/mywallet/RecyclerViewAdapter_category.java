package com.tdtu.mywallet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.tdtu.mywallet.model.Category;

import java.util.List;

public class RecyclerViewAdapter_category extends RecyclerView.Adapter<CategoryViewHolder>{

    private List<Category> categoryList;
    private Context context;
    private LayoutInflater layoutInflater;

    public RecyclerViewAdapter_category(List<Category> categoryList, Context context) {
        this.categoryList = categoryList;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View recyclerView = layoutInflater.inflate(R.layout.layout_item_category, parent, false);
        return new CategoryViewHolder(recyclerView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.icon_category.setBackgroundResource(category.getIconResID());
        holder.name_category.setText(category.getCategoryName());

        String color = category.getCategoryColor();
        if(color.equals("black")){
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context,R.color.black));
        } else if(color.equals("white")){
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context,R.color.white));
        }if(color.equals("red")){
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context,R.color.red));
        }if(color.equals("green")){
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context,R.color.green));
        }if(color.equals("blue")){
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context,R.color.blue));
        }if(color.equals("orange")){
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context,R.color.orange));
        }if(color.equals("yellow")){
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context,R.color.yellow));
        }

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}
