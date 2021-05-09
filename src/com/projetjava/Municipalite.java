package com.projetjava;

import java.util.ArrayList;

public class Municipalite {

    private String nom;
    private ServiceMairie serviceMairie;
    private ArrayList<Arbre> listeArbre;

    public Municipalite(String name, ServiceMairie serviceEspaceVert){
        this.listeArbre = new ArrayList<Arbre>();
        this.nom = name;
        this.serviceMairie = serviceEspaceVert;
    }

    public String getNom(){
        return this.nom;
    }

    public ServiceMairie getServiceMairie(){
        return this.serviceMairie;
    }

    public ArrayList<Arbre> getListeArbre(){
        return this.listeArbre;
    }

    public void addArbre(Arbre arbre){
        this.listeArbre.add(arbre);
    }

    public String afficheListeArbre(){
        StringBuilder sb = new StringBuilder();
        for(Arbre arbre : this.listeArbre){
            sb.append(arbre.toString());
        }
        String s = sb.toString();
        return s;
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
