package com.restbucks;

import java.lang.reflect.Array;
import java.util.List;

public class OrderStateControl implements StateControl {
	
	private DefaultStateControl control = new DefaultStateControl();
	
	public String getStateField() {
		return "status";
	}
	
	public List<Transition> getTransitions() {
		return null;
		/*return Array.asList(
				control.transition("cancel").resultsInStatus("cancelled").uses(OrderingController.class), // same method
				//control.transition("pay").resultsInStatus("payed").uses(OrderingController.class).payThisGuy(), // another method
				control.transition("custom").uses("/orders/{order.id}/customized") // will only process and invoke its method. there is no method on the controller itself
				); 
				*/
	}
	
	public List<State> getStates() {
		return null;
		/*
		return Array.asList(
				control.state("unpaid").allow("cancel", "pay", "latest"), // always allowed
				control.state("ready").allow("receive").when(new Dependency<Order>() {
					public boolean allows(Order order) {
						//return order.getStatus()=="payed" && order.getPaymentTime()<1minute;
						return true;
					}

				})
				);
				*/
	}

}
