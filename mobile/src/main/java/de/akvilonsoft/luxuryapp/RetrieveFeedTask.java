package de.akvilonsoft.luxuryapp;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import de.akvilonsoft.luxuryapp.Coupon;

class RetrieveFeedTask extends AsyncTask<URL, Void, JSONArray> {

    public interface AsyncResponse {
        void processFinish(JSONArray output);
    }
    public AsyncResponse delegate = null;
    private Exception exception;

    public JSONArray getjArray() {
        return jArray;
    }

    public JSONArray jArray;
  //  public RetrieveFeedTask(AsyncResponse delegate){
   //     this.delegate = delegate;
   // }

    protected void onPreExecute() {

    }

    protected JSONArray doInBackground(URL... urls) {
        try {
            //URL url = new URL("http://10.0.2.2:8080/LuxuryApp/rest/demo/allCoupons");
           // URL url = new URL("http://10.0.2.2:8080/LuxuryApp/rest/demo/localeCoupons?city=Metzingen");
            HttpURLConnection urlConnection = (HttpURLConnection) urls[0].openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                jArray = new JSONArray(bufferedReader.readLine());


                //              StringBuilder stringBuilder = new StringBuilder();
                //              String line;
                //              int id = 1;
                //              while ((line = bufferedReader.readLine()) != null) {
                //                  stringBuilder.append(line).append("\n");
                //                  String name=stringBuilder.toString();
                //                  coupons.add(new Coupon(id++, name, name));
                //              }
                bufferedReader.close();


                return jArray;
            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return jArray;
        }
    }

    protected void onPostExecute(JSONArray response) {
      //  delegate.processFinish(response);
       // if(response == null) {
       //     response = new JSONArray();
       // }
        //       progressBar.setVisibility(View.GONE);
       // Log.i("INFO", response);
        //       responseView.setText(response);
    }
}
