package de.akvilonsoft.luxuryapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MainActivity extends AppCompatActivity {
JSONArray jArray = new JSONArray();
    ArrayAdapter<String> adapter;
    EditText editText = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Button button = (Button) findViewById(R.id.button4);
        editText = (EditText) findViewById(R.id.editText);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Coupon> listCoupons = getObjects();
                List<String> list = new ArrayList<>();
                String test = "";
                for (Coupon coupon: listCoupons) {
                    test +=coupon.getBeschreibung();
                }
if (listCoupons.size()==0) test = "Keine Coupons gefunden";
           //     Toast.makeText(getBaseContext(), test , Toast.LENGTH_LONG).show();
                Intent myIntent = new Intent(MainActivity.this, CouponListActivity.class);
                 myIntent.putExtra("city", editText.getText().toString()); //Optional parameters
                MainActivity.this.startActivity(myIntent);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    protected List<Coupon> getObjects() {
        List<Coupon> coupons = new ArrayList<>();
        RetrieveFeedTask rft = new RetrieveFeedTask();

        try {
            if (editText.getText().toString().equals("") || editText.getText().toString()=="" ){
                jArray = rft.execute(new URL("http://10.0.2.2:8080/LuxuryApp/rest/demo/allCoupons")).get(5, TimeUnit.SECONDS);
            } else {
                jArray = rft.execute(new URL("http://10.0.2.2:8080/LuxuryApp/rest/demo/localeCoupons?city=" + editText.getText().toString())).get(5, TimeUnit.SECONDS);
            }

        //jArray = rft.getjArray();
       // new RetrieveFeedTask(new RetrieveFeedTask.AsyncResponse() {
       //     @Override
       //     public void processFinish(JSONArray output) {
       //         jArray = output;
       //     }
       // }).execute();
        int i;
        for (i = 0; i < jArray.length(); i++) {

            JSONObject jObject = null;

                jObject = jArray.getJSONObject(i);


            String name = jObject.getString("name");
            String desc = jObject.getString("bezeichnung");
            String desc_long = jObject.getString("bezeichnung_lang");
            // String add = jObject.getString("additional");
            // String loc = jObject.getString("location");
            coupons.add(new Coupon(i + 1, name, desc, desc_long));

        }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return coupons;
    }



}