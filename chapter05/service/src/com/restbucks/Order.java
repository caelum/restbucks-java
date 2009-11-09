package com.restbucks;

import java.io.IOException;
import java.util.List;

import br.com.caelum.vraptor.rest.Restfulie;
import br.com.caelum.vraptor.rest.StateResource;
import br.com.caelum.vraptor.rest.Transition;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("order")
public class Order implements StateResource{

	private String id;
	private Location location;
	private List<Item> items;
	
	private String status;
	
/*	public List<String> getPossibleTransitions() {
		
	}*/
	
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
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatus() {
		return status;
	}
	
	public void cancel() {
		status = "cancelled";
	}

	public List<Transition> getFollowingTransitions(Restfulie control) {
		if(status.equals("unpaid")) {
			control.transition("cancel").resultsInStatus("cancelled").uses(OrderingController.class).cancel(id);
		}
		return control.getTransitions();
	}

}
