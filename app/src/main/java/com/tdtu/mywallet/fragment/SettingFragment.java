package com.tdtu.mywallet.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tdtu.mywallet.R;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private ImageView userAvatarSetting;
    private TextView emailUserSetting;
    private DatabaseReference reference;
    private Button editPassword;
    private Button editCustomization;
    private Button editAbout;
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String email;

    private void getUser() {
        email = user.getEmail();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        String uid = user.getUid().toString();
        reference = firebaseDatabase.getReference(uid);
    }

    private void openEditPassword(int gravity) {
        Dialog dialogPassword = new Dialog(getActivity());
        dialogPassword.setContentView(R.layout.dialog_edit_password);
        Window windowPassword = dialogPassword.getWindow();
        if (windowPassword == null) {
            return;
        } else {
            windowPassword.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            windowPassword.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            windowPassword.setGravity(gravity);
            windowPassword.setWindowAnimations(R.style.anim1);
            dialogPassword.show();
        }

        TextInputLayout layoutCurrentPassword = dialogPassword.findViewById(R.id.layoutCurrentPassword);
        TextInputLayout layoutNewPassword = dialogPassword.findViewById(R.id.layoutNewPassword);
        TextInputLayout layoutConfirmNewPassword = dialogPassword.findViewById(R.id.layoutConfirmNewPassword);
        TextInputEditText etCurrentPassword = dialogPassword.findViewById(R.id.etCurrentPassword);
        TextInputEditText etNewPassword = dialogPassword.findViewById(R.id.etNewPassword);
        TextInputEditText etConfirmNewPassword = dialogPassword.findViewById(R.id.etConfirmNewPassword);
        Button btnEditPassword = dialogPassword.findViewById(R.id.btnEditPassword);

        // _____________________________________________________________________________________________
        // ***Validate***
        // Delete Error Line when user enter something
        etCurrentPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                layoutCurrentPassword.setError(null);
                String currentPassword = s.toString();
                if(currentPassword.length() >= 8){
                    layoutCurrentPassword.setError(null);
                }else{
                    layoutCurrentPassword.setError("Enter At Least 8 Characters !!!");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etNewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String newPassword = s.toString();
                if(newPassword.length() >= 8){
                    Pattern pattern = Pattern.compile("^(?=.*[A-Z])(?=.*\\d).+$");
                    Matcher matcher = pattern.matcher(newPassword);
                    boolean isPwdContain = matcher.find();
                    if(isPwdContain){
                        layoutNewPassword.setHelperText("Strong Password");
                        layoutNewPassword.setError(null);
                    }else{
                        layoutNewPassword.setHelperText(null);
                        layoutNewPassword.setError("Weak Password");
                    }
                }else{
                    layoutNewPassword.setHelperText("Enter At Least 8 Characters !!!");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etConfirmNewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                layoutConfirmNewPassword.setError(null);
                String confirmPassword = s.toString();
                if(confirmPassword.length() >= 8){
                    layoutConfirmNewPassword.setError(null);
                }else{
                    layoutConfirmNewPassword.setError("Enter At Least 8 Characters !!!");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnEditPassword.setOnClickListener((View view)->{
            String currentPassword = etCurrentPassword.getText().toString();
            String newPassword = etNewPassword.getText().toString();
            String confirmNewPassword = etConfirmNewPassword.getText().toString();

            // Validate
            // Notify user when 3 editTexts are empty.
            // Notify user when Confirm password is not the same as New Password

            if(TextUtils.isEmpty(currentPassword)){
                layoutCurrentPassword.setError("Please Enter Current Password");
            }else if(TextUtils.isEmpty(newPassword)){
                layoutNewPassword.setError("Please Enter New Password");
            }else if(TextUtils.isEmpty(confirmNewPassword)){
                layoutConfirmNewPassword.setError("Please Confirm New Password");
            }else if(!confirmNewPassword.equals(newPassword)){
                layoutConfirmNewPassword.setError("Confirm password is not the same as New Password !!!");
            }else if(newPassword.equals(currentPassword)){
                layoutNewPassword.setError("Please Enter Different New Password ");
            }else{
                changePassword(layoutCurrentPassword ,currentPassword, newPassword, dialogPassword );

            }






        });


    }

    private void changePassword(TextInputLayout layoutCurrentPassword, String currentPassword, String newPassword,Dialog dialogPassword ) {
        AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), currentPassword);
        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    // The user has been successfully re authenticated.
                    // Now, update the password with the new password.
                    user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getContext(), "Password updated successfully", Toast.LENGTH_SHORT).show();
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                LayoutInflater inflater = LayoutInflater.from(getContext());
                                View customView = inflater.inflate(R.layout.custom_dialog_edit_transaction,null);
                                builder.setView(customView);
                                AlertDialog alertDialog = builder.create();
                                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                alertDialog.show();
                                Button btnOK = customView.findViewById(R.id.btnOK);
                                btnOK.setOnClickListener((View view)->{
                                    alertDialog.dismiss();
                                    dialogPassword.dismiss();
                                });
                            }else{
                                Toast.makeText(getContext(), "Update Fail !!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    layoutCurrentPassword.setError("Wrong Password");
                    Toast.makeText(getContext(), "Fail !!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);


        userAvatarSetting = view.findViewById(R.id.userAvatarSetting);
        emailUserSetting = view.findViewById(R.id.emailUserSetting);
        editPassword = view.findViewById(R.id.editPassword);
        editCustomization = view.findViewById(R.id.editCustomization);
        editAbout = view.findViewById(R.id.editAbout);

        // connect to firebase and get user
        getUser();

        // set email for setting fragment
        emailUserSetting.setText(email);

        // set avatar for setting fragment
        reference.child("User Detail").child("userAvatar").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String avatar = Objects.requireNonNull(snapshot.getValue()).toString();

                int imageResID = view.getResources().getIdentifier(avatar, "drawable", getContext().getPackageName());
                userAvatarSetting.setBackgroundResource(imageResID);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //______________________________________________________________________________________________________________________________________________________________________________________
        // Change Password section
        editPassword.setOnClickListener((View viewPassword) -> {
            openEditPassword(Gravity.BOTTOM);
        });


        //______________________________________________________________________________________________________________________________________________________________________________________
        // cardCustomization section

        //______________________________________________________________________________________________________________________________________________________________________________________
        // cardAbout Section

        return view;
    }
}