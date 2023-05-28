package com.tdtu.mywallet;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tdtu.mywallet.model.User;

public class SignUpActivity extends AppCompatActivity {
    private Button btnSignUp;
    private EditText emailUserSignUp;
    private EditText passwordUserSignUp;

    private FirebaseAuth mAuth;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference reference = db.getReference("User");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initUI();
        initListener();
    }

    private void initUI(){
        btnSignUp = findViewById(R.id.btnSignUp);
        emailUserSignUp = findViewById(R.id.emailUserSignUp);
        passwordUserSignUp = findViewById(R.id.passwordUserSignUp);

    }

    private void initListener(){
        mAuth = FirebaseAuth.getInstance();

        btnSignUp.setOnClickListener((View view)->{
            String email = emailUserSignUp.getText().toString().trim();
            String password = passwordUserSignUp.getText().toString().trim();
            Toast.makeText(this, email, Toast.LENGTH_LONG).show();
            Toast.makeText(this, password, Toast.LENGTH_LONG).show();
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information

                                String[] words = email.split("@");
                                User userRegister = new User(email, password);
                                reference.child(words[0]).setValue(user);

                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
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
        });
    }
}