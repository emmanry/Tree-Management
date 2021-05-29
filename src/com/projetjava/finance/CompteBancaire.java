package com.projetjava.finance;

public class CompteBancaire {

    private double solde;

    public CompteBancaire(double soldeInitial){
        solde = soldeInitial;
    }

    public void depot(double valeur){
        solde += valeur;
    }

    public boolean retirer(double valeur)  {
        if(solde - valeur >= 0){
            solde -= valeur;
            return true;
        }else{
            return false;
        }
    }

    public double getSolde() {
        return solde;
    }
}
