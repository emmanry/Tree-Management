package com.projetjava;

import com.projetjava.exception.DateException;

/**
 * CompteRendu : construit après chaque visite
 */

public class CompteRendu {

    private String rapport;
    private MyDate dateRapport;
    private Association associationRapport;
    private Membre membreRapport;

    public CompteRendu(String contenu, int annee, int mois, int jour, Association association, Membre membre) throws DateException {
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

    public void removeMembreRapport(){
        this.membreRapport = null;
    }

    /**
     * Méthode toString
     * @return String
     */
    public String toString(){
        String membre;
        if(this.membreRapport == null){
            membre = "Personne ayant quitté l'association";
        }
        else {
            membre = this.membreRapport.getNom() + " " + this.membreRapport.getPrenom();
        }
        return "\n" +
                "{ Compte Rendu..............."                             + "\n" +
                "[ Contenu du Rapport........."   + this.rapport            + " ] \n" +
                "[ Date du Rapport............"   + this.dateRapport        + " ] \n" +
                "[ Association................"   + this.associationRapport + " ] \n" +
                "[ Membre....................."   + membre                  + " ) ] \n}"
                ;
    }

}
