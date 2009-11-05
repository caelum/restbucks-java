package com.restbucks;

import java.util.List;

public class Order {

	private String id;
	private Location location;
	private List<Item> items;
	
	public enum Location {takeAway, drinkIn};
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public List<Item> getItems() {
		return items;
	}
	public void setItems(List<Item> items) {
		this.items = items;
	}

}
