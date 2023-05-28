package com.tdtu.mywallet;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class ActivityViewHolder extends RecyclerView.ViewHolder {

    public ImageView item_icon_category;
    public TextView item_name_Actitvity;
    public TextView item_time_date_Actitvity;
    public TextView item_money_Activity;

    public CardView card_icon_category;

    public ActivityViewHolder(@NonNull View itemView) {
        super(itemView);

        this.item_icon_category =itemView.findViewById(R.id.item_icon_category);
        this.item_name_Actitvity = itemView.findViewById(R.id.item_name_Activity);
        this.item_time_date_Actitvity = itemView.findViewById(R.id.item_time_date_Activity);
        this.item_money_Activity = itemView.findViewById(R.id.item_money_Activity);
        this.card_icon_category = itemView.findViewById(R.id.card_icon_category);
    }
}
