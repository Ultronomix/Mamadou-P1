package com.revature.ers.common.exceptions;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(){
        super("ERROR: Resource Not Found");
    }

    public ResourceNotFoundException(String message){
        super(message);
    }
}
