package com.projetjava;

public class Personne {

    private String nom;
    private String prenom;

    public Personne(String lastname, String firstname){
        this.nom = lastname;
        this.prenom = firstname;
    }

    public String getNom(){
        return this.nom;
    }

    public String getPrenom(){
        return this.prenom;
    }

}
