package com.restbucks;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import br.com.caelum.vraptor.rest.Restfulie;
import br.com.caelum.vraptor.rest.StateResource;
import br.com.caelum.vraptor.rest.Transition;

@XStreamAlias("order")
public class Order implements StateResource {

	private String id;
	private Location location;
	@XStreamImplicit
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

	public List<Transition> getFollowingTransitions(Restfulie control) {
		if (status.equals("unpaid")) {
			control.transition("latest").uses(OrderingController.class).get(this);
//				 when(notFound()).then(404);
//				 when(invalidState()).then(customWhatever());
			control.transition("cancel").uses(OrderingController.class).cancel(this);
			control.transition("pay").uses(OrderingController.class).pay(this,null);
		}
		return control.getTransitions();
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

}
