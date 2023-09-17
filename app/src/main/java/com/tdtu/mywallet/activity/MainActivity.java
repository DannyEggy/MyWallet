package com.tdtu.mywallet.activity;

import static android.content.ContentValues.TAG;
import static com.tdtu.mywallet.activity.SignInActitvity.REMEMBER_ME;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
import com.tdtu.mywallet.AutoCompleteAdapter.AutoCompleteCategoryAdapter;
import com.tdtu.mywallet.viewmodel.CurrentAvatarViewModel;
import com.tdtu.mywallet.viewpager2.MyViewPage2Adapter;
import com.tdtu.mywallet.R;
import com.tdtu.mywallet.model.Activity;
import com.tdtu.mywallet.model.Category;
import com.tdtu.mywallet.transformer.DepthPageTransformer;
import com.tdtu.mywallet.transformer.ZoomOutPageTransformer;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager2;
    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_ANALYSIS = 1;
    private static final int FRAGMENT_USER = 2;
    private static final int FRAGMENT_SETTING = 3;
    private int currentFragment = FRAGMENT_HOME;
    private FloatingActionButton floatingActionButton;
    private Button btnSpending;
    private Button btnIncome;
    private ImageView selectionAvatar;
    private String avatarUser = "avatar0";
    private TextView tv_name_main_activity;
    private Toolbar toolbar;
    private List<Category> categoryList = new ArrayList<Category>();
    private ArrayAdapter<Category> adapterCategory;
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
    private String activityMoney;
    private String activityTimeDate;
    private String activityPlace;
    private Category activityCategory;
    private String activityType;

    private String categoryName;
    private String categoryIcon;
    private ImageView imageView4;
    private CurrentAvatarViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Start initiate UX UI
        // Call function to get data from SplashActivity
        // Call function to get data from Setting Fragment
        // Optimize the time to retrieve and display data
        initUI();
        getDataFromSplashActivity();
        getDataFromSettingFragment();


    }

    private void getDataFromSettingFragment() {
        // USing Live Data to get imageID user choose from Setting Fragment
        viewModel = new ViewModelProvider(this).get(CurrentAvatarViewModel.class);
        final Observer<String> avatarObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String imageID) {
                // Update the UI, in this case, an ImageView.
                Toast.makeText(getApplicationContext(), imageID, Toast.LENGTH_SHORT).show();
                int imageResID = getResources().getIdentifier(imageID, "drawable", getPackageName());
                // Checking imageResID exist or not.
                if (imageResID != 0) {
                    selectionAvatar.setBackgroundResource(imageResID);
                } else {
                    return;
                }
            }
        };
        viewModel.getCurrentAvatarLiveData().observe(this, avatarObserver);
    }

    private void getDataFromSplashActivity() {
        // Get data from SplashActivity or SignInActivity intent
        // If user already log in, get data from SplashActivity including name and avatar of user.
        // If user are not log in, get data from SignInActivity including default name and avatar of new user.
        Intent intent = getIntent();
        if (intent != null) {
            String userName = "";
            String userAvatar = "avatar0";

            if (intent.hasExtra("userName") && intent.hasExtra("avatarResID")) {
                userName = intent.getStringExtra("userName");
                Log.d(TAG, "userName " + userName);
                userAvatar = intent.getStringExtra("avatarResID");
            } else {
                userName = intent.getStringExtra("userNameSignIn");
                Log.d(TAG, "userNameSignIn " + userName);
                userAvatar = intent.getStringExtra("avatarResIDSignIn");
            }

            // Use the retrieved data as needed
            tv_name_main_activity.setText(userName);

            int imageResID = getResources().getIdentifier(userAvatar, "drawable", getPackageName());

            // Checking imageResID exist or not.
            if (imageResID != 0) {
                selectionAvatar.setBackgroundResource(imageResID);
            } else {
                Toast.makeText(this, "Something Wrong!!!", Toast.LENGTH_LONG).show();
            }


        } else {
            Log.d(TAG, "failed");
        }
    }


    private void initUI() {
        // This section including Toolbar, ViewPager2, Bottom Navigation View

        //  ***TOOLBAR***
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Turn off title of Toolbar to customize
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        // There is a imageView allow user to choose avatar and a textView display name
        selectionAvatar = findViewById(R.id.selectionAvatar);
        tv_name_main_activity = findViewById(R.id.name_main_activity);

        selectionAvatar.setOnClickListener((View view) -> {
            openSelectionDialog(Gravity.CENTER);
        });

        // _______________________________________________________________________________________________________________________________________________
        //   ***VIEWPAGER2***
        // Configure for viewpager2
        viewPager2 = findViewById(R.id.content_frame);
        MyViewPage2Adapter myViewPage2Adapter = new MyViewPage2Adapter(this);
        viewPager2.setAdapter(myViewPage2Adapter);
        viewPager2.setCurrentItem(0);

        // _______________________________________________________________________________________________________________________________________________
        //  ***BOTTOM_NAVIGATION_VIEW***
        // Bottom navigation including bottom bar and floating action button: for adding Transaction
        AnimatedBottomBar bottomBar = findViewById(R.id.bottom_bar);
        bottomBar.setupWithViewPager2(viewPager2);

        FloatingActionButton fab = findViewById(R.id.fab_adding);
        fab.setOnClickListener((View view) -> {
            openAddingDialog(Gravity.BOTTOM);
        });
    }


    //  ***BOTTOM NAVIGATION OPTION MENU***
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    //  ***TOOL BAR OPTION***
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.zoom) {
            viewPager2.setPageTransformer(new ZoomOutPageTransformer());
        } else if (id == R.id.depth) {
            viewPager2.setPageTransformer(new DepthPageTransformer());
        } else if (id == R.id.normal) {
            // reset to normal
            MyViewPage2Adapter myViewPage2Adapter = new MyViewPage2Adapter(this);
            viewPager2.setAdapter(myViewPage2Adapter);
            viewPager2.setPageTransformer(null);
        } else if (id == R.id.signOut) {
            // when sign out, clear all the shared preferences and sign out firebase authentication
            SharedPreferences sharedPreferencesRememberMe = getSharedPreferences(REMEMBER_ME, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferencesRememberMe.edit();
            editor.clear();
            editor.apply();
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, SignInActitvity.class);
            startActivity(intent);
        }


        return true;
    }

    //  ***LOGICAL HANDLING
    @Override
    public void onBackPressed() {
        if (viewPager2.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            finishAffinity();
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
        }
    }


    //  ***DIALOG HANDLING***
    @SuppressLint("ClickableViewAccessibility")
    private void openAddingDialog(int gravity) {
        // Create a dialog to allow user to add Transaction from Toolbar
        Dialog dialog = new Dialog(this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_adding_activity);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        } else {
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setGravity(gravity);
            window.setWindowAnimations(R.style.anim1);

            dialog.show();
        }
        //  ***DIALOG***

        // _______________________________________________________________________________________________________________________________________________
        //  Binding view
        layout_nameActivity = dialog.findViewById(R.id.layout_nameActivity);
        et_nameActivity = dialog.findViewById(R.id.et_nameActivity);
        layout_categoryActivity = dialog.findViewById(R.id.layout_categoryActivity);
        et_categoryActivity = dialog.findViewById(R.id.et_categoryActivity);
        layout_TimeActivity = dialog.findViewById(R.id.layout_TimeActivity);
        et_timeActivity = dialog.findViewById(R.id.et_timeActivity);
        layout_dateActivity = dialog.findViewById(R.id.layout_dateActivity);
        et_dateActivity = dialog.findViewById(R.id.et_dateActivity);
        layout_moneyActivity = dialog.findViewById(R.id.layout_moneyActivity);
        et_moneyActivity = dialog.findViewById(R.id.et_moneyActivity);
        layout_placeActivity = dialog.findViewById(R.id.layout_placeActivity);
        et_placeActivity = dialog.findViewById(R.id.et_placeActivity);
        //  ***BUTTON***
        btnSpending = dialog.findViewById(R.id.btnSpending);
        btnIncome = dialog.findViewById(R.id.btnIncome);

        // _______________________________________________________________________________________________________________________________________________
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

        // _______________________________________________________________________________________________________________________________________________
        // If user enters data, remove error line
        // When the user enters data, convert it to money format. for example 100000 -> 100,000 VND
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
                    // Turn off to edit text, avoid repeating infinitely
                    et_moneyActivity.removeTextChangedListener(this);

//                    String cleanString = s.toString().replaceAll("[,.]", "");

                    String text = s.toString().replaceAll("[^0-9]", "");
                    BigDecimal amount = new BigDecimal(text);
                    // Format number to money. Ex: 1000 -> 1,000
                    DecimalFormat decimalFormat = new DecimalFormat("#,##0");
                    // Format currency for this data
                    decimalFormat.setCurrency(Currency.getInstance("VND"));
                    String formatted = decimalFormat.format(amount);

                    et_moneyActivity.setText(formatted);
                    // Position the cursor at the end of the string, allow user continue enter data
                    et_moneyActivity.setSelection(formatted.length());
                    // Turn on after edit successfully
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


        // Get categoryList from database then configure autocomplete textview
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
                // When user choose a category from autocomplete textview, set current Category.
                Category selectedCategory = (Category) adapterView.getItemAtPosition(i);
                categoryName = selectedCategory.getCategoryName();
                categoryIcon = selectedCategory.getIconResID();
                Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
                activityCategory = selectedCategory;

            }
        });

        // Handling time edit text
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
                        MainActivity.this,
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

        // Handling date edit text
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {


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

        // Handling button Spending
        btnSpending.setOnClickListener((View view) -> {
            Toast toast = Toast.makeText(this, "Spending", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.show();
            // Handling when user ignore name and category and money text box
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

            // Get current user and database
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            String uid = user.getUid().toString();
            DatabaseReference reference = firebaseDatabase.getReference(uid);

            // Adding new activity base on activityCount
            // Calculate the id for Transaction by counting Transaction in this account
            // Update activityCount +=1 after adding
            reference.child("User Detail").child("userActivityCount").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    activityID = snapshot.getValue().toString();
                    int count = Integer.parseInt(activityID) + 1;
                    activityName = et_nameActivity.getText().toString();
                    activityMoney = et_moneyActivity.getText().toString().replaceAll("[^0-9]", "");
                    activityTimeDate = et_timeActivity.getText().toString() + " " + et_dateActivity.getText().toString();
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

            // Calculate the total balance and update to firebase
            reference.child("User Detail").child("userBalance").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    BigInteger currentBalance = new BigInteger(snapshot.getValue().toString());
                    BigInteger spendingMoney = new BigInteger(et_moneyActivity.getText().toString().replaceAll("[^0-9]", ""));
//                    BigInteger totalBalance = currentBalance - spendingMoney;
                    String totalBalance = (currentBalance.subtract(spendingMoney)).toString();

                    reference.child("User Detail").child("userBalance").setValue(totalBalance);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            dialog.dismiss();
        });

        // Handling button Income

        btnIncome.setOnClickListener((View view) -> {
            Toast toast = Toast.makeText(this, "Income", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.show();

            // Handling when user ignore name and category and money text box
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

            // Get current user and database
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            String uid = user.getUid().toString();
            DatabaseReference reference = firebaseDatabase.getReference(uid);

            // Adding new activity base on activityCount
            // Calculate the id for Transaction by counting Transaction in this account
            // Update activityCount +=1 after adding
            reference.child("User Detail").child("userActivityCount").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    activityID = snapshot.getValue().toString();
                    int count = Integer.parseInt(activityID) + 1;
                    activityName = et_nameActivity.getText().toString();
                    activityMoney = et_moneyActivity.getText().toString().replaceAll("[^0-9]", "");
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

            // Calculate the total balance
            reference.child("User Detail").child("userBalance").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    BigInteger currentBalance = new BigInteger(snapshot.getValue().toString());
                    BigInteger incomeMoney = new BigInteger(et_moneyActivity.getText().toString().replaceAll("[^0-9]", ""));
                    String totalBalance = (currentBalance.add(incomeMoney)).toString();
                    reference.child("User Detail").child("userBalance").setValue(totalBalance);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


            dialog.dismiss();
        });
    }

    private void getCategoryList() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        String uid = user.getUid().toString();
        DatabaseReference reference = firebaseDatabase.getReference(uid);
        categoryList.clear();
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

    //  ***AVATAR SELECTION***
    private void openSelectionDialog(int gravity) {
        // Create a dialog allow user to choose avatar from toolbar
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_avatar_selection);
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setGravity(gravity);
        dialog.show();

        ImageView avatar0 = dialog.findViewById(R.id.avatar0);
        ImageView avatar1 = dialog.findViewById(R.id.avatar1);
        ImageView avatar2 = dialog.findViewById(R.id.avatar2);
        ImageView avatar3 = dialog.findViewById(R.id.avatar3);
        ImageView avatar4 = dialog.findViewById(R.id.avatar4);
        ImageView avatar5 = dialog.findViewById(R.id.avatar5);
        ImageView avatar6 = dialog.findViewById(R.id.avatar6);
        ImageView avatar7 = dialog.findViewById(R.id.avatar7);
        ImageView avatar8 = dialog.findViewById(R.id.avatar8);

        // firebase connection
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference reference = db.getReference(currentUser.getUid().toString());

        avatar0.setOnClickListener((View view) -> {
            reference.child("User Detail").child("userAvatar").setValue("avatar0");
            selectionAvatar.setBackgroundResource(R.drawable.avatar0);
            dialog.dismiss();
        });

        avatar1.setOnClickListener((View view) -> {
            reference.child("User Detail").child("userAvatar").setValue("avatar1");
            selectionAvatar.setBackgroundResource(R.drawable.avatar1);
            dialog.dismiss();
        });

        avatar2.setOnClickListener((View view) -> {
            reference.child("User Detail").child("userAvatar").setValue("avatar2");
            ;
            selectionAvatar.setBackgroundResource(R.drawable.avatar2);
            dialog.dismiss();
        });

        avatar3.setOnClickListener((View view) -> {
            reference.child("User Detail").child("userAvatar").setValue("avatar3");
            selectionAvatar.setBackgroundResource(R.drawable.avatar3);
            dialog.dismiss();
        });

        avatar4.setOnClickListener((View view) -> {
            reference.child("User Detail").child("userAvatar").setValue("avatar4");
            selectionAvatar.setBackgroundResource(R.drawable.avatar4);
            dialog.dismiss();
        });

        avatar5.setOnClickListener((View view) -> {
            reference.child("User Detail").child("userAvatar").setValue("avatar5");
            selectionAvatar.setBackgroundResource(R.drawable.avatar5);
            dialog.dismiss();
        });

        avatar6.setOnClickListener((View view) -> {
            reference.child("User Detail").child("userAvatar").setValue("avatar6");
            selectionAvatar.setBackgroundResource(R.drawable.avatar6);
            dialog.dismiss();
        });

        avatar7.setOnClickListener((View view) -> {
            reference.child("User Detail").child("userAvatar").setValue("avatar7");
            selectionAvatar.setBackgroundResource(R.drawable.avatar7);
            dialog.dismiss();
        });

        avatar8.setOnClickListener((View view) -> {
            reference.child("User Detail").child("userAvatar").setValue("avatar8");
            selectionAvatar.setBackgroundResource(R.drawable.avatar8);
            dialog.dismiss();
        });

    }

}