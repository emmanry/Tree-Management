package com.projetjava;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ArbreVisite extends Arbre{

    private List<CompteRendu> listeCompteRendus = new ArrayList<>();
    private static HashMap<ArbreVisite, Date> dicoArbresVisites = new HashMap<>();

    // todo un arbre visité doit etre remarquable
    public ArbreVisite(int idBase, String idAdresse, String nomFr, String genreA, String especeA, double circonference, double hauteur, String stadeDev, boolean remarquableA, Date dateRemarquable, double x, double y, String contenu, int annee, int mois, int jour, Association association, Membre membre){
        super( idBase, idAdresse, nomFr, genreA, especeA, circonference, hauteur, stadeDev, remarquableA, dateRemarquable, x, y);
        this.ecrireCompteRendu(contenu, annee, mois, jour, association, membre);
        dicoArbresVisites.put(this, this.listeCompteRendus.get(0).getDateRapport());
    }

    public List<CompteRendu> getListeCompteRendus(){
        return this.listeCompteRendus;
    }

    public static HashMap<ArbreVisite, Date> getDicoArbresVisites(){
        return dicoArbresVisites;
    }

    public void ecrireCompteRendu(String contenu, int annee, int mois, int jour, Association association, Membre membre){
        CompteRendu compteRendu = new CompteRendu(contenu, annee, mois, jour, association, membre);
        this.listeCompteRendus.add(compteRendu);
    }

}
