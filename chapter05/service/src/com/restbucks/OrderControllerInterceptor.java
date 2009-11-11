package com.restbucks;

import javax.servlet.http.HttpServletRequest;

import br.com.caelum.restfulie.vraptor.StateControlInterceptor;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.core.MethodInfo;
import br.com.caelum.vraptor.rest.Restfulie;
import br.com.caelum.vraptor.view.Status;

/**
 * Create a generic component which looks up all StateControl's.<br/>
 * Registers one interceptor which receives this component and intercepts all state controls.
 */
@Intercepts
public class OrderControllerInterceptor extends StateControlInterceptor<Order>{
	
	public OrderControllerInterceptor(OrderStateControl control, Restfulie restfulie, Status status, MethodInfo info, HttpServletRequest request) {
		super(control, restfulie, status, info, request);
	}

}
