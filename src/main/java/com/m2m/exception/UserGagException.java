package com.m2m.exception;

public class UserGagException extends RuntimeException {

	private static final long serialVersionUID = -508746666425240902L;

	public UserGagException(String message){
		super(message);
	}
}
