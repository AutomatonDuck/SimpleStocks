package com.example.simplestocks;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NewsFragment extends Fragment {
    String stock;
    String name;
    String currency;
    ListView news;
    RequestQueue queue;

    //String key = getString(R.string.APIKey);
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_news,container,false);
        Button search = v.findViewById(R.id.search);
        news =  v.findViewById(R.id.newslist);
        queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNews(v);
            }
        });
        //getNews(v);




        return v;
    }

    public void getNews(View view){
        EditText tickerST = (EditText) view.findViewById(R.id.tickerSearch);
        String tickerName = "AAPL";
        if(tickerST != null) {
            tickerName = tickerST.getText().toString();
        } else {
            System.out.println("NULL");
        }
        String url = "https://api.polygon.io/v1/meta/symbols/"+ tickerName +"/news?perpage=20&page=1&apiKey=" + getString(R.string.APIKey);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,
                response -> {


                    System.out.println(response);
                    try {
                        JSONArray jordan = new JSONArray(response);
                        //System.out.println(jordan);

                        ArrayList stockList = new ArrayList(jordan.length());
                        for(int i = 0; i < jordan.length(); i++)
                        {
                            JSONObject parse = jordan.getJSONObject(i);

                            stock = parse.getString("title");
                            name = parse.getString("url");
                            currency = parse.getString("summary");
                            ArrayList<String> sList = new ArrayList<>(); // need to figure out how to get more then 2 elements in the array
                            //sList.add(0,name);
                            sList.add(0,stock);
                            sList.add(1,currency);
                            stockList.add(sList);

                            System.out.println(stock);
                            /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    setFav(fav, stock, currency);
                                    System.out.println("Click detected");
                                }
                            });*/


                        }
                        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, stockList);
                        news.setAdapter(listViewAdapter);



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
