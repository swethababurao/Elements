package com.example.elements;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class OpenImageActivity extends Activity{
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.open_image);
 
        // get intent data
        Intent i = getIntent();
 
        // Selected image id
        int position = i.getExtras().getInt("id");
        LoadImagesActivity id1 = new LoadImagesActivity();
        LoadImagesActivity.ImageAdapter imageAdapter = id1.new ImageAdapter(this);
 
        ImageView imageView = (ImageView) findViewById(R.id.image);
        
        
        Log.i("Image position",""+imageAdapter.getItemId(position) );
        //Log.i("path", imageAdapter.getItem(position));
        //imageAdapter.getItem(position);
       //imageView.setImageDrawable(getResources().getDrawable((int)imageAdapter.getItemId(position)));
       imageView.setImageResource((int) imageAdapter.getItemId(position));
       //return imageView;
    }
	
}
