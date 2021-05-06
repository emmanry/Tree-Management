package com.projetjava;

import java.util.Date;

public class ArbreVisite {

    private Arbre arbreVisite;
    private CompteRendu rapport;
    private Date dateVisite;

    public ArbreVisite(Arbre arbre, CompteRendu cr, int year, int month, int day){
        this.arbreVisite = arbre;
        this.rapport = cr;
        this.dateVisite = new Date(year, month, day);
    }

}
