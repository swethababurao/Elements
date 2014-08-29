package com.example.elements;



import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends Activity {
    ListView listView ;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        listView = (ListView) findViewById(R.id.grandlist);
        
        //ProgressBar progressBar = new ProgressBar(this);
       //progressBar.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
         //      LayoutParams.WRAP_CONTENT, Gravity.CENTER));
        //progressBar.setIndeterminate(true);
		// getListView().setEmptyView(progressBar);

        // Must add the progress bar to the root of the layout
        ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
       // root.addView(progressBar);
        
        
        String[] values = new String[] { "GridView",
        								"RadioButton",
        								"Complex List View",
        								"VideoView",
        								"GoogleMapsDemo",
        								"Your Location on Google Maps"
                                        };

        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
          android.R.layout.simple_list_item_1, android.R.id.text1, values);


       
        listView.setAdapter(adapter); 
        
        listView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				
				int itemPosition     = position;
	               
	               
	               String  itemValue    = (String) listView.getItemAtPosition(position);
	               
	               if(itemValue == "GridView")
	               {
	            	   Intent intent = new Intent(MainActivity.this, LoadImagesActivityInflater.class);
	                   startActivity(intent);
	               }
	               
	               if(itemValue == "RadioButton")
	               {
	            	   Intent intent = new Intent(MainActivity.this, CurrencyConverterActivity.class);
	            	   startActivity(intent);
	               }
	               
	               if(itemValue == "Complex List View")
	               {
	            	   Intent intent = new Intent(MainActivity.this, ListViewPopulate.class);
	            	   startActivity(intent);
	               }
	                  
	               if(itemValue == "VideoView")
	               {
	            	   Intent intent = new Intent(MainActivity.this, VideoViewActivity.class);
	            	   startActivity(intent);
	               }
	               
	               if(itemValue == "GoogleMapsDemo")
	               {
	            	   Intent intent = new Intent(MainActivity.this, GoogleMaps.class);
	            	   startActivity(intent);
	               }
	               if(itemValue == "Your Location on Google Maps")
	               {
	            	   Intent intent = new Intent(MainActivity.this, GoogleMapMyLocation.class);
	            	   startActivity(intent);
	               }
	                 
	                 
	                Toast.makeText(getApplicationContext(),
	                  "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
	                  .show();
				
				
			}
        	
        });
        
       
        
    }

}
