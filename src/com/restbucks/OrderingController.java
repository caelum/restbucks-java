package com.restbucks;

import static br.com.caelum.vraptor.view.Results.xml;

import java.io.IOException;

import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Status;

/**
 * Ordering system provides two services: order retrieval and insertion.
 * 
 * @author guilherme silveira
 */
public class OrderingController {

	private final Result result;
	private final Status status;
	private final OrderDatabase database;
	private final Routes routes;

	public OrderingController(Result result, Status status, OrderDatabase database, Routes router) {
		this.result = result;
		this.status = status;
		this.database = database;
		this.routes = router;
	}

	@Get
	@Path("/{orderId}")
	public void get(String orderId) throws IOException {
		Order order = database.getOrder(orderId);
		if (order != null) {
			result.use(xml()).from(order).namespace(
					"http://restbucks.com/order", "o").serialize();
		} else {
			status.notFound();
		}
	}
	
	@Post
	@Path("/")
	@Consumes("application/xml")
	public void add(Order order) throws IOException {
		database.saveOrder(order);
		routes.uriFor(OrderingController.class).get(order.getId());
		status.header("Location", routes.getUri());
		status.created();
	}

	/*public Object payThisGuy() {
		return null;
	}*/

}
