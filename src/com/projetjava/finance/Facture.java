package com.projetjava.finance;

import com.projetjava.finance.Depense;

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
}
