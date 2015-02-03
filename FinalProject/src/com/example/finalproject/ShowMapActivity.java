package com.example.finalproject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.example.finalproject.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager.OnActivityResultListener;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import android.support.v4.app.FragmentActivity;


public class ShowMapActivity extends FragmentActivity {
	private static final int  GOOGLE_NAV = 5;
	private List<LatLng> latLngs = new ArrayList<LatLng>();
	private LocationManager locationMangaer = null;  
	private LocationListener locationListener = null;  
	private GoogleMap googleMap;
	private Activity map;
	private String source;
	private String destination;
	private List<Route> routes = new ArrayList<Route>();
	private Navigation nav = new Navigation();
	private int endLatitude;
	private int endLongitude;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_map);
		locationMangaer = (LocationManager) getSystemService(Context.LOCATION_SERVICE);  
		Intent intent = getIntent();
		source = intent.getStringExtra("source");
		destination = intent.getStringExtra("destination");	
		endLatitude = (int) intent.getIntExtra("endLatitude", 0);
		endLongitude = (int) intent.getIntExtra("endLongitude", 0);
		routes = (List<Route>) intent.getSerializableExtra("routes");	
		nav = (Navigation) intent.getSerializableExtra("navObj");
		try {
			// Loading map
			initilizeMap();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * function to load Google navigation activity.
	 * */
	@SuppressLint("NewApi") 
	private void initilizeMap() {		
		//		Uri navigationUrl = Uri.parse("http://maps.google.com/maps?saddr=" + source + "&daddr=" + destination);		
		//		Intent i = new Intent(ShowMapActivity.this, NavigationActivity.class);
		//		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		//		i.putExtra("navigationUrl", navigationUrl.toString());
		//		i.putExtra("routes", (Serializable)routes);  
		//		i.putExtra("navObj", (Serializable)nav ); 
		//		startActivity(i);
		//startActivityForResult(intent, VIEW);

		//******************************************************
		//Uri navigationUrl = Uri.parse("google.navigation:q=New+York+NY");
		Intent i = new Intent(this, LocationService.class);
		i.putExtra("endLatitude", endLatitude);
		i.putExtra("endLongitude", endLongitude);
		i.putExtra("routes", (Serializable)routes);  
		i.putExtra("navObj", (Serializable)nav ); 
		startService(i);
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + destination));
		startActivity(intent);       
	}


	@Override
	protected void onResume() {
		super.onResume();
		initilizeMap();
	}	
}