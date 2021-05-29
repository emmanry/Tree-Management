package com.projetjava;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Municipalite {

    private String nom;
    private ServiceMairie serviceMairie;
    private ArrayList<Arbre> listeArbre;
    private HashMap<Integer, List<Arbre>> dicoArbresNominesParAn = new HashMap<Integer, List<Arbre>>();

    public Municipalite(String name, ServiceMairie serviceEspaceVert){
        this.listeArbre = new ArrayList<>();
        this.nom = name;
        this.serviceMairie = serviceEspaceVert;
    }

    public String getNom(){
        return this.nom;
    }

    public ServiceMairie getServiceMairie(){
        return this.serviceMairie;
    }

    /**
     * Renvoie un clone de la liste des arbres présents sur une municipalité
     * @return ArrayList<Arbre>
     */
    public ArrayList<Arbre> getListeArbre(){
        return (ArrayList<Arbre>) this.listeArbre.clone();
    }

    public HashMap<Integer, List<Arbre>> getDicoArbresNominesParAn(){
        return this.dicoArbresNominesParAn;
    }

    public void addArbre(Arbre arbre){
        listeArbre.add(arbre);
        serviceMairie.notifier(ActionArbre.PLANTATION,arbre);
    }

    public void rendreRemarquable(Arbre arbre, int jour, int mois, int annee){
        if(arbre.getRemarquable()){
            System.err.println("Attention ! L'arbre " + arbre.getIdArbre() + " est déjà remarquable");
        }
        else{
            arbre.setRemarquable(true);
            arbre.setDateRemarquable(jour, mois, annee);
        }
    }

    public void removeArbre(Arbre arbre){
        listeArbre.remove(arbre);
        serviceMairie.notifier(ActionArbre.ABATTAGE,arbre);
    }

    // todo utiliser dico
    public void removeArbreById(int id){
        Arbre arbre = listeArbre.stream().filter(a->(a.getIdArbre() == id)).findFirst().orElse(null);
        if(arbre != null){
            listeArbre.remove(arbre);
            serviceMairie.notifier(ActionArbre.ABATTAGE,arbre);
        }
    }
    //todo classification

    /**
     * Méthode appelé dans envoyerListeArbresNomines(int année) d'Association
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
