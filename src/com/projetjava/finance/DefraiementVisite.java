package com.projetjava.finance;

import com.projetjava.Arbre;
import com.projetjava.ArbreVisite;

public class DefraiementVisite extends Depense {

    private Arbre arbre;

    //todo ajouter la visite
    public DefraiementVisite(double montant, Arbre arbre) {
        super(montant);
        this.arbre = arbre;
    }

    @Override
    public String toString() {
        return "{ DefraiementVisite  "                         + " \n" +
                "[ Montant........." + this.montant            + " ]\n" +
                "[ Arbre..........." + this.arbre.getIdArbre() + " ]\n}"
                ;
    }

}
