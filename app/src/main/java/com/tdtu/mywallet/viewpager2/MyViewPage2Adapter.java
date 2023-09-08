package com.tdtu.mywallet.viewpager2;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.tdtu.mywallet.fragment.AnalysisFragment;
import com.tdtu.mywallet.fragment.HomeFragment;
import com.tdtu.mywallet.fragment.SettingFragment;
import com.tdtu.mywallet.fragment.UserFragment;

public class MyViewPage2Adapter extends FragmentStateAdapter {


    public MyViewPage2Adapter(@NonNull FragmentActivity fragmentActivity) {
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
                return new UserFragment();
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
