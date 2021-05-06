package com.projetjava;

import java.util.Date;

public class President extends Membre{

    private boolean president;

    public President(String lastname, String firstname, int yearNaissance, int monthNaissance, int dayNaissance, int yearInscription, int monthInscription, int dayInscription, String adresseMembre, boolean pres){
        super(lastname, firstname, yearNaissance, monthNaissance, dayNaissance, yearInscription, monthInscription, dayInscription, adresseMembre);
        this.president = pres;
    }

    public boolean estPresident(){
        return this.president;
    }

}
