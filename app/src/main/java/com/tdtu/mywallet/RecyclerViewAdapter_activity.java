package com.tdtu.mywallet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.tdtu.mywallet.model.Activity;
import com.tdtu.mywallet.model.Category;

import java.util.List;

public class RecyclerViewAdapter_activity extends RecyclerView.Adapter<ActivityViewHolder> {
    private List<Activity> activityList;
    private Context context;
    private LayoutInflater layoutInflater;

    public RecyclerViewAdapter_activity(List<Activity> activityList, Context context) {
        this.activityList = activityList;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View recyclerViewItem =layoutInflater.inflate(R.layout.layout_item_activity, parent, false);


        return new ActivityViewHolder(recyclerViewItem) ;
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityViewHolder holder, int position) {
        Activity activity =this.activityList.get(position);
        Category category = activity.getActivityCategory();

        holder.item_icon_category.setBackgroundResource(category.getIconResID());

        holder.item_name_Actitvity.setText(activity.getActivityName());
        String time_date = activity.getActivityTimeDate();
        holder.item_time_date_Actitvity.setText(time_date);
        String money = String.valueOf(activity.getActivityMoney()+ " VND");
        holder.item_money_Activity.setText(money);

        String color = category.getCategoryColor();
        if(color.equals("black")){
            holder.card_icon_category.setCardBackgroundColor(ContextCompat.getColor(context,R.color.black));
        } else if(color.equals("white")){
            holder.card_icon_category.setCardBackgroundColor(ContextCompat.getColor(context,R.color.white));
        }if(color.equals("red")){
            holder.card_icon_category.setCardBackgroundColor(ContextCompat.getColor(context,R.color.red));
        }if(color.equals("green")){
            holder.card_icon_category.setCardBackgroundColor(ContextCompat.getColor(context,R.color.green));
        }if(color.equals("blue")){
            holder.card_icon_category.setCardBackgroundColor(ContextCompat.getColor(context,R.color.blue));
        }if(color.equals("orange")){
            holder.card_icon_category.setCardBackgroundColor(ContextCompat.getColor(context,R.color.orange));
        }if(color.equals("yellow")){
            holder.card_icon_category.setCardBackgroundColor(ContextCompat.getColor(context,R.color.yellow));
        }
    }



    @Override
    public int getItemCount() {
        return activityList.size();
    }


}
