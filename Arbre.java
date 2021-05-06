package com.projetjava;

import java.awt.Point;

import java.io.FileReader;

public class Arbre {

    private String genre, espece, nomFrancais, stadeDeveloppement;
    private int circonferenceEnCm, hauteurEnM, adresseAcces;
    private boolean remarquable;
    private Point coordonnees;

    public Arbre(String genreA, String especeA, String nomFr, String stadeDev, int circonference, int hauteur, int idAdresse, int x, int y, boolean remarquableA){
        this.genre = genreA;
        this.espece = especeA;
        this.nomFrancais = nomFr;
        this.stadeDeveloppement = stadeDev;
        this.circonferenceEnCm = circonference;
        this.hauteurEnM = hauteur;
        this.adresseAcces = idAdresse;
        this.remarquable = remarquableA;
        this.coordonnees = new Point(x,y);
    }

}
