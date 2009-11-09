package com.restbucks;

import com.restbucks.restfulie.StateMachineController;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;

@Component
@RequestScoped
public class OrderMachineController implements StateMachineController<Order>{

	public String getBaseUri() {
		return "http://localhost:8080/orders/{order.id}";
	}

	public Order retrieve(String id) {
		return null; // session.load(Order.class, Long.parseLong(id));
	}

}
