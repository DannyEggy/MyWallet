package com.tdtu.mywallet.anaysis_fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.tdtu.mywallet.R;
import com.tdtu.mywallet.main_fragment.HomeFragment;
import com.tdtu.mywallet.model.Activity;
import com.tdtu.mywallet.recyclerview_adapter.TransactionAdapter;
import com.tdtu.mywallet.recyclerview_adapter.TransactionAnalysisAdapter;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all, container, false);

        // Firebase connection
        connectFirebase();

        // View Binding
        viewBindingAllPage(view);

        // Get current time
        Calendar calendar = Calendar.getInstance();
        long currentTime = System.currentTimeMillis();
        calendar.setTimeInMillis(currentTime);

        int year = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);

        // Get transaction at current month
        getTransactionListByMonth(year, currentMonth, rv_current_month_all, recent_current_month_all);

        // Get transaction at 1 month ago
        getTransactionListByMonth(year, currentMonth-1, rv_1_month_all, recent_1_month_all);

        // Get transaction at 2 month ago
        getTransactionListByMonth(year, currentMonth-2, rv_2_month_all, recent_2_month_all);

        // Get transaction at 3 month ago
        getTransactionListByMonth(year, currentMonth-3, rv_3_month_all, recent_3_month_all);

        // Get transaction at 4 month ago
        getTransactionListByMonth(year, currentMonth-4, rv_4_month_all, recent_4_month_all);

        // Get transaction at 5 month ago
        getTransactionListByMonth(year, currentMonth-5, rv_5_month_all, recent_5_month_all);

        // Get transaction at 6 month ago
        getTransactionListByMonth(year, currentMonth-6, rv_6_month_all, recent_6_month_all);

        // Get transaction at 7 month ago
        getTransactionListByMonth(year, currentMonth-7, rv_7_month_all, recent_7_month_all);

        // Get transaction at 8 month ago
        getTransactionListByMonth(year, currentMonth-8, rv_8_month_all, recent_8_month_all);

        // Get transaction at 9 month ago
        getTransactionListByMonth(year, currentMonth-9, rv_9_month_all, recent_9_month_all);

        // Get transaction at 10 month ago
        getTransactionListByMonth(year, currentMonth-10, rv_10_month_all, recent_10_month_all);

        // Get transaction at 11 month ago
        getTransactionListByMonth(year, currentMonth-11, rv_11_month_all, recent_11_month_all);

        // Get transaction at 12 month ago
        getTransactionListByMonth(year, currentMonth-12, rv_12_month_all, recent_12_month_all);

        //get transaction at 1 year ago
        getTransactionListLastYear(year, rv_1_year, recent_1_year);


        return view;
    }

    public void connectFirebase() {
        // Firebase connection
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        reference = db.getReference(currentUser.getUid());
    }

    public void getTransactionListByMonth(int year, int month, RecyclerView rv_month_all, TextView recent_month_all) {
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

        // Lấy ngày hiện tại
        Calendar currentDate = Calendar.getInstance();

        // Trừ đi một năm để lấy năm trước đó
        currentDate.add(Calendar.YEAR, -1);

        // Đặt ngày cuối của tháng là ngày 30 hoặc 31 (tùy tháng)
        int lastDayOfMonth = currentDate.getActualMaximum(Calendar.DAY_OF_MONTH);
        currentDate.set(Calendar.DAY_OF_MONTH, lastDayOfMonth);

        // Lấy tháng và năm của tháng trước đó
        int lastYear = currentDate.get(Calendar.YEAR);
        int lastMonth = currentDate.get(Calendar.MONTH) -1;

        // Tính startTime (đầu năm) và endTime (cuối tháng)
        Calendar firstDay = Calendar.getInstance();
        firstDay.set(lastYear, Calendar.JANUARY, 1); // Đầu năm
        Calendar lastDay = Calendar.getInstance();
        lastDay.set(lastYear, lastMonth, lastDayOfMonth); // Cuối tháng


        long startTime = firstDay.getTimeInMillis();
        long endTime = lastDay.getTimeInMillis();

        String tittleTransactionList = String.valueOf(lastYear);
//        Toast.makeText(getActivity(), tittleTransactionList, Toast.LENGTH_LONG).show();
        recent_month_all.setText(tittleTransactionList);

        Toast.makeText(getActivity(), String.valueOf(year)+" "+String.valueOf(startTime)+" " + String.valueOf(endTime), Toast.LENGTH_LONG).show();
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
        recent_current_month_all = view.findViewById(R.id.recent_current_month_all);
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




    }
}