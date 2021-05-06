package com.projetjava;

import java.util.Date;

public class Membre extends Personne{

    private Date dateDeNaissance, dateDerniereInscription;
    private String adresse;

    public Membre(String lastname, String firstname, int yearNaissance, int monthNaissance, int dayNaissance, int yearInscription, int monthInscription, int dayInscription, String adresseMembre){
        super(lastname, firstname);
        this.dateDeNaissance = new Date(yearNaissance-1900, monthNaissance, dayNaissance);
        this.dateDerniereInscription = new Date(yearInscription-1900, monthInscription, dayInscription);
        this.adresse = adresseMembre;
    }

}
