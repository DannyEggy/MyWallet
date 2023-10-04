package com.tdtu.mywallet.recyclerview_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.tdtu.mywallet.R;
import com.tdtu.mywallet.model.Activity;

import java.util.List;

public class TransactionAnalysisAdapter extends RecyclerView.Adapter<TransactionAnalysisAdapter.TransactionViewHolder> {
    private List<Activity> transactionList;
    private Context context;
    private LayoutInflater layoutInflater;



    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.layout_item_activity, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {

    }



    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public class TransactionViewHolder extends RecyclerView.ViewHolder{
        private CardView card_icon_category_anal;
        private ImageView item_icon_category_anal;
        private TextView item_name_Activity_anal;
        private TextView item_time_date_Activity_anal;
        private TextView item_place_Activity_anal;
        private TextView item_money_Activity_anal;


        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            card_icon_category_anal = itemView.findViewById(R.id.card_icon_category_anal);
            item_icon_category_anal = itemView.findViewById(R.id.item_icon_category_anal);
            item_name_Activity_anal = itemView.findViewById(R.id.item_name_Activity_anal);
            item_time_date_Activity_anal = itemView.findViewById(R.id.item_time_date_Activity_anal);
            item_place_Activity_anal = itemView.findViewById(R.id.item_place_Activity_anal);
            item_money_Activity_anal = itemView.findViewById(R.id.item_money_Activity_anal);
        }
    }
}


