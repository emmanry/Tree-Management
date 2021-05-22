package com.projetjava;

import java.util.ArrayList;

public class Association {

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


}
/*(Service Mairie,ExerciceBudgetaire,Listes de classification (1 par année),liste des membres
        ,président,listes des ArbresVisité (et arbre prévu),liste rapport d'activité,liste des donateurs,montant du compte bancaire)*/