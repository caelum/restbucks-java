package com.restbucks;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.interceptor.Interceptor;
import br.com.caelum.vraptor.resource.ResourceMethod;
import br.com.caelum.vraptor.rest.StateControl;

import com.sun.tools.javadoc.ParameterizedTypeImpl;

/**
 * Intercepts invocations to state control's intercepted controllers.
 * 
 * @author guilherme silveira
 * @author pedro mariano
 * @since 3.0.3
 */
public class StateControlInterceptor<T> implements Interceptor {

	private final StateControl<T> control;
	@SuppressWarnings("unchecked")
	private final List<Class> controllers;

	public StateControlInterceptor(StateControl<T> control) {
		this.control = control;
		this.controllers = Arrays.asList(control.getControllers());
	}

	public boolean accepts(ResourceMethod method) {
		return controllers.contains(method.getResource().getType());
	}

	public void intercept(InterceptorStack stack, ResourceMethod method,
			Object instance) throws InterceptionException {
		if(!executeFor(control.getClass())) {
			System.out.println("Found type for this guy!");
		}
		stack.next(method, instance);
	}

	private boolean executeFor(Class<?> baseType) {
		if(baseType.equals(Object.class)) {
			return false;
		}
		Type[] interfaces = baseType.getGenericInterfaces();
		for (Type type : interfaces) {
			if (type instanceof ParameterizedType) {
				ParameterizedType parameterized = (ParameterizedType) type;
				if(parameterized.getRawType().equals(StateControl.class)) {
					Type parameterType = parameterized.getActualTypeArguments()[0];
					Class found = (Class) parameterType;
					System.out.println(found);
					return true;
				}
			} else {
				Class simple = (Class) type;
				if (simple.equals(StateControl.class)) {
					throw new IllegalStateException(
							"Unable to detect which state control it is because "
									+ control.getClass()
									+ " does not implement StateControl of an specific type");
				}
			}
		}
		return executeFor(baseType.getSuperclass());
	}

}
