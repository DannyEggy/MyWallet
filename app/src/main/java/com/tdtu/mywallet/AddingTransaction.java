package com.tdtu.mywallet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.transition.Slide;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tdtu.mywallet.model.Activity;
import com.tdtu.mywallet.model.Category;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.List;
import java.util.Locale;


public class AddingTransaction extends AppCompatActivity {
    private Button btnSpending;
    private Button btnIncome;
    private TextInputLayout layout_nameActivity;
    private TextInputEditText et_nameActivity;
    private TextInputLayout layout_categoryActivity;
    private AutoCompleteTextView et_categoryActivity;
    private TextInputLayout layout_TimeActivity;
    private TextInputEditText et_timeActivity;
    private TextInputLayout layout_dateActivity;
    private TextInputEditText et_dateActivity;
    private TextInputLayout layout_placeActivity;
    private TextInputEditText et_placeActivity;

    private TextInputLayout layout_moneyActivity;
    private TextInputEditText et_moneyActivity;

    private String activityID;
    private String activityName;
    private int activityMoney;
    private String activityTimeDate;
    private String activityPlace;
    private Category activityCategory;
    private String activityType;
    private List<Category> categoryList = new ArrayList<Category>();
    private String categoryName ;
    private int categoryIcon ;
    private ArrayAdapter<Category> adapterCategory;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_transaction);

        layout_nameActivity = findViewById(R.id.layout_nameActivity);
        et_nameActivity = findViewById(R.id.et_nameActivity);
        layout_categoryActivity = findViewById(R.id.layout_categoryActivity);
        et_categoryActivity = findViewById(R.id.et_categoryActivity);
        layout_TimeActivity = findViewById(R.id.layout_TimeActivity);
        et_timeActivity = findViewById(R.id.et_timeActivity);
        layout_dateActivity = findViewById(R.id.layout_dateActivity);
        et_dateActivity = findViewById(R.id.et_dateActivity);
        layout_moneyActivity = findViewById(R.id.layout_moneyActivity);
        et_moneyActivity = findViewById(R.id.et_moneyActivity);
        layout_placeActivity = findViewById(R.id.layout_placeActivity);
        et_placeActivity = findViewById(R.id.et_placeActivity);
        //  ***BUTTON***
        btnSpending = findViewById(R.id.btnSpending);
        btnIncome = findViewById(R.id.btnIncome);

        // Reset name_layout and money_layout and category_layout to original state when clicked
        et_nameActivity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                layout_nameActivity.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_moneyActivity.addTextChangedListener(new TextWatcher() {
            private String current = "";
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                layout_moneyActivity.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(current)) {
                    et_moneyActivity.removeTextChangedListener(this);

//                    String cleanString = s.toString().replaceAll("[,.]", "");
                    String text = s.toString().replaceAll("[^0-9]", "");
                    BigDecimal amount = new BigDecimal(text);
                    DecimalFormat decimalFormat = new DecimalFormat("#,##0");
                    decimalFormat.setCurrency(Currency.getInstance("VND"));
                    String formatted = decimalFormat.format(amount);

                    et_moneyActivity.setText(formatted);
                    et_moneyActivity.setSelection(formatted.length());

                    et_moneyActivity.addTextChangedListener(this);
                }
            }
        });

        et_categoryActivity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                layout_categoryActivity.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });






        // get categoryList from database then setting autocomplete textview
        getCategoryList();
        adapterCategory = new AutoCompleteCategoryAdapter(this, categoryList);
        et_categoryActivity.setAdapter(adapterCategory);

        et_categoryActivity.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                et_categoryActivity.showDropDown();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(et_categoryActivity.getWindowToken(), 0);
                return false;
            }
        });

        et_categoryActivity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Category selectedCategory = (Category) adapterView.getItemAtPosition(i);
                categoryName = selectedCategory.getCategoryName();
                categoryIcon =selectedCategory.getIconResID();
                Toast.makeText(AddingTransaction.this, "success", Toast.LENGTH_SHORT).show();
                activityCategory = selectedCategory;

            }
        });

        // handling time edit text
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        et_timeActivity.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
        et_timeActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the current time
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                // Create a TimePickerDialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        AddingTransaction.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                // Handle the selected time
                                et_timeActivity.setText(String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute));
                            }
                        },
                        hour, minute, true);

                // Show the TimePickerDialog
                timePickerDialog.show();
            }
        });

        // handling date edit text
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String selectedDate = day + "/" + (month + 1) + "/" + year;
        et_dateActivity.setText(selectedDate);
        et_dateActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // Create DatePickerDialog and set initial date
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddingTransaction.this, new DatePickerDialog.OnDateSetListener() {


                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Handle selected date
                        String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                        et_dateActivity.setText(selectedDate);
                    }
                }, year, month, day);

                // Show the DatePickerDialog
                datePickerDialog.show();
            }
        });

        // handling button Spending
        btnSpending.setOnClickListener((View view)->{
            Toast toast = Toast.makeText(this,"Spending", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL,0, 0);
            toast.show();
            //handling when user ignore name and category and money text box
            if(TextUtils.isEmpty(et_nameActivity.getText().toString())){
                layout_nameActivity.setError("Missing!!!");
                return;
            }else if(TextUtils.isEmpty(et_moneyActivity.getText().toString())){
                layout_moneyActivity.setError("Missing!!!");
                return;
            }else if(TextUtils.isEmpty(et_categoryActivity.getText().toString())){
                layout_categoryActivity.setError("Missing!!!");
                return;
            }



            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            String uid = user.getUid().toString();
            DatabaseReference reference= firebaseDatabase.getReference(uid);

            // adding new activity base on activityCount
            // update activityCount +=1 after adding
            reference.child("User Detail").child("userActivityCount").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    activityID = snapshot.getValue().toString();
                    int count = Integer.parseInt(activityID) + 1;
                    activityName = et_nameActivity.getText().toString();
                    activityMoney = Integer.parseInt(et_moneyActivity.getText().toString().replaceAll("[^0-9]", ""));
                    activityTimeDate = et_timeActivity.getText().toString() +" "+ et_dateActivity.getText().toString();
                    activityPlace = et_placeActivity.getText().toString();
                    activityType = "Spending";

                    Activity activity = new Activity(activityID, activityName, activityMoney, activityCategory, activityType, activityTimeDate, activityPlace);

                    reference.child("User Detail").child("userActivityList").child(activityID).setValue(activity);
                    reference.child("User Detail").child("userActivityCount").setValue(count);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            // calculate the total balance
            reference.child("User Detail").child("userBalance").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    int currentBalance = Integer.parseInt(snapshot.getValue().toString());
                    int spendingMoney = Integer.parseInt(et_moneyActivity.getText().toString().replaceAll("[^0-9]", ""));
                    int totalBalance = currentBalance - spendingMoney;
                    reference.child("User Detail").child("userBalance").setValue(totalBalance);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            Intent intent = new Intent(AddingTransaction.this, MainActivity.class);
            // Hiệu ứng trượt từ trên xuống
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                Slide slide = new Slide(Gravity.END);
//                slide.setDuration(300);
//                getWindow().setExitTransition(slide);
//                getWindow().setEnterTransition(slide);
//            }
//            startActivity(intent);
            finish();
//            overridePendingTransition(R.style.anim1);

        });

        // handling button Income

        btnIncome.setOnClickListener((View view)-> {
            Toast toast = Toast.makeText(this, "Income", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.show();

            //handling when user ignore name and category and money text box
            if (TextUtils.isEmpty(et_nameActivity.getText().toString())) {
                layout_nameActivity.setError("Missing!!!");
                return;
            } else if (TextUtils.isEmpty(et_moneyActivity.getText().toString())) {
                layout_moneyActivity.setError("Missing!!!");
                return;
            } else if (TextUtils.isEmpty(et_categoryActivity.getText().toString())) {
                layout_categoryActivity.setError("Missing!!!");
                return;
            }

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            String uid = user.getUid().toString();
            DatabaseReference reference = firebaseDatabase.getReference(uid);

            reference.child("User Detail").child("userActivityCount").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    activityID = snapshot.getValue().toString();
                    int count = Integer.parseInt(activityID) + 1;
                    activityName = et_nameActivity.getText().toString();
                    activityMoney = Integer.parseInt(et_moneyActivity.getText().toString().replaceAll("[^0-9]", ""));
                    activityTimeDate = et_timeActivity.getText().toString() + " " + et_dateActivity.getText().toString();
                    activityPlace = et_placeActivity.getText().toString();
                    activityType = "Income";

                    Activity activity = new Activity(activityID, activityName, activityMoney, activityCategory, activityType, activityTimeDate, activityPlace);

                    reference.child("User Detail").child("userActivityList").child(activityID).setValue(activity);
                    reference.child("User Detail").child("userActivityCount").setValue(count);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            // calculate the total balance
            reference.child("User Detail").child("userBalance").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    int currentBalance = Integer.parseInt(snapshot.getValue().toString());
                    int incomeMoney = Integer.parseInt(et_moneyActivity.getText().toString().replaceAll("[^0-9]", ""));
                    int totalBalance = currentBalance + incomeMoney;
                    reference.child("User Detail").child("userBalance").setValue(totalBalance);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            Intent intent = new Intent(AddingTransaction.this, MainActivity.class);
            // Hiệu ứng trượt từ trên xuống
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                Slide slide = new Slide(Gravity.END);
//                slide.setDuration(300);
//                getWindow().setExitTransition(slide);
//                getWindow().setEnterTransition(slide);
//            }
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left,
                    R.anim.slide_out_right);
        });
    }
    private void getCategoryList(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        String uid = user.getUid().toString();
        DatabaseReference reference= firebaseDatabase.getReference(uid);
        categoryList.clear();
        reference.child("User Detail").child("userCategory").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Category category = snapshot.getValue(Category.class);
                if(category != null){
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
}