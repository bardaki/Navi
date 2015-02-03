package com.example.finalproject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Navigation implements Serializable{
	private String startAdd = "";
	private List<String> addresses;
	private String endAdd = "";

	public Navigation(){
		addresses = new ArrayList<String>();
	}

	public void addAddress(String address){
		addresses.add(address);
	}

	public void addStartAdd(String address){
		startAdd = address;
	}

	public void addEndAdd(String address){
		endAdd = address;
	}

	public String getStartAdd(){
		return startAdd;
	}

	public String getEndAdd(){
		return endAdd;
	}

	public List<String> getAddresses(){
		return addresses;
	}

	public void removeFromAddresses(int position){
		addresses.remove(position);
	}
}
