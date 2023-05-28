package com.tdtu.mywallet;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
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

public class SignInActitvity extends AppCompatActivity {
    private Button signUp;
    private Button login;

    private EditText emailUser;
    private EditText passwordUser;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        emailUser = findViewById(R.id.emailUser);
        passwordUser = findViewById(R.id.passwordUser);

        signUp = findViewById(R.id.btnToSignUp);
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

    }

    private void login(String email, String password){
//        String email,password;
//        email=account.getText().toString();
//
//        password=pass.getText().toString();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Vui lòng nhập email",Toast.LENGTH_SHORT).show();

            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Vui lòng nhập mật khẩu",Toast.LENGTH_SHORT).show();

            return;
        }

        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();




//                            SharedPreferences sharedPreferences = getSharedPreferences("LoginSession",MODE_PRIVATE);
//                            SharedPreferences.Editor myEdit = sharedPreferences.edit();
//                            myEdit.putString("session", "yes");
//                            myEdit.commit();
                            if(user.isEmailVerified()){
                                Intent intent = new Intent(SignInActitvity.this, MainActivity.class);
                                intent.putExtra("email", email);
                                intent.putExtra("password", password);
                                SignInActitvity.this.startActivity(intent);
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Please verify your email", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            // If sign in fails, displ  ay a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
//
                            Toast.makeText(getApplicationContext(), "Can't sign in, please check your Email, Password And Internet", Toast.LENGTH_LONG).show();

                        }
                    }
                });


    }
}