package com.mohsproject.bitup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private final String BASE_URL = "https://apiv2.bitcoinaverage.com/indices/global/ticker/BTC";
    TextView mAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView mPrice = (TextView) findViewById(R.id.textView);
        mAmount = (TextView) findViewById(R.id.textView2);
        LottieAnimationView mbackground = (LottieAnimationView) findViewById(R.id.background);
        LottieAnimationView mLogo = (LottieAnimationView) findViewById(R.id.logo);
        Spinner mCurrency = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter <CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Currency_items, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_items);
        mCurrency.setAdapter(adapter);

        mCurrency.setOnItemSelectedListener (new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String finalurl = BASE_URL + parent.getItemAtPosition(position);
                Api_call(finalurl);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        }

        private void Api_call (String url){
            AsyncHttpClient client = new AsyncHttpClient();
            client.get(url, new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    try {
                        String price = response.getString("last");
                        mAmount.setText(price);

                    } catch (JSONException e){

                    }
                }
            });
        }



    }