package com.projetjava;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Membre extends Personne{

    private Date dateDeNaissance, dateDerniereInscription;
    private String adresse;
    private Association association;
    private List<Arbre> listArbresVotes = new ArrayList<>();

    public Membre(String lastname, String firstname, Association assoc, int yearNaissance, int monthNaissance, int dayNaissance, int yearInscription, int monthInscription, int dayInscription, String adresseMembre){
        super(lastname, firstname);
        this.association = assoc;
        this.dateDeNaissance = new Date(yearNaissance-1900, monthNaissance, dayNaissance);
        this.dateDerniereInscription = new Date(yearInscription-1900, monthInscription, dayInscription);
        this.adresse = adresseMembre;

        this.association.addListeMembres(this);
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

    public void demandeVisiteArbre(Arbre arbre){
        if(!this.association.verificationVisite(arbre)){
            System.err.println("La visite n'est pas autorisée");
        }
        else{
            this.association.addVisitesEnAttente(arbre, this);
        }
    }

    // todo exception pour la cohérence des dates
    // todo défraiement de la visite
    public void visiteArbre(Arbre arbre, int annee, int mois, int jour, String contenu){
        if(this.association.getDicoVisitesEnAttente().get(arbre) == this){
            this.association.getDicoVisitesEnAttente().remove(arbre);
            if(ArbreVisite.getDicoArbresVisites().containsKey(arbre)){
                ArbreVisite.getDicoArbresVisites().put((ArbreVisite) arbre, ((ArbreVisite) arbre).getListeCompteRendus().get(((ArbreVisite) arbre).getListeCompteRendus().size()).getDateRapport());
            }
            else{
                ArbreVisite arbreVisite = new ArbreVisite(arbre.getIdArbre(), arbre.getAdresseAcces(), arbre.getNomFrancais(), arbre.getGenre(), arbre.getEspece(),
                        arbre.getCirconferenceEnCm(), arbre.getHauteurEnM(), arbre.getStadeDeveloppement(), arbre.getRemarquable(), arbre.getDateRemarquable(),
                        arbre.getCoordonnees().getX(), arbre.getCoordonnees().getY(), contenu, annee, mois, jour, this.association, this);
            }
        }
    }

}
