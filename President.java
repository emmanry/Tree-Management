package com.projetjava;

import java.util.Date;

public class President extends Membre{

    private boolean president;

    public President(String lastname, String firstname, Date birthday, Date lastInscription, String adresseMembre, boolean pres){
        super(lastname, firstname, birthday, lastInscription, adresseMembre);
        this.president = pres;
    }

    public boolean estPresident(){
        return this.president;
    }
    
}
