package com.tdtu.mywallet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tdtu.mywallet.fragment.HomeFragment;
import com.tdtu.mywallet.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryListHolder {
    private static CategoryListHolder instance;
    private List<Category> categoryList;

    private CategoryListHolder() {
        categoryList = new ArrayList<Category>();
    }

    public static synchronized CategoryListHolder getInstance() {
        if (instance == null) {
            instance = new CategoryListHolder();
        }
        return instance;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public void updateUserList() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        String uid = user.getUid().toString();
        DatabaseReference reference= firebaseDatabase.getReference(uid);
        reference.child("User Detail").child("userCategory").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Category category = snapshot.getValue(Category.class);
                if(category != null){
                    categoryList.add(category);
                }
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                HomeFragment homeFragment = (HomeFragment) fragmentManager.findFragmentById(R.id.home_fragment_container);
                CategoryListHolder.getInstance().setCategoryList(categoryList);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
