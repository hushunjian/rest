package com.m2m.exception;

public class UnKnownHostException extends RuntimeException {
	private static final long serialVersionUID = -8233971449740948702L;

	public UnKnownHostException(String message){
        super(message);
    }

}
