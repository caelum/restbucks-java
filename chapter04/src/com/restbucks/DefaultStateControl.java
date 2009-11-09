package com.restbucks;

public class DefaultStateControl implements StateControl{
	
	public String getStatusField() {
		return "status";
	}
	
	public TransitionBuilder transition(String name) {
		return new TransitionBuilder(this, name);
	}

	public StateBuilder state(String name) {
		return new StateBuilder(this,name);
	}

}
