package com.example.simplestocks;


import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

import java.util.ArrayList;


public class ListFragment extends Fragment {
    String stock;
    String name;
    String currency;

    @Nullable
    @Override




    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        String url = "https://api.polygon.io/v2/reference/tickers?sort=ticker&perpage=50&page=1&apiKey=p43H0UpbcDsGNvlzCI62sVnwoapw4su_"; //need to modify for resuability
        View view = inflater.inflate(R.layout.fragment_list,container,false);
        ListView listView = (ListView) view.findViewById(R.id.ticker_list);

            RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());



            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    url,
                    response -> {


                        //System.out.println(response);
                        try {
                            JSONObject jordan = new JSONObject(response);
                            //System.out.println(jordan);
                            JSONArray tickers = jordan.getJSONArray("tickers");
                            ArrayList stockList = new ArrayList(tickers.length());
                            for(int i = 0; i < tickers.length(); i++)
                            {
                                JSONObject parse = tickers.getJSONObject(i);
                                stock = parse.getString("ticker");
                                name = parse.getString("name");
                                currency = parse.getString("currency");
                                ArrayList<String> sList = new ArrayList<>(); // need to figure out how to get more then 2 elements in the array
                                sList.add(0,name);
                                sList.add(1,stock);
                                //sList.add(2,currency);
                                stockList.add(sList);

                            }
                            ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, stockList);
                            listView.setAdapter(listViewAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }




                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            queue.add(stringRequest);



        return view;

    }


}
