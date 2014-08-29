package com.example.elements;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

public class GridViewActivity extends Activity {
    GridView gridview;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_view);
        
        
        gridview = (GridView) findViewById(R.id.gridview);
        
        
        String[] values = new String[] { "A",
        								"B",
        								"C",
        								"D"
                                        };

        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
          android.R.layout.simple_list_item_1, android.R.id.text1, values);


       
        gridview.setAdapter(adapter); 
        
        gridview.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				
				int itemPosition     = position;
	               
	               
	               String  itemValue    = (String) gridview.getItemAtPosition(position);
	                  
	                 
	                Toast.makeText(getApplicationContext(),
	                  "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
	                  .show();
				
				
			}
        	
        });
        
       
        
    }

}
