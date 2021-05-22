package com.projetjava;

import com.projetjava.notification.Notifiable;

import java.util.ArrayList;

public class Association implements Notifiable {

    private ServiceMairie serviceMairie;
    private ExerciceBudgetaire exerciceBudgetaire;
    private ArrayList<Membre> listeMembres = new ArrayList<>();

    public Association(){

    }

    public ArrayList<Membre> getListeMembres(){
        return listeMembres;
    }

    public void setListeMembres(Membre membre){
        this.listeMembres.add(membre);
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