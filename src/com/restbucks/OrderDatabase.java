package com.restbucks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.restbucks.Item.Coffee;
import com.restbucks.Item.Milk;
import com.restbucks.Item.Size;
import com.restbucks.Order.Location;

public class OrderDatabase {

	private static int orderCounter = 0;
	private static OrderDatabase theDatabase = new OrderDatabase();
	private Map<String, Order> orders = new HashMap<String, Order>();
	
	static {
	    Item item = new Item(Coffee.latte, 1, Milk.whole, Size.small);
	    ArrayList<Item> items = new ArrayList<Item>();
	    items.add(item);
	    
	    Order order = new Order();
	    order.setId("1234");
	    order.setItems(items);
	    order.setLocation(Location.takeAway);
	    
	    theDatabase.saveOrder(order.getId(), order);
	}
	
	public static OrderDatabase getDatabase() {
		return theDatabase;
	}
	
	public synchronized String saveOrder(Order order) {
		String id = String.valueOf(orderCounter);
		orders.put(id, order);
		orderCounter++;
		return id;
	}
	
	public void saveOrder(String id, Order order) {
		orders.put(id, order);
	}
	
	public boolean orderExists(String id) {
		return orders.containsKey(id);
	}
	
	private static final long serialVersionUID = 1L;

	public Order getOrder(String id) {
		return orders.get(id);
	}

}
