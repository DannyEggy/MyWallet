package com.tdtu.mywallet.main_fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;


import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.tdtu.mywallet.R;
import com.tdtu.mywallet.model.Activity;
import com.tdtu.mywallet.model.Category;
import com.tdtu.mywallet.viewmodel.SearchTextViewModel;
import com.tdtu.mywallet.viewpager2.HistoryViewPager2Adapter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AnalysisFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnalysisFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AnalysisFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AnalysisFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AnalysisFragment newInstance(String param1, String param2) {
        AnalysisFragment fragment = new AnalysisFragment();
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






    private TabLayout tabLayout;
    private SearchView searchView;
    private ViewPager2 viewPager2;

    private List<Activity> activityList = new ArrayList<Activity>();
    private List<Activity> filterList = new ArrayList<Activity>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_analysis, container, false);

        // View Binding
        viewBindingAnalysis(view);


        // Set viewpager2 adapter
        // Setting 3 tab in viewpager2
        HistoryViewPager2Adapter historyViewPager2Adapter = new HistoryViewPager2Adapter(getActivity());
        viewPager2.setAdapter(historyViewPager2Adapter);



        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout,viewPager2,false,false,  (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("All");
                    break;
                case 1:
                    tab.setText("Spending");
                    break;
                case 2:
                    tab.setText("Income");
                    break;
            }
        });
        tabLayoutMediator.attach();
        TabLayout.Tab defaultTab = tabLayout.getTabAt(0);
        defaultTab.select();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                SearchTextViewModel viewModel = new ViewModelProvider(requireActivity()).get(SearchTextViewModel.class);
                viewModel.setTitleQuery(newText);

                return false;
            }
        });


        return view;
    }

    public void viewBindingAnalysis(View view){
        searchView = view.findViewById(R.id.searchAnalysis);
        tabLayout = view.findViewById(R.id.tabLayoutAnalysis);
        viewPager2 = view.findViewById(R.id.transactionHistory);
    }





}