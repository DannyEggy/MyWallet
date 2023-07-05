package com.tdtu.mywallet.fragment;

import static android.content.Context.INPUT_METHOD_SERVICE;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.tdtu.mywallet.AutoCompleteIconAdapter;
import com.tdtu.mywallet.CategoryListHolder;
import com.tdtu.mywallet.R;
import com.tdtu.mywallet.RecyclerViewAdapter_activity;
import com.tdtu.mywallet.RecyclerViewAdapter_category;
import com.tdtu.mywallet.RecyclerViewInterface;
import com.tdtu.mywallet.model.Activity;
import com.tdtu.mywallet.model.Category;
import com.tdtu.mywallet.model.Icon;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements RecyclerViewInterface {



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private RecyclerView recyclerView;
    public RecyclerView recyclerView_category;
    private String categoryColor;
    private String categoryName;
    private int categoryIcon;
    private String categoryIconName;
    private TextInputLayout layout_nameCategory;
    private TextInputLayout layout_colorCategory;
    private TextInputLayout layout_iconCategory;
    private AutoCompleteTextView colorAutoCompleteTextView;
    private TextInputEditText nameTextInputEditText;
    private AutoCompleteTextView iconAutoCompleteTextView;
    private ArrayAdapter<String> adapterColors;
    private ArrayAdapter<Icon> adapterIcons;
    private TextView card_category_addMore;
    private int userBalance;
    private TextView home_totalBalance;
    private List<Category> categoryList = new ArrayList<Category>();
    private List<Activity> activityList = new ArrayList<Activity>();
    private ImageView info;
    private List<Icon> getIconList(){
        List<Icon> iconList = new ArrayList<Icon>();
        iconList.add(new Icon("Icon 1", R.drawable.cart));
        iconList.add(new Icon("Icon 2", R.drawable.bubble_tea));
        iconList.add(new Icon("Icon 3", R.drawable.coffee_cup));
        iconList.add(new Icon("Icon 4", R.drawable.console));
        iconList.add(new Icon("Icon 5", R.drawable.money));
        iconList.add(new Icon("Icon 6", R.drawable.video));
        iconList.add(new Icon("Icon 7", R.drawable.icon_food));

        return iconList;
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        home_totalBalance = view.findViewById(R.id.home_totalBalance);
        card_category_addMore = view.findViewById(R.id.card_category_addMore);
        recyclerView_category = view.findViewById(R.id.recyclerView_category);
        recyclerView = view.findViewById(R.id.recyclerView_recent_activity);
        info = view.findViewById(R.id.info);
//        imageView4 = findViewById(R.id.imageView4);
//        imageView4.setOnClickListener((View view)->{
//            openAddingDialog(Gravity.CENTER);
//        });


        // handling all of logical event from view
        handleLogic();
        // get balance of user and show it in Home
        getHomeBalance();
        // adding data to categoryList and update the adapter
        getCategoryList();
        // info about category
        getMoreAboutCategory();

        return view;
    }

    private void getMoreAboutCategory() {
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("More About Category");  // Tiêu đề của Dialog
                builder.setMessage("You can delete a category by holding it!!!");  // Nội dung của Dialog

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Xử lý khi người dùng bấm nút OK
                        dialog.dismiss();  // Đóng Dialog
                    }
                });

                // Tạo và hiển thị Dialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    private void handleLogic(){
        getActivityList();
//        List<Category> categoryList = new ArrayList<Category>();
        card_category_addMore.setOnClickListener((View mView)->{
            openAddingCategoryDialog(Gravity.CENTER);
        });

        //  ***RECYCLER_VIEW***
        //  **RECYCLER_VIEW_CATEGORY**
//        CategoryListHolder.getInstance().updateUserList();
//        categoryList = CategoryListHolder.getInstance().getCategoryList();
        RecyclerViewAdapter_category categoryAdapter = new RecyclerViewAdapter_category(categoryList, getActivity(),this);
        recyclerView_category.setAdapter(categoryAdapter);
        LinearLayoutManager linearLayoutManager_category = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView_category.setLayoutManager(linearLayoutManager_category);


        //  **RECYCLER_VIEW_RECENT_ACTIVITY**
        recyclerView.setAdapter(new RecyclerViewAdapter_activity(activityList, getActivity()));
        LinearLayoutManager linearLayoutManager_recent_activity = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager_recent_activity);
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager_recent_activity.getOrientation());
        recyclerView.addItemDecoration(mDividerItemDecoration);
    }


    private void getHomeBalance(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        String uid = user.getUid().toString();
        DatabaseReference reference= firebaseDatabase.getReference(uid);
        reference.child("User Detail").child("userBalance").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                userBalance = Integer.parseInt(snapshot.getValue().toString());
                BigDecimal amount = new BigDecimal(snapshot.getValue().toString());
                DecimalFormat decimalFormat = new DecimalFormat("#,##0 ₫");
                decimalFormat.setCurrency(Currency.getInstance("VND"));
                String formattedAmount = decimalFormat.format(amount);
                home_totalBalance.setText(formattedAmount);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getCategoryList(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        String uid = user.getUid().toString();
        DatabaseReference reference= firebaseDatabase.getReference(uid);
        reference.child("User Detail").child("userCategory").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Category category = snapshot.getValue(Category.class);
                if(category != null){
                    categoryList.add(category);
                }
                recyclerView_category.getAdapter().notifyDataSetChanged();
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

    private void getActivityList(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        String uid = user.getUid().toString();
        DatabaseReference reference= firebaseDatabase.getReference(uid);
        reference.child("User Detail").child("userActivityList").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Activity activity = snapshot.getValue(Activity.class);
                if(activity != null){
                    activityList.add(0, activity);
                }
                recyclerView.getAdapter().notifyDataSetChanged();
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

    String[] colors = {"White","Red","Blue","Green", "Yellow", "Orange"};

    @SuppressLint("ClickableViewAccessibility")
    private void openAddingCategoryDialog(int gravity) {
        final Dialog dialog = new Dialog(getActivity());
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_adding_category);

        Window window = dialog.getWindow();
        if(window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setGravity(gravity);
        window.setWindowAnimations(R.style.anim2);
        dialog.show();

        //  Binding View
        layout_nameCategory = dialog.findViewById(R.id.layout_nameCategory);
        layout_colorCategory = dialog.findViewById(R.id.layout_colorCategory);
        layout_iconCategory = dialog.findViewById(R.id.layout_iconCategory);
        nameTextInputEditText = dialog.findViewById(R.id.et_nameCatogory);
        colorAutoCompleteTextView = dialog.findViewById(R.id.et_colorCategory);
        iconAutoCompleteTextView = dialog.findViewById(R.id.et_iconCategory);
        Button btnSaveCategory = dialog.findViewById(R.id.btnSaveCategory);

        //Reset error text when click again
        nameTextInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                layout_nameCategory.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        colorAutoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                layout_colorCategory.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        iconAutoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                layout_iconCategory.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });





            // **ColorInput**

        adapterColors = new ArrayAdapter<String>(getActivity(), R.layout.layout_color_selection, colors);
        colorAutoCompleteTextView.setAdapter(adapterColors);
        colorAutoCompleteTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                colorAutoCompleteTextView.showDropDown();
                InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(colorAutoCompleteTextView.getWindowToken(), 0);
                return false;
            }
        });


        colorAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                nameTextInputEditText.clearFocus();
                categoryColor = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getActivity(), categoryColor, Toast.LENGTH_SHORT).show();


            }
        });

            //  **IconInput**

        adapterIcons = new AutoCompleteIconAdapter(getActivity(), getIconList());
        iconAutoCompleteTextView.setAdapter(adapterIcons);

        iconAutoCompleteTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                iconAutoCompleteTextView.showDropDown();
                InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(iconAutoCompleteTextView.getWindowToken(), 0);
                return false;
            }
        });



        iconAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Icon selectedIcon = (Icon) adapterView.getItemAtPosition(i);
                categoryIconName = selectedIcon.getIconName();
                categoryIcon =selectedIcon.getIconResID();
                Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();

            }
        });


        btnSaveCategory.setOnClickListener((View view)->{
            Toast.makeText(getActivity(),"Adding Category Successfully", Toast.LENGTH_SHORT).show();
            categoryName = nameTextInputEditText.getText().toString();

            if(TextUtils.isEmpty(categoryName)){
                layout_nameCategory.setError("Please Input Name");
                return;
            }else if(TextUtils.isEmpty(categoryColor)){
                layout_colorCategory.setError("Please Choose Color");
                return;
            }else if(TextUtils.isEmpty(categoryIconName)){
                layout_iconCategory.setError("Please Choose Icon");
                return;

            }

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            String uid = user.getUid().toString();
            DatabaseReference reference= firebaseDatabase.getReference(uid);
            reference.child("User Detail").child("userCategoryCount").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String categoryID = snapshot.getValue().toString();
                    int count = Integer.parseInt(categoryID)+1;
                    Category category = new Category(categoryName, categoryColor, categoryIcon);
                    reference.child("User Detail").child("userCategory").child(categoryID).setValue(category);
                    reference.child("User Detail").child("userCategoryCount").setValue(count);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });





            dialog.dismiss();
        });

    }

    @Override
    public void onItemLongClick(int position) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        String uid = user.getUid().toString();
        DatabaseReference reference= firebaseDatabase.getReference(uid);
        Category category = categoryList.get(position);
        String deleteCategory = category.getCategoryName();
        reference.child("User Detail").child("userCategory").child(deleteCategory).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                // Xóa dữ liệu thành công
                // Thực hiện các hành động khác sau khi xóa dữ liệu
                categoryList.remove(position);
                recyclerView_category.getAdapter().notifyItemRemoved(position);
                Toast.makeText(getActivity(), "Delete Category Successfully", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Xóa dữ liệu thất bại
                // Xử lý lỗi hoặc thực hiện các hành động khác khi xóa dữ liệu thất bại
                Toast.makeText(getActivity(), "An error occurred while deleting Category", Toast.LENGTH_LONG).show();
            }
        });

    }

    public RecyclerView getRecyclerView_category(){
        return  recyclerView_category;
    }
}