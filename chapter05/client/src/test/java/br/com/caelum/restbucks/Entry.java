package br.com.caelum.restbucks;

import java.net.URI;
import java.net.URISyntaxException;

import br.com.caelum.restbucks.model.Order;

public class Entry {
	
	public static void main(String[] args) throws URISyntaxException {
		URI uri = processCommandLineArgs(args);
		 happyPathTest(serviceUri);
        exampleForBook(serviceUri);
    }
	    
    private static void exampleForBook(URI entryPointUri) {
        Order order = order().withRandomItems().build();
        
        PlaceOrderActivity placeOrderActivity = new PlaceOrderActivity();
        placeOrderActivity.placeOrder(order, entryPointUri);
        ClientOrder createdOrder = placeOrderActivity.getOrder();
        // Do something with the order
        Actions actions = placeOrderActivity.getActions();
        
        if(actions.has(UpdateOrderActivity.class)) {
            UpdateOrderActivity updateOrderActivity = actions.get(UpdateOrderActivity.class);
            createdOrder = new ClientOrder(order().withRandomItems().build());
            updateOrderActivity.updateOrder(createdOrder.getOrder());
            actions = updateOrderActivity.getActions();
            createdOrder = updateOrderActivity.getOrder();
        }
                
        if(actions.has(PaymentActivity.class)) {
            PaymentActivity paymentActivity = actions.get(PaymentActivity.class);
            paymentActivity.payForOrder(new Payment(createdOrder.getCost(), "Martin Fowler", "1234567890", 12, 2121));
            actions = paymentActivity.getActions();
        }
        
        if(actions.has(GetReceiptActivity.class)) {
            GetReceiptActivity getReceiptActivity = actions.get(GetReceiptActivity.class);
            getReceiptActivity.getReceiptForOrder();
            // Store the receipt
            fileReceipt(getReceiptActivity.getReceipt());
            actions = getReceiptActivity.getActions();
        }
        
        if(actions.has(ReadOrderActivity.class)) {
            ReadOrderActivity readOrderActivity = actions.get(ReadOrderActivity.class);
            readOrderActivity.readOrder();
            ClientOrder baristaOrder = readOrderActivity.getOrder();
            while(baristaOrder.getStatus() != OrderStatus.READY) {
                hangAround(ONE_MINUTE);
                readOrderActivity = actions.get(ReadOrderActivity.class);
                baristaOrder = readOrderActivity.getOrder();
            }
        }
    }

    private static void hangAround(long backOffTimeInMillis) {
        try {
            Thread.sleep(backOffTimeInMillis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void fileReceipt(ReceiptRepresentation receipt) {
        // Store the receipt, or not as your business logic dictates
    }

    private static void happyPathTest(URI serviceUri) throws Exception {
        // Place the order
        System.out.println(String.format("About to start happy path test. Placing order at [%s] via POST", serviceUri.toString()));
        Order order = order().withRandomItems().build();
        Client client = Client.create();
        OrderRepresentation orderRepresentation = client.resource(serviceUri).accept(RESTBUCKS_MEDIA_TYPE).type(RESTBUCKS_MEDIA_TYPE).post(OrderRepresentation.class, new ClientOrder(order));
        System.out.println(String.format("Order placed at [%s]", orderRepresentation.getLatestLink().getUri().toString()));
        
        // Try to update a different order
        System.out.println(String.format("About to update an order with bad URI [%s] via POST", orderRepresentation.getUpdateLink().getUri().toString() + "/bad-uri"));
        order = order().withRandomItems().build();
        Link badLink = new Link("bad", new RestbucksUri(orderRepresentation.getLatestLink().getUri().toString() + "/bad-uri"), RESTBUCKS_MEDIA_TYPE);
        ClientResponse badUpdateResponse = client.resource(badLink.getUri()).accept(badLink.getMediaType()).type(badLink.getMediaType()).post(ClientResponse.class, new OrderRepresentation(order));
        System.out.println(String.format("Tried to update order with bad URI at [%s] via POST, outcome [%d]", badLink.getUri().toString(), badUpdateResponse.getStatus()));
        
        // Change the order
        System.out.println(String.format("About to update order at [%s] via POST", orderRepresentation.getUpdateLink().getUri().toString()));
        order = order().withRandomItems().build();
        Link updateLink = orderRepresentation.getUpdateLink();
        OrderRepresentation updatedRepresentation = client.resource(updateLink.getUri()).accept(updateLink.getMediaType()).type(updateLink.getMediaType()).post(OrderRepresentation.class, new OrderRepresentation(order));
        System.out.println(String.format("Order updated at [%s]", updatedRepresentation.getLatestLink().getUri().toString()));
        
        // Pay for the order 
        System.out.println(String.format("About to create a payment resource at [%s] via PUT", updatedRepresentation.getPaymentLink().getUri().toString()));
        Link paymentLink = updatedRepresentation.getPaymentLink();
        Payment payment = new Payment(updatedRepresentation.getCost(), "A.N. Other", "12345677878", 12, 2999);
        PaymentRepresentation  paymentRepresentation = client.resource(paymentLink.getUri()).accept(paymentLink.getMediaType()).type(paymentLink.getMediaType()).put(PaymentRepresentation.class, new PaymentRepresentation(payment));
        System.out.println(String.format("Payment made, receipt at [%s]", paymentRepresentation.getReceiptLink().getUri().toString()));
        
        // Get a receipt
        System.out.println(String.format("About to request a receipt from [%s] via GET", paymentRepresentation.getReceiptLink().getUri().toString()));
        Link receiptLink = paymentRepresentation.getReceiptLink();
        ReceiptRepresentation receiptRepresentation = client.resource(receiptLink.getUri()).get(ReceiptRepresentation.class);
        System.out.println(String.format("Payment made, amount in receipt [%f]", receiptRepresentation.getAmountPaid()));
        
        // Check on the order status
        System.out.println(String.format("About to check order status at [%s] via GET", receiptRepresentation.getOrderLink().getUri().toString()));
        Link orderLink = receiptRepresentation.getOrderLink();
        OrderRepresentation finalOrderRepresentation = client.resource(orderLink.getUri()).accept(RESTBUCKS_MEDIA_TYPE).get(OrderRepresentation.class);
        System.out.println(String.format("Final order placed, current status [%s]", finalOrderRepresentation.getStatus()));
        
        // Allow the barista some time to make the order
        System.out.println("Pausing the client, press a key to continue");
        System.in.read();
        
        // Take the order if possible
        System.out.println(String.format("Trying to take the ready order from [%s] via DELETE. Note: the internal state machine must progress the order to ready before this should work, otherwise expect a 405 response.", receiptRepresentation.getOrderLink().getUri().toString()));
        ClientResponse finalResponse = client.resource(orderLink.getUri()).delete(ClientResponse.class);
        System.out.println(String.format("Tried to take final order, HTTP status [%d]", finalResponse.getStatus()));
        if(finalResponse.getStatus() == 200) {
            System.out.println(String.format("Order status [%s], enjoy your drink", finalResponse.getEntity(OrderRepresentation.class).getStatus()));
        }
    }
    
	/**
	 * Code from Jim Webber's restbucks example app
	 */
    private static URI processCommandLineArgs(String[] args) throws URISyntaxException {
        if(args.length != 1) {
            System.out.println("Must specify entry point URI as the only command line argument ");
            System.exit(1);
        } else {
            System.out.println("Binding to service at: " + args[0]);
        }
        return new URI(args[0]);
    }

}
