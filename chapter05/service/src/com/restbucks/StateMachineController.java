package com.restbucks;

public interface StateMachineController<T> {

	public String getBaseUri();
	public T retrieve(String id);

}
