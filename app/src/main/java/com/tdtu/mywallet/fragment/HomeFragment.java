package com.tdtu.mywallet.fragment;

import static android.content.Context.INPUT_METHOD_SERVICE;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.tdtu.mywallet.AutoCompleteIconAdapter;
import com.tdtu.mywallet.R;
import com.tdtu.mywallet.RecyclerViewAdapter_activity;
import com.tdtu.mywallet.RecyclerViewAdapter_category;
import com.tdtu.mywallet.model.Activity;
import com.tdtu.mywallet.model.Category;
import com.tdtu.mywallet.model.Icon;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {



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

    RecyclerView recyclerView;
    RecyclerView recyclerView_category;

    private List<Activity> getActivityList(){
        List<Activity> activityList = new ArrayList<Activity>();
        int iconResID = R.drawable.icon_food;
        Category category = new Category("Food", "orange", iconResID);
        activityList.add(0, new Activity("activity1","Com Tam", 120000, category, "Spending"));
        activityList.add(0, new Activity("activity2","Banh Mi", 120000, category, "Spending"));
        activityList.add(0, new Activity("activity3","Pho", 120000, category, "Spending"));
        activityList.add(0, new Activity("activity4","Pizza", 120000, category, "Spending"));
        activityList.add(0, new Activity("activity5","Ga Ran", 120000, category, "Spending"));

        activityList.add(0, new Activity("activity6","Bun Cha", 120000, category, "Spending", "18:11 - 28/5/2023", "180 Ly Chinh Thang"));
        activityList.add(0, new Activity("activity7","Nem Nuong", 120000, category, "Spending", "18:11 - 28/5/2023", "180 Ly Chinh Thang"));
        activityList.add(0, new Activity("activity8","Bun Dau Mam Tom", 120000, category, "Spending", "18:11 - 28/5/2023", "180 Ly Chinh Thang"));
        activityList.add(0, new Activity("activity9","Thit Trau Gac Bep", 120000, category, "Spending", "18:11 - 28/5/2023", "180 Ly Chinh Thang"));
        activityList.add(0, new Activity("activity10","Cao Lau", 120000, category, "Spending", "18:11 - 28/5/2023", "180 Ly Chinh Thang"));



        return activityList;
    }

    private List<Category> getCategoryList(){
        List<Category> categoryList = new ArrayList<Category>();
        int iconResID = R.drawable.icon_food;
        categoryList.add(new Category("Food", "orange", iconResID));
        categoryList.add(new Category("Movie", "red", iconResID));
        categoryList.add(new Category("Milk Tea", "orange", iconResID));
        categoryList.add(new Category("Coffee", "blue", iconResID));
        categoryList.add(new Category("Salary", "red", iconResID));
        categoryList.add(new Category("Salary", "yellow", iconResID));
        categoryList.add(new Category("Salary", "orange", iconResID));
        categoryList.add(new Category("Salary", "red", iconResID));
        categoryList.add(new Category("Mommy Give", "green", iconResID));
        return categoryList;
    }

    TextView card_category_addMore;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //  ***HANDLE CLICK ACTIVITY***
        card_category_addMore = view.findViewById(R.id.card_category_addMore);
        card_category_addMore.setOnClickListener((View mView)->{
            openAddingCategoryDialog(Gravity.CENTER);
        });

        TextView home_totalBalance = view.findViewById(R.id.home_totalBalance);
        home_totalBalance.setText(" "+" VND");

        //  ***GET LIST***
        List<Activity> activityList = getActivityList();
        List<Category> categoryList = getCategoryList();

        //  ***RECYCLER_VIEW***
            //  **RECYCLER_VIEW_CATEGORY**
        recyclerView_category = view.findViewById(R.id.recyclerView_category);
        recyclerView_category.setAdapter(new RecyclerViewAdapter_category(categoryList, getActivity()));
        LinearLayoutManager linearLayoutManager_category = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView_category.setLayoutManager(linearLayoutManager_category);

            //  **RECYCLER_VIEW_RECENT_ACTIVITY**
        recyclerView = view.findViewById(R.id.recyclerView_recent_activity);
        recyclerView.setAdapter(new RecyclerViewAdapter_activity(activityList, getActivity()));
        LinearLayoutManager linearLayoutManager_recent_activity = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager_recent_activity);


        return view;
    }

    AutoCompleteTextView colorAutoCompleteTextView;

    TextInputEditText nameTextInputEditText;

    AutoCompleteTextView iconAutoCompleteTextView;
    ArrayAdapter<String> adapterColors;
    ArrayAdapter<Icon> adapterIcons;
    private List<Icon> getIconList(){
        List<Icon> iconList = new ArrayList<Icon>();
        iconList.add(new Icon("Icon 1", R.drawable.cart));
        iconList.add(new Icon("Icon 2", R.drawable.bubble_tea));
        iconList.add(new Icon("Icon 3", R.drawable.coffee_cup));
        iconList.add(new Icon("Icon 4", R.drawable.console));
        iconList.add(new Icon("Icon 5", R.drawable.money));
        iconList.add(new Icon("Icon 6", R.drawable.video));
        return iconList;
    }


    String[] colors = {"White","Red","Blue","Green", "Yellow", "Orange"};

    @SuppressLint("ClickableViewAccessibility")
    private void openAddingCategoryDialog(int gravity) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_adding_category);

        Window window = dialog.getWindow();
        if(window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setGravity(gravity);
        dialog.show();

        //  ***LOGICAL HANDLE***
            // **NameInput**
        nameTextInputEditText = dialog.findViewById(R.id.et_nameCatogory);

            // **ColorInput**
        colorAutoCompleteTextView = dialog.findViewById(R.id.et_colorCategory);
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
                String color = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getActivity(), color, Toast.LENGTH_SHORT).show();


            }
        });

            //  **IconInput**
        iconAutoCompleteTextView = dialog.findViewById(R.id.et_iconCategory);
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
                String icon =selectedIcon.getIconName();
                Toast.makeText(getActivity(), icon, Toast.LENGTH_SHORT).show();

            }
        });

        Button btnSaveCategory = dialog.findViewById(R.id.btnSaveCategory);
        btnSaveCategory.setOnClickListener((View view)->{
            Toast.makeText(getActivity(),"Adding Category Successfully", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

    }

}