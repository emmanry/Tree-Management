package com.projetjava.don;

/**
 * Don pour une association
 */
public class Don {
    /**
     * Montant du don
     */
    private final double montant;
    /**
     * Donateur du don
     */
    private final Donateur donateur;

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
