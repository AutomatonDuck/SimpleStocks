package com.example.simplestocks;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.content.ContentValues.TAG;


public class LoginFragment extends Fragment {
    private FirebaseAuth mAuth;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference userRef = db.getReference("user");

    @Nullable
    @Override


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login,container,false);
        FirebaseApp.initializeApp(getContext());
        mAuth = FirebaseAuth.getInstance();

        Button signinBT = view.findViewById(R.id.signInButton);
        Button newuser = view.findViewById(R.id.newUserButton);
       // onStart();

        signinBT.setOnClickListener(v -> {
            signin(view);
        });
        newuser.setOnClickListener(v -> createAccount(view));
        //hide navbar
        BottomNavigationView bottomNav = getActivity().findViewById(R.id.bottom_navigation);
        bottomNav.setVisibility(View.GONE);




        return view;
    }

    @Override
    // used for persistant login, not implemented yet
    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }
    }
    public void createAccount(View view){
        try {


            EditText emailST = (EditText) view.findViewById(R.id.userID);
            EditText passwordST = (EditText) view.findViewById(R.id.userPassword);


            String email = emailST.getText().toString();
            String password = passwordST.getText().toString();
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener((Activity) getContext(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "CreateUserWithEmail:Success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                String userId = user.getUid(); // get unique hash for user
                                writeNewUser(userId, email, password);
                                updateUI(user);
                            } else {
                                Log.d(TAG, "CreateUserWithEmail:Failure", task.getException());
                                Toast.makeText(getContext(), "Authentication Failed.", Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        }
                    });
        } catch (Exception e){
            Toast.makeText(getContext(), "Enter new username and password",Toast.LENGTH_SHORT).show();
        }
    }

    public void signin(View view){
        try {


            EditText emailST = (EditText) view.findViewById(R.id.userID);
            EditText passwordST = (EditText) view.findViewById(R.id.userPassword);
            if (emailST == null || passwordST == null) {
                Toast.makeText(getContext(), "Enter username and password", Toast.LENGTH_SHORT).show();
                return;
            }
            String email = emailST.getText().toString();
            String password = passwordST.getText().toString();
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener((Activity) getContext(), new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "SignIn: Successful");
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                    } else {
                        Log.d(TAG, "LoginUserWithEmail:Failure", task.getException());
                        Toast.makeText(getContext(), "Authentication Failed.", Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }
                }
            });
        }catch (Exception e){
            Toast.makeText(getContext(), "Enter username and password",Toast.LENGTH_SHORT).show();
        }
    }

    //would be used to reload loginfragment if onStart() showed NULL
    private void reload(){
       // getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LoginFragment()).commit();
    }

    //Transfers user to home fragment if login or user creation in successful
    private void updateUI(FirebaseUser user){
        if(user != null) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment(), "Find this fragment").addToBackStack(null).commit();
        }
    }

    //creates new user and upload to Firebase database
    public void writeNewUser(String userId, String email, String password){
        User user = new User(email, password);
        userRef.child("user").child(userId).setValue(user);
    }

}
