package com.restbucks;

import static br.com.caelum.vraptor.view.Results.xml;

import java.net.URI;

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

	private @Context
	UriInfo uriInfo;
	private final Result result;
	private final Status status;

	public OrderingController(Result result, Status status) {
		this.result = result;
		this.status = status;
	}

	@Get
	@Path("/{orderId}")
	public String get(String orderId) {
		Order order = OrderDatabase.getDatabase().getOrder(orderId);
		if (order != null) {
			result.use(xml()).from(order).namespace(
					"http://restbucks.com/order", "o").serialize();
		} else {
			status.notFound();
		}
	}
	
	@Post
	@Path("/")
	@ConsumeXml
	public void add(Order order) {
		order = deserializer.from(Order.class, "order")
		Order order = (Order) xstream.fromXML(orderXml);
		String internalOrderId = OrderDatabase.getDatabase().saveOrder(order);

		Response response = Response.created(
				new URI(uriInfo.getAbsolutePath() + internalOrderId)).build();
		return response;
	}

}
