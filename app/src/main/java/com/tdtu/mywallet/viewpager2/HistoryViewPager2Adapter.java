package com.tdtu.mywallet.viewpager2;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.tdtu.mywallet.anaysis_fragment.AllFragment;
import com.tdtu.mywallet.anaysis_fragment.IncomeFragment;
import com.tdtu.mywallet.anaysis_fragment.SpendingFragment;

public class HistoryViewPager2Adapter extends FragmentStateAdapter {

    public HistoryViewPager2Adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new AllFragment();
            case 1:
                return new SpendingFragment();
            case 2:
                return new IncomeFragment();
            default:
                return new AllFragment();
        }

    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
