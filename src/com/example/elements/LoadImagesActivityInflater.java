package com.example.elements;

import java.io.File;
import java.util.ArrayList;

import com.example.elements.helper.ZoomImageActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.animation.DecelerateInterpolator;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;

public class LoadImagesActivityInflater extends Activity
// implements OnLongClickListener
{
	String mPosition;
	private Animator mCurrentAnimator;
	private int mShortAnimationDuration;
	private int reqWidth = 400;
	private int reqHeight = 400;

	public class ImageAdapter extends BaseAdapter {

		private Context mContext;
		private ImageView imageView;
		// private int reqWidth = 400;
		// private int reqHeight = 400;
		private LayoutInflater inflater;

		private ArrayList<String> itemList = new ArrayList<String>();

		public ImageAdapter(Context c) {
			mContext = c;
			inflater = LayoutInflater.from(c);
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
			View v = convertView;
			ImageView imageView;
			if (convertView == null) {
				// if it's not recycled, initialize some
				// attributes
				v = inflater.inflate(R.layout.open_image, parent, false);
				v.setTag(R.id.image, v.findViewById(R.id.image));

			}
			imageView = (ImageView) v.getTag(R.id.image);

			Bitmap bm = decodeSampleBitmapFromUri(itemList.get(position), 350,
					350);

			imageView.setImageBitmap(bm);

			return v;
		}

		// public Bitmap decodeSampleBitmapFromUri(String path, int i, int j) {
		// // TODO Auto-generated method stub
		//
		// Bitmap bm = null;
		// // First decode with inJustDecodeBounds=true to check dimensions
		// final BitmapFactory.Options options = new BitmapFactory.Options();
		// options.inJustDecodeBounds = true;
		//
		// BitmapFactory.decodeFile(path, options);
		//
		// // Calculate inSampleSize
		// options.inSampleSize = calculateInSampleSize(options, reqWidth,
		// reqHeight);
		//
		// // Decode bitmap with inSampleSize set
		// options.inJustDecodeBounds = false;
		// bm = BitmapFactory.decodeFile(path, options);
		//
		// return bm;
		//
		// }
		//
		// public int calculateInSampleSize(BitmapFactory.Options option,
		// int reqWidth, int reqHeight) {
		// // TODO Auto-generated method stub
		//
		// // Raw height and width of image
		// final int height = option.outHeight;
		// final int width = option.outWidth;
		// int inSampleSize = 1;
		//
		// if (height > reqHeight || width > reqWidth) {
		// if (width > height) {
		// inSampleSize = Math.round((float) height
		// / (float) reqHeight);
		// } else {
		// inSampleSize = Math.round((float) width / (float) reqWidth);
		// }
		// }
		//
		// return inSampleSize;
		// }

	}

	ImageAdapter myImageAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.grid_view);

		GridView mGridView = (GridView) findViewById(R.id.gridview);
		myImageAdapter = new ImageAdapter(this);

		mGridView.setAdapter(myImageAdapter);

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

		mGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				// String imagePath = myImageAdapter.getItem(position);
				// Drawable d = getResources().getDrawable((int)
				// myImageAdapter.getItemId(position));

				setContentView(R.layout.open_image);
				ImageView imageView = (ImageView) findViewById(R.id.image);
				// mPosition = position;
				mPosition = myImageAdapter.getItem(position);
				Log.i("Position", mPosition);
				Bitmap bm = decodeSampleBitmapFromUri(
						myImageAdapter.getItem(position), 50, 50);
				// Bitmap bm = BitmapFactory.decodeFile(myImageAdapter
				// .getItem(position));

				Log.i("position", myImageAdapter.getItem(position));
				imageView.setImageBitmap(bm);

				// ZoomImageActivity mZoomImage = new ZoomImageActivity();
				zoomImageFromThumbMethod(v, mPosition);

				// imageView.setImageDrawable(d)
				// imageView.setImageResource((int)myImageAdapter.getItemId(position));

			}
		});

		// mGridView.setOnLongClickListener(new View.OnLongClickListener() {
		//
		// @Override
		// public boolean onLongClick(View v) {
		// // TODO Auto-generated method stub
		// Log.i("LoadImages", "setOnClickListener");
		// setContentView(R.layout.open_image);
		// v = findViewById(R.id.zoomimage);
		// ZoomImageActivity mZoomImage = new ZoomImageActivity();
		// mZoomImage.zoomImageFromThumb(v, mPosition);
		// return false;
		// }
		// });
	}

	public void zoomImageFromThumbMethod(View thumbView, String imageResId) {
		// If there's an animation in progress, cancel it immediately and
		// proceed with this one.
		Log.i("ZoomImageActivity", "InsidezoomImageFromThumb");
		if (mCurrentAnimator != null) {
			mCurrentAnimator.cancel();
		}

		// Load the high-resolution "zoomed-in" image.
		final ImageView normalImageView = (ImageView) thumbView
				.findViewById(R.id.image);

		final ImageView zoomedImageView = (ImageView) thumbView
				.findViewById(R.id.zoomimage);
		// Bitmap bm1 = BitmapFactory.decodeFile(imageResId);
		Bitmap bm1 = decodeSampleBitmapFromUri(imageResId, 0, 0);

		zoomedImageView.setImageBitmap(bm1);
		// zoomedImageView.setImageResource(Integer.parseInt(imageResId));

		// Calculate the starting and ending bounds for the zoomed-in image.
		// This step
		// involves lots of math. Yay, math.
		final Rect startBounds = new Rect();
		final Rect finalBounds = new Rect();
		final Point globalOffset = new Point();

		// The start bounds are the global visible rectangle of the thumbnail,
		// and the
		// final bounds are the global visible rectangle of the container view.
		// Also
		// set the container view's offset as the origin for the bounds, since
		// that's
		// the origin for the positioning animation properties (X, Y).
		thumbView.getGlobalVisibleRect(startBounds);
		findViewById(R.id.container).getGlobalVisibleRect(finalBounds,
				globalOffset);
		startBounds.offset(-globalOffset.x, -globalOffset.y);
		finalBounds.offset(-globalOffset.x, -globalOffset.y);

		// Adjust the start bounds to be the same aspect ratio as the final
		// bounds using the
		// "center crop" technique. This prevents undesirable stretching during
		// the animation.
		// Also calculate the start scaling factor (the end scaling factor is
		// always 1.0).
		float startScale;
		if ((float) finalBounds.width() / finalBounds.height() > (float) startBounds
				.width() / startBounds.height()) {
			// Extend start bounds horizontally
			startScale = (float) startBounds.height() / finalBounds.height();
			float startWidth = startScale * finalBounds.width();
			float deltaWidth = (startWidth - startBounds.width()) / 2;
			startBounds.left -= deltaWidth;
			startBounds.right += deltaWidth;
		} else {
			// Extend start bounds vertically
			startScale = (float) startBounds.width() / finalBounds.width();
			float startHeight = startScale * finalBounds.height();
			float deltaHeight = (startHeight - startBounds.height()) / 2;
			startBounds.top -= deltaHeight;
			startBounds.bottom += deltaHeight;
		}

		// Hide the thumbnail and show the zoomed-in view. When the animation
		// begins,
		// it will position the zoomed-in view in the place of the thumbnail.
		normalImageView.setVisibility(View.INVISIBLE);
		zoomedImageView.setVisibility(View.VISIBLE);

		// Set the pivot point for SCALE_X and SCALE_Y transformations to the
		// top-left corner of
		// the zoomed-in view (the default is the center of the view).
		zoomedImageView.setPivotX(0f);
		zoomedImageView.setPivotY(0f);

		// Construct and run the parallel animation of the four translation and
		// scale properties
		// (X, Y, SCALE_X, and SCALE_Y).
		AnimatorSet set = new AnimatorSet();
		set.play(
				ObjectAnimator.ofFloat(zoomedImageView, View.X,
						startBounds.left, finalBounds.left))
				.with(ObjectAnimator.ofFloat(zoomedImageView, View.Y,
						startBounds.top, finalBounds.top))
				.with(ObjectAnimator.ofFloat(zoomedImageView, View.SCALE_X,
						startScale, 1f))
				.with(ObjectAnimator.ofFloat(zoomedImageView, View.SCALE_Y,
						startScale, 1f));
		set.setDuration(mShortAnimationDuration);
		set.setInterpolator(new DecelerateInterpolator());
		set.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				mCurrentAnimator = null;
			}

			@Override
			public void onAnimationCancel(Animator animation) {
				mCurrentAnimator = null;
			}
		});
		set.start();
		mCurrentAnimator = set;

		// Upon clicking the zoomed-in image, it should zoom back down to the
		// original bounds
		// and show the thumbnail instead of the expanded image.
		final float startScaleFinal = startScale;
		zoomedImageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (mCurrentAnimator != null) {
					mCurrentAnimator.cancel();
				}

				// Animate the four positioning/sizing properties in parallel,
				// back to their
				// original values.
				AnimatorSet set = new AnimatorSet();
				set.play(
						ObjectAnimator.ofFloat(zoomedImageView, View.X,
								startBounds.left))
						.with(ObjectAnimator.ofFloat(zoomedImageView, View.Y,
								startBounds.top))
						.with(ObjectAnimator.ofFloat(zoomedImageView,
								View.SCALE_X, startScaleFinal))
						.with(ObjectAnimator.ofFloat(zoomedImageView,
								View.SCALE_Y, startScaleFinal));
				set.setDuration(mShortAnimationDuration);
				set.setInterpolator(new DecelerateInterpolator());
				set.addListener(new AnimatorListenerAdapter() {
					@Override
					public void onAnimationEnd(Animator animation) {
						normalImageView.setVisibility(View.INVISIBLE);
						zoomedImageView.setVisibility(View.GONE);
						mCurrentAnimator = null;
					}

					@Override
					public void onAnimationCancel(Animator animation) {
						normalImageView.setVisibility(View.INVISIBLE);
						zoomedImageView.setVisibility(View.GONE);
						mCurrentAnimator = null;
					}
				});
				set.start();
				mCurrentAnimator = set;
			}
		});
	}

	public Bitmap decodeSampleBitmapFromUri(String path, int i, int j) {
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

	public int calculateInSampleSize(BitmapFactory.Options option,
			int reqWidth, int reqHeight) {
		// TODO Auto-generated method stub

		// Raw height and width of image
		final int height = option.outHeight;
		final int width = option.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			if (width > height) {
				inSampleSize = Math.round((float) height / (float) reqHeight);
			} else {
				inSampleSize = Math.round((float) width / (float) reqWidth);
			}
		}

		return inSampleSize;
	}

	// @Override
	// public boolean onLongClick(View v) {
	// // TODO Auto-generated method stub
	// if (v.getId() == R.id.image) {
	// // call ZoomImageActivity
	// ZoomImageActivity mZoomImage = new ZoomImageActivity();
	// mZoomImage.zoomImageFromThumb(v, R.id.image);
	// }
	// return false;
	// }
}
