package com.restbucks;

import static br.com.caelum.vraptor.view.Results.xml;

import java.io.IOException;
import java.net.URI;

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

	public OrderingController(Result result, Status status, OrderDatabase database) {
		this.result = result;
		this.status = status;
		this.database = database;
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
	public void add(Order order) {
		String internalOrderId = database.saveOrder(order);

		Response response = Response.created(
				new URI(uriInfo.getAbsolutePath() + internalOrderId)).build();
		return response;
	}

	/*public Object payThisGuy() {
		return null;
	}*/

}
