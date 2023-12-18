package com.tdtu.mywallet.anaysis_fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.tdtu.mywallet.R;
import com.tdtu.mywallet.model.Activity;
import com.tdtu.mywallet.recyclerview_adapter.TransactionAnalysisAdapter;
import com.tdtu.mywallet.viewmodel.SearchTextViewModel;
import com.tdtu.mywallet.viewmodel.TransactionListViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AllFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AllFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AllFragment newInstance(String param1, String param2) {
        AllFragment fragment = new AllFragment();
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

    private boolean firstOpen = true;

    private List<Activity> activityList0 = new ArrayList<Activity>();
    private List<Activity> activityList1 = new ArrayList<Activity>();
    private List<Activity> activityList2 = new ArrayList<Activity>();
    private List<Activity> activityList3 = new ArrayList<Activity>();
    private List<Activity> activityList4 = new ArrayList<Activity>();
    private List<Activity> activityList5 = new ArrayList<Activity>();
    private List<Activity> activityList6 = new ArrayList<Activity>();
    private List<Activity> activityList7 = new ArrayList<Activity>();
    private List<Activity> activityList8 = new ArrayList<Activity>();
    private List<Activity> activityList9 = new ArrayList<Activity>();
    private List<Activity> activityList10 = new ArrayList<Activity>();
    private List<Activity> activityList11 = new ArrayList<Activity>();
    private List<Activity> activityList12 = new ArrayList<Activity>();
    private List<Activity> activityListPreviousYear = new ArrayList<Activity>();


    // Tittle for 12 recent months and a previous year
    private TextView recent_current_month_all;
    private TextView recent_1_month_all;
    private TextView recent_2_month_all;
    private TextView recent_3_month_all;
    private TextView recent_4_month_all;
    private TextView recent_5_month_all;
    private TextView recent_6_month_all;
    private TextView recent_7_month_all;
    private TextView recent_8_month_all;
    private TextView recent_9_month_all;
    private TextView recent_10_month_all;
    private TextView recent_11_month_all;
    private TextView recent_12_month_all;
    private TextView recent_1_year;

    // RecyclerView for recent 12 months and a previous year
    private RecyclerView rv_current_month_all;
    private RecyclerView rv_1_month_all;
    private RecyclerView rv_2_month_all;
    private RecyclerView rv_3_month_all;
    private RecyclerView rv_4_month_all;
    private RecyclerView rv_5_month_all;
    private RecyclerView rv_6_month_all;
    private RecyclerView rv_7_month_all;
    private RecyclerView rv_8_month_all;
    private RecyclerView rv_9_month_all;
    private RecyclerView rv_10_month_all;
    private RecyclerView rv_11_month_all;
    private RecyclerView rv_12_month_all;
    private RecyclerView rv_1_year;

    private TransactionListViewModel transactionModel;
    private DatabaseReference reference;
    private LinearLayout linearLayout;
    private RecyclerView searchTransaction;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all, container, false);

        // Firebase connection
        connectFirebase();

        // View Binding
        viewBindingAllPage(view);

        setRecyclerView();

//        getDataRecyclerView();
//        firstOpen= false;
        reference.child("User Detail").child("userActivityList").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            Activity activity = snapshot.getValue(Activity.class);
            Toast.makeText(getActivity(), activity.getActivityName(), Toast.LENGTH_SHORT).show();
            long timestamp = activity.getActivityDateTime();
            Date date = new Date(timestamp);

            Calendar currentCalendar = Calendar.getInstance();
            long currentTime = System.currentTimeMillis();
            currentCalendar.setTimeInMillis(currentTime);

            int currentYear = currentCalendar.get(Calendar.YEAR);
            int currentMonth = currentCalendar.get(Calendar.MONTH);

            // Sử dụng Calendar để lấy tháng và năm
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            // Lấy tháng và năm
            int month = calendar.get(Calendar.MONTH); // Tháng bắt đầu từ 0, nên cộng thêm 1
            int year = calendar.get(Calendar.YEAR);

//                    getDataRecyclerView();
            Toast.makeText(requireActivity(), String.valueOf(currentYear - year), Toast.LENGTH_SHORT).show();
            if(currentYear - year == 0){
                //months apart
                int monthDiff = currentMonth - month;
                month+=1;
                if(monthDiff == 0){
                    monthDiff+=12;
                    activityList0.add(0, activity);
                    rv_current_month_all.getAdapter().notifyDataSetChanged();
                    String tittleTransactionList = String.valueOf(month)+"/"+String.valueOf(year);
                    recent_current_month_all.setText(tittleTransactionList);
                    if(activityList0.size()==0){
                        recent_current_month_all.setVisibility(View.GONE);
                        rv_current_month_all.setVisibility(View.GONE);
                    }
//                    getTransactionListByMonth(year, monthDiff, rv_current_month_all, recent_current_month_all);
                }else if(monthDiff == 1){
                    activityList1.add(0, activity);
                    rv_1_month_all.getAdapter().notifyDataSetChanged();
                    String tittleTransactionList = String.valueOf(month)+"/"+String.valueOf(year);
                    recent_1_month_all.setText(tittleTransactionList);
                    if(activityList1.size()==0){
                        recent_1_month_all.setVisibility(View.GONE);
                        rv_1_month_all.setVisibility(View.GONE);
                    }
//                    getTransactionListByMonth(year, monthDiff, rv_1_month_all, recent_1_month_all);
                }else if(monthDiff == 2){
                    activityList2.add(0, activity);
                    rv_2_month_all.getAdapter().notifyDataSetChanged();
                    String tittleTransactionList = String.valueOf(month)+"/"+String.valueOf(year);
                    recent_2_month_all.setText(tittleTransactionList);
                    if(activityList2.size()==0){
                        recent_2_month_all.setVisibility(View.GONE);
                        rv_2_month_all.setVisibility(View.GONE);
                    }
//                    getTransactionListByMonth(year, monthDiff, rv_2_month_all, recent_2_month_all);
                }else if(monthDiff == 3){
                    activityList3.add(0, activity);
                    rv_3_month_all.getAdapter().notifyDataSetChanged();
                    String tittleTransactionList = String.valueOf(month)+"/"+String.valueOf(year);
                    recent_3_month_all.setText(tittleTransactionList);
                    if(activityList3.size()==0){
                        recent_3_month_all.setVisibility(View.GONE);
                        rv_3_month_all.setVisibility(View.GONE);
                    }
//                    getTransactionListByMonth(year, monthDiff, rv_3_month_all, recent_3_month_all);
                }else if(monthDiff ==4){
                    activityList4.add(0, activity);
                    rv_4_month_all.getAdapter().notifyDataSetChanged();
                    String tittleTransactionList = String.valueOf(month)+"/"+String.valueOf(year);
                    recent_4_month_all.setText(tittleTransactionList);
                    if(activityList4.size()==0){
                        recent_4_month_all.setVisibility(View.GONE);
                        rv_4_month_all.setVisibility(View.GONE);
                    }
//                    getTransactionListByMonth(year, monthDiff, rv_4_month_all, recent_4_month_all);
                }else if(monthDiff == 5){
                    activityList5.add(0, activity);
                    rv_5_month_all.getAdapter().notifyDataSetChanged();
                    String tittleTransactionList = String.valueOf(month)+"/"+String.valueOf(year);
                    recent_5_month_all.setText(tittleTransactionList);
                    if(activityList5.size()==0){
                        recent_5_month_all.setVisibility(View.GONE);
                        rv_5_month_all.setVisibility(View.GONE);
                    }
//                    getTransactionListByMonth(year, monthDiff, rv_5_month_all, recent_5_month_all);
                }else if(monthDiff == 6){
                    activityList6.add(0, activity);
                    rv_6_month_all.getAdapter().notifyDataSetChanged();
                    String tittleTransactionList = String.valueOf(month)+"/"+String.valueOf(year);
                    recent_6_month_all.setText(tittleTransactionList);
                    if(activityList6.size()==0){
                        recent_6_month_all.setVisibility(View.GONE);
                        rv_6_month_all.setVisibility(View.GONE);
                    }
//                    getTransactionListByMonth(year, monthDiff, rv_6_month_all, recent_6_month_all);
                }else if(monthDiff == 7){
                    activityList7.add(0, activity);
                    rv_7_month_all.getAdapter().notifyDataSetChanged();
                    String tittleTransactionList = String.valueOf(month)+"/"+String.valueOf(year);
                    recent_7_month_all.setText(tittleTransactionList);
                    if(activityList7.size()==0){
                        recent_7_month_all.setVisibility(View.GONE);
                        rv_7_month_all.setVisibility(View.GONE);
                    }
//                    getTransactionListByMonth(year, monthDiff, rv_7_month_all, recent_7_month_all);
                }else if(monthDiff == 8){
                    activityList8.add(0, activity);
                    rv_8_month_all.getAdapter().notifyDataSetChanged();
                    String tittleTransactionList = String.valueOf(month)+"/"+String.valueOf(year);
                    recent_8_month_all.setText(tittleTransactionList);
                    if(activityList8.size()==0){
                        recent_8_month_all.setVisibility(View.GONE);
                        rv_8_month_all.setVisibility(View.GONE);
                    }
//                    getTransactionListByMonth(year, monthDiff, rv_8_month_all, recent_8_month_all);
                }else if(monthDiff == 9){
                    activityList9.add(0, activity);
                    rv_9_month_all.getAdapter().notifyDataSetChanged();
                    String tittleTransactionList = String.valueOf(month)+"/"+String.valueOf(year);
                    recent_9_month_all.setText(tittleTransactionList);
                    if(activityList9.size()==0){
                        recent_9_month_all.setVisibility(View.GONE);
                        rv_9_month_all.setVisibility(View.GONE);
                    }
//                    getTransactionListByMonth(year, monthDiff, rv_9_month_all, recent_9_month_all);
                }else if(monthDiff == 10){
                    activityList10.add(0, activity);
                    rv_10_month_all.getAdapter().notifyDataSetChanged();
                    String tittleTransactionList = String.valueOf(month)+"/"+String.valueOf(year);
                    recent_10_month_all.setText(tittleTransactionList);
                    if(activityList10.size()==0){
                        recent_10_month_all.setVisibility(View.GONE);
                        rv_10_month_all.setVisibility(View.GONE);
                    }
//                    getTransactionListByMonth(year, monthDiff, rv_10_month_all, recent_10_month_all);
                }else if(monthDiff == 11){
                    activityList11.add(0, activity);
                    rv_11_month_all.getAdapter().notifyDataSetChanged();
                    String tittleTransactionList = String.valueOf(month)+"/"+String.valueOf(year);
                    recent_11_month_all.setText(tittleTransactionList);
                    if(activityList11.size()==0){
                        recent_11_month_all.setVisibility(View.GONE);
                        rv_11_month_all.setVisibility(View.GONE);
                    }
//                    getTransactionListByMonth(year, monthDiff, rv_11_month_all, recent_11_month_all);
                }

            }
            if(currentYear - year ==1){
                activityListPreviousYear.add(0, activity);
                rv_1_year.getAdapter().notifyDataSetChanged();
                String tittleTransactionList = String.valueOf(year);
                recent_1_year.setText(tittleTransactionList);
                if(activityList11.size()==0){
                    recent_1_year.setVisibility(View.GONE);
                    rv_1_year.setVisibility(View.GONE);
                }
            }

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


//        firstOpen = false;
        SearchTextViewModel viewModel = new ViewModelProvider(requireActivity()).get(SearchTextViewModel.class);
        viewModel.getTitleQuery().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {

                if(s != null && !s.isEmpty()){
                    // case: search query is not null
                    List<Activity> searchList = new ArrayList<>();
                    reference.child("User Detail").child("userActivityList").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            searchList.clear();

                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Activity activity = snapshot.getValue(Activity.class);

                                // Kiểm tra xem title của transaction có chứa searchTitle không
                                if (activity.getActivityName().toLowerCase().contains(s.toLowerCase())) {
                                    searchList.add(0, activity);
                                }
                            }

                            linearLayout.setVisibility(View.GONE);

                            TransactionAnalysisAdapter searchAdapter = new TransactionAnalysisAdapter(searchList, getActivity());
                            searchTransaction.setAdapter(searchAdapter);

//                            searchTransaction.removeItemDecorationAt(0);

                            searchTransaction.setVisibility(View.VISIBLE);

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Xử lý lỗi nếu cần
                        }
                    });









                }else{
                    // case: search query is null
                    searchTransaction.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.VISIBLE);

                }


            }
        });

        return view;
    }

    public void setRecyclerView(){

//         current month
        TransactionAnalysisAdapter currentTransactionAdapter = new TransactionAnalysisAdapter(activityList0, getActivity());
        rv_current_month_all.setAdapter(currentTransactionAdapter);
        LinearLayoutManager linearLayoutManagerCurrentMonth= new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_current_month_all.setLayoutManager(linearLayoutManagerCurrentMonth);
        DividerItemDecoration dividerItemDecorationCurrentMonth = new DividerItemDecoration(rv_current_month_all.getContext()
                , linearLayoutManagerCurrentMonth.getOrientation());
        rv_current_month_all.addItemDecoration(dividerItemDecorationCurrentMonth);

        TransactionAnalysisAdapter month1TransactionAdapter = new TransactionAnalysisAdapter(activityList1, getActivity());
        rv_1_month_all.setAdapter(month1TransactionAdapter);
        LinearLayoutManager linearLayoutManagerMonth1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_1_month_all.setLayoutManager(linearLayoutManagerMonth1);
        DividerItemDecoration dividerItemDecorationMonth1 = new DividerItemDecoration(rv_1_month_all.getContext()
                , linearLayoutManagerMonth1.getOrientation());
        rv_1_month_all.addItemDecoration(dividerItemDecorationMonth1);

        TransactionAnalysisAdapter month2TransactionAdapter = new TransactionAnalysisAdapter(activityList2, getActivity());
        rv_2_month_all.setAdapter(month2TransactionAdapter);
        LinearLayoutManager linearLayoutManagerMonth2= new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_2_month_all.setLayoutManager(linearLayoutManagerMonth2);
        DividerItemDecoration dividerItemDecorationMonth2 = new DividerItemDecoration(rv_2_month_all.getContext()
                , linearLayoutManagerMonth2.getOrientation());
        rv_2_month_all.addItemDecoration(dividerItemDecorationMonth2);

        TransactionAnalysisAdapter month3TransactionAdapter = new TransactionAnalysisAdapter(activityList3, getActivity());
        rv_3_month_all.setAdapter(month3TransactionAdapter);
        LinearLayoutManager linearLayoutManagerMonth3= new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_3_month_all.setLayoutManager(linearLayoutManagerMonth3);
        DividerItemDecoration dividerItemDecorationMonth3 = new DividerItemDecoration(rv_3_month_all.getContext()
                , linearLayoutManagerMonth3.getOrientation());
        rv_3_month_all.addItemDecoration(dividerItemDecorationMonth3);

        TransactionAnalysisAdapter month4TransactionAdapter = new TransactionAnalysisAdapter(activityList4, getActivity());
        rv_4_month_all.setAdapter(month4TransactionAdapter);
        LinearLayoutManager linearLayoutManagerMonth4= new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_4_month_all.setLayoutManager(linearLayoutManagerMonth4);
        DividerItemDecoration dividerItemDecorationMonth4 = new DividerItemDecoration(rv_4_month_all.getContext()
                , linearLayoutManagerMonth4.getOrientation());
        rv_4_month_all.addItemDecoration(dividerItemDecorationMonth4);

        TransactionAnalysisAdapter month5TransactionAdapter = new TransactionAnalysisAdapter(activityList5, getActivity());
        rv_5_month_all.setAdapter(month5TransactionAdapter);
        LinearLayoutManager linearLayoutManagerMonth5= new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_5_month_all.setLayoutManager(linearLayoutManagerMonth5);
        DividerItemDecoration dividerItemDecorationMonth5 = new DividerItemDecoration(rv_5_month_all.getContext()
                , linearLayoutManagerMonth5.getOrientation());
        rv_5_month_all.addItemDecoration(dividerItemDecorationMonth5);

        TransactionAnalysisAdapter month6TransactionAdapter = new TransactionAnalysisAdapter(activityList6, getActivity());
        rv_6_month_all.setAdapter(month6TransactionAdapter);
        LinearLayoutManager linearLayoutManagerMonth6= new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_6_month_all.setLayoutManager(linearLayoutManagerMonth6);
        DividerItemDecoration dividerItemDecorationMonth6 = new DividerItemDecoration(rv_6_month_all.getContext()
                , linearLayoutManagerMonth6.getOrientation());
        rv_6_month_all.addItemDecoration(dividerItemDecorationMonth6);

        TransactionAnalysisAdapter month7TransactionAdapter = new TransactionAnalysisAdapter(activityList7, getActivity());
        rv_7_month_all.setAdapter(month7TransactionAdapter);
        LinearLayoutManager linearLayoutManagerMonth7= new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_7_month_all.setLayoutManager(linearLayoutManagerMonth7);
        DividerItemDecoration dividerItemDecorationMonth7 = new DividerItemDecoration(rv_7_month_all.getContext()
                , linearLayoutManagerMonth7.getOrientation());
        rv_7_month_all.addItemDecoration(dividerItemDecorationMonth7);

        TransactionAnalysisAdapter month8TransactionAdapter = new TransactionAnalysisAdapter(activityList8, getActivity());
        rv_8_month_all.setAdapter(month8TransactionAdapter);
        LinearLayoutManager linearLayoutManagerMonth8= new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_8_month_all.setLayoutManager(linearLayoutManagerMonth8);
        DividerItemDecoration dividerItemDecorationMonth8 = new DividerItemDecoration(rv_8_month_all.getContext()
                , linearLayoutManagerMonth8.getOrientation());
        rv_8_month_all.addItemDecoration(dividerItemDecorationMonth8);

        TransactionAnalysisAdapter month9TransactionAdapter = new TransactionAnalysisAdapter(activityList9, getActivity());
        rv_9_month_all.setAdapter(month9TransactionAdapter);
        LinearLayoutManager linearLayoutManagerMonth9= new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_9_month_all.setLayoutManager(linearLayoutManagerMonth9);
        DividerItemDecoration dividerItemDecorationMonth9 = new DividerItemDecoration(rv_9_month_all.getContext()
                , linearLayoutManagerMonth9.getOrientation());
        rv_9_month_all.addItemDecoration(dividerItemDecorationMonth9);

        TransactionAnalysisAdapter month10TransactionAdapter = new TransactionAnalysisAdapter(activityList10, getActivity());
        rv_10_month_all.setAdapter(month10TransactionAdapter);
        LinearLayoutManager linearLayoutManagerMonth10= new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_10_month_all.setLayoutManager(linearLayoutManagerMonth10);
        DividerItemDecoration dividerItemDecorationMonth10 = new DividerItemDecoration(rv_10_month_all.getContext()
                , linearLayoutManagerMonth10.getOrientation());
        rv_10_month_all.addItemDecoration(dividerItemDecorationMonth10);

        TransactionAnalysisAdapter month11TransactionAdapter = new TransactionAnalysisAdapter(activityList11, getActivity());
        rv_11_month_all.setAdapter(month11TransactionAdapter);
        LinearLayoutManager linearLayoutManagerMonth11= new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_11_month_all.setLayoutManager(linearLayoutManagerMonth11);
        DividerItemDecoration dividerItemDecorationMonth11 = new DividerItemDecoration(rv_11_month_all.getContext()
                , linearLayoutManagerMonth11.getOrientation());
        rv_11_month_all.addItemDecoration(dividerItemDecorationMonth11);

        TransactionAnalysisAdapter month12TransactionAdapter = new TransactionAnalysisAdapter(activityList12, getActivity());
        rv_12_month_all.setAdapter(month12TransactionAdapter);
        LinearLayoutManager linearLayoutManagerMonth12= new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_12_month_all.setLayoutManager(linearLayoutManagerMonth12);
        DividerItemDecoration dividerItemDecorationMonth12 = new DividerItemDecoration(rv_12_month_all.getContext()
                , linearLayoutManagerMonth12.getOrientation());
        rv_12_month_all.addItemDecoration(dividerItemDecorationMonth12);

        TransactionAnalysisAdapter previousYearTransactionAdapter = new TransactionAnalysisAdapter(activityListPreviousYear, getActivity());
        rv_1_year.setAdapter(previousYearTransactionAdapter);
        LinearLayoutManager linearLayoutManagerPreviousYear= new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_1_year.setLayoutManager(linearLayoutManagerPreviousYear);
        DividerItemDecoration dividerItemDecorationPreviousYear = new DividerItemDecoration( rv_1_year.getContext()
                , linearLayoutManagerPreviousYear.getOrientation());
        rv_1_year.addItemDecoration(dividerItemDecorationPreviousYear);


//        for (int i = 0; i <= 12; i++) {
//            // Tạo danh sách activity và adapter
//            List<Activity> activityList = new ArrayList<>();
//            TransactionAnalysisAdapter transactionAdapter = new TransactionAnalysisAdapter(activityList, getActivity());
//
//            // Tạo RecyclerView và thiết lập adapter, layout manager và item decoration
//
//
//            RecyclerView recyclerView = getRecyclerViewByMonth(i); // Phương thức này trả về RecyclerView tương ứng với tháng i
//
//
//
//            recyclerView.setAdapter(transactionAdapter);
//            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//            recyclerView.setLayoutManager(linearLayoutManager);
//            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
//            recyclerView.addItemDecoration(dividerItemDecoration);
//        }


    }




//    public void getDataRecyclerView(){
//        // Get current time
//        Calendar calendar = Calendar.getInstance();
//        long currentTime = System.currentTimeMillis();
//        calendar.setTimeInMillis(currentTime);
//
//        int year = calendar.get(Calendar.YEAR);
//        int currentMonth = calendar.get(Calendar.MONTH);
//
//        // Get transaction at current month
//        getTransactionListByMonth(year, currentMonth, rv_current_month_all, recent_current_month_all);
//
//        // Get transaction at 1 month ago
//        getTransactionListByMonth(year, currentMonth-1, rv_1_month_all, recent_1_month_all);
//
//        // Get transaction at 2 month ago
//        getTransactionListByMonth(year, currentMonth-2, rv_2_month_all, recent_2_month_all);
//
//        // Get transaction at 3 month ago
//        getTransactionListByMonth(year, currentMonth-3, rv_3_month_all, recent_3_month_all);
//
//        // Get transaction at 4 month ago
//        getTransactionListByMonth(year, currentMonth-4, rv_4_month_all, recent_4_month_all);
//
//        // Get transaction at 5 month ago
//        getTransactionListByMonth(year, currentMonth-5, rv_5_month_all, recent_5_month_all);
//
//        // Get transaction at 6 month ago
//        getTransactionListByMonth(year, currentMonth-6, rv_6_month_all, recent_6_month_all);
//
//        // Get transaction at 7 month ago
//        getTransactionListByMonth(year, currentMonth-7, rv_7_month_all, recent_7_month_all);
//
//        // Get transaction at 8 month ago
//        getTransactionListByMonth(year, currentMonth-8, rv_8_month_all, recent_8_month_all);
//
//        // Get transaction at 9 month ago
//        getTransactionListByMonth(year, currentMonth-9, rv_9_month_all, recent_9_month_all);
//
//        // Get transaction at 10 month ago
//        getTransactionListByMonth(year, currentMonth-10, rv_10_month_all, recent_10_month_all);
//
//        // Get transaction at 11 month ago
//        getTransactionListByMonth(year, currentMonth-11, rv_11_month_all, recent_11_month_all);
//
//        // Get transaction at 12 month ago
//        getTransactionListByMonth(year, currentMonth-12, rv_12_month_all, recent_12_month_all);
//
//        //get transaction at 1 year ago
//        getTransactionListLastYear(year, rv_1_year, recent_1_year);
//
//        // Update data when firebase database has a change.
//
//
//
//
//
//    }

    public void connectFirebase() {
        // Firebase connection
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        reference = db.getReference(currentUser.getUid());
    }

    public void getTransactionListByMonth(int year, int month, RecyclerView rv_month_all, TextView recent_month_all) {
        // show recyclerview and text.
        rv_month_all.setVisibility(View.VISIBLE);
        recent_month_all.setVisibility(View.VISIBLE);

        int currentMonth = month +1;
        if(currentMonth <0){
            currentMonth = currentMonth+12;
            String tittleTransactionList = String.valueOf(currentMonth)+"/"+String.valueOf(year -1);
            recent_month_all.setText(tittleTransactionList);
        }else if(currentMonth == 0){
            Calendar calendar = Calendar.getInstance();
            currentMonth = calendar.get(Calendar.MONTH);
            String tittleTransactionList = String.valueOf(currentMonth)+"/"+String.valueOf(year -1);
            recent_month_all.setText(tittleTransactionList);
        }else{
            String tittleTransactionList = String.valueOf(currentMonth)+"/"+String.valueOf(year);
            recent_month_all.setText(tittleTransactionList);
        }


        // Set the first day of the month
        Calendar firstDay = Calendar.getInstance();
        firstDay.set(year, month,1); // Set to the 1st day of the month.

        // Set the last day of the month
        Calendar lastDay = Calendar.getInstance();
        lastDay.set(year,month, 1); // Set to the 1st day of the current month.
        lastDay.add(Calendar.MONTH, 1); // Move to the 1st day of the next month.
        lastDay.add(Calendar.DAY_OF_MONTH, -1); // Move back one day to the last day of the month.

        long startTime = firstDay.getTimeInMillis();
        long endTime = lastDay.getTimeInMillis();

//        calendar.set(Calendar.MONTH, Calendar.SEPTEMBER);
//        long startTime = calendar.getTimeInMillis();
//        calendar.set(Calendar.MONTH, Calendar.OCTOBER);
//        long endTime = calendar.getTimeInMillis();


        Query query = reference.child("User Detail").child("userActivityList")
                .orderByChild("activityDateTime").startAt(startTime).endAt(endTime);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Activity> transactionList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Activity transaction = snapshot.getValue(Activity.class);
                    transactionList.add(0,transaction);

                    TransactionAnalysisAdapter transactionAdapter = new TransactionAnalysisAdapter(transactionList, getActivity());
                    rv_month_all.setAdapter(transactionAdapter);
                    LinearLayoutManager linearLayoutManager_1month = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    rv_month_all.setLayoutManager(linearLayoutManager_1month);
                    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv_month_all.getContext()
                            , linearLayoutManager_1month.getOrientation());
                    rv_month_all.addItemDecoration(dividerItemDecoration);



                }
                if(transactionList.size() ==0){
                    rv_month_all.setVisibility(View.GONE);
                    recent_month_all.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void getTransactionListLastYear(int year, RecyclerView rv_month_all, TextView recent_month_all) {
        rv_month_all.setVisibility(View.VISIBLE);
        recent_month_all.setVisibility(View.VISIBLE);
        // Lấy ngày hiện tại
        Calendar currentDate = Calendar.getInstance();

        // Trừ đi một năm để lấy năm trước đó
        currentDate.add(Calendar.YEAR, -1);
        currentDate.add(Calendar.MONTH, -1);

//        currentDate.add(Calendar.MONTH, -1);
//        Toast.makeText(getActivity(), String.valueOf(currentDate.get(Calendar.MONTH)), Toast.LENGTH_LONG).show();

        // Lấy tháng và năm của tháng trước đó
        int lastYear = currentDate.get(Calendar.YEAR);
        int lastMonth = currentDate.get(Calendar.MONTH) + 1 ; // Đảm bảo tháng trước đó là 0-11.

        // Tính startTime (đầu năm) và endTime (cuối tháng)
        Calendar firstDay = Calendar.getInstance();
        firstDay.set(lastYear, Calendar.JANUARY, 1); // Đầu năm
        Calendar lastDay = Calendar.getInstance();
        lastDay.add(Calendar.MONTH, -1);
        lastDay.set(lastYear, lastMonth, lastDay.getActualMaximum(Calendar.MONTH)); // Cuối tháng

//        Toast.makeText(getActivity(), String.valueOf(lastYear)+" "+String.valueOf(lastMonth)+" " + String.valueOf(lastDay.getActualMaximum(Calendar.DAY_OF_MONTH)), Toast.LENGTH_LONG).show();


        long startTime = firstDay.getTimeInMillis();
        long endTime = lastDay.getTimeInMillis();

        String tittleTransactionList = String.valueOf(lastYear);
//        Toast.makeText(getActivity(), tittleTransactionList, Toast.LENGTH_LONG).show();
        recent_month_all.setText(tittleTransactionList);

//        Toast.makeText(getActivity(), String.valueOf(year)+" "+String.valueOf(startTime)+" " + String.valueOf(endTime), Toast.LENGTH_LONG).show();
        Query query = reference.child("User Detail").child("userActivityList")
                .orderByChild("activityDateTime").startAt(startTime).endAt(endTime);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Activity> transactionList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Activity transaction = snapshot.getValue(Activity.class);
                    transactionList.add(0,transaction);

                    TransactionAnalysisAdapter transactionAdapter = new TransactionAnalysisAdapter(transactionList, getActivity());
                    rv_month_all.setAdapter(transactionAdapter);
                    LinearLayoutManager linearLayoutManager_1month = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    rv_month_all.setLayoutManager(linearLayoutManager_1month);
                    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv_month_all.getContext()
                            , linearLayoutManager_1month.getOrientation());
                    rv_month_all.addItemDecoration(dividerItemDecoration);


                }
                if(transactionList.size() ==0){
                    rv_month_all.setVisibility(View.GONE);
                    recent_month_all.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




//        Query query = reference.child("User Detail").child("userActivityList")
//                .orderByChild("activityDateTime").startAt(startTime).endAt(endTime);
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                Toast.makeText(getActivity(), "test 1 year ago", Toast.LENGTH_LONG).show();
//                List<Activity> transactionList = new ArrayList<>();
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Activity transaction = snapshot.getValue(Activity.class);
//                    transactionList.add(0,transaction);
//
//                    Toast.makeText(getActivity(), String.valueOf(transactionList.size()), Toast.LENGTH_LONG ).show();
//
//                    TransactionAnalysisAdapter transactionAdapter = new TransactionAnalysisAdapter(transactionList, getActivity());
//                    rv_month_all.setAdapter(transactionAdapter);
//                    LinearLayoutManager linearLayoutManager_1month = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//                    rv_month_all.setLayoutManager(linearLayoutManager_1month);
//                    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv_month_all.getContext()
//                            , linearLayoutManager_1month.getOrientation());
//                    rv_month_all.addItemDecoration(dividerItemDecoration);
//
//
//                }
//                if(transactionList.size() ==0){
//                    rv_month_all.setVisibility(View.GONE);
//                    recent_month_all.setVisibility(View.GONE);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }



    public void viewBindingAllPage(View view) {
        //TextView view binding
        recent_current_month_all = view.findViewById(R.id.spendingText);
        recent_1_month_all = view.findViewById(R.id.recent_1_month_all);
        recent_2_month_all = view.findViewById(R.id.recent_2_month_all);
        recent_3_month_all = view.findViewById(R.id.recent_3_month_all);
        recent_4_month_all = view.findViewById(R.id.recent_4_month_all);
        recent_5_month_all = view.findViewById(R.id.recent_5_month_all);
        recent_6_month_all = view.findViewById(R.id.recent_6_month_all);
        recent_7_month_all = view.findViewById(R.id.recent_7_month_all);
        recent_8_month_all = view.findViewById(R.id.recent_8_month_all);
        recent_9_month_all = view.findViewById(R.id.recent_9_month_all);
        recent_10_month_all = view.findViewById(R.id.recent_10_month_all);
        recent_11_month_all = view.findViewById(R.id.recent_11_month_all);
        recent_12_month_all = view.findViewById(R.id.recent_12_month_all);
        recent_1_year = view.findViewById(R.id.recent_1_year);
        linearLayout = view.findViewById(R.id.layoutLinear);

        // RecyclerView view binding
        rv_current_month_all = view.findViewById(R.id.rv_current_month_all);
        rv_1_month_all = view.findViewById(R.id.rv_1_month_all);
        rv_2_month_all = view.findViewById(R.id.rv_2_month_all);
        rv_3_month_all = view.findViewById(R.id.rv_3_month_all);
        rv_4_month_all = view.findViewById(R.id.rv_4_month_all);
        rv_5_month_all = view.findViewById(R.id.rv_5_month_all);
        rv_6_month_all = view.findViewById(R.id.rv_6_month_all);
        rv_7_month_all = view.findViewById(R.id.rv_7_month_all);
        rv_8_month_all = view.findViewById(R.id.rv_8_month_all);
        rv_9_month_all = view.findViewById(R.id.rv_9_month_all);
        rv_10_month_all = view.findViewById(R.id.rv_10_month_all);
        rv_11_month_all = view.findViewById(R.id.rv_11_month_all);
        rv_12_month_all = view.findViewById(R.id.rv_12_month_all);
        rv_1_year = view.findViewById(R.id.rv_1_year);

        searchTransaction = view.findViewById(R.id.searchTransactionIncome);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        searchTransaction.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(searchTransaction.getContext()
                , linearLayoutManager.getOrientation());
        searchTransaction.addItemDecoration(dividerItemDecoration);

    }
}