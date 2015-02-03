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


public class StartAddressActivity extends Activity implements OnItemClickListener {
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
}