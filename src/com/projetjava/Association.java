package com.projetjava;

import com.projetjava.don.Demandeur;
import com.projetjava.don.Don;
import com.projetjava.don.Donateur;
import com.projetjava.exception.DateException;
import com.projetjava.finance.CompteBancaire;
import com.projetjava.finance.DefraiementVisite;
import com.projetjava.finance.Depense;
import com.projetjava.notification.Notifiable;

import java.util.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Association
 * @see com.projetjava.don.Demandeur
 * @see com.projetjava.don.Donateur
 * @see com.projetjava.notification.Notifiable
 */
public class Association implements Notifiable, Donateur, Demandeur {

    private final Municipalite municipalite;
    private ExerciceBudgetaire exerciceBudgetaire;
    private final ArrayList<Membre> listeMembres = new ArrayList<>();
    private final List<Classification> listeClassifications = new ArrayList<>();
    private final HashMap<Arbre, Membre> dicoVisitesEnAttente = new HashMap<>();
    private List<String> notifications = new ArrayList<>();
    private final CompteBancaire compteBancaire;
    private final List<Donateur> listeDonateurs;
    private final double prixCotisation;
    private final String nom;
    private final int nbDefraiementAutorise;
    private final double montantDefraiement;
    RapportActivite lastRapportActivite;

    /**
     *
     * @param nom
     * @param municipalite
     * @param soldeinitial
     * @param prixCotisation
     * @param nbDefraiementAutorise
     * @param montantDefraiement
     */
    public Association(String nom,Municipalite municipalite, double soldeinitial, double prixCotisation, int nbDefraiementAutorise, double montantDefraiement){
        this.municipalite = municipalite;
        compteBancaire = new CompteBancaire(soldeinitial);
        this.prixCotisation = prixCotisation;
        listeDonateurs = new ArrayList<>();
        this.nom = nom;
        this.nbDefraiementAutorise = nbDefraiementAutorise;
        this.montantDefraiement = montantDefraiement;

        Calendar cal = Calendar.getInstance();
        exerciceBudgetaire = new ExerciceBudgetaire(this,cal.get(Calendar.YEAR));
    }

    /**
     *
     * @return Liste des membres
     */
    public ArrayList<Membre> getListeMembres(){
        return this.listeMembres;
    }

    /**
     *
     * @return Liste des classifications
     */
    public List<Classification> getListeClassifications(){
        return this.listeClassifications;
    }

    /**
     * @return Map
     */
    public HashMap<Arbre, Membre> getDicoVisitesEnAttente(){
        return this.dicoVisitesEnAttente;
    }

    public Municipalite getMunicipalite(){
        return this.municipalite;
    }

    /**
     * Le premier membre ajouté à la listeMembres doit être un Président et
     * les suivants doivent être des Membre
     * @param membre
     * @throws RuntimeException
     */
    public void addListeMembres(Membre membre) throws RuntimeException {
        if(this.listeMembres.isEmpty()){
            if(membre instanceof President){
                this.listeMembres.add(membre);
            }
            else {
                throw new RuntimeException("Le premier membre inscrit doit être le président");
            }
        }
        else {
            if(membre instanceof President){
                throw new RuntimeException("L'association a déjà un président");
            }
            else if(membre != null){
                this.listeMembres.add(membre);
            }
        }
    }

    /**
     *
     * @param classification
     */
    public void addListeClassifications(Classification classification){
        this.listeClassifications.add(classification);
    }

    /**
     * || Dernière étape de la classification : Transmission de la ListeArbresNomines à la municipalité ||
     * @param annee
     */
    public void envoyerListeArbresNomines(int annee){
        Classification classification = new Classification(this, annee);
        this.addListeClassifications(classification);
        classification.nomination();
        municipalite.recevoirListeArbresNomines(classification.getAnnee(), classification.getListeArbresNomines());
    }

    /**
     * On vérifie que l'arbre est remarquable, qu'il n'a pas de visite de prévu et qu'il n'a pas dépassé le nombre de visites autorisées
     * @param arbre
     * @param membre
     * @return boolean si la demande est autorisée ou non
     */
    public boolean verificationVisite(Arbre arbre, Membre membre) {
        return (!(this.dicoVisitesEnAttente.containsKey(arbre)) && arbre.getRemarquable() && (membre.getNbDefraiement() < this.nbDefraiementAutorise));
    }

    /**
     *
     * @param arbre
     * @param membre
     */
    public void addVisitesEnAttente(Arbre arbre, Membre membre){
        this.dicoVisitesEnAttente.put(arbre, membre);
    }

    /**
     * @return l'historique des ArbreVisite de la plus ancienne visite à la plus récente
     */
    public String getHistoriqueArbresVisites() throws DateException {
        StringBuilder sb = new StringBuilder();
        String s;
        sb.append("Enumération des arbres remarquables par ancienneté de leur dernière visite : \n");

        // Faire une copie profonde du dicoArbresVisites
        HashMap<ArbreVisite, MyDate> dicoArbresVisitesCopie = new HashMap<>();
        for (ArbreVisite arbreVisite : ArbreVisite.getDicoArbresVisites().keySet()){
            dicoArbresVisitesCopie.put(arbreVisite, ArbreVisite.getDicoArbresVisites().get(arbreVisite));
        }

        // Tri des arbres du dicoArbresVisites pour les classer de la plus ancienne à la plus récente visite
        while(!dicoArbresVisitesCopie.isEmpty()){
            Calendar dateAujourdhui = Calendar.getInstance();
            MyDate dateMin = new MyDate(dateAujourdhui.get(Calendar.YEAR), dateAujourdhui.get(Calendar.MONTH), dateAujourdhui.get(Calendar.DATE));
            ArbreVisite arbreVisiteMin = null;
            for (ArbreVisite arbreVisite : dicoArbresVisitesCopie.keySet()) {
                if(arbreVisite.getDateDerniereVisite().before(dateMin)){
                    dateMin = arbreVisite.getDateDerniereVisite();
                    arbreVisiteMin = arbreVisite;
                }
            }

            // On considère le cas où le Membre est désinscrit
            if(arbreVisiteMin.getMembreVisite() == null){
                sb.append(arbreVisiteMin.getDateDerniereVisite() + " : " + " Arbre n° " + arbreVisiteMin.getIdArbre()
                        + " visité par une personne ayant quitté l'association"+ "\n");
            }
            else {
                sb.append(arbreVisiteMin.getDateDerniereVisite() + " : " + " Arbre n° " + arbreVisiteMin.getIdArbre() + " visité par " +
                        arbreVisiteMin.getMembreVisite().getNom() + " " + arbreVisiteMin.getMembreVisite().getPrenom() + "\n");
            }

            dicoArbresVisitesCopie.remove(arbreVisiteMin);

        }
        s = sb.toString();
        return s;
    }

    /**
     * Permet directement aux Associations de s'incrire aux informations sur les arbres de la commune
     */
    public void inscriptionNotification(){
        this.municipalite.getServiceMairie().addNotifiable(this);
    }

    @Override
    public void notifier(ActionArbre action, Arbre arbre) {
        this.notifications.add(action.toString() + arbre);
    }

    /**
     * Retourne les notifications et les supprime
     * @return liste des notifications
     */
    public List<String> alertNotifications(){
        List<String> tmp = this.notifications;
        this.notifications = null;
        return tmp;
    }

    @Override
    public void receiveDemandeDon(String message, Demandeur demandeur, RapportActivite rapport) {
        if(compteBancaire.retirer(50)){
            rapport.getAssociation().receiveDon(new Don(50,this));
        }
        else{
            System.err.println("Vous n'avez pas assez sur votre compte bancaire pour effectuer ce don : " + message);
        }
    }

    @Override
    public String getNom() {
        return nom;
    }

    @Override
    public void demandeDon(String message){
        for (Donateur donateur : listeDonateurs) {
            donateur.receiveDemandeDon(message,this, lastRapportActivite);
        }
    }

    @Override
    public void addDonateur(Donateur donateur) {
        this.listeDonateurs.add(donateur);
    }

    @Override
    public void removeDonateur(Donateur donateur) {
        this.listeDonateurs.remove(donateur);
    }

    @Override
    public ArrayList<Donateur> getDonateur() {
        return (ArrayList<Donateur>) this.listeDonateurs;
    }

    @Override
    public void receiveDon(Don don) {
        compteBancaire.depot(don.getMontant());
        exerciceBudgetaire.addDon(don);
    }

    /**
     *
     * @return Rapport d'activités
     */
    public RapportActivite getLastRapportActivite() {
        return lastRapportActivite;
    }

    /**
     *
     * @param lastRapportActivite
     */
    public void setLastRapportActivite(RapportActivite lastRapportActivite) {
        this.lastRapportActivite = lastRapportActivite;
    }

    /**
     *
     * @param depense
     * @return si le paiement est réussi ou non
     */
    public boolean paiement(Depense depense){
        if(compteBancaire.retirer(depense.getMontant())){
            exerciceBudgetaire.addDepense(depense);
            return true;
        }else {
            return false;
        }
    }

    /**
     * Défraiement d'une visite
     * @param membre
     * @param a
     * @return
     */
    public boolean defraiement(Membre membre, Arbre a) {
        if(compteBancaire.retirer(montantDefraiement)) {
            exerciceBudgetaire.addDepense(new DefraiementVisite(montantDefraiement,membre.getNom(), a.getIdArbre()));
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Suppression des infos personnelles du membre supprimé, en conservant ses activités
     */
    public void gestionSuppressionMembre(Membre membre){
        // On supprime les visites en attente du membre
        if(this.dicoVisitesEnAttente.values().contains(membre)){
            for (Arbre arbre : dicoVisitesEnAttente.keySet()) {
                if(dicoVisitesEnAttente.get(arbre) == membre){
                    dicoVisitesEnAttente.remove(arbre);
                }
            }
        }
        // On supprime le membre des comptes-rendus qu'il a édité
        for (ArbreVisite arbreVisite : ArbreVisite.getDicoArbresVisites().keySet()) {
            for (CompteRendu cr : arbreVisite.getListeCompteRendus()) {
                if(cr.getMembreRapport() == membre){
                    cr.removeMembreRapport();
                }
            }
        }
    }

    /**
     * Désinscrit un membre
     * @param membre
     */
    public void removeMembre(Membre membre){
        listeMembres.removeIf(membre1 -> membre.getId() == membre1.getId());
        gestionSuppressionMembre(membre);
    }

    /**
     * Déinscrit un membre en fonction de son id
     * @param id
     */
    public void removeMembreById(int id){
        listeMembres.removeIf(membre -> id == membre.getId());
        for (Membre membre : this.listeMembres) {
            if(membre.getId() == id){
                gestionSuppressionMembre(membre);
            }
        }
    }

    public void setExerciceBudgetaire(ExerciceBudgetaire exerciceBudgetaire) {
        this.exerciceBudgetaire = exerciceBudgetaire;
    }

    /**
     * Prend en compte une cotisation
     */
    public void cotisationRecette(){
        compteBancaire.depot(prixCotisation);
    }

    /**
     *
     * @return solde actuel du CB
     */
    public double getSolde(){
        return compteBancaire.getSolde();
    }

    /**
     *
     * @return Exercice Budgétaire
     */
    public ExerciceBudgetaire getExerciceBudgetaire() {
        return exerciceBudgetaire;
    }

    /**
     *
     * @return prix de la cotisation
     */
    public double getPrixCotisation() {
        return prixCotisation;
    }
}
