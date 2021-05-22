package com.projetjava;

import java.util.Date;

public class President extends Membre{

    private boolean president;

    public President(String lastname, String firstname, Association assoc, int yearNaissance, int monthNaissance, int dayNaissance, int yearInscription, int monthInscription, int dayInscription, String adresseMembre){
        super(lastname, firstname, assoc, yearNaissance, monthNaissance, dayNaissance, yearInscription, monthInscription, dayInscription, adresseMembre);
        this.president = true;
    }

    public boolean estPresident(){
        return this.president;
    }

}
