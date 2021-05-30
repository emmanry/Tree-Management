package com.projetjava.finance;

/**
 * Dépense d'une association
 */
public class Depense {
    protected double montant;

    public Depense(double montant) {
        this.montant = montant;
    }

    public double getMontant() {
        return montant;
    }

}
