package br.com.caelum.restbucks.model;

import java.util.ArrayList;
import java.util.List;

public class Order {

	private String id;
	private Location location;
	private List<Item> items;

	private String status;
	private Payment payment;

	public enum Location {
		takeAway, drinkIn
	};

	public Order(String status, List<Item> items, Location location) {
		this.status = status;
		this.items = items;
		this.location = location;
	}

	public Order() {
		this.items = new ArrayList<Item>();
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void cancel() {
		status = "cancelled";
	}

	public void pay(Payment payment) {
		status = "paid";
		this.payment = payment;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public Payment getPayment() {
		return payment;
	}

	public void add(Item item) {
		this.items.add(item);
	}

}
