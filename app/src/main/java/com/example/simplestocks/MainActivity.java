package com.example.simplestocks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    String url = "https://api.polygon.io/v2/reference/tickers?sort=ticker&perpage=50&page=1&apiKey=p43H0UpbcDsGNvlzCI62sVnwoapw4su_";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LoginFragment()).commit();
        getTicker(url);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener(){

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch ((item.getItemId())) {
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_ticker:
                            selectedFragment = new ListFragment();
                            break;
                        case R.id.nav_news:
                            selectedFragment = new NewsFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;

                }
            };

    public void getTicker(String url) {
        RequestQueue queue = Volley.newRequestQueue(this);



        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,
                response -> {
                    //textView.setText("Response is: " + response.substring(0, 489)); //response is only length 489

                    //System.out.println(response);
                  try {
                       JSONObject jordan = new JSONObject(response);

                       //temp = main.getString("temp");
                    } catch (JSONException e) {
                       e.printStackTrace();
                   }

                    //tempView.setText("Temprature: " + temp);


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "No Response", Toast.LENGTH_SHORT).show(); // change to toast

            }
        });
        queue.add(stringRequest);


    }

    public void sendResponse(JSONObject response){
        Intent intent = new Intent(this, ListFragment.class);
        intent.putExtra("Response",response.toString());
        startActivity(intent);
    }
}