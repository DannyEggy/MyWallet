package com.tdtu.mywallet;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tdtu.mywallet.model.User;

import java.util.List;

public class SignInActitvity extends AppCompatActivity {
    private TextView signUp;
    private Button login;
    private TextInputEditText emailUser;
    private TextInputLayout textInputLayoutEmail;
    private TextInputEditText passwordUser;
    private TextInputLayout textInputLayoutPassword;
    private CheckBox checkBoxRememberMe;
    public static final String REMEMBER_ME = "rememberMe";


    private FirebaseAuth mAuth;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);




        emailUser = findViewById(R.id.emailUser);
        passwordUser = findViewById(R.id.passwordUser);
        textInputLayoutEmail = findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword);

        emailUser.setOnTouchListener((v, event) -> {
            textInputLayoutEmail.setError(null);
            return false;
        });

        passwordUser.setOnTouchListener((v, event) -> {
            textInputLayoutPassword.setError(null);
            return false;
        });



        signUp = findViewById(R.id.toSignUp);
        signUp.setOnClickListener((View view) ->{
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        });

        login = findViewById(R.id.btnLogin);
        login.setOnClickListener((View view)->{
            String email = emailUser.getText().toString();
            String password = passwordUser.getText().toString();
            login(email, password);
        });

        checkBoxRememberMe = findViewById(R.id.rememberMe);



    }

    private void login(String email, String password){
        if(TextUtils.isEmpty(email)){
            textInputLayoutEmail.setError("Please Input Email");
            return;
        } else if (!email.contains("@")) {
            textInputLayoutEmail.setError("This is not Email");
            return;
        }
        else if(TextUtils.isEmpty(password)){
            textInputLayoutPassword.setError("Please Input Password");
            return;
        }

        mAuth = FirebaseAuth.getInstance();

        mAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                if (task.isSuccessful()) {
                    SignInMethodQueryResult result = task.getResult();
                    List<String> signInMethods = result.getSignInMethods();
                    if (signInMethods != null && !signInMethods.isEmpty()) {
                        // Email is registered
                        // Xử lí tại đây
                        signInHandle(email,password);

                    } else {
                        // Email is not registered
                        // Xử lí tại đây
                        textInputLayoutEmail.setError("You Haven't Registered Your Account Yet");
                    }
                } else {
                    // Đã xảy ra lỗi trong quá trình kiểm tra
                    Exception exception = task.getException();
                    // Xử lí lỗi tại đây

                }
            }
        });





    }

    private void signInHandle(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
//                            if(user.isEmailVerified()){
                                // create shared preference to save login state of user
                                // true when user check and false when user doesn't check

                                if(checkBoxRememberMe.isChecked()){
                                    Toast.makeText(getApplication(),"Save state", Toast.LENGTH_LONG).show();
                                    SharedPreferences sharedPreferencesRememberMe = getSharedPreferences(REMEMBER_ME, MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferencesRememberMe.edit();
                                    editor.putBoolean("isLoggedIn", true);
                                    editor.apply();
                                }else{
                                    Toast.makeText(getApplication(),"Don't Save", Toast.LENGTH_LONG).show();
                                    SharedPreferences sharedPreferencesRememberMe = getSharedPreferences(REMEMBER_ME, MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferencesRememberMe.edit();
                                    editor.putBoolean("isLoggedIn", false);
                                    editor.apply();
                                }

                                //intent to move to MainActivity

                                Intent intent = new Intent(SignInActitvity.this, MainActivity.class);
                                intent.putExtra("email", email);
                                intent.putExtra("password", password);
                                SignInActitvity.this.startActivity(intent);
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Please verify your email", Toast.LENGTH_LONG).show();
                            }
//                        } else {
//                            // If sign in fails, displ  ay a message to the user.
//                            Log.w(TAG, "signInWithEmail:failure", task.getException());
////
//                            textInputLayoutPassword.setError("Wrong Password !!!");
//
//                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser == null){
            finishAffinity();
            super.onBackPressed();
        }

    }
}