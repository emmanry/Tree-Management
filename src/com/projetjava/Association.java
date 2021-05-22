package com.projetjava;

import com.projetjava.notification.Notifiable;

import java.util.ArrayList;
import java.util.List;

public class Association implements Notifiable {

    private Municipalite municipalite;
    private ServiceMairie serviceMairie;
    private ExerciceBudgetaire exerciceBudgetaire;
    private ArrayList<Membre> listeMembres = new ArrayList<>();
    private List<Classification> listeClassifications = new ArrayList<>();

    public Association(){

    }

    public ArrayList<Membre> getListeMembres(){
        return this.listeMembres;
    }

    public List<Classification> getListeClassifications(){
        return this.listeClassifications;
    }

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
            if(membre instanceof Membre){
                this.listeMembres.add(membre);
            }
            else if(membre instanceof President){
                System.err.println("L'association a déjà un président");
            }
        }
    }

    public void addListeClassifications(Classification classification){
        this.listeClassifications.add(classification);
    }

    public void envoyerListArbresNomines(int annee){
        Classification classification = new Classification(this, annee);
        this.addListeClassifications(classification);
        municipalite.recevoirListArbresNomines(classification.getAnnee(), classification.getListArbresNomines());
    }

    @Override
    public void notifier(ActionArbre action, Arbre arbre) {
        //todo faire quelque chose
        System.out.println(action.toString() + arbre);
        switch (action){
            case ABATTAGE:
                break;
            case PLANTATION:
                break;
            case CLASSIFICATION:
                break;
        }
    }
}
/*(Service Mairie,ExerciceBudgetaire,Listes de classification (1 par année),liste des membres
        ,président,listes des ArbresVisité (et arbre prévu),liste rapport d'activité,liste des donateurs,montant du compte bancaire)*/