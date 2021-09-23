package com.example.slptracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    private TextView slpPrice;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Tat title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        slpPrice = findViewById(R.id.slp_price);
        Button buttonRefresh = findViewById(R.id.button_refresh);

        mQueue = Volley.newRequestQueue(this);

        buttonRefresh.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                jsonParse();
            }
        });

    }
    private void jsonParse() {
        String url = "https://api.coingecko.com/api/v3/simple/price?ids=smooth-love-potion&vs_currencies=usd";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.v("response", String.valueOf(response));
                    JSONObject jsonObject = response.getJSONObject("smooth-love-potion");
                    float price = (float) jsonObject.getDouble("usd");
                    slpPrice.setText(String.format("%.4f",price));
                    Log.v("slp_price", String.valueOf(price));

                } catch (JSONException e) {
                    Log.v("Response", String.valueOf(e));
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);
    }
}