package com.hexaware.cozyhavenproject.exceptionhandler;

public class ResourceNotFoundException extends RuntimeException {

	
	public ResourceNotFoundException(String message) {
        super(message);
    }
}
