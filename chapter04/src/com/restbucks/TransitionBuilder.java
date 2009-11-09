package com.restbucks;

public class TransitionBuilder {

	private final DefaultStateControl defaultStateControl;
	private final String name;
	private String resultingStatus;

	public TransitionBuilder(DefaultStateControl defaultStateControl,
			String name) {
		this.defaultStateControl = defaultStateControl;
		this.name = name;
	}

	public TransitionBuilder resultsInStatus(String newStatus) {
		this.resultingStatus = newStatus;
		return this;
	}

	public <T> T uses(Class<T> class1) {
		return null;
	}

	public TransitionBuilder uses(String customURI) {
		return this;
	}

}
