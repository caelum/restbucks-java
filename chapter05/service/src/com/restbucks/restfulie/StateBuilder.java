package com.restbucks.restfulie;

import com.restbucks.Dependency;

public class StateBuilder {

	private final DefaultStateControl defaultStateControl;
	private final String name;

	public StateBuilder(DefaultStateControl defaultStateControl, String name) {
		this.defaultStateControl = defaultStateControl;
		this.name = name;
	}

	public StateBuilder allow(String ... states) {
		return this;
	}
	
	public StateBuilder when(Dependency dependency) {
		return this;
	}

	public State build() {
		return null;
	}

}
