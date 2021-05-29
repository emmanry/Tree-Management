package com.projetjava;

import com.projetjava.don.Demandeur;
import com.projetjava.don.Don;
import com.projetjava.don.Donateur;
import com.projetjava.finance.CompteBancaire;
import com.projetjava.finance.DefraiementVisite;
import com.projetjava.finance.Depense;
import com.projetjava.notification.Notifiable;

import java.util.*;
import java.util.ArrayList;
import java.util.List;

public class Association implements Notifiable, Donateur, Demandeur {

    private Municipalite municipalite;
    private ServiceMairie serviceMairie;
    private ExerciceBudgetaire exerciceBudgetaire;
    private ArrayList<Membre> listeMembres = new ArrayList<>();
    private List<Classification> listeClassifications = new ArrayList<>();
    private HashMap<Arbre, Membre> dicoVisitesEnAttente = new HashMap<>();
    private List<String> notifications = new ArrayList<>();
    private CompteBancaire compteBancaire;
    private List<Donateur> donateurs;
    private double prixCotisation;
    private String nom;
    private int nbDefraiementAutorise;
    private double montantDefraiement;
    RapportActivite lastRapportActivite;

    public Association(String nom,Municipalite municipalite){
        this.municipalite = municipalite;
        compteBancaire = new CompteBancaire(1000.f);
        prixCotisation = 50.f;
        donateurs = new ArrayList<>();
        this.nom = nom;
        nbDefraiementAutorise = 5;
        montantDefraiement= 30;
        exerciceBudgetaire = new ExerciceBudgetaire(this);
    }

    public ArrayList<Membre> getListeMembres(){
        return this.listeMembres;
    }

    public List<Classification> getListeClassifications(){
        return this.listeClassifications;
    }

    public HashMap<Arbre, Membre> getDicoVisitesEnAttente(){
        return this.dicoVisitesEnAttente;
    }

    /**
     * Le premier membre ajouté à la listeMembres doit être un Président et
     * les suivants doivent êtres des Membre
     */
    public void addListeMembres(Membre membre){
        if(this.listeMembres.isEmpty()){
            if(membre instanceof President){
                this.listeMembres.add(membre);
            }
            else {
                System.err.println("Le premier membre doit être le président");
            }
        }
        else {
            if(membre instanceof President){
                System.err.println("L'association a déjà un président");
            }
            else if(membre instanceof Membre){
                this.listeMembres.add(membre);
            }
        }
    }

    public void addListeClassifications(Classification classification){
        this.listeClassifications.add(classification);
    }

    /**
     * || Deuxième étape de la classification : Transmission de la ListeArbresNomines à la municipalité ||
     */
    public void envoyerListeArbresNomines(int annee){
        Classification classification = new Classification(this, annee);
        this.addListeClassifications(classification);
        classification.nomination();
        municipalite.recevoirListeArbresNomines(classification.getAnnee(), classification.getListeArbresNomines());
    }

    /**
     * On vérifie que l'arbre est remarquable et qu'il n'a pas de visite de prévu
     * @return boolean si la demande est autorisée ou non
     */
    public boolean verificationVisite(Arbre arbre){
        return (!(this.dicoVisitesEnAttente.containsKey(arbre)) && arbre.getRemarquable());
    }

    public void addVisitesEnAttente(Arbre arbre, Membre membre){
        this.dicoVisitesEnAttente.put(arbre, membre);
    }

    /**
     * @return l'historique des ArbreVisite de la plus ancienne visite à la plus récente
     */
    public String getHistoriqueArbresVisites(){
        StringBuilder sb = new StringBuilder();
        String s;
        sb.append("Enumération des arbres remarquables par ancienneté de leur dernière visite : \n");

        // Faire une copie profonde du dicoArbresVisites
        HashMap<ArbreVisite, MyDate> dicoArbresVisitesCopie = new HashMap<>();
        for (ArbreVisite arbreVisite : ArbreVisite.getDicoArbresVisites().keySet()){
            dicoArbresVisitesCopie.put(arbreVisite, ArbreVisite.getDicoArbresVisites().get(arbreVisite));
        }

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

            sb.append(arbreVisiteMin.getDateDerniereVisite() + " : " + " Arbre n° " + arbreVisiteMin.getIdArbre() + " visité par " +
                    arbreVisiteMin.getMembreVisite().getNom() + " " + arbreVisiteMin.getMembreVisite().getPrenom() + "\n");
            dicoArbresVisitesCopie.remove(arbreVisiteMin);

        }
        s = sb.toString();
        return s;
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
