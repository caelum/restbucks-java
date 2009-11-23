package com.restbucks;

import java.util.Calendar;

public class Receipt {
	
	private final Calendar paymentTime= Calendar.getInstance();
	public Calendar getPaymentTime() {
		return paymentTime;
	}

}
