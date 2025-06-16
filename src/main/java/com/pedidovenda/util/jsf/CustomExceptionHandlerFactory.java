package com.pedidovenda.util.jsf;


import jakarta.faces.context.ExceptionHandler;
import jakarta.faces.context.ExceptionHandlerFactory;

public class CustomExceptionHandlerFactory extends ExceptionHandlerFactory {

	ExceptionHandlerFactory parent;
	
	public CustomExceptionHandlerFactory(ExceptionHandlerFactory parent) {
		this.parent = parent;
	}
	
	@Override
	public ExceptionHandler getExceptionHandler() {
		return new CustomExceptionHandler(parent.getExceptionHandler());
	}

}
