package com.example.simplestocks;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class LogoutFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_logout,container,false);
        //hide navbar
        //BottomNavigationView bottomNav = getActivity().findViewById(R.id.bottom_navigation);
        //bottomNav.setVisibility(View.GONE);


        return view;
    }

}
