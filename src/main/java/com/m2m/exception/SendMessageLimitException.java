package com.m2m.exception;

public class SendMessageLimitException extends RuntimeException {
	private static final long serialVersionUID = 3286826861253357631L;

	public SendMessageLimitException(String message){
        super(message);
    }

}
