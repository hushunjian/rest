package com.m2m.exception;

public class SendMessageTimeException extends RuntimeException {
	private static final long serialVersionUID = -4549889507393420264L;

	public SendMessageTimeException(String message){
        super(message);
    }

}
