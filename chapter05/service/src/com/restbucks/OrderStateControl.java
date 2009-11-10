package com.restbucks;

import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.rest.StateControl;

@Component
@ApplicationScoped
public class OrderStateControl implements StateControl<Order> {
	
	private final OrderDatabase database;

	public OrderStateControl(OrderDatabase database) {
		this.database = database;
	}
	
	@SuppressWarnings("unchecked")
	public Class[] getControllers() {
		return new Class[]{OrderingController.class};
	}
	
	public Order retrieve(String id) {
		return database.getOrder(id);
	}
//	
//	public List<Transition> getTransitions(Restfulie control, Order order) {
//		control.transition("cancel").resultsInStatus("cancelled").uses(OrderingController.class).cancel(order.getId());
//		return control.getTransitions();
//				//control.transition("pay").resultsInStatus("payed").uses(OrderingController.class).payThisGuy(), // another method
//				//control.transition("custom").uses("/orders/{order.id}/customized") // will only process and invoke its method. there is no method on the controller itself
//	}
//	
//	public List<State> getStates(Restfulie control) {
//		control.state("unpaid").allow("cancel");
//		return control.getStates();
////		
////				control.state("ready").allow("receive").when(new Dependency<Order>() {
////					public boolean allows(Order order) {
////						//return order.getStatus()=="payed" && order.getPaymentTime()<1minute;
////						return true;
////					}
////
////				})
//	}

}
