package com.restbucks.restfulie;

import java.util.List;

import com.restbucks.OrderingController;

public interface StateControl {

	public String getStateField();

	public List<Transition> getTransitions();

}
