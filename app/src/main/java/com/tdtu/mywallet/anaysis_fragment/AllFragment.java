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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all, container, false);

        // View Binding
        viewBindingAllPage(view);
        getTransactionList();

        return view;
    }

    public void connectFirebase() {
        // Firebase connection
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference reference = db.getReference(currentUser.getUid());
    }

    public void getTransactionList() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2023);
        calendar.set(Calendar.MONTH, Calendar.SEPTEMBER);
        long startTime = calendar.getTimeInMillis();
        calendar.set(Calendar.MONTH, Calendar.OCTOBER);
        long endTime = calendar.getTimeInMillis();

        // Firebase connection
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference reference = db.getReference(currentUser.getUid());
        Query query = reference.child("User Detail").child("userActivityList")
                .orderByChild("activityDateTime").startAt(startTime).endAt(endTime);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Activity> activityList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Activity transaction = snapshot.getValue(Activity.class);
                    activityList.add(transaction);

                    TransactionAdapter transactionAdapter = new TransactionAdapter(activityList, getActivity(), getActivity());
                    rv_1_month_all.setAdapter(transactionAdapter);
                    LinearLayoutManager linearLayoutManager_1month = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    rv_1_month_all.setLayoutManager(linearLayoutManager_1month);
                    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv_1_month_all.getContext()
                            , linearLayoutManager_1month.getOrientation());
                    rv_1_month_all.addItemDecoration(dividerItemDecoration);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void viewBindingAllPage(View view) {
        //TextView view binding
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