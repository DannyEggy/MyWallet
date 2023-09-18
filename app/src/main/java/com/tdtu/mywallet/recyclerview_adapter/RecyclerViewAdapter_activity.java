package com.tdtu.mywallet.recyclerview_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.tdtu.mywallet.ActivityViewHolder;
import com.tdtu.mywallet.R;
import com.tdtu.mywallet.model.Activity;
import com.tdtu.mywallet.model.Category;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

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
        View recyclerViewItem = layoutInflater.inflate(R.layout.layout_item_activity, parent, false);


        return new ActivityViewHolder(recyclerViewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityViewHolder holder, int position) {
        Activity activity = this.activityList.get(position);
        Category category = activity.getActivityCategory();
        String type = activity.getActivityType();
        int imageResID = holder.itemView.getResources().getIdentifier(category.getIconResID(),"drawable",context.getPackageName());
        if(imageResID != 0){
            holder.item_icon_category.setBackgroundResource(imageResID);
        }else{
            Toast.makeText(context, "Something Wrong!!!", Toast.LENGTH_LONG).show();
        }

        holder.item_name_Actitvity.setText(activity.getActivityName());
        long time_date = activity.getActivityDateTime();
        holder.item_time_date_Actitvity.setText(String.valueOf(time_date));
        String place = activity.getActivityPlace();
        holder.item_place_Activity.setText(place);
        if (type.equals("Spending")) {
            // change format to 2,000 ₫
            BigDecimal amount = new BigDecimal(activity.getActivityMoney());
            DecimalFormat decimalFormat = new DecimalFormat("#,##0 ₫");
            decimalFormat.setCurrency(Currency.getInstance("VND"));
            String formattedAmount = decimalFormat.format(amount);
            String displayMoney = "-" + String.valueOf(formattedAmount);

            holder.item_money_Activity.setText(displayMoney);
        } else {
            // change format to 2,000 ₫
            BigDecimal amount = new BigDecimal(activity.getActivityMoney());
            DecimalFormat decimalFormat = new DecimalFormat("#,##0 ₫");
            decimalFormat.setCurrency(Currency.getInstance("VND"));
            String formattedAmount = decimalFormat.format(amount);
            String displayMoney = "+" + String.valueOf(formattedAmount);
            holder.item_money_Activity.setText(displayMoney);
        }


        String color = category.getCategoryColor();
        if (color.equals("Black")) {
            holder.card_icon_category.setCardBackgroundColor(ContextCompat.getColor(context, R.color.gray));
        } else if (color.equals("White")) {
            holder.card_icon_category.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white));
        }
        if (color.equals("Red")) {
            holder.card_icon_category.setCardBackgroundColor(ContextCompat.getColor(context, R.color.red));
        }
        if (color.equals("Green")) {
            holder.card_icon_category.setCardBackgroundColor(ContextCompat.getColor(context, R.color.green));
        }
        if (color.equals("Blue")) {
            holder.card_icon_category.setCardBackgroundColor(ContextCompat.getColor(context, R.color.blue));
        }
        if (color.equals("Orange")) {
            holder.card_icon_category.setCardBackgroundColor(ContextCompat.getColor(context, R.color.orange));
        }
        if (color.equals("Yellow")) {
            holder.card_icon_category.setCardBackgroundColor(ContextCompat.getColor(context, R.color.yellow));
        }
    }


    @Override
    public int getItemCount() {
        return activityList.size();
    }


}
