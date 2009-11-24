package com.restbucks.restfulie;

import br.com.caelum.vraptor.core.Routes;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;
import br.com.caelum.vraptor.proxy.Proxifier;
import br.com.caelum.vraptor.rest.DefaultRestfulie;

@Component
@RequestScoped
public class RestfulSystem extends DefaultRestfulie {

	public RestfulSystem(Routes routes, Proxifier proxifier) {
		super(routes, proxifier);
	}

}
