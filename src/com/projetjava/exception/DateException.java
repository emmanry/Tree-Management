package com.projetjava.exception;

public class DateException extends Exception{

    private String messageErreur;

    public DateException(String message){
        this.messageErreur = message;
    }

    public String getMessageErreur(){
        return this.messageErreur;
    }

}
