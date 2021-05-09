package com.projetjava;

import java.util.ArrayList;

public class ServiceMairie {

    private String nom;

    public ServiceMairie(String name){
        this.nom = name;
        ArrayList <Association> listeAssociation = new ArrayList<Association>();
        ArrayList <Personne> listPersonne = new ArrayList<Personne>();
    }

    public String getNom(){
        return this.nom;
    }

    public static void main(String[] args){

    }

}