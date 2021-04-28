package com.example.simplestocks;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    String stock;
    String currency;
    TextView userTV;
    String uname;
    ListView favList;
    String favs;
    String fq;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference userRef = db.getReference("user/user/"+user.getUid()+"/Favorites");

    @Nullable
    @Override


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //FirebaseApp.initializeApp(getContext());
        if(user!=null) {
            uname = user.getEmail();
           // System.out.println(uname);

        }
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference userRef = db.getReference("user");

        View view = inflater.inflate(R.layout.fragment_home,container,false);
        BottomNavigationView bottomNav = getActivity().findViewById(R.id.bottom_navigation);
        //this is needed as the navbar state is carried over from the login fragment
        bottomNav.setVisibility(View.VISIBLE);
        userTV = view.findViewById(R.id.userView);
        userTV.setText("Welcome "+ uname);
        favList = view.findViewById(R.id.favoritesList);






        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //an attempt to receivce data in an intent sent from the list fragment
        //did not work as the fragments are recreated and destoryed each time
        //FirebaseUser currentUser = mAuth.getCurrentUser();
        //System.out.println(currentUser);
        getFav();


    }
    public void getFav(){
        userRef.orderByKey().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                int i;



                favs = (String) snapshot.getValue();
                String[] strArray = favs.split(" ");
                System.out.println(strArray.length);
                ArrayList favListAR = new ArrayList(50);

                for(i = 0; i < strArray.length; i++ ){
                    System.out.println(strArray[i]);
                    ArrayList<String> sList = new ArrayList<>(); // need to figure out how to get more then 2 elements in the array
                    //sList.add(0,name);
                    sList.add(0,strArray[i]);

                    favListAR.add(sList);
                }
                ArrayAdapter<String> listViewAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,favListAR);
                favList.setAdapter(listViewAdapter);





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
