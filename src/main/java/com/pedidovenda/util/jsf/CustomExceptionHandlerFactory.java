package com.pedidovenda.util.jsf;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

public class CustomExceptionHandlerFactory extends ExceptionHandlerFactory{

	ExceptionHandlerFactory parent;
	
	public CustomExceptionHandlerFactory(ExceptionHandlerFactory parent) {
		this.parent = parent;
	}
	
	@Override
	public ExceptionHandler getExceptionHandler() {
		return new CustomExceptionHandler(parent.getExceptionHandler());
	}

}
