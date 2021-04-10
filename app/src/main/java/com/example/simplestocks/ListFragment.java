package com.example.simplestocks;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class ListFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    /*    try {
            JSONObject stonks = new JSONObject(getIntent().getStringExtra("Response"));
        }catch (Exception e){
            System.out.println("****JSON PARSING ERROR****");
        }*/


        return inflater.inflate(R.layout.fragment_list,container,false);
    }

}
