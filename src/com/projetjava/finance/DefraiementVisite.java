package com.projetjava.finance;

import com.projetjava.Arbre;

/**
 * Defraiement d'une visite
 */
public class DefraiementVisite extends Depense {
    /**
     * Arbre de la visite
     */
    private final int idArbre;
    /**
     * Nom du membre qui a visité
     */
    private final String nomMembre;

    /**
     * Create DefraiementVisite
     * @param nomMembre
     * @param idArbre
     */
    public DefraiementVisite(double montant,String nomMembre, int idArbre) {
        super(montant);
        this.idArbre = idArbre;
        this.nomMembre = nomMembre;
    }

    @Override
    public String toString() {
        return "{ DefraiementVisite  "                         + " \n" +
                "[ Montant........." + this.montant            + " ]\n" +
                "[ Nom membre......" + this.nomMembre + " ]\n" +
                "[ Arbre..........." + this.idArbre + " ]\n}"
                ;
    }

}
