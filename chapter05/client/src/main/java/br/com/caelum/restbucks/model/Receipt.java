package br.com.caelum.restbucks.model;

import java.util.Calendar;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("receipt")
public class Receipt {

	private Calendar paymentTime;

	public Calendar getPaymentTime() {
		return paymentTime;
	}

}
