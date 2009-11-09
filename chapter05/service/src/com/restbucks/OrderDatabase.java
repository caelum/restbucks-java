package com.restbucks;

import java.util.ArrayList;
import java.util.Collection;
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

	private static int total = 1;
	private Map<String, Order> orders = new HashMap<String, Order>();
	
	public OrderDatabase() {
	    Item item = new Item(Coffee.latte, 1, Milk.whole, Size.small);
	    ArrayList<Item> items = new ArrayList<Item>();
	    items.add(item);
	    
	    Order order = new Order();
	    order.setId("1");
	    order.setItems(items);
	    order.setLocation(Location.takeAway);
	    
	    save(order.getId(), order);
	}
	
	public synchronized void save(Order order) {
		order.setStatus("unpaid");
		total++;
		String id = String.valueOf(total);
		order.setId(id);
		orders.put(id, order);
	}
	
	public void save(String id, Order order) {
		orders.put(id, order);
	}
	
	public boolean orderExists(String id) {
		return orders.containsKey(id);
	}
	
	private static final long serialVersionUID = 1L;

	public Order getOrder(String id) {
		return orders.get(id);
	}

	public Collection<Order> all() {
		return orders.values();
	}

}
