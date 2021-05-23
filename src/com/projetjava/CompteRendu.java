package com.projetjava;

import java.util.Date;

public class CompteRendu {

    private String rapport;
    private Date dateRapport;
    private Association associationRapport;
    private Membre membreRapport;

    public CompteRendu(String contenu, int annee, int mois, int jour, Association association, Membre membre){
        this.rapport = contenu;
        this.dateRapport = new Date(annee - 1900, mois - 1, jour);
        this.associationRapport = association;
        this.membreRapport = membre;
    }

    public Date getDateRapport(){
        return this.dateRapport;
    }

}
