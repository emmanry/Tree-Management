package com.projetjava.finance;

public class Facture extends Depense {

    private String creancier;
    private String description;
    
    public Facture(String creancier, String description, double montant) {
        super(montant);
        this.creancier = creancier;
        this.description = description;
    }

    public String getCreancier() {
        return creancier;
    }

    public String getDescription() {
        return description;
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
