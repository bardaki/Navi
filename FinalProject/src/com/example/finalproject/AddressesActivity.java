package com.example.finalproject;

import java.io.Serializable;
import java.util.ArrayList;
import com.example.finalproject.R;
import android.os.Bundle;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class AddressesActivity extends ActionBarActivity implements OnItemClickListener  {
	private ArrayList<Address> placesArray = new ArrayList<Address>();
	private static AddressAdapter adapter;
	private Navigation nav = new Navigation();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addresses);		
		Intent i = getIntent();
	    nav = (Navigation) i.getSerializableExtra("navObj");	
	    
	    //Start Address
	    final AutoCompleteTextView autoCompViewStart = (AutoCompleteTextView) findViewById(R.id.autocompleteStart);
	    autoCompViewStart.setAdapter(new PlacesAutoCompleteAdapter(this, R.layout.list_item));
	    autoCompViewStart.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long id) {
				String str = (String) adapterView.getItemAtPosition(position);
				nav.addStartAdd(str);				
			}
		});
	    autoCompViewStart.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				autoCompViewStart.setHint("");
				return false;
			}
		});
	    //Addresses
		final AutoCompleteTextView autoCompViewAddresses = (AutoCompleteTextView) findViewById(R.id.autocompleteAddresses);
		autoCompViewAddresses.setAdapter(new PlacesAutoCompleteAdapter(this, R.layout.list_item));
		autoCompViewAddresses.setOnItemClickListener(this);
		autoCompViewAddresses.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				autoCompViewAddresses.setHint("");
				return false;
			}
		});
		//End Address
		final AutoCompleteTextView autoCompViewEnd = (AutoCompleteTextView) findViewById(R.id.autocompleteEnd);
		autoCompViewEnd.setAdapter(new PlacesAutoCompleteAdapter(this, R.layout.list_item));
		autoCompViewEnd.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long id) {
				String str = (String) adapterView.getItemAtPosition(position);
				nav.addEndAdd(str);				
			}
		});
		autoCompViewEnd.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				autoCompViewEnd.setHint("");
				return false;
			}
		});
		
		//List of Addresses
		for(int j = 0; j < nav.getAddresses().size(); j++){
			placesArray.add(new Address(nav.getAddresses().get(j) , R.drawable.edit));
		}
		
		adapter = new AddressAdapter(this, placesArray);	
		ListView places = (ListView) findViewById(R.id.addressesListView);
		places.setAdapter(adapter);		
		ActionBar bar = getSupportActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00ABFF")));	
		ListView lv = (ListView)findViewById(R.id.addressesListView);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
				final int pos = position;
				Address address = (Address) adapterView.getItemAtPosition(position);
				final Dialog dialogEditAddress = new Dialog(AddressesActivity.this);			
				dialogEditAddress.setContentView(R.layout.activity_address_edit);
				dialogEditAddress.setTitle(address.getName());
				TextView titleTextView = (TextView) dialogEditAddress.findViewById(android.R.id.title);
				titleTextView.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
				Button removeBtn = (Button) dialogEditAddress.findViewById(R.id.buttonRemove);
				removeBtn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						placesArray.remove(pos);
						nav.removeFromAddresses(pos);
						adapter = new AddressAdapter(AddressesActivity.this, placesArray);	
						ListView places = (ListView) findViewById(R.id.addressesListView);
						places.setAdapter(adapter);
						dialogEditAddress.dismiss();
					}
				}); 
				dialogEditAddress.show();				
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
    	MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_actions, menu);
 
        return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		 // Take appropriate action for each action item click
        switch (item.getItemId()) {
        case R.id.action_startnav:
        	startNavClicked();
            return true;
        case R.id.action_startnav2:
        	startNavClicked();
            return true;
//        case R.id.action_person:
//            // refresh
//            return true;
//        case R.id.action_help:
//            // help action
//            return true;
//        case R.id.action_settings:
//            // check for updates action
//            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
	}

	public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
		String str = (String) adapterView.getItemAtPosition(position);
		placesArray.add(new Address(str, R.drawable.edit));
		adapter = new AddressAdapter(this, placesArray);	
		ListView places = (ListView) findViewById(R.id.addressesListView);
		places.setAdapter(adapter);
		nav.addAddress(str);
		AutoCompleteTextView autoCompViewAddreses = (AutoCompleteTextView) findViewById(R.id.autocompleteAddresses);
		autoCompViewAddreses.setText("");
	}
	
	public void startNavClicked(){
		DirectionsFetcher df = new DirectionsFetcher(nav, this);
		df.execute();
	}
	
//	public void nextClicked(){
//		Intent i = new Intent(AddressesActivity.this, EndAddressActivity.class);
//		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//		i.putExtra("navObj", (Serializable)nav );  
//		startActivity(i);
//	}
	
//	public void backClicked(View v){
//		Intent i = new Intent(AddressesActivity.this, StartAddressActivity.class);
//		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//		i.putExtra("navObj", (Serializable)nav );  
//		startActivity(i);
//	}
}