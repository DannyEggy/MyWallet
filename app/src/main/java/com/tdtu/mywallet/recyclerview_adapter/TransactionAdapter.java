package com.tdtu.mywallet.recyclerview_adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.ramotion.foldingcell.FoldingCell;
import com.tdtu.mywallet.AutoCompleteAdapter.AutoCompleteCategoryAdapter;
import com.tdtu.mywallet.R;
import com.tdtu.mywallet.model.Activity;
import com.tdtu.mywallet.model.Category;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.List;
import java.util.Locale;


public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {
    private List<Activity> transactionList;
    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayAdapter<Category> adapterCategory;
    private final List<Category> categoryList = new ArrayList<Category>();
    private Category activityCategory;
    private DatabaseReference reference;
    private Activity activity;
    private String id;
    private String money;

    private String type;

    private Category category;


    private void connectFirebase() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        String uid = user.getUid().toString();
        reference = firebaseDatabase.getReference(uid);
    }

    //todo
    // getting the Category List from Firebase
    // before getting list, delete the previous list to avoid repeating it
    private void getCategoryList() {
        // clear to avoid repeat list
        categoryList.clear();
        //connect to firebase
        connectFirebase();
        reference.child("User Detail").child("userCategory").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Category category = snapshot.getValue(Category.class);
                if (category != null) {
                    categoryList.add(category);
                }
                adapterCategory.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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

    public TransactionAdapter(List<Activity> transactionList, Context context) {
        this.transactionList = transactionList;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.layout_transaction_folding, parent, false);
        return new TransactionViewHolder(view);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        // Get default data including activity, id, category, money and type of this transaction
        activity = transactionList.get(position);
        id = activity.getActivityID();
        category = activity.getActivityCategory();
        money = activity.getActivityMoney();
        type = activity.getActivityType();

        // Enable/Disable FoldingCell's expand mode on click
        // Every time click on a transaction, retrieve the category list to display
        // Clear categoryList then retrieve it to avoid repeating it.
        // Get activity and several other data when click 1 transaction
        // To update 1 transaction more than 2 times.
        // The reason is because when updating from the second time, the variables have not been updated so we need to redefine.
        holder.foldingCell.setOnClickListener((View view) -> {
            holder.foldingCell.toggle(false);
            categoryList.clear();
            getCategoryList();
            activity = transactionList.get(position);
            id = activity.getActivityID();
            category = activity.getActivityCategory();
            money = activity.getActivityMoney();
            type = activity.getActivityType();
            // edited section
        });
        // Connect to Firebase to get data and save data.
        connectFirebase();

        //______________________________________________________________________________________________________________________________________________________________________________________
        // ***TittleView Section***
        // This section needs to get data from a transaction and then display it on Tittle View  for the user
        // display name, time-date and place by getting the data above
        holder.item_name_Actitvity.setText(activity.getActivityName());
        String time_date = activity.getActivityTimeDate();
        holder.item_time_date_Actitvity.setText(time_date);
        String place = activity.getActivityPlace();
        holder.item_place_Activity.setText(place);

        // Displays the money, if Spending, add a "-" sign before the price
        // If Income, add a "+" sign before the price
        // Change format to 2,000 ₫
        BigDecimal amount = new BigDecimal(activity.getActivityMoney());
        DecimalFormat decimalFormat = new DecimalFormat("#,##0 ₫");
        decimalFormat.setCurrency(Currency.getInstance("VND"));
        String formattedAmount = decimalFormat.format(amount);
        String displayMoney = null;
        if (activity.getActivityType().equals("Spending")) {
            displayMoney = "-" + String.valueOf(formattedAmount);
        } else {
            displayMoney = "+" + String.valueOf(formattedAmount);
        }
        holder.item_money_Activity.setText(displayMoney);

        // get color and then set the icon and background for category
        String color = category.getCategoryColor();
        setColorCategory(color, holder.card_icon_category);
        // set card background for Tittle View
        int imageResID = holder.itemView.getResources().getIdentifier(category.getIconResID(), "drawable", context.getPackageName());
        if(imageResID != 0){
            holder.item_icon_category.setBackgroundResource(imageResID);
        }else{
            Toast.makeText(context, "Something Wrong!!!", Toast.LENGTH_LONG).show();
        }


        //______________________________________________________________________________________________________________________________________________________________________________________
        // ***ContentView section***
        // This section needs to get data from a transaction and then display it on Content View  for the user
        // display name, time-date and place by getting the data
        holder.et_nameActivity_edit.setText(activity.getActivityName());
        holder.et_moneyActivity_edit.setText(activity.getActivityMoney());

        // split the string of time-date to 2 string of time and date then display it to users
        String[] time_date_edit = activity.getActivityTimeDate().split(" ");
        String time = time_date_edit[0];
        String date = time_date_edit[1];
        holder.et_timeActivity_edit.setText(time);
        holder.et_dateActivity_edit.setText(date);

        // display place
        if (activity.getActivityPlace() == null) {
            holder.et_placeActivity_edit.setText("");
        } else {
            holder.et_placeActivity_edit.setText(place);
        }

        //*******************************************************
        // **Category section**
        // default category
        activityCategory = category;

        //set the current Category
        setColorCategory(color, holder.cardEdit);
        if(imageResID != 0){
            holder.itemIconEdit.setBackgroundResource(imageResID);
        }else{
            Toast.makeText(context, "Something Wrong!!!", Toast.LENGTH_LONG).show();
        }

//        holder.itemIconEdit.setBackgroundResource(category.getIconResID());

        holder.et_categoryActivity_edit.setText(category.getCategoryName());

        // set category selection by define Adapter and set it to AutoCompleteTextView
        adapterCategory = new AutoCompleteCategoryAdapter(context, categoryList);
        holder.et_categoryActivity_edit.setAdapter(adapterCategory);

        // if click -> choose again CategoryOption by showDropDown.
        // hide keyboard when select the Category.
        holder.et_categoryActivity_edit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                holder.et_categoryActivity_edit.showDropDown();
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(holder.et_categoryActivity_edit.getWindowToken(), 0);
                return false;
            }
        });

        // category selection
        holder.et_categoryActivity_edit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Category selectedCategory = (Category) adapterView.getItemAtPosition(i);
                String categoryName = selectedCategory.getCategoryName();
                String categoryIcon = selectedCategory.getIconResID();
                Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();

                // when select, set the icon and background category
                int imageResIDEdit = holder.itemView.getResources().getIdentifier(categoryIcon, "drawable", context.getPackageName());
                if(imageResID != 0){
                    holder.itemIconEdit.setBackgroundResource(imageResIDEdit);
                }else{
                    Toast.makeText(context, "Something Wrong!!!", Toast.LENGTH_LONG).show();
                }

                String color = selectedCategory.getCategoryColor();
                setColorCategory(color, holder.cardEdit);

                // update current category which is being selected
                activityCategory = selectedCategory;
            }
        });

        // set time picker and date picker for user to choose
        holder.et_timeActivity_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the current time
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                // Create a TimePickerDialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        context,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                // Handle the selected time
                                holder.et_timeActivity_edit.setText(String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute));
                            }
                        },
                        hour, minute, true);

                // Show the TimePickerDialog
                timePickerDialog.show();
            }
        });


        holder.et_dateActivity_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // Create DatePickerDialog and set initial date
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Handle selected date
                        String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                        holder.et_dateActivity_edit.setText(selectedDate);
                    }
                }, year, month, day);

                // Show the DatePickerDialog
                datePickerDialog.show();
            }
        });
        // _______________________________________________________________________________________________________________________________
        // ***Handle edit button***
        holder.btnEdit.setOnClickListener((View view)->{
            // Define the edited Transaction after user enter data
            String activityName = holder.et_nameActivity_edit.getText().toString();
            String activityMoney = holder.et_moneyActivity_edit.getText().toString().replaceAll("[^0-9]", "");
            String activityTimeDate = holder.et_timeActivity_edit.getText().toString() + " " + holder.et_dateActivity_edit.getText().toString();
            String activityPlace = holder.et_placeActivity_edit.getText().toString();
            Activity editedActivity = new Activity(id, activityName, activityMoney, activityCategory, type, activityTimeDate, activityPlace);

            // Update the edited Transaction to transactionList
            transactionList.set(position,editedActivity);

            // update
            reference.child("User Detail").child("userActivityList").child(id).setValue(editedActivity).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        // Update successfully
                        // Update Category on Tittle View
                        int imageResID = holder.itemView.getResources().getIdentifier(activityCategory.getIconResID(), "drawable", context.getPackageName());
                        if(imageResID != 0){
                            holder.item_icon_category.setBackgroundResource(imageResID);
                        }else{
                            Toast.makeText(context, "Something Wrong!!!", Toast.LENGTH_LONG).show();
                        }

                        String color = activityCategory.getCategoryColor();
                        // set card background for tittle view
                        setColorCategory(color, holder.card_icon_category);

                        //Update several data for Tittle View after edit the transaction
                        holder.item_place_Activity.setText(activityPlace);
                        holder.item_time_date_Actitvity.setText(activityTimeDate);
                        holder.item_name_Actitvity.setText(activityName);

                        BigDecimal amount = new BigDecimal(activityMoney);
                        DecimalFormat decimalFormat = new DecimalFormat("#,##0 ₫");
                        decimalFormat.setCurrency(Currency.getInstance("VND"));
                        String formattedAmount = decimalFormat.format(amount);
                        String displayMoney = "";
                        if (type.equals("Spending")) {
                            displayMoney = "-" + String.valueOf(formattedAmount);
                        } else {
                            displayMoney = "+" + String.valueOf(formattedAmount);
                        }
                        holder.item_money_Activity.setText(displayMoney);

                        // Close the folding cell after save the data
                        // Create a dialog to notify user
                        holder.foldingCell.toggle(false);
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        LayoutInflater inflater = LayoutInflater.from(context);
                        View customView = inflater.inflate(R.layout.custom_dialog_edit_transaction, null);
                        builder.setView(customView);

                        AlertDialog alertDialog =builder.create();
                        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        alertDialog.show();
                        Button btnOK = customView.findViewById(R.id.btnOK);
                        btnOK.setOnClickListener((View view)->{
                            alertDialog.dismiss();

                        });

                    } else {
                        // Fail
                        // appear a dialog to notify user
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        LayoutInflater inflater = LayoutInflater.from(context);
                        View customView = inflater.inflate(R.layout.custom_dialog_edit_transaction, null);
                        builder.setView(customView);

                        ImageView iconDialog = customView.findViewById(R.id.iconDialog);
                        TextView textDialog = customView.findViewById(R.id.textDialog);
                        iconDialog.setBackgroundResource(R.drawable.remove);
                        textDialog.setText("Edit Failed Transaction!!!");
                        AlertDialog alertDialog =builder.create();
                        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        alertDialog.show();
                        Button btnOK = customView.findViewById(R.id.btnOK);
                        btnOK.setOnClickListener((View view)->{
                            alertDialog.dismiss();
                        });


                    }
                }
            });

            //update balance
            reference.child("User Detail").child("userBalance").runTransaction(new Transaction.Handler() {
                @NonNull
                @Override
                public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                    // Get Balance and calculate the balance after edited.
                    // If type of Transaction is Spending -> convert previousMoney and newMoney to negative number.
                    // Calculate the difference amount = newMoney - previousMoney
                    // Calculate the newBalance = currentBalance + difference amount
                    // After that, save the newBalance to Firebase
                    String currentBalance = currentData.getValue(String.class);
                    BigInteger newBalance = new BigInteger("0");
                    BigInteger previousMoney = new BigInteger("0");
                    BigInteger newMoney = new BigInteger("0");
                    if(type.equals("Spending")){
                        previousMoney = new BigInteger(money).negate();
                        newMoney = new BigInteger(editedActivity.getActivityMoney()).negate();
                    }
                    else{
                        previousMoney = new BigInteger(money);
                        newMoney = new BigInteger(editedActivity.getActivityMoney());
                    }
                    BigInteger differenceAmount = newMoney.subtract(previousMoney);
                    newBalance = new BigInteger(currentBalance).add(differenceAmount);

                    // Save new Balance to Firebase
                    currentData.setValue(newBalance.toString());

                    return Transaction.success(currentData);
                }

                @Override
                public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                    if (error != null) {
                        // Xử lý lỗi nếu có
                    } else {
                        // Giao dịch thành công
                    }
                }
            });
        });
    }


    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public class TransactionViewHolder extends RecyclerView.ViewHolder {

        private ImageView item_icon_category;
        private TextView item_name_Actitvity;
        private TextView item_time_date_Actitvity;
        private TextView item_money_Activity;
        private CardView card_icon_category;
        private TextView item_place_Activity;
        private FoldingCell foldingCell;

        private CardView cardEdit;
        private ImageView itemIconEdit;
        private TextInputLayout layout_nameActivity_edit;
        private TextInputEditText et_nameActivity_edit;
        private TextInputLayout layout_categoryActivity_edit;
        private AutoCompleteTextView et_categoryActivity_edit;
        private TextInputLayout layout_moneyActivity_edit;
        private TextInputEditText et_moneyActivity_edit;
        private TextInputLayout layout_timeActivity_edit;
        private TextInputEditText et_timeActivity_edit;
        private TextInputLayout layout_dateActivity_edit;
        private TextInputEditText et_dateActivity_edit;
        private TextInputLayout layout_placeActivity_edit;
        private TextInputEditText et_placeActivity_edit;
        private Button btnEdit;


        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            item_icon_category = itemView.findViewById(R.id.item_icon_category);
            item_name_Actitvity = itemView.findViewById(R.id.item_name_Activity);
            item_time_date_Actitvity = itemView.findViewById(R.id.item_time_date_Activity);
            item_money_Activity = itemView.findViewById(R.id.item_money_Activity);
            card_icon_category = itemView.findViewById(R.id.card_icon_category);
            item_place_Activity = itemView.findViewById(R.id.item_place_Activity);
            foldingCell = itemView.findViewById(R.id.folding_cell);

            cardEdit = itemView.findViewById(R.id.card_icon_category_edit);
            itemIconEdit = itemView.findViewById(R.id.item_icon_category_edit);
            layout_nameActivity_edit = itemView.findViewById(R.id.layout_nameActivity_edit);
            et_nameActivity_edit = itemView.findViewById(R.id.et_nameActivity_edit);
            layout_categoryActivity_edit = itemView.findViewById(R.id.layout_categoryActivity_edit);
            et_categoryActivity_edit = itemView.findViewById(R.id.et_categoryActivity_edit);
            layout_moneyActivity_edit = itemView.findViewById(R.id.layout_moneyActivity_edit);
            et_moneyActivity_edit = itemView.findViewById(R.id.et_moneyActivity_edit);
            layout_timeActivity_edit = itemView.findViewById(R.id.layout_timeActivity_edit);
            et_timeActivity_edit = itemView.findViewById(R.id.et_timeActivity_edit);
            layout_dateActivity_edit = itemView.findViewById(R.id.layout_dateActivity_edit);
            et_dateActivity_edit = itemView.findViewById(R.id.et_dateActivity_edit);
            layout_placeActivity_edit = itemView.findViewById(R.id.layout_placeActivity_edit);
            et_placeActivity_edit = itemView.findViewById(R.id.et_placeActivity_edit);
            btnEdit = itemView.findViewById(R.id.btnEdit);


        }
    }
}
