package com.projetjava;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Membre extends Personne{

    private MyDate dateDeNaissance, dateDerniereInscription;
    private String adresse;
    private Association association;
    private List<Arbre> listArbresVotes = new ArrayList<>();
    private boolean cotise;
    private int nbDefraiement;
    private int id;
    private static int indexId = 0;

    public Membre(String nom, String prenom, Association assoc, int anneeNaissance, int moisNaissance, int jourNaissance, int anneeInscription, int moisInscription, int jourInscription, String adresseMembre){
        super(nom, prenom);
        this.association = assoc;
        this.dateDeNaissance = new MyDate(anneeNaissance, moisNaissance, jourNaissance);
        this.dateDerniereInscription = new MyDate(anneeInscription, moisInscription, jourInscription);
        this.adresse = adresseMembre;

        this.association.addListeMembres(this);
        this.cotise = false;
        this.nbDefraiement = 0;
        id = indexId;
        indexId++;
    }

    public MyDate getDateDeNaissance(){
        return this.dateDeNaissance;
    }

    public MyDate getDateDerniereInscription(){
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

    public void demandeVisiteArbre(Arbre arbre){
        if(!this.association.verificationVisite(arbre)){
            System.err.println("La visite n'est pas autorisée");
        }
        else{
            this.association.addVisitesEnAttente(arbre, this);
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

    // todo exception pour la cohérence des dates
    // todo défraiement de la visite
    public void visiteArbre(Arbre arbre, int annee, int mois, int jour, String contenu){
        if(this.association.getDicoVisitesEnAttente().get(arbre) == this){
            this.association.getDicoVisitesEnAttente().remove(arbre);
            if(Arbre.getDicoArbre().get(arbre.getIdArbre()) instanceof ArbreVisite){
                ArbreVisite arbreVisite = (ArbreVisite) Arbre.getDicoArbre().get(arbre.getIdArbre());
                MyDate dateDerniereVisite = new MyDate(annee, mois, jour);
                ArbreVisite.getDicoArbresVisites().put(arbreVisite, dateDerniereVisite);
                arbreVisite.ecrireCompteRendu(contenu, annee, mois, jour, this.association, this);
            }
            else{
                ArbreVisite arbreVisite = new ArbreVisite(arbre.getIdArbre(), arbre.getAdresseAcces(), arbre.getNomFrancais(), arbre.getGenre(), arbre.getEspece(),
                        arbre.getCirconferenceEnCm(), arbre.getHauteurEnM(), arbre.getStadeDeveloppement(), true, arbre.getDateRemarquable(),
                        arbre.getCoordonnees().getX(), arbre.getCoordonnees().getY(), contenu, annee, mois, jour, this.association, this);
            }
        }
        else{
            System.err.println("Vous n'avez pas eu d'autorisation pour visiter cet arbre, envoyez nous une demandeVisiteArbre");
        }
    }

}
