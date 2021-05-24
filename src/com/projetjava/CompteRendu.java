package com.projetjava;

import java.util.Date;

public class CompteRendu {

    private String rapport;
    private MyDate dateRapport;
    private Association associationRapport;
    private Membre membreRapport;

    public CompteRendu(String contenu, int annee, int mois, int jour, Association association, Membre membre){
        this.rapport = contenu;
        this.dateRapport = new MyDate(annee, mois, jour);
        this.associationRapport = association;
        this.membreRapport = membre;
    }

    public MyDate getDateRapport(){
        return this.dateRapport;
    }

    public Membre getMembreRapport(){
        return this.membreRapport;
    }

}
