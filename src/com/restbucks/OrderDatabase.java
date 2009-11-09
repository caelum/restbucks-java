package com.restbucks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;

import com.restbucks.Item.Coffee;
import com.restbucks.Item.Milk;
import com.restbucks.Item.Size;
import com.restbucks.Order.Location;

@Component
@ApplicationScoped
public class OrderDatabase {

	private static int orderCounter = 0;
	private Map<String, Order> orders = new HashMap<String, Order>();
	
	public OrderDatabase() {
	    Item item = new Item(Coffee.latte, 1, Milk.whole, Size.small);
	    ArrayList<Item> items = new ArrayList<Item>();
	    items.add(item);
	    
	    Order order = new Order();
	    order.setId("1234");
	    order.setItems(items);
	    order.setLocation(Location.takeAway);
	    
	    this.saveOrder(order.getId(), order);
	}
	
	public synchronized String saveOrder(Order order) {
		String id = String.valueOf(orderCounter);
		orderCounter++;
		order.setId(id);
		orders.put(id, order);
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
