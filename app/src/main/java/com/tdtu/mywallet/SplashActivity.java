package com.tdtu.mywallet;

import static com.tdtu.mywallet.SignInActitvity.REMEMBER_ME;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
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
            Intent intent = new Intent(this, MainActivity.class);
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