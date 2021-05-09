package com.projetjava;

import java.util.Date;

public class CompteRendu {

    private String rapport;
    private Date dateRapport;
    private Association associationRapport;
    private Membre membreRapport;

    public CompteRendu(String contenu, int year, int month, int day, Association association, Membre membre){
        this.rapport = contenu;
        this.dateRapport = new Date(year, month, day);
        this.associationRapport = association;
        this.membreRapport = membre;
    }

}
