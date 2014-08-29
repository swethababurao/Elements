package com.example.elements;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;


public class ListViewPopulate extends Activity {

	ArrayList<Dummy> gList;
	Context c = ListViewPopulate.this;

	@Override
	    public void onCreate(Bundle savedInstanceState) {
	       super.onCreate(savedInstanceState);
	        setContentView(R.layout.list_view);
	        
	        gList = new ArrayList<Dummy>();
	        new ReadWeatherJSONFeedTask().execute("http://www.mooshak.in/sn/index.php/api/statuses/friends_timeline.json?screen_name=riteshdubey&count=25");  
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
            	//Log.i("result", result);
            	//Toast.makeText(getBaseContext(), "Received!", Toast.LENGTH_LONG).show();
            	//Rate.setText(result);
            	JSONArray jsonarray = new JSONArray(result);
            	for( int i =0; i<jsonarray.length(); ++i)
            	{
            		
            		JSONObject jsonObject = jsonarray.getJSONObject(i);
            		//text.setText(jsonObject.getString("text"));
            		//time.setText(jsonObject.getString("created_at"));
            		String text = jsonObject.getString("text");
            		Log.i("text", text);
            		String time = jsonObject.getString("created_at");
            		Log.i("time", time);
            		JSONObject personItems = jsonObject.getJSONObject("user");
            		String name = personItems.getString("name");
            		Log.i("name", name);
            		String screen_name = personItems.getString("screen_name");
            		Log.i("screen_name", screen_name);
            		String image_url = personItems.getString("profile_image_url");
            		Log.i("image_url", image_url);
            		Dummy obj = new Dummy(text, time, name, screen_name, image_url);
            		gList.add(obj);
            		
            	}
                
            } catch (Exception e) {
                Log.d("ReadWeatherJSONFeedTask", e.getLocalizedMessage());
            }     
            
            ListView list = (ListView)findViewById(R.id.list);
            ListViewAdapterActivityHolder adapter = new ListViewAdapterActivityHolder(c, gList);
            list.setAdapter(adapter);
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
