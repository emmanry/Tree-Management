package com.projetjava.don;

import com.projetjava.RapportActivite;

/**
 * Donateur d'une association
 */
public interface Donateur {

    /**
     * Recevoir une demande de don
     * @param message
     * @param demandeur
     * @param rapport
     */
    void receiveDemandeDon(String message, Demandeur demandeur,RapportActivite rapport);

    /**
     * Un donateur doit avoir un nom
     * @return le nom du donateur
     */
    String getNom();
}
