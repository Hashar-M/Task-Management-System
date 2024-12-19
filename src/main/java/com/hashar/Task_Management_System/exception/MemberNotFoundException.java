package com.hashar.Task_Management_System.exception;

public class MemberNotFoundException extends RuntimeException{
    public MemberNotFoundException(String message){
        super(message);
    }
}
