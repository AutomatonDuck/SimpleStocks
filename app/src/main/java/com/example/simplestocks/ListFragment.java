package com.example.simplestocks;


import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ListFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        String url = "https://api.polygon.io/v2/reference/tickers?sort=ticker&perpage=50&page=1&apiKey=p43H0UpbcDsGNvlzCI62sVnwoapw4su_";


            RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());



            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    url,
                    response -> {


                        //System.out.println(response);
                        try {
                            JSONObject jordan = new JSONObject(response);
                            //System.out.println(jordan);
                            JSONArray tickers = jordan.getJSONArray("tickers");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }




                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            queue.add(stringRequest);



        return inflater.inflate(R.layout.fragment_list,container,false);

    }


}
