package com.projetjava;

import com.projetjava.exception.DateException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Un ArbreVisite se forme suite à une visite
 * @see Arbre
 * @see CompteRendu
 */

public class ArbreVisite extends Arbre{

    /**
     * Liste des comptes-rendus d'un ArbreVisite
     */
    private List<CompteRendu> listeCompteRendus = new ArrayList<>();
    /**
     * Chaque ArbreVisite est associé à sa date de dernière visite qui est la date du dernier compte-rendu
     */
    private static HashMap<ArbreVisite, MyDate> dicoArbresVisites = new HashMap<>();

    public ArbreVisite(int idBase, String idAdresse, String nomFr, String genreA, String especeA, double circonference, double hauteur, String stadeDev, boolean remarquableA, MyDate dateRemarquable, double x, double y, String contenu, int annee, int mois, int jour, Association association, Membre membre) throws DateException {
        super( idBase, idAdresse, nomFr, genreA, especeA, circonference, hauteur, stadeDev, remarquableA, dateRemarquable, x, y);
        this.ecrireCompteRendu(contenu, annee, mois, jour, association, membre);
        dicoArbresVisites.put(this, this.listeCompteRendus.get(0).getDateRapport());
        Arbre.getDicoArbre().put(this.getIdArbre(), this);
    }

    public List<CompteRendu> getListeCompteRendus(){
        return this.listeCompteRendus;
    }

    public Membre getMembreVisite(){
        return this.listeCompteRendus.get(this.listeCompteRendus.size() - 1).getMembreRapport();
    }

    public static HashMap<ArbreVisite, MyDate> getDicoArbresVisites(){
        return dicoArbresVisites;
    }

    public MyDate getDateDerniereVisite(){
        return this.listeCompteRendus.get(this.listeCompteRendus.size() - 1).getDateRapport();
    }

    public void ecrireCompteRendu(String contenu, int annee, int mois, int jour, Association association, Membre membre) throws DateException {
        CompteRendu compteRendu = new CompteRendu(contenu, annee, mois, jour, association, membre);
        this.listeCompteRendus.add(compteRendu);
    }

    /**
     * Redéfinition du toString d'Arbre
     * @return String
     */
    @Override
    public String toString(){
        StringBuilder dateConnue = new StringBuilder();
        if (this.getRemarquable()) {
            dateConnue.append("[ Date remarquable...........");
            if (this.getDateRemarquable() == null) {
                dateConnue.append("Pas connue");
            } else {
                dateConnue.append(this.getDateRemarquable().toString());
            }
            dateConnue.append(" ] \n");
        }
        return "\n" +
                "{ Arbre Visité d'ID unique..." + this.getIdArbre() + "\n" +
                "[ ID d'emplacement..........." + this.getAdresseAcces() + " ] \n" +
                "[ Nom en français............" + this.getNomFrancais() + " ] \n" +
                "[ Genre......................" + this.getGenre() + " ] \n" +
                "[ Espèce....................." + this.getEspece() + " ] \n" +
                "[ Circonférence en cm........" + this.getCirconferenceEnCm() + " ] \n" +
                "[ Hauteur en m..............." + this.getHauteurEnM() + " ] \n" +
                "[ Stade de développement....." + this.getStadeDeveloppement() + " ] \n" +
                "[ Remarquable................" + (this.getRemarquable() ? "Oui" : "Non") + " ] \n" +
                dateConnue.toString() +
                "[ Coordonnées................( " + this.getCoordonnees().getX() + ", " + this.getCoordonnees().getY() +  " ] \n" +
                "[ Nombre de comptes-rendus....." + this.listeCompteRendus.size() + " ] \n" +
                "[ Date de dernière visite......" + this.getDateDerniereVisite().toString() + " ) ] \n}"
                ;
    }

}
