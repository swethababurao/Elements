package com.example.elements.helper;

import com.example.elements.R;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

public class ZoomImageActivity extends FragmentActivity {
	/**
	 * Hold a reference to the current animator, so that it can be canceled
	 * mid-way.
	 */
	private Animator mCurrentAnimator;
	private int mShortAnimationDuration;

	private int reqWidth = 400;
	private int reqHeight = 400;

	/**
	 * The system "short" animation time duration, in milliseconds. This
	 * duration is ideal for subtle animations or animations that occur very
	 * frequently.
	 */

	// @Override
	// protected void onCreate(Bundle savedInstanceState) {
	// super.onCreate(savedInstanceState);
	// setContentView(R.layout.open_image);
	//
	// // Hook up clicks on the thumbnail views.
	//
	// final View thumb1View = findViewById(R.id.thumb_button_1);
	// thumb1View.setOnClickListener(new View.OnClickListener() {
	// @Override
	// public void onClick(View view) {
	// zoomImageFromThumb(thumb1View, R.drawable.image1);
	// }
	// });
	//
	// final View thumb2View = findViewById(R.id.thumb_button_2);
	// thumb2View.setOnClickListener(new View.OnClickListener() {
	// @Override
	// public void onClick(View view) {
	// zoomImageFromThumb(thumb2View, R.drawable.image2);
	// }
	// });
	//
	// // Retrieve and cache the system's default "short" animation time.
	// mShortAnimationDuration = getResources().getInteger(
	// android.R.integer.config_shortAnimTime);
	// }
	//
	// @Override
	// public boolean onOptionsItemSelected(MenuItem item) {
	// switch (item.getItemId()) {
	// case android.R.id.home:
	// // Navigate "up" the demo structure to the launchpad activity.
	// // See http://developer.android.com/design/patterns/navigation.html
	// // for more.
	// NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
	// return true;
	// }
	//
	// return super.onOptionsItemSelected(item);
	// }

	/**
	 * "Zooms" in a thumbnail view by assigning the high resolution image to a
	 * hidden "zoomed-in" image view and animating its bounds to fit the entire
	 * activity content area. More specifically:
	 * 
	 * <ol>
	 * <li>Assign the high-res image to the hidden "zoomed-in" (expanded) image
	 * view.</li>
	 * <li>Calculate the starting and ending bounds for the expanded view.</li>
	 * <li>Animate each of four positioning/sizing properties (X, Y, SCALE_X,
	 * SCALE_Y) simultaneously, from the starting bounds to the ending bounds.</li>
	 * <li>Zoom back out by running the reverse animation on click.</li>
	 * </ol>
	 * 
	 * @param thumbView
	 *            The thumbnail view to zoom in.
	 * @param imageResId
	 *            The high-resolution version of the image represented by the
	 *            thumbnail.
	 */
	public void zoomImageFromThumb(final View thumbView, String imageResId) {
		// If there's an animation in progress, cancel it immediately and
		// proceed with this one.
		Log.i("ZoomImageActivity", "InsidezoomImageFromThumb");
		if (mCurrentAnimator != null) {
			mCurrentAnimator.cancel();
		}
		// setContentView(thumbView);

		// Load the high-resolution "zoomed-in" image.
		final ImageView zoomedImageView = (ImageView) thumbView
				.findViewById(R.id.image);
		Bitmap bm = decodeSampleBitmapFromUri(imageResId, 300, 300);
		zoomedImageView.setImageBitmap(bm);
		// zoomedImageView.setImageResource(imageResId);

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
		thumbView.setAlpha(0f);
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
						thumbView.setAlpha(1f);
						zoomedImageView.setVisibility(View.GONE);
						mCurrentAnimator = null;
					}

					@Override
					public void onAnimationCancel(Animator animation) {
						thumbView.setAlpha(1f);
						zoomedImageView.setVisibility(View.GONE);
						mCurrentAnimator = null;
					}
				});
				set.start();
				mCurrentAnimator = set;
			}
		});
	}

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
				inSampleSize = Math.round((float) height / (float) reqHeight);
			} else {
				inSampleSize = Math.round((float) width / (float) reqWidth);
			}
		}

		return inSampleSize;
	}
}
