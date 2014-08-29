package com.example.elements;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.MapFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class GoogleMaps extends FragmentActivity {

	private GoogleMap googleMap;
	static final LatLng mLatLng = new LatLng(110.574, 111.32);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.google_maps_demo);
		try {
			if (googleMap == null) {
				googleMap = ((MapFragment) getFragmentManager()
						.findFragmentById(R.id.map)).getMap();
				// googleMap = ((SupportMapFragment)
				// getSupportFragmentManager().findFragmentById(R.id.map))
				// .getMap();
			}
			googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			googleMap.addMarker(new MarkerOptions().position(mLatLng).title(
					"Pin pointing a Location"));

			googleMap
					.moveCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, 15));

			googleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
