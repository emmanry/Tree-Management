package com.projetjava;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Membre extends Personne{

    private Date dateDeNaissance, dateDerniereInscription;
    private String adresse;
    private Association association;
    private List<Arbre> listArbresVotes = new ArrayList<>();
    private boolean cotise;
    private int nbDefraiement;
    private int id;
    private static int indexId = 0;

    public Membre(String lastname, String firstname, Association assoc, int yearNaissance, int monthNaissance, int dayNaissance, int yearInscription, int monthInscription, int dayInscription, String adresseMembre){
        super(lastname, firstname);
        this.association = assoc;
        this.dateDeNaissance = new Date(yearNaissance-1900, monthNaissance, dayNaissance);
        this.dateDerniereInscription = new Date(yearInscription-1900, monthInscription, dayInscription);
        this.adresse = adresseMembre;
        this.cotise = false;
        this.nbDefraiement = 0;
        this.association.setListeMembres(this);
        id = indexId;
        indexId++;
    }

    public Date getDateDeNaissance(){
        return this.dateDeNaissance;
    }

    public Date getDateDerniereInscription(){
        return this.dateDerniereInscription;
    }

    public String getAdresse(){
        return this.adresse;
    }

    public List<Arbre> getListArbresVotes(){
        return this.listArbresVotes;
    }

    public void vote(Arbre...arbre){

        for (Arbre a : arbre) {
            if (!this.listArbresVotes.contains(a)){
                this.listArbresVotes.add(a);
            }
            else{
                System.err.println("Vous avez déjà voté pour " + a.toString());
            }
        }
        if(this.listArbresVotes.size() > 5){
            this.listArbresVotes = this.listArbresVotes.subList(this.listArbresVotes.size() - 5, this.listArbresVotes.size());
        }
    }

    public void cotiser() {
        if(!cotise){
            cotise = true;
            association.cotisationRecette();
        }
    }

    public boolean hasCotiser(){
        return cotise;
    }

    public void defraiement(){
        if(association.demandeDefraiement(this)){
            nbDefraiement++;
        }
    }

    public int getNbDefraiement() {
        return nbDefraiement;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Membre{" +
                "Nom=" + nom +
                "Prénom=" + prenom +
                "dateDeNaissance=" + dateDeNaissance +
                ", dateDerniereInscription=" + dateDerniereInscription +
                ", adresse='" + adresse + '\'' +
                ", association=" + association +
                ", listArbresVotes=" + listArbresVotes +
                ", cotise=" + cotise +
                ", nbDefraiement=" + nbDefraiement +
                ", id=" + id +
                '}';
    }

    public static void main(String[] args){
        Association assoc = new Association("nom");
        Membre membre = new Membre("azerty", "emma", assoc, 1999, 1, 22, 2018, 2, 22, "add");
        ServiceMairie serviceParis = new ServiceMairie("Service des espaces verts");
        Municipalite paris = new Municipalite("Paris", serviceParis);
        Arbre.createArbre("C:\\Users\\emman\\OneDrive\\Bureau\\S6\\Java\\Projet\\les-arbres.csv", paris);

        Arbre a = Arbre.getDicoArbre().get(214468);
        Arbre b = Arbre.getDicoArbre().get(298184);

        membre.vote(a, a, a, a, a, b, b, a, a, a, a, a, b, b, b, b);
        membre.getListArbresVotes();

    }

}
