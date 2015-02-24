package com.example.finalproject.activities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.example.finalproject.R;
import com.example.finalproject.classes.Navigation;
import com.example.finalproject.classes.Route;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class NavigationActivity extends Activity {
	static String myBlogAddr = "";
	private List<Route> routes = new ArrayList<Route>();
	private Navigation nav = new Navigation();
	private LocationListener locationListener = null;  
	private LocationManager locationMangaer = null;

	static public class MyWebViewFragment extends Fragment {
		WebView myWebView;
		String myUrl;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View view = inflater.inflate(R.layout.layout_webfragment, container, false);
			myWebView = (WebView)view.findViewById(R.id.mywebview);
			myWebView.getSettings().setJavaScriptEnabled(true);                
			myWebView.setWebViewClient(new MyWebViewClient());
			if(myUrl == null){
				myUrl = myBlogAddr;
			}
			myWebView.loadUrl(myUrl);

			return view;
		}

		private class MyWebViewClient extends WebViewClient {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				myUrl = url;
				view.loadUrl(url);
				return true;
			}
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			setRetainInstance(true);
		}
	}

//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		Intent i = getIntent();
//		myBlogAddr = (String) i.getStringExtra("navigationUrl");	
//		routes = (List<Route>) i.getSerializableExtra("routes");	
//		nav = (Navigation) i.getSerializableExtra("navObj");
//		setContentView(R.layout.activity_navigation);		
//		locationListener = new MyLocationListener();  
//		locationMangaer = (LocationManager) getSystemService(Context.LOCATION_SERVICE);  
//		locationMangaer.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener); 
//	}

	public void backClick(View view){
		Intent i = new Intent(NavigationActivity.this, RoutsActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		i.putExtra("routes", (Serializable)routes);  
		i.putExtra("navObj", (Serializable)nav ); 
		startActivity(i);
		locationMangaer.removeUpdates(locationListener);
		locationMangaer = null;
		finish();
	}

	public class MyLocationListener implements LocationListener {  
		@Override  
		public void onLocationChanged(Location loc) {  
			//editLocation.setText(""); 
			Toast.makeText(getBaseContext(), "Location changed", Toast.LENGTH_LONG).show(); 
		}  

		@Override  
		public void onProviderDisabled(String provider) {  
			// TODO Auto-generated method stub           
		}  

		@Override  
		public void onProviderEnabled(String provider) {  
			// TODO Auto-generated method stub           
		}  

		@Override  
		public void onStatusChanged(String provider,   
				int status, Bundle extras) {  
			// TODO Auto-generated method stub           
		}  
	}
}