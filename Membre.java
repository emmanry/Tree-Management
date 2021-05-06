package com.projetjava;

import java.util.Date;

public class Membre extends Personne{

    private Date dateDeNaissance, dateDerniereInscription;
    private String adresse;

    public Membre(String lastname, String firstname, Date birthday, Date lastInscription, String adresseMembre){
        super(lastname, firstname);
        this.dateDeNaissance = birthday;
        this.dateDerniereInscription = lastInscription;
        this.adresse = adresseMembre;
    }

}
