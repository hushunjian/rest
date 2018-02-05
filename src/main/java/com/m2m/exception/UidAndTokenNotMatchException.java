package com.m2m.exception;

public class UidAndTokenNotMatchException extends RuntimeException {
	private static final long serialVersionUID = -8233971449740948702L;

	public UidAndTokenNotMatchException(String message){
        super(message);
    }

}
