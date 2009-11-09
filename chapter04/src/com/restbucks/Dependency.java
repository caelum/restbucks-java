package com.restbucks;

public interface Dependency<T> {
	
	boolean allows(T object);

}
