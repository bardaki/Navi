package com.example.finalproject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class RoutsActivity extends ActionBarActivity  {
	private List<Route> routes = new ArrayList<Route>();
	private Navigation nav = new Navigation();

	Intent intService;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_routs);
		intService = new Intent(this, LocationService.class);
		stopService(intService);
		Intent intent = getIntent();
		routes = (List<Route>) intent.getSerializableExtra("routes");	 
		String[] values = new String[routes.size()];
		nav = (Navigation) intent.getSerializableExtra("navObj");
		for(int i = 0; i< routes.size(); i++){
			values[i] = (routes.get(i).toString());
		}
		final ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < values.length; ++i) {
			list.add(values[i]);
		}
		CustomAdapter adapter = new CustomAdapter(this, android.R.layout.simple_list_item_1, list);
		ListView lv = (ListView) findViewById(R.id.list);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				intService.putExtra("endLatitude", routes.get(position).getLatitude());
				intService.putExtra("endLongitude", routes.get(position).getLongitude());
				intService.putExtra("routes", (Serializable)routes);  
				intService.putExtra("navObj", (Serializable)nav ); 
				startService(intService);
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + routes.get(position).getDestination()));
				startActivity(intent); 
				
			}
		});
		
		ActionBar bar = getSupportActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00ABFF")));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.actionbar, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Take appropriate action for each action item click
		switch (item.getItemId()) {
//		case R.id.action_map:
//			// search action
//			return true;
//		case R.id.action_favorits:
//			// location found
//			//nextClicked();
//			return true;
//		case R.id.action_person:
//			// refresh
//			return true;
//		case R.id.action_help:
//			// help action
//			return true;
//		case R.id.action_settings:
//			// check for updates action
//			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	protected void onListItemClick(ListView l, View v, int position, long id) {
		//Start google navigation activity
		//		Intent i = new Intent(RoutsActivity.this, ShowMapActivity.class);
		//		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		//i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		//		i.putExtra("endLatitude", routes.get(position).getLatitude());
		//		i.putExtra("endLongitude", routes.get(position).getLongitude());
		//		i.putExtra("source", routes.get(position).getSource());
		//		i.putExtra("destination", routes.get(position).getDestination());   	
		//		i.putExtra("routes", (Serializable)routes);  
		//		i.putExtra("navObj", (Serializable)nav );  	
		//		startActivity(i);
		//finish();
		//Intent i = new Intent(this, LocationService.class);
		intService.putExtra("endLatitude", routes.get(position).getLatitude());
		intService.putExtra("endLongitude", routes.get(position).getLongitude());
		intService.putExtra("routes", (Serializable)routes);  
		intService.putExtra("navObj", (Serializable)nav ); 
		startService(intService);
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + routes.get(position).getDestination()));
		startActivity(intent);       
	}

	public void backClicked(View v){	
		Intent i = new Intent(RoutsActivity.this, MainActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		i.putExtra("navObj", (Serializable)nav ); 
		startActivity(i);
	}

	@Override
	public void onResume(){
		super.onResume();
		stopService(intService);
	}

	public class CustomAdapter extends ArrayAdapter<String> {

		public CustomAdapter(Context context, int resID, ArrayList<String> items) {
			super(context, resID, items);                       
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = super.getView(position, convertView, parent);
			((TextView) v).setTextColor(Color.BLACK); 
			((TextView) v).setTextSize(18);
			return v;
		}
	}
}
