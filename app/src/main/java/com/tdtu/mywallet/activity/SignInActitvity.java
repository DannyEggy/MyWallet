package com.tdtu.mywallet.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tdtu.mywallet.R;

import java.util.List;

public class SignInActitvity extends AppCompatActivity {
    private TextView signUp;
    private Button login;
    private TextInputEditText emailUser;
    private TextInputLayout textInputLayoutEmail;
    private TextInputEditText passwordUser;
    private TextInputLayout textInputLayoutPassword;
    private CheckBox checkBoxRememberMe;
    private String userNameSignIn = "Eggy";
    private String avatarResIDSignIn = "avatar0";

    public static final String REMEMBER_ME = "rememberMe";
//    private String userName ="Eggy";
//    private int  avatarResID;

    private FirebaseAuth mAuth;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Get Data from Firebase
        // Then push data from Splash Activity to another Activity


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
        } else if (password.length() <= 7 && password.length()>=1)  {
            textInputLayoutPassword.setError("Please Input Enough Password");
            return;
        } else if(TextUtils.isEmpty(password)){
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

                        textInputLayoutEmail.setError("You Haven't Registered Your Account Yet");
                    }
                } else {
                    // Error when checking email
                    Exception exception = task.getException();

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
                            // checking the checkbox
                            if(checkBoxRememberMe.isChecked()){
                                // create shared preference when checkbox is checked: true
                                Toast.makeText(getApplication(),"Save state", Toast.LENGTH_LONG).show();
                                SharedPreferences sharedPreferencesRememberMe = getSharedPreferences(REMEMBER_ME, MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferencesRememberMe.edit();
                                editor.putBoolean("isLoggedIn", true);
                                editor.apply();
                            }else{
                                // create shared preference when checkbox is not checked: false
                                Toast.makeText(getApplication(),"Don't Save", Toast.LENGTH_LONG).show();
                                SharedPreferences sharedPreferencesRememberMe = getSharedPreferences(REMEMBER_ME, MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferencesRememberMe.edit();
                                editor.putBoolean("isLoggedIn", false);
                                editor.apply();
                            }

                            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                            FirebaseDatabase db = FirebaseDatabase.getInstance();
                            DatabaseReference reference = db.getReference(currentUser.getUid().toString());

                            // get user name from firebase
                            reference.child("User Detail").child("userName").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    // change the name on toolbar
                                    userNameSignIn = snapshot.getValue().toString();
                                    // get userAvatar from firebase
                                    reference.child("User Detail").child("userAvatar").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            // change the avatar on toolbar
                                            avatarResIDSignIn = snapshot.getValue().toString();
                                            // intent to pass data
                                            Intent intentMove = new Intent(SignInActitvity.this, MainActivity.class);
                                            intentMove.putExtra("userNameSignIn", userNameSignIn);
                                            intentMove.putExtra("avatarResIDSignIn", avatarResIDSignIn);
                                            startActivity(intentMove);
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Sign In Failed !!!", Toast.LENGTH_LONG).show();
                            textInputLayoutPassword.setError("Wrong Password !!!");
                        }
//                        } else {
//                            // If sign in fails, displ  ay a message to the user.
//                            Log.w(TAG, "signInWithEmail:failure", task.getException());
////
//
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