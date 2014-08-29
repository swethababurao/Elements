package com.example.elements;

import android.app.Activity;
import android.content.Intent;

public class LaunchAnotherAppActivity extends Activity {
	public LaunchAnotherAppActivity() {
		// TODO Auto-generated constructor stub
		Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.example.sayapy");
		startActivity(LaunchIntent);
	}
}
	
	
