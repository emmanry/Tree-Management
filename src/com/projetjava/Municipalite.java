package com.projetjava;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Municipalité d'une ville
 */
public class Municipalite {

    private final String nom;
    private final ServiceMairie serviceMairie;
    private final ArrayList<Arbre> listeArbre;
    private final HashMap<Integer, List<Arbre>> dicoArbresNominesParAn = new HashMap<Integer, List<Arbre>>();

    public Municipalite(String name, ServiceMairie serviceEspaceVert, ArrayList<Arbre> arbres){
        this.nom = name;
        this.serviceMairie = serviceEspaceVert;
        this.listeArbre = arbres;
    }

    public String getNom(){
        return this.nom;
    }

    public ServiceMairie getServiceMairie(){
        return this.serviceMairie;
    }

    /**
     * Renvoie un clone de la liste des arbres présents sur une municipalité
     * @return ArrayList d'Arbre
     */
    public ArrayList<Arbre> getListeArbre(){
        return (ArrayList<Arbre>) this.listeArbre.clone();
    }

    public HashMap<Integer, List<Arbre>> getDicoArbresNominesParAn(){
        return this.dicoArbresNominesParAn;
    }

    public void addArbre(Arbre arbre){
        this.listeArbre.add(arbre);
        this.serviceMairie.notifier(ActionArbre.PLANTATION,arbre);
    }

    public void rendreRemarquable(Arbre arbre, int jour, int mois, int annee){
        if(!arbre.getRemarquable()) {
            arbre.setRemarquable(true);
            arbre.setDateRemarquable(jour, mois, annee);
            this.serviceMairie.notifier(ActionArbre.CLASSIFICATION, arbre);
        }
    }

    public void removeArbre(Arbre arbre){
        listeArbre.remove(arbre);
        serviceMairie.notifier(ActionArbre.ABATTAGE,arbre);
    }

    public void removeArbreById(int id){
        Arbre arbre = Arbre.getDicoArbre().get(id);
        if(arbre != null){
            listeArbre.remove(arbre);
            serviceMairie.notifier(ActionArbre.ABATTAGE,arbre);
        }
    }

    /**
     * Méthode appelée dans envoyerListeArbresNomines(int annee) d'Association
     * On remplit le dicoArbresNominesParAn afin d'avoir un historique des arbres nominés par an
     */
    public void recevoirListeArbresNomines(int annee, List<Arbre> listeArbresNomines){
        dicoArbresNominesParAn.put(annee, listeArbresNomines);
    }

    public String afficheListeArbre(){
        StringBuilder sb = new StringBuilder();
        for(Arbre arbre : this.listeArbre){
            sb.append(arbre.toString());
        }
        return sb.toString();
    }

    public String toString(){
        return "{ La municipalité de " + this.nom + " :\n" +
               "[ contient " + this.listeArbre.size() + " arbres ]\n" +
               "[ a le service \"" + this.serviceMairie.getNom() + "\" ]\n}"
                ;
    }
}
