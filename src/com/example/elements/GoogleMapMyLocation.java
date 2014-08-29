package com.example.elements;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;



public class GoogleMapMyLocation extends FragmentActivity {
	
	private GoogleMap mMap;
	private LocationManager locationManager;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.google_maps_demo);
		
			//mLatLng = showCurrentPosition(mMap);
		
		
		try {
			if (mMap == null) {
				mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
	              // googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
	               //        .getMap();
	            }
			mMap.setMyLocationEnabled(true);
			mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			mMap.setOnMyLocationChangeListener(new OnMyLocationChangeListener() {
				
				@Override
				public void onMyLocationChange(Location iLocation) {
					// TODO Auto-generated method stub
					LatLng aLatLng = new LatLng(iLocation.getLatitude(), iLocation.getLongitude());
					mMap.addMarker(new MarkerOptions().
					         position(aLatLng).title("Pin pointing to your Location"));
					
					
				   mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(aLatLng, 15));

				    
				    mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
					
				}
			});
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/*private LatLng showCurrentPosition(GoogleMap map){
	    LatLng posLatLon    = getCurrentPosition();
	    return posLatLon;
	}
	
	private LatLng getCurrentPosition(){
	    // location details
	    locationManager     = (LocationManager) getSystemService(LOCATION_SERVICE);
	    Criteria criteria   = new Criteria();// a class indication the app criterion for selecting a location provider 
	    //LocationProvider is an abstract superclass for location providers. it provides periodic reports on geographical location of device
	    String bestProvider = locationManager.getBestProvider(criteria, false);
	    Log.i("Best Provider", bestProvider);
	    Location location   = locationManager.getLastKnownLocation(bestProvider);
	    LatLng posLatLon    = new LatLng(location.getLatitude(), location.getLongitude());
	    Toast.makeText(this, "position: "+ location.getLatitude() + "," + location.getLongitude(), Toast.LENGTH_SHORT).show();
	    return posLatLon;
	}*/

}
