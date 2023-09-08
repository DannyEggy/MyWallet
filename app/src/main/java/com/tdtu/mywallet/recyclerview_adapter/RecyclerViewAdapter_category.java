package com.tdtu.mywallet.recyclerview_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.tdtu.mywallet.CategoryViewHolder;
import com.tdtu.mywallet.R;
import com.tdtu.mywallet.RecyclerViewInterface;
import com.tdtu.mywallet.model.Category;

import java.util.List;

public class RecyclerViewAdapter_category extends RecyclerView.Adapter<CategoryViewHolder>{
    public final RecyclerViewInterface recyclerViewInterface;
    private List<Category> categoryList;
    private Context context;
    private LayoutInflater layoutInflater;

    public RecyclerViewInterface getRecyclerViewInterface() {
        return recyclerViewInterface;
    }
    public RecyclerViewAdapter_category(List<Category> categoryList, Context context) {
        this.categoryList = categoryList;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.recyclerViewInterface = getRecyclerViewInterface();
    }

    public RecyclerViewAdapter_category(List<Category> categoryList, Context context, RecyclerViewInterface recyclerViewInterface) {
        this.categoryList = categoryList;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.recyclerViewInterface = recyclerViewInterface;
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
        if(color.equals("Black")){
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context,R.color.gray_bg));
        } else if(color.equals("White")){
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context,R.color.white));
        }if(color.equals("Red")){
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context,R.color.red));
        }if(color.equals("Green")){
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context,R.color.green));
        }if(color.equals("Blue")){
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context,R.color.blue));
        }if(color.equals("Orange")){
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context,R.color.orange));
        }if(color.equals("Yellow")){
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context,R.color.yellow));
        }
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(recyclerViewInterface != null){
                    Toast.makeText(layoutInflater.getContext(), "success", Toast.LENGTH_LONG).show();
                    int pos = holder.getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        recyclerViewInterface.onItemLongClick(pos);
                    }
                }
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}
