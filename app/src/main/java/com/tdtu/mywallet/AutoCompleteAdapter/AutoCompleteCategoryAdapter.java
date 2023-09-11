package com.tdtu.mywallet.AutoCompleteAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.tdtu.mywallet.R;
import com.tdtu.mywallet.model.Category;
import com.tdtu.mywallet.model.Icon;

import java.util.List;

public class AutoCompleteCategoryAdapter extends ArrayAdapter<Category> {
    private List<Category> categoryList;
    public AutoCompleteCategoryAdapter(@NonNull Context context, @NonNull List<Category> categoryList) {
        super(context, 0, categoryList);

    }



    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Lấy View hiện tại hoặc tạo mới nếu chưa có
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.layout_category_selection, parent, false);
        }

        // Lấy đối tượng Icon tại vị trí position
        Category category = getItem(position);
        CardView cardView = view.findViewById(R.id.color_category_selection);
        String color = category.getCategoryColor();
        if(color.equals("Black")){
            cardView.setCardBackgroundColor(ContextCompat.getColor(getContext(),R.color.gray_bg));
        } else if(color.equals("White")){
            cardView.setCardBackgroundColor(ContextCompat.getColor(getContext(),R.color.white));
        }if(color.equals("Red")){
            cardView.setCardBackgroundColor(ContextCompat.getColor(getContext(),R.color.red));
        }if(color.equals("Green")){
            cardView.setCardBackgroundColor(ContextCompat.getColor(getContext(),R.color.green));
        }if(color.equals("Blue")){
            cardView.setCardBackgroundColor(ContextCompat.getColor(getContext(),R.color.blue));
        }if(color.equals("Orange")){
           cardView.setCardBackgroundColor(ContextCompat.getColor(getContext(),R.color.orange));
        }if(color.equals("Yellow")){
            cardView.setCardBackgroundColor(ContextCompat.getColor(getContext(),R.color.yellow));
        }


        ImageView imageView = view.findViewById(R.id.icon_category_selection);
        int imageResID = view.getResources().getIdentifier(category.getIconResID(), "drawable", getContext().getPackageName());
        if(imageResID != 0){
            imageView.setBackgroundResource(imageResID);
        }else{
            Toast.makeText(getContext(), "Something Wrong!!!", Toast.LENGTH_LONG).show();
        }

        // Ánh xạ TextView trong layout
        TextView textView = view.findViewById(R.id.name_category_selection);

        // Thiết lập giá trị cho TextView
        if (category != null) {
            textView.setText(category.getCategoryName());
        }

        return view;
    }
}