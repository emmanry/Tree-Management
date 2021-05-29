package com.projetjava.don;

public class Don {

    private double montant;
    private Donateur donateur;

    public Don(double montant,Donateur donateur){
        this.montant = montant;
        this.donateur = donateur;
    }

    public double getMontant() {
        return montant;
    }

    public Donateur getDonateur() {
        return donateur;
    }

    public String toString(){
        return "{ Don :"                             + " \n" +
                "[ Donateur........." + this.donateur+ " ]\n" +
                "[ Montant du don..." + this.montant + " ]\n}"
                ;
    }

}
