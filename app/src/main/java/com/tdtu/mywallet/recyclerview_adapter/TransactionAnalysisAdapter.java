package com.tdtu.mywallet.recyclerview_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tdtu.mywallet.R;
import com.tdtu.mywallet.model.Activity;
import com.tdtu.mywallet.model.Category;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TransactionAnalysisAdapter extends RecyclerView.Adapter<TransactionAnalysisAdapter.TransactionViewHolder> {
    private List<Activity> transactionList;
    private Context context;
    private LayoutInflater layoutInflater;
    private Activity transaction;
    private String id;
    private String money;
    private String type;
    private Category category;
    private DatabaseReference reference;

    public TransactionAnalysisAdapter(List<Activity> transactionList, Context context) {
        this.transactionList = transactionList;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.layout_item_activity, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        transaction = transactionList.get(position);
        id = transaction.getActivityID();
        category = transaction.getActivityCategory();
        money = transaction.getActivityMoney();
        type = transaction.getActivityType();

        // Connect to Firebase to get data
        connectFirebase();

        // This section needs to get data from a transaction and then display it on Tittle View  for the user
        // display name, time-date and place by getting the data above
        holder.item_name_Activity_anal.setText(transaction.getActivityName());
        long time_date = transaction.getActivityDateTime();
        Date dateDisplay = new Date(time_date);
        SimpleDateFormat dateFormatDisplay = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        String time_date_display = dateFormatDisplay.format(dateDisplay);
        holder.item_time_date_Activity_anal.setText(String.valueOf(time_date_display));
        String place = transaction.getActivityPlace();
        holder.item_place_Activity_anal.setText(place);

        // Displays the money, if Spending, add a "-" sign before the price
        // If Income, add a "+" sign before the price
        // Change format to 2,000 ₫
        BigDecimal amount = new BigDecimal(transaction.getActivityMoney());
        DecimalFormat decimalFormat = new DecimalFormat("#,##0 ₫");
        decimalFormat.setCurrency(Currency.getInstance("VND"));
        String formattedAmount = decimalFormat.format(amount);
        String displayMoney = null;
        if (transaction.getActivityType().equals("Spending")) {
            displayMoney = "-" + String.valueOf(formattedAmount);
        } else {
            displayMoney = "+" + String.valueOf(formattedAmount);
        }
        holder.item_money_Activity_anal.setText(displayMoney);

        // get color and then set the icon and background for category
        String color = category.getCategoryColor();
        setColorCategory(color, holder.card_icon_category_anal);
        // set card background for Tittle View
        int imageResID = holder.itemView.getResources().getIdentifier(category.getIconResID(), "drawable", context.getPackageName());
        if(imageResID != 0){
            holder.item_icon_category_anal.setBackgroundResource(imageResID);
        }else{
            Toast.makeText(context, "Something Wrong!!!", Toast.LENGTH_LONG).show();
        }

    }



    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    private void connectFirebase(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        String uid = user.getUid().toString();
        reference = firebaseDatabase.getReference(uid);
    }

    private void setColorCategory(String color, CardView card_icon_category) {
        if (color.equals("Black")) {
            card_icon_category.setCardBackgroundColor(ContextCompat.getColor(context, R.color.gray));
//            cardEdit.setCardBackgroundColor(ContextCompat.getColor(context, R.color.gray));
        } else if (color.equals("White")) {
            card_icon_category.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white));
//            cardEdit.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white));
        }
        if (color.equals("Red")) {
            card_icon_category.setCardBackgroundColor(ContextCompat.getColor(context, R.color.red));
//            cardEdit.setCardBackgroundColor(ContextCompat.getColor(context, R.color.red));
        }
        if (color.equals("Green")) {
            card_icon_category.setCardBackgroundColor(ContextCompat.getColor(context, R.color.green));
//            cardEdit.setCardBackgroundColor(ContextCompat.getColor(context, R.color.green));
        }
        if (color.equals("Blue")) {
            card_icon_category.setCardBackgroundColor(ContextCompat.getColor(context, R.color.blue));
//            cardEdit.setCardBackgroundColor(ContextCompat.getColor(context, R.color.blue));
        }
        if (color.equals("Orange")) {
            card_icon_category.setCardBackgroundColor(ContextCompat.getColor(context, R.color.orange));
//            cardEdit.setCardBackgroundColor(ContextCompat.getColor(context, R.color.orange));
        }
        if (color.equals("Yellow")) {
            card_icon_category.setCardBackgroundColor(ContextCompat.getColor(context, R.color.yellow));
//            cardEdit.setCardBackgroundColor(ContextCompat.getColor(context, R.color.yellow));
        }
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


