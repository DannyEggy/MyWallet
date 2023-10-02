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


    private FirebaseAuth mAuth;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Handling login function
        // Click text signUp -> move to SignUpActivity
        // Click Login Button -> Validate input
        // -> Check if email is registered: (Yes -> signInHandle, No -> Notify user)
        // -> (Login success -> send several data to MainActivity, Login fail -> notify user)
        // Handling the back button press event:
        // Account is not login then press back button -> close app. Prevent user from go back to previous activity.


        // View Binding
        emailUser = findViewById(R.id.emailUser);
        passwordUser = findViewById(R.id.passwordUser);
        textInputLayoutEmail = findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword);
        signUp = findViewById(R.id.toSignUp);
        login = findViewById(R.id.btnLogin);
        checkBoxRememberMe = findViewById(R.id.rememberMe);


        // Event handling Email and Password TextInputLayout
        emailUser.setOnTouchListener((v, event) -> {
            textInputLayoutEmail.setError(null);
            return false;
        });

        passwordUser.setOnTouchListener((v, event) -> {
            textInputLayoutPassword.setError(null);
            return false;
        });

        // Button signup and login handling
        signUp.setOnClickListener((View view) -> {
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        });

        login.setOnClickListener((View view) -> {
            String email = emailUser.getText().toString();
            String password = passwordUser.getText().toString();

            login(email, password);
        });




    }

    private void login(String email, String password) {

        // Validating input
        if (TextUtils.isEmpty(email)) {
            textInputLayoutEmail.setError("Please Input Email");
            return;
        } else if (!email.contains("@")) {
            textInputLayoutEmail.setError("This is not Email");
            return;
        } else if (password.length() <= 7 && password.length() >= 1) {
            textInputLayoutPassword.setError("Please Input Enough Password");
            return;
        } else if (TextUtils.isEmpty(password)) {
            textInputLayoutPassword.setError("Please Input Password");
            return;
        }

        // Checking if email is registered
        mAuth = FirebaseAuth.getInstance();
        mAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                if (task.isSuccessful()) {
                    SignInMethodQueryResult result = task.getResult();
                    List<String> signInMethods = result.getSignInMethods();
                    if (signInMethods != null && !signInMethods.isEmpty()) {
                        // Email is registered
                        signInHandle(email, password);

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

    private void signInHandle(String email, String password) {
        // Handling signIn with rememberMe checkbox
        // Create SharedPreferences to save rememberMe state
        // Login successfully -> Get userName and userAvatar from this user and then sent to MainActivity
        // Login failed -> Notify user

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            // checking the checkbox
                            if (checkBoxRememberMe.isChecked()) {
                                // create shared preference when checkbox is checked: true
                                Toast.makeText(getApplication(), "Save state", Toast.LENGTH_LONG).show();
                                SharedPreferences sharedPreferencesRememberMe = getSharedPreferences(REMEMBER_ME, MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferencesRememberMe.edit();
                                editor.putBoolean("isLoggedIn", true);
                                editor.apply();
                            } else {
                                // create shared preference when checkbox is not checked: false
                                Toast.makeText(getApplication(), "Don't Save", Toast.LENGTH_LONG).show();
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
                        } else {
                            Toast.makeText(getApplicationContext(), "Sign In Failed !!!", Toast.LENGTH_LONG).show();
                            textInputLayoutPassword.setError("Wrong Password !!!");
                        }
                    }
                });
    }


    @Override
    public void onBackPressed() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser == null) {
            finishAffinity();
            super.onBackPressed();
        }

    }
}