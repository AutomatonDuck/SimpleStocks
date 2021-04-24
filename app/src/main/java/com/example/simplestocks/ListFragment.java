package com.example.simplestocks;


import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
    View fav;
    ListView listView;
    EditText symbolST;
    Button symbolBT;

    @Nullable
    @Override




    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list,container,false);
        listView = (ListView) view.findViewById(R.id.ticker_list);
        symbolST = view.findViewById(R.id.stocksearch);
        symbolBT = view.findViewById(R.id.getStockBT);
        symbolBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStock(v);
            }
        });


        return view;

    }
    public void setFav(View v, String ticker, String currency){
        HomeFragment fragment = new HomeFragment();
        Bundle intent = new Bundle();
        intent.putSerializable("ticker",ticker);
        intent.putSerializable("currency",currency);
        fragment.setArguments(intent);

    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);




    }

    public void getStock(View view){
        String tick = "AAPL";
       if(symbolST != null) {
          tick = symbolST.getText().toString();
        }
        String url = "https://api.polygon.io/vX/reference/tickers?ticker="+tick+"&type=CS&market=stocks&active=true&sort=ticker&order=asc&limit=10&apiKey=" + getString(R.string.APIKey);



        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());



        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,
                response -> {


                    //System.out.println(response);
                    try {
                        JSONObject jordan = new JSONObject(response);
                        //System.out.println(jordan);
                        JSONArray tickers = jordan.getJSONArray("results");
                        ArrayList stockList = new ArrayList(tickers.length());
                        for(int i = 0; i < tickers.length(); i++)
                        {
                            JSONObject parse = tickers.getJSONObject(i);
                            stock = parse.getString("ticker");
                            name = parse.getString("name");
                            currency = parse.getString("currency_name");
                            ArrayList<String> sList = new ArrayList<>(); // need to figure out how to get more then 2 elements in the array
                            //sList.add(0,name);
                            sList.add(0,stock);
                            sList.add(1,currency);
                            stockList.add(sList);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    setFav(fav, stock, currency);
                                    System.out.println("Click detected");
                                }
                            });


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

    }
}
