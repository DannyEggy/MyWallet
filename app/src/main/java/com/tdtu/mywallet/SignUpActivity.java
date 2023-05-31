package com.tdtu.mywallet;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tdtu.mywallet.model.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    private Button btnSignUp;
    private TextInputLayout textInputLayoutEmailSignUp;
    private TextInputLayout textInputLayoutPasswordSignUp;
    private TextInputEditText emailUserSignUp;
    private TextInputEditText passwordUserSignUp;

    private FirebaseAuth mAuth;



    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btnSignUp = findViewById(R.id.btnSignUp);
        emailUserSignUp = findViewById(R.id.emailUserSignUp);
        passwordUserSignUp = findViewById(R.id.passwordUserSignUp);
        textInputLayoutEmailSignUp = findViewById(R.id.textInputLayoutEmailSignUp);
        textInputLayoutPasswordSignUp = findViewById(R.id.textInputLayoutPasswordSignUp);

        emailUserSignUp.setOnTouchListener((v, event) -> {
            textInputLayoutEmailSignUp.setError(null);
            return false;
        });

        passwordUserSignUp.setOnTouchListener((v, event) -> {
            textInputLayoutPasswordSignUp.setError(null);
            return false;
        });

        passwordUserSignUp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = s.toString();
                if(password.length()>=8){
                    Pattern pattern = Pattern.compile("^(?=.*[A-Z])(?=.*\\d).+$");
                    Matcher matcher = pattern.matcher(password);
                    boolean isPwdContain = matcher.find();
                    if(isPwdContain){
                        textInputLayoutPasswordSignUp.setHelperText("Strong Password");
                        textInputLayoutPasswordSignUp.setError(null);
                    }else{
                        textInputLayoutPasswordSignUp.setHelperText(null);
                        textInputLayoutPasswordSignUp.setError("Weak Password");
                    }


                }else{
                    textInputLayoutPasswordSignUp.setHelperText("Enter At Least 8 Characters");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnSignUp.setOnClickListener((View view)->{
            String email = emailUserSignUp.getText().toString();
            String password = passwordUserSignUp.getText().toString();
            signUp(email, password);
        });
    }

    private void signUp(String email, String password){
        if(TextUtils.isEmpty(email)){
            textInputLayoutEmailSignUp.setError("Please Input Email");
            return;
        } else if (!email.contains("@")) {
            textInputLayoutEmailSignUp.setError("This is not Email");
            return;
        }else if(TextUtils.isEmpty(password)){
            textInputLayoutPasswordSignUp.setError("Please Input Password");
            return;
        }


        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseDatabase db = FirebaseDatabase.getInstance();
                            DatabaseReference reference = db.getReference();
                            String[] words = email.split("@");
                            User userRegister = new User(email, password);
                            reference.child(words[0]).setValue(userRegister);
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(SignUpActivity.this, SignInActitvity.class);
                            startActivity(intent);
                            finishAffinity();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }
}