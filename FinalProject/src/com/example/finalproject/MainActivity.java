package com.example.finalproject;

import java.io.Serializable;
import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity implements OnItemClickListener{
	private ArrayList<Address> placesArray = new ArrayList<Address>();
	//private static ArrayAdapter<String> adapter;
	private static AddressAdapter adapter;
	private Navigation nav = new Navigation();
	private int dialogIndex = 0;

	Dialog dialogAddresses;
	Dialog dialogEndAddress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void nextClicked(View button) {
		PopupMenu popup = new PopupMenu(this, button);
		popup.getMenuInflater().inflate(R.menu.popup, popup.getMenu()); 
		popup.show();
	}

	public void startClicked(MenuItem item){
		//				Intent i = new Intent(MainActivity.this, StartAddressActivity.class);
		//				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		//				Navigation nav = new Navigation();
		//				i.putExtra("navObj", (Serializable)nav );  
		//				startActivity(i);

		dialogAddresses = new Dialog(this);	
		dialogAddresses.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialogAddresses.setContentView(R.layout.popupwin);
		//dialogAddresses.setTitle("הכנס כתובות לביקור");
		//dialogAddresses.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
//		TextView titleTextView = (TextView) dialogAddresses.findViewById(android.R.id.title);
//		titleTextView.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
		Button btnNextDialog = (Button) dialogAddresses.findViewById(R.id.btn3Next);
		btnNextDialog.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				dialogIndex++;
				dialogEndAddress = new Dialog(MainActivity.this);	
				dialogEndAddress.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialogEndAddress.setContentView(R.layout.popupwinend);
				//dialogEndAddress.setTitle("הכנס כתובת סיום");
				dialogEndAddress.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
				//TextView titleTextView = (TextView) dialogEndAddress.findViewById(android.R.id.title);
				//titleTextView.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
				Button btnNextDialog = (Button) dialogEndAddress.findViewById(R.id.btn4Next);
				btnNextDialog.setOnClickListener(new OnClickListener() {

					public void onClick(View arg0) {
						dialogAddresses.cancel();
						dialogEndAddress.cancel();
						DirectionsFetcher df = new DirectionsFetcher(nav, MainActivity.this);
						df.execute();
					}
				});
				Button btnBacktDialog = (Button) dialogEndAddress.findViewById(R.id.btn4Back);
				btnBacktDialog.setOnClickListener(new OnClickListener() {

					public void onClick(View arg0) {
						dialogIndex--;
						dialogEndAddress.cancel();
					}
				});

				AutoCompleteTextView autoCompView = (AutoCompleteTextView) dialogEndAddress.findViewById(R.id.autocomplete7);
				autoCompView.setAdapter(new PlacesAutoCompleteAdapter(MainActivity.this, R.layout.list_item));
				autoCompView.setOnItemClickListener(MainActivity.this);
				dialogEndAddress.show();
			}
		});
		Button btnBackDialog = (Button) dialogAddresses.findViewById(R.id.btn3Back);
		btnBackDialog.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				dialogAddresses.dismiss();
			}
		});

		AutoCompleteTextView autoCompView = (AutoCompleteTextView) dialogAddresses.findViewById(R.id.autocomplete6);
		autoCompView.setAdapter(new PlacesAutoCompleteAdapter(MainActivity.this, R.layout.list_item));
		autoCompView.setOnItemClickListener(this);

		ListView addressListView = (ListView) dialogAddresses.findViewById(R.id.addressesListView6);
		addressListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
				//				  Intent i = new Intent(MainActivity.this, AddressEditActivity.class);
				//				  Address address = (Address) adapterView.getItemAtPosition(position);
				//				  i.putExtra("address", (Serializable) address.getName());  
				//				  startActivity(i);
				final int pos = position;
				Address address = (Address) adapterView.getItemAtPosition(position);
				final Dialog dialogEditAddress = new Dialog(MainActivity.this);			
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
						adapter = new AddressAdapter(MainActivity.this, placesArray);	
						ListView places = (ListView) dialogAddresses.findViewById(R.id.addressesListView6);
						places.setAdapter(adapter);
						dialogEditAddress.dismiss();
					}
				}); 
				dialogEditAddress.show();
			}
		}); 

		dialogAddresses.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	//		public void nextClicked(View v){
	//			Intent i = new Intent(MainActivity.this, StartAddressActivity.class);
	//			i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	//			Navigation nav = new Navigation();
	//			i.putExtra("navObj", (Serializable)nav );  
	//			startActivity(i);
	//		}

	//	public void nextDialogClicked(View v){
	//		//		Intent i = new Intent(MainActivity.this, EndAddressActivity.class);
	//		//		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	//		//		i.putExtra("navObj", (Serializable)nav );  
	//		//		startActivity(i);
	//
	//		dialogEndAddress = new Dialog(this);	
	//		dialogEndAddress.setContentView(R.layout.popupwinend);
	//		dialogEndAddress.setTitle("הכנס כתובת סיום");
	//		AutoCompleteTextView autoCompView = (AutoCompleteTextView) dialogEndAddress.findViewById(R.id.autocomplete7);
	//		autoCompView.setAdapter(new PlacesAutoCompleteAdapter(MainActivity.this, R.layout.list_item));
	//		autoCompView.setOnItemClickListener(this);
	//		dialogEndAddress.show();
	//	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {		
		if(dialogIndex > 0){
			String str = (String) adapterView.getItemAtPosition(position);
			nav.addEndAdd(str);
		}
		else{
			String str = (String) adapterView.getItemAtPosition(position);
			placesArray.add(new Address(str, R.drawable.edit));
			adapter = new AddressAdapter(this, placesArray);	
			ListView places = (ListView) dialogAddresses.findViewById(R.id.addressesListView6);
			places.setAdapter(adapter);
			nav.addAddress(str);
			AutoCompleteTextView autoCompView = (AutoCompleteTextView) dialogAddresses.findViewById(R.id.autocomplete6);
			autoCompView.setText("");
		}
	}
}
