package com.example.finalproject;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AddressAdapter extends BaseAdapter {
	ArrayList<Address> data;
	Context context;
	
	public AddressAdapter(Context c, ArrayList<Address> d){
		this.data = d;
		this.context = c;	
	}

	@Override
	public int getCount() {
		if(data != null)
			return data.size();
		return 0;
	}

	@Override
	public Object getItem(int index) {
		return data.get(index);
	}

	@Override
	public long getItemId(int index) {
		return index;
	}

	public View getView(int index, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.address_item, null);
		TextView txtName = (TextView) view.findViewById(R.id.addressTxt);
		ImageView imgView = (ImageView) view.findViewById(R.id.editImg);
		
		Address p = data.get(index);
		txtName.setText(p.getName());
		imgView.setImageResource(p.getUrlImage());
		
		return view;
	}
}
