package com.projetjava;

import com.projetjava.don.Demandeur;
import com.projetjava.don.Don;
import com.projetjava.don.Donateur;
import com.projetjava.finance.CompteBancaire;
import com.projetjava.finance.DefraiementVisite;
import com.projetjava.finance.Depense;
import com.projetjava.notification.Notifiable;

import java.util.ArrayList;
import java.util.List;

public class Association implements Notifiable, Donateur, Demandeur {

    private ServiceMairie serviceMairie;
    private ExerciceBudgetaire exerciceBudgetaire;
    private ArrayList<Membre> listeMembres = new ArrayList<>();
    private List<String> notifications = new ArrayList<>();
    private CompteBancaire compteBancaire;
    private List<Donateur> donateurs;
    private double prixCotisation;
    private String nom;
    private int nbDefraiementAutorise;
    private double montantDefraiement;
    RapportActivite lastRapportActivite;

    public Association(String nom){
        compteBancaire = new CompteBancaire(1000.f);
        prixCotisation = 50.f;
        donateurs = new ArrayList<>();
        this.nom = nom;
        nbDefraiementAutorise = 5;
        montantDefraiement= 30;
        exerciceBudgetaire = new ExerciceBudgetaire(this);
    }

    @Override
    public void notifier(ActionArbre action, Arbre arbre) {
        notifications.add(action.toString() + arbre);
        //todo retirer des listes de classification
        switch (action){
            case ABATTAGE:
                break;
            case CLASSIFICATION:
                break;
        }
    }

    /**
     *
     * @return liste des notifications
     */
    public List<String> alertNotifications(){
        List<String> tmp = notifications;
        notifications = null;
        return tmp;
    }

    @Override
    public void receiveDemandeDon(String message,Demandeur demandeur, RapportActivite rapport) {
        //todo autre comportement ?
        if(compteBancaire.retirer(50.f)){
            rapport.getAssociation().receiveDon(new Don(50.f,this));
        }
    }

    @Override
    public String getNom() {
        return null;
    }

    @Override
    public void demandeDon(String message){
        for (Donateur donateur:donateurs) {
            donateur.receiveDemandeDon(message,this,lastRapportActivite);
        }
    }

    @Override
    public void addDonateur(Donateur donateur) {
        donateurs.add(donateur);
    }

    @Override
    public void removeDonateur(Donateur donateur) {
        donateurs.remove(donateur);
    }

    @Override
    public void receiveDon(Don don) {
        compteBancaire.depot(don.getMontant());
        exerciceBudgetaire.addDon(don);
    }

    public RapportActivite getLastRapportActivite() {
        return lastRapportActivite;
    }

    public void setLastRapportActivite(RapportActivite lastRapportActivite) {
        this.lastRapportActivite = lastRapportActivite;
    }

    public boolean paiement(Depense depense){
        if(compteBancaire.retirer(depense.getMontant())){
            exerciceBudgetaire.addDepense(depense);
            return true;
        }else {
            return false;
        }
    }

    public boolean demandeDefraiement(Membre membre) {
        if(membre.getNbDefraiement() < nbDefraiementAutorise){
            if(compteBancaire.retirer(montantDefraiement)){
                exerciceBudgetaire.addDepense(new DefraiementVisite(montantDefraiement));
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    public void deinscription(Membre membre){
        listeMembres.removeIf(membre1 -> membre.getId() == membre1.getId());
        //todo visite
    }
    public void setExerciceBudgetaire(ExerciceBudgetaire exerciceBudgetaire) {
        this.exerciceBudgetaire = exerciceBudgetaire;
    }
    public ArrayList<Membre> getListeMembres(){
        return listeMembres;
    }

    public void setListeMembres(Membre membre){
        this.listeMembres.add(membre);
    }

    public void cotisationRecette(){
        compteBancaire.depot(prixCotisation);
    }

    //todo temporaire
    public CompteBancaire getCompteBancaire() {
        return compteBancaire;
    }

    public ExerciceBudgetaire getExerciceBudgetaire() {
        return exerciceBudgetaire;
    }
    public double getPrixCotisation() {
        return prixCotisation;
    }
}
/*(Service Mairie,ExerciceBudgetaire,Listes de classification (1 par année),liste des membres
        ,président,listes des ArbresVisité (et arbre prévu),liste rapport d'activité,liste des donateurs,montant du compte bancaire)*/