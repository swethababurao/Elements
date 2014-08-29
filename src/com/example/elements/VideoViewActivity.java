package com.example.elements;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoViewActivity extends Activity {
	
	
	
	private VideoView videoView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video_view);
		videoView = (VideoView) findViewById(R.id.video_view);
		//videoView.setData(Uri.parse("http://wowmobile.in/app/portfolio/Videos/iBay_iPhone.mov"));
		videoView.setVideoPath("rtsp://v4.cache7.c.youtube.com/CjYLENy73whIaLQky7ThXrRjPYRMYDSANFEIJbXYtZ29vZ2xlSARSBXdhdGNoYKjR78WV1ZH5Tgw=/0/0/0/video.3gp");
		//videoView.setVideoURI(Uri.parse("http://wowmobile.in/app/portfolio/Videos/iBay_iPhone.mov"));
		//videoView.setVideoPath("http://wowmobile.in/app/portfolio/Videos/iBay_iPhone.mov");
		MediaController mediaController = new MediaController(this);
		mediaController.setAnchorView(videoView);
		videoView.setMediaController(mediaController);
		
		videoView.setOnPreparedListener(new 
				MediaPlayer.OnPreparedListener()  {
            @Override
            public void onPrepared(MediaPlayer mp) {                         
            	Log.i("Video Duration", "Duration = " + videoView.getDuration());
            }
        });		
		
		videoView.start(); 
	} 
}
