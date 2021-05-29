package com.projetjava;

import java.util.*;

public class Membre extends Personne{

    private MyDate dateDeNaissance, dateDerniereInscription;
    private String adresse;
    private Association association;
    private List<Arbre> listArbresVotes = new ArrayList<>();
    private int nbDefraiement;
    private final HashMap<Integer,Double> cotisations;
    private int id;
    private static int indexId = 0;

    public Membre(String nom, String prenom, Association assoc, int anneeNaissance, int moisNaissance, int jourNaissance, int anneeInscription, int moisInscription, int jourInscription, String adresseMembre){
        super(nom, prenom);
        this.association = assoc;
        this.dateDeNaissance = new MyDate(anneeNaissance, moisNaissance, jourNaissance);
        this.dateDerniereInscription = new MyDate(anneeInscription, moisInscription, jourInscription);
        this.adresse = adresseMembre;
        cotisations = new HashMap<>();
        this.association.addListeMembres(this);
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
    public void cotiser(int year) {
        if(cotisations.get(year) == null){
            association.cotisationRecette();
            cotisations.put(year,association.getPrixCotisation());
        }
    }

    public boolean hasCotiser(int year){
        return (cotisations.get(year) != null);
    }

    public int getNbDefraiement() {
        return nbDefraiement;
    }

    public int getId() {
        return id;
    }

    // todo exception pour la cohérence des dates
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
            if(association.demandeDefraiement(this,arbre)){
                nbDefraiement++;
            }
        }
        else{
            System.err.println("Vous n'avez pas eu d'autorisation pour visiter cet arbre, envoyez nous une demandeVisiteArbre");
        }
    }

    public HashMap<Integer, Double> getCotisations() {
        return cotisations;
    }

    public Association getAssociation() {
        return association;
    }

    @Override
    public String toString() {
        return "Membre " +
                "Nom=" + nom +
                "Prénom=" + prenom +
                "dateDeNaissance=" + dateDeNaissance +
                ", dateDerniereInscription=" + dateDerniereInscription +
                ", adresse='" + adresse + '\'' +
                ", association=" + association.getNom() +
                ", listArbresVotes=" + listArbresVotes +
                ", nbDefraiement=" + nbDefraiement +
                ", id=" + id;
    }
}
