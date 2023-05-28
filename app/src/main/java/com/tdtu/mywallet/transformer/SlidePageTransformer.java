package com.tdtu.mywallet.transformer;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

public class SlidePageTransformer implements ViewPager2.PageTransformer {
    @Override
    public void transformPage(@NonNull View page, float position) {
        int pageWidth = page.getWidth();

        if (position < -1) { // Trang bên trái của màn hình
            page.setAlpha(0f);
        } else if (position <= 0) { // Trang hiện tại và trang bên phải của màn hình
            page.setAlpha(1f);
            page.setTranslationX(0f);
        } else if (position <= 1) { // Trang bên phải của màn hình
            page.setAlpha(1f);
            page.setTranslationX(-pageWidth * position);
        } else { // Trang bên phải xa màn hình
            page.setAlpha(0f);
        }
    }
}

