package com.tdtu.mywallet.anaysis_fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tdtu.mywallet.R;
import com.tdtu.mywallet.model.Activity;
import com.tdtu.mywallet.recyclerview_adapter.TransactionAnalysisAdapter;
import com.tdtu.mywallet.viewmodel.SearchTextViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IncomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IncomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public IncomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IncomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IncomeFragment newInstance(String param1, String param2) {
        IncomeFragment fragment = new IncomeFragment();
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

    private RecyclerView income_rv;
    private DatabaseReference reference;
    private List<Activity> incomeList = new ArrayList<Activity>();

    private CardView cardIncome;

    private RecyclerView searchTransaction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_income, container, false);

        cardIncome = view.findViewById(R.id.card_income);

        income_rv = view.findViewById(R.id.income_rv);
        TransactionAnalysisAdapter analysisAdapter = new TransactionAnalysisAdapter(incomeList,requireActivity());
        income_rv.setAdapter(analysisAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        income_rv.setLayoutManager(linearLayoutManager);
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(income_rv.getContext(),
                linearLayoutManager.getOrientation());
        income_rv.addItemDecoration(mDividerItemDecoration);

        searchTransaction = view.findViewById(R.id.searchTransactionIncome);
        LinearLayoutManager linearLayoutManagerSearch = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        searchTransaction.setLayoutManager(linearLayoutManagerSearch);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(searchTransaction.getContext()
                , linearLayoutManagerSearch.getOrientation());
        searchTransaction.addItemDecoration(dividerItemDecoration);


        connectFirebase();
        reference.child("User Detail").child("userActivityList").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Activity activity = snapshot.getValue(Activity.class);

                if(activity.getActivityType().equals("Income")){
                    incomeList.add(0, activity);
                }
                income_rv.getAdapter().notifyDataSetChanged();
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
                                if (activity.getActivityName().toLowerCase().contains(s.toLowerCase()) &&
                                        activity.getActivityType().equals("Income")) {
                                    searchList.add(0, activity);
                                }
                            }

                            cardIncome.setVisibility(View.GONE);
                            income_rv.setVisibility(View.GONE);

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
                    cardIncome.setVisibility(View.VISIBLE);
                    income_rv.setVisibility(View.VISIBLE);

                }


            }
        });


        return view;
    }

    public void connectFirebase() {
        // Firebase connection
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        reference = db.getReference(currentUser.getUid());
    }
}