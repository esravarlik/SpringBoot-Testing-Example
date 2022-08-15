package com.jojo.springboottestingexample.exception;

public class UserApplicationException extends RuntimeException{

    public UserApplicationException(String message){
        super(message);
    }

}
