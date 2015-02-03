package com.example.finalproject;
import java.io.Serializable;

import com.example.finalproject.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.AdapterView.OnItemClickListener;

public class EndAddressActivity extends Activity implements OnItemClickListener {
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
	}

	public void startNavClicked(View v){
		DirectionsFetcher df = new DirectionsFetcher(nav, this);
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