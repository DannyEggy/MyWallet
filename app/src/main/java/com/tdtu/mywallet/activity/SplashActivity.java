package com.tdtu.mywallet.activity;

import static com.tdtu.mywallet.activity.SignInActitvity.REMEMBER_ME;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tdtu.mywallet.R;
import com.tdtu.mywallet.activity.MainActivity;
import com.tdtu.mywallet.activity.SignInActitvity;

public class SplashActivity extends AppCompatActivity {

    private String userName;
    private String  avatarResID;
    private int userBalance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        // get Data from Firebase
        // then push data from Splash Activity to another Activity

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase db = FirebaseDatabase.getInstance();

        if(currentUser != null){
            String uid = currentUser.getUid().toString();
            DatabaseReference reference = db.getReference(uid);
            reference.child("User Detail").child("userName").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    // change the name on toolbar
                    userName = snapshot.getValue().toString();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            // get userAvatar from firebase
            reference.child("User Detail").child("userAvatar").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    // change the name on toolbar
                    avatarResID = snapshot.getValue().toString();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            reference.child("User Detail").child("userBalance").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    // change the name on toolbar
                    userBalance = Integer.parseInt(snapshot.getValue().toString());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else{

        }

        // get user name from firebase






        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                nextActivity();
            }
        }, 2000);
    }

    private void nextActivity() {
        SharedPreferences sharedPreferences = getSharedPreferences(REMEMBER_ME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        Toast.makeText(getApplicationContext(),String.valueOf(isLoggedIn) , Toast.LENGTH_LONG).show();
        if (isLoggedIn) {
            // push data to MainActivity
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("userName", userName);
            intent.putExtra("avatarResID", avatarResID);
            intent.putExtra("userBalance", userBalance);
            startActivity(intent);

        } else {
            FirebaseAuth.getInstance().signOut();
            editor.clear().apply();
            Intent intent = new Intent(this, SignInActitvity.class);
            startActivity(intent);

        }


//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if (user == null){
//            //login failed
//
//        }
//        else{
//            //login successfully
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);
//        }
    }
}