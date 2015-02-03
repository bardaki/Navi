package com.example.finalproject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.example.finalproject.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.client.util.Key;
import com.google.maps.android.PolyUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.DownloadManager.Request;
import android.content.Context;
import android.content.Intent;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

public class AddressesActivity extends Activity implements OnItemClickListener  {
	private ArrayList<String> placesArray = new ArrayList<String>();
	private static ArrayAdapter<String> adapter;
	private Navigation nav = new Navigation();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addresses);		
		Intent i = getIntent();
	    nav = (Navigation) i.getSerializableExtra("navObj");		
		AutoCompleteTextView autoCompView = (AutoCompleteTextView) findViewById(R.id.autocomplete2);
		autoCompView.setAdapter(new PlacesAutoCompleteAdapter(this, R.layout.list_item));
		autoCompView.setOnItemClickListener(this);
		for(int j = 0; j < nav.getAddresses().size(); j++){
			placesArray.add(nav.getAddresses().get(j));
		}
		adapter = new ArrayAdapter<String>(this, R.layout.address_item, placesArray);	
		ListView places = (ListView) findViewById(R.id.addressesListView);
		places.setAdapter(adapter);
	}

	public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
		String str = (String) adapterView.getItemAtPosition(position);
		placesArray.add(str);
		adapter = new ArrayAdapter<String>(this, R.layout.address_item, placesArray);	
		ListView places = (ListView) findViewById(R.id.addressesListView);
		places.setAdapter(adapter);
		nav.addAddress(str);
		AutoCompleteTextView autoCompView = (AutoCompleteTextView) findViewById(R.id.autocomplete2);
		autoCompView.setText("");
	}
	
	public void nextClicked(View v){
		Intent i = new Intent(AddressesActivity.this, EndAddressActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		i.putExtra("navObj", (Serializable)nav );  
		startActivity(i);
	}
	
	public void backClicked(View v){
		Intent i = new Intent(AddressesActivity.this, StartAddressActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		i.putExtra("navObj", (Serializable)nav );  
		startActivity(i);
	}
}