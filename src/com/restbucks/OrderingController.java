package com.restbucks;

import java.net.URI;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.caelum.vraptor.view.Status;

/**
 * Ordering system provides two services: order retrieval and insertion.
 * 
 * @author guilherme silveira
 */
public class OrderingController {
	
	private @Context UriInfo uriInfo;
	private final Result result;
	private final Status status;

	public OrderingController(Result result, Status status) {
		this.result = result;
		this.status = status;
	}

	@Get
	@Produces("application/xml")
	@Path("/{orderId}")
	public String get(String orderId) {

		Order order = OrderDatabase.getDatabase().getOrder(orderId);

		try {
			if (order != null) {
				result.use(Results.xml()).from(order).namespace("http://restbucks.com/order", "o").serialize();
			}
			status.notFound();
		} catch (Exception e) {
			status.internalServerError(e);
		}
	}

	@Post
	@Path("/")
	public void add(String order) {
		try {

			Order order = (Order) xstream.fromXML(orderXml);
			String internalOrderId = OrderDatabase.getDatabase().saveOrder(
					order);

			Response response = Response.created(
					new URI(uriInfo.getAbsolutePath() + internalOrderId))
					.build();
			return response;

		} catch (Exception e) {
			status.internalServerError(e);
		}
	}

}
