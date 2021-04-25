package com.example.simplestocks;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeFragment extends Fragment {
    String stock;
    String currency;
    @Nullable
    @Override


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_home,container,false);
        BottomNavigationView bottomNav = getActivity().findViewById(R.id.bottom_navigation);
        //this is needed as the navbar state is carried over from the login fragment
        bottomNav.setVisibility(View.VISIBLE);





        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //an attempt to receivce data in an intent sent from the list fragment
        //did not work as the fragments are recreated and destoryed each time
        Bundle intent = this.getArguments();
        if(intent != null){
            stock = (String) intent.getSerializable("ticker");
            currency = (String) intent.getSerializable("currency");

        }
        System.out.println(stock);


    }
}
