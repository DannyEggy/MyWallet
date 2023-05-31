package com.tdtu.mywallet;

import static com.tdtu.mywallet.SignInActitvity.REMEMBER_ME;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tdtu.mywallet.fragment.HomeFragment;
import com.tdtu.mywallet.transformer.DepthPageTransformer;
import com.tdtu.mywallet.transformer.SlidePageTransformer;
import com.tdtu.mywallet.transformer.ZoomOutPageTransformer;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager2;
    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_ANALYSIS = 1;
    private static final int FRAGMENT_USER = 2;
    private static final int FRAGMENT_SETTING = 3;
    private int currentFragment = FRAGMENT_HOME;
    private FloatingActionButton floatingActionButton;
    private Button btnSpending;
    private Button btnIncome;
    private ImageView selectionAvatar;
    private String avatarUser ="avatar0";

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
    }



    private void initUI(){

        //  ***TOOLBAR***
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        selectionAvatar = findViewById(R.id.selectionAvatar);

//        avatarUser = ;  GET userAvatar from FireBase
        //setting the avatar selection of user
        switch (avatarUser) {
            case "avatar0":
                selectionAvatar.setBackgroundResource(R.drawable.user);
                break;
            case "avatar1":
                selectionAvatar.setBackgroundResource(R.drawable.avatar_man);
                break;
            case "avatar2":
                selectionAvatar.setBackgroundResource(R.drawable.avatar_man_1);
                break;
            case "avatar3":
                selectionAvatar.setBackgroundResource(R.drawable.avatar_man_2);
                break;
            case "avatar4":
                selectionAvatar.setBackgroundResource(R.drawable.avatar_man_3);
                break;
            case "avatar5":
                selectionAvatar.setBackgroundResource(R.drawable.avatar_woman);
                break;
            case "avatar6":
                selectionAvatar.setBackgroundResource(R.drawable.avatar_woman_1);
                break;
            case "avatar7":
                selectionAvatar.setBackgroundResource(R.drawable.avatar_woman_2);
                break;
            case "avatar8":
                selectionAvatar.setBackgroundResource(R.drawable.avatar_woman_3);
                break;
        }

        selectionAvatar.setOnClickListener((View view)->{
            openSelectionDialog(Gravity.CENTER);
        });


        //  ***BUTTON***
        floatingActionButton = findViewById(R.id.fab_adding);
        floatingActionButton.setOnClickListener((View view)->{
            openAddingDialog(Gravity.CENTER);
        });


        //   ***VIEWPAGER2***
        viewPager2 = findViewById(R.id.content_frame);
        MyViewPage2Adapter myViewPage2Adapter = new MyViewPage2Adapter(this);
        viewPager2.setAdapter(myViewPage2Adapter);
        viewPager2.setCurrentItem(0);

        //  ***BOTTOM_NAVIGATION_VIEW***
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.nav_home){
                    if(currentFragment != FRAGMENT_HOME){
//
                        viewPager2.setCurrentItem(0);
                        currentFragment = FRAGMENT_HOME;
                    }
                } else if(id == R.id.nav_analysis){
                    if(currentFragment != FRAGMENT_ANALYSIS){
//
                        viewPager2.setCurrentItem(1);
                        currentFragment = FRAGMENT_ANALYSIS;
                    }
                }else if(id==R.id.nav_user){
                    if(currentFragment != FRAGMENT_USER) {
                        viewPager2.setCurrentItem(2);
                        currentFragment = FRAGMENT_USER;
                    }
                }else if(id == R.id.nav_setting){
                    if(currentFragment != FRAGMENT_SETTING){
//
                        viewPager2.setCurrentItem(3);
                        currentFragment = FRAGMENT_SETTING;
                    }
                }
                return true;
            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position){
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.nav_analysis).setChecked(true);
                        break;

                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.nav_user).setChecked(true);
                        break;
                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.nav_setting).setChecked(true);
                        break;
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });


    }



    //  ***BOTTOM NAVIGATION***
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    //  ***TOOL BAR OPTION***
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.zoom){
            viewPager2.setPageTransformer(new ZoomOutPageTransformer());
        }else if(id == R.id.depth){
            viewPager2.setPageTransformer(new DepthPageTransformer());
        }
        else if(id == R.id.normal){
            // reset to normal
            MyViewPage2Adapter myViewPage2Adapter = new MyViewPage2Adapter(this);
            viewPager2.setAdapter(myViewPage2Adapter);
            viewPager2.setPageTransformer(null);
        }else if(id == R.id.signOut){
            // when sign out, clear all the shared preferences and sign out firebase authentication
            SharedPreferences sharedPreferencesRememberMe = getSharedPreferences(REMEMBER_ME, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferencesRememberMe.edit();
            editor.clear();
            editor.apply();
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this,SignInActitvity.class);
            startActivity(intent);
        }


        return true;
    }

    //  ***LOGICAL HANDLING
    @Override
    public void onBackPressed(){
        if (viewPager2.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            finishAffinity();
            super.onBackPressed();
        }
        else {
            // Otherwise, select the previous step.
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
        }
    }



    //  ***DIALOG HANDLING***
    private void openAddingDialog(int gravity) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_adding_activity);
        Window window = dialog.getWindow();

        if(window == null){
            return;
        }
        //  ***DIALOG***
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setGravity(gravity);
        dialog.show();

        // ***BUTTON***
        btnSpending = dialog.findViewById(R.id.btnSpending);
        btnIncome = dialog.findViewById(R.id.btnIncome);

        btnSpending.setOnClickListener((View view)->{
            Toast toast = Toast.makeText(this,"Spending", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL,0, 0);
            toast.show();
            dialog.dismiss();
        });

        btnIncome.setOnClickListener((View view)->{
            Toast toast = Toast.makeText(this, "Income", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL,0, 0);
            toast.show();
            dialog.dismiss();
        });
    }

    //  ***AVATAR SELECTION***
    private void openSelectionDialog(int gravity) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_avatar_selection);
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setGravity(gravity);
        dialog.show();

        ImageView avatar0 = dialog.findViewById(R.id.avatar0);
        ImageView avatar1 = dialog.findViewById(R.id.avatar1);
        ImageView avatar2 = dialog.findViewById(R.id.avatar2);
        ImageView avatar3 = dialog.findViewById(R.id.avatar3);
        ImageView avatar4 = dialog.findViewById(R.id.avatar4);
        ImageView avatar5 = dialog.findViewById(R.id.avatar5);
        ImageView avatar6 = dialog.findViewById(R.id.avatar6);
        ImageView avatar7 = dialog.findViewById(R.id.avatar7);
        ImageView avatar8 = dialog.findViewById(R.id.avatar8);

        avatar0.setOnClickListener((View view)->{
            avatarUser = "avatar0";
            selectionAvatar.setBackgroundResource(R.drawable.user);
            dialog.dismiss();
        });

        avatar1.setOnClickListener((View view)->{
            avatarUser = "avatar1";
            selectionAvatar.setBackgroundResource(R.drawable.avatar_man);
            dialog.dismiss();
        });

        avatar2.setOnClickListener((View view)->{
            avatarUser = "avatar2";
            selectionAvatar.setBackgroundResource(R.drawable.avatar_man_1);
            dialog.dismiss();
        });

        avatar3.setOnClickListener((View view)->{
            avatarUser = "avatar3";
            selectionAvatar.setBackgroundResource(R.drawable.avatar_man_2);
            dialog.dismiss();
        });

        avatar4.setOnClickListener((View view)->{
            avatarUser = "avatar4";
            selectionAvatar.setBackgroundResource(R.drawable.avatar_man_3);
            dialog.dismiss();
        });

        avatar5.setOnClickListener((View view)->{
            avatarUser = "avatar5";
            selectionAvatar.setBackgroundResource(R.drawable.avatar_woman);
            dialog.dismiss();
        });

        avatar6.setOnClickListener((View view)->{
            avatarUser = "avatar6";
            selectionAvatar.setBackgroundResource(R.drawable.avatar_woman_1);
            dialog.dismiss();
        });

        avatar7.setOnClickListener((View view)->{
            avatarUser = "avatar7";
            selectionAvatar.setBackgroundResource(R.drawable.avatar_woman_2);
            dialog.dismiss();
        });

        avatar8.setOnClickListener((View view)->{
            avatarUser = "avatar8";
            selectionAvatar.setBackgroundResource(R.drawable.avatar_woman_3);
            dialog.dismiss();
        });

    }

}