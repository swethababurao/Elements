package com.example.elements;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;

public class LoadImagesActivity extends Activity {
	
	ImageAdapter myImageAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.grid_view);

		GridView gridview = (GridView) findViewById(R.id.gridview);
		myImageAdapter = new ImageAdapter(this);

		gridview.setAdapter(myImageAdapter);

		String ExternalStorageDirectoryPath = Environment
				.getExternalStorageDirectory().getAbsolutePath();

		Log.i("path", ExternalStorageDirectoryPath);

		final String targetPath = ExternalStorageDirectoryPath + "/TempImages";

		Log.i("path", targetPath);

		// Toast.makeText(getApplicationContext(), targetPath,
		// Toast.LENGTH_LONG).show();
		File targetDirector = new File(targetPath);

		File[] files = targetDirector.listFiles();

		Log.i("File Size", "" + files.length);
		try {
			for (File file : files) {
				myImageAdapter.add(file.getAbsolutePath());
				Log.i("absolutepath", file.getAbsolutePath());
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		gridview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                    int position, long id) {
            	//String imagePath = myImageAdapter.getItem(position);
            	//Drawable d = getResources().getDrawable((int) myImageAdapter.getItemId(position));
            	
            	setContentView(R.layout.open_image);
            	ImageView imageView = (ImageView) findViewById(R.id.image);
            	
            	Bitmap bm = BitmapFactory.decodeFile(myImageAdapter.getItem(position));
            	
            	Log.i("position",myImageAdapter.getItem(position));
            	imageView.setImageBitmap(bm);
            	//imageView.setImageDrawable(d)
            	//imageView.setImageResource((int)myImageAdapter.getItemId(position));
 
            }
        });
	}
	public class ImageAdapter extends BaseAdapter {

		private Context mContext;
		private ImageView imageView;
		private int reqWidth = 400;
		private int reqHeight = 400;

		private ArrayList<String> itemList = new ArrayList<String>();

		public ImageAdapter(Context c) {
			mContext = c;
		}

		public void add(String path) {
			itemList.add(path);
		}

		@Override
		public int getCount() {
			return itemList.size();
		}

		@Override
		public String getItem(int arg0) {
			return itemList.get(arg0);
		}

		@Override
		public long getItemId(int position) {
			// return position;
			return position;
		}

		// create a new ImageView for each item referenced by the Adapter
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) 
			{
				// if it's not recycled, initialize some
										// attributes
				imageView = new ImageView(mContext);
				imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
				imageView.setPadding(10, 10, 10, 10);
			} else {
				imageView = (ImageView) convertView;
			}

			Bitmap bm = decodeSampleBitmapFromUri(itemList.get(position), 350,
				350);
			//Bitmap bm = BitmapFactory.decodeFile(itemList.get(position));
			Log.i("bitmap", itemList.get(position));

			imageView.setImageBitmap(bm);

			return imageView;
		}
		//BitmapFactory.decodeFile(f.getAbsolutePath());

		private Bitmap decodeSampleBitmapFromUri(String path, int i, int j) {
			// TODO Auto-generated method stub

			Bitmap bm = null;
			// First decode with inJustDecodeBounds=true to check dimensions
			final BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;

			BitmapFactory.decodeFile(path, options);

			// Calculate inSampleSize
			options.inSampleSize = calculateInSampleSize(options, reqWidth,
					reqHeight);

			// Decode bitmap with inSampleSize set
			options.inJustDecodeBounds = false;
			bm = BitmapFactory.decodeFile(path, options);

			return bm;

		}

		private int calculateInSampleSize(BitmapFactory.Options option,
				int reqWidth, int reqHeight) {
			// TODO Auto-generated method stub

			// Raw height and width of image
			final int height = option.outHeight;
			final int width = option.outWidth;
			int inSampleSize = 1;

			if (height > reqHeight || width > reqWidth) {
				if (width > height) {
					inSampleSize = Math.round((float) height
							/ (float) reqHeight);
				} else {
					inSampleSize = Math.round((float) width / (float) reqWidth);
				}
			}

			return inSampleSize;
		}

	}

	
}
