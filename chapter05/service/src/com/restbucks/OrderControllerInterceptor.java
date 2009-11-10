package com.restbucks;

import br.com.caelum.vraptor.Intercepts;

@Intercepts
public class OrderControllerInterceptor extends StateControlInterceptor<Order>{
	
	public OrderControllerInterceptor(OrderStateControl control) {
		super(control);
	}

}
