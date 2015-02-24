package com.example.finalproject.activities;

import java.io.Serializable;

import com.example.finalproject.R;
import com.example.finalproject.bl.DirectionsFetcher;
import com.example.finalproject.classes.Navigation;
import com.example.finalproject.custom.PlacesAutoCompleteAdapter;
import android.os.Bundle;
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

public class EndAddressActivity extends ActionBarActivity implements OnItemClickListener {
	private Navigation nav = new Navigation();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_end_address);

		Intent i = getIntent();
		nav = (Navigation) i.getSerializableExtra("navObj");

		AutoCompleteTextView autoCompView = (AutoCompleteTextView) findViewById(R.id.autocomplete3);
		autoCompView.setAdapter(new PlacesAutoCompleteAdapter(this, R.layout.list_item));
		autoCompView.setOnItemClickListener(this);
		if(nav.getEndAdd() != "")
			autoCompView.setText(nav.getEndAdd());
		
		ActionBar bar = getSupportActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0000ff")));
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
	
	public void startNavClicked(View v){
		DirectionsFetcher df = new DirectionsFetcher(this);
		df.execute();
	}

	public void backClicked(View v){
		Intent i = new Intent(EndAddressActivity.this, AddressesActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		i.putExtra("navObj", (Serializable)nav );  
		startActivity(i);
	}

	public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
		String str = (String) adapterView.getItemAtPosition(position);
		nav.addEndAdd(str);
	}
}