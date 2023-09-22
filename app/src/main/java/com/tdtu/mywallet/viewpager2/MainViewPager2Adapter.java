package com.tdtu.mywallet.viewpager2;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.tdtu.mywallet.main_fragment.AnalysisFragment;
import com.tdtu.mywallet.main_fragment.HomeFragment;
import com.tdtu.mywallet.main_fragment.SettingFragment;
import com.tdtu.mywallet.main_fragment.BudgetFragment;

public class MainViewPager2Adapter extends FragmentStateAdapter {


    public MainViewPager2Adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new HomeFragment();
            case 1:
                return new AnalysisFragment();
            case 2:
                return new BudgetFragment();
            case 3:
                return new SettingFragment();

            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
