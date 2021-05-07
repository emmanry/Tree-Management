package com.projetjava;

import java.util.Date;

public class CompteRendu {

    private String rapport;
    private Date dateRapport;

    public CompteRendu(String contenu, int year, int month, int day){
        this.rapport = contenu;
        this.dateRapport = new Date(year, month, day);
    }

}
