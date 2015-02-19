package com.example.finalproject;
import java.io.Serializable;
import com.example.finalproject.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.AdapterView.OnItemClickListener;


public class StartAddressActivity extends ActionBarActivity implements OnItemClickListener {
	private Navigation nav = new Navigation();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_address);
		AutoCompleteTextView autoCompView = (AutoCompleteTextView) findViewById(R.id.autocomplete);
		autoCompView.setAdapter(new PlacesAutoCompleteAdapter(this, R.layout.list_item));
		autoCompView.setOnItemClickListener(this);
		Intent i = getIntent();
		nav = (Navigation) i.getSerializableExtra("navObj");
		if(nav.getStartAdd() != "")
			autoCompView.setText(nav.getStartAdd());
	}

	public void nextClicked(View v){	
		Intent i = new Intent(StartAddressActivity.this, AddressesActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		i.putExtra("navObj", (Serializable)nav );  
		startActivity(i);
	}

	public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
		String str = (String) adapterView.getItemAtPosition(position);
		nav.addStartAdd(str);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_main_actions, menu);

		super.onCreateOptionsMenu(menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Take appropriate action for each action item click
		switch (item.getItemId()) {
		//        case R.id.action_map:
		//            // search action
		//            return true;
		//        case R.id.action_favorits:
		//            // location found
		//        	//nextClicked();
		//            return true;
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
}