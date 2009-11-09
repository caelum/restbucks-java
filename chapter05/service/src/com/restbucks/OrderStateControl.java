package com.restbucks;

import java.util.List;

import com.restbucks.restfulie.DefaultStateControl;
import com.restbucks.restfulie.State;
import com.restbucks.restfulie.StateControl;
import com.restbucks.restfulie.Transition;

public class OrderStateControl implements StateControl {
	
	private final DefaultStateControl control = new DefaultStateControl();
	
	public String getStateField() {
		return "status";
	}
	
	public List<Transition> getTransitions() {
		control.transition("cancel").resultsInStatus("cancelled").uses(OrderingController.class);
		return control.getTransitions();
				//control.transition("pay").resultsInStatus("payed").uses(OrderingController.class).payThisGuy(), // another method
				//control.transition("custom").uses("/orders/{order.id}/customized") // will only process and invoke its method. there is no method on the controller itself
	}
	
	public List<State> getStates() {
		control.state("unpaid").allow("cancel");
		return control.getStates();
//		
//				control.state("ready").allow("receive").when(new Dependency<Order>() {
//					public boolean allows(Order order) {
//						//return order.getStatus()=="payed" && order.getPaymentTime()<1minute;
//						return true;
//					}
//
//				})
	}

}
