package com.example.elements;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

public class readJSONFeedActivity extends Activity {
	
	EditText Rate;

	@Override
	    public void onCreate(Bundle savedInstanceState) {
	       super.onCreate(savedInstanceState);
	        setContentView(R.layout.currency_converter);
	        
	        Rate = (EditText) this.findViewById(R.id.Rate);
	        
	        new ReadWeatherJSONFeedTask().execute("https://openexchangerates.org/api/latest.json?app_id=7ac645b7f5ce463f973847b8c0666004");  
	    }
	private class ReadWeatherJSONFeedTask extends AsyncTask
    <String, Void, String> {
		
        @Override
		protected String doInBackground(String... urls) {
            return readJSONFeed(urls[0]);
        }
 
        @Override
		protected void onPostExecute(String result) {
        	
            try {
            	//Toast.makeText(getBaseContext(), "Received!", Toast.LENGTH_LONG).show();
            	//Rate.setText(result);
            	
                JSONObject jsonObject = new JSONObject(result);
                JSONObject ratesItems = 
                    new JSONObject(jsonObject.getString("rates"));
                Rate.setText(ratesItems.getString("SEK"));
 
                /*Toast.makeText(getBaseContext(), 
                    weatherObservationItems.getString("clouds") + 
                 " - " + weatherObservationItems.getString("stationName"), 
                 Toast.LENGTH_SHORT).show(); */
            } catch (Exception e) {
                Log.d("ReadWeatherJSONFeedTask", e.getLocalizedMessage());
            }          
        }

	
	public String readJSONFeed(String URL) {
        StringBuilder stringBuilder = new StringBuilder();
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(URL);
        try {
            HttpResponse response = httpClient.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream inputStream = entity.getContent();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                inputStream.close();
            } else {
                Log.d("JSON", "Failed to download file");
            }
        } catch (Exception e) {
            Log.d("readJSONFeed", e.getLocalizedMessage());
        }    
        //Rate.setText(stringBuilder.toString());
        return stringBuilder.toString();
    }
	
	    }
 
   

}
