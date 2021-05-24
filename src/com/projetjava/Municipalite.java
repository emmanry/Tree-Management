package com.projetjava;

import java.util.ArrayList;

public class Municipalite {

    private String nom;
    private ServiceMairie serviceMairie;
    private ArrayList<Arbre> listeArbre;

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
     * Renvoi un clone de la liste des arbres présent sur une municipalité
     * @return ArrayList<Arbre>
     */
    public ArrayList<Arbre> getListeArbre(){
        return (ArrayList<Arbre>) this.listeArbre.clone();
    }


    public void addArbre(Arbre arbre){
        listeArbre.add(arbre);
        serviceMairie.notifier(ActionArbre.PLANTATION,arbre);
    }

    public void removeArbre(Arbre arbre){
        listeArbre.remove(arbre);
        serviceMairie.notifier(ActionArbre.ABATTAGE,arbre);
    }

    public void removeArbreById(int id){
        Arbre arbre = listeArbre.stream().filter(a->(a.getIdArbre() == id)).findFirst().orElse(null);
        if(arbre != null){
            listeArbre.remove(arbre);
            serviceMairie.notifier(ActionArbre.ABATTAGE,arbre);
        }
    }
    //todo classification

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

    public static void main(String[] args){
        ServiceMairie serviceParis = new ServiceMairie("Service des espaces verts");
        Municipalite paris = new Municipalite("Paris", serviceParis);
        System.out.println(paris.toString());

    }

}
