package com.projetjava.exception;

import com.projetjava.Association;

public class SoldeNegatifException extends Exception {

    private Association association;

    public SoldeNegatifException(Association association){
        this.association = association;
    }

}
