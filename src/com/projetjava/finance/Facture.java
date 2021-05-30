package com.projetjava.finance;

/**
 * Facture
 */
public class Facture extends Depense {

    private final String creancier;
    private final String description;
    
    public Facture(String creancier, String description, double montant) {
        super(montant);
        this.creancier = creancier;
        this.description = description;
    }

    @Override
    public String toString() {
        return "{ Facture   \n" +
                "[ Montant............." + this.montant     + " ]\n" +
                "[ Créancier..........." + this.creancier   + " ]\n" +
                "[ Description........." + this.description + " ]\n}"
                ;
    }
}
