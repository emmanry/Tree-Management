package com.projetjava.don;

import com.projetjava.RapportActivite;

import java.util.ArrayList;

/**
 * Demandeur de don
 */
public interface Demandeur {

    /**
     * Demande un don à tout les donateurs inscrit
     * @param message
     */
    void demandeDon(String message);

    /**
     * Ajoute un potentiel donateur
     * @param donateur
     */
    void addDonateur(Donateur donateur);

    /**
     * Enlève un donateur
     * @param donateur
     */
    void removeDonateur(Donateur donateur);

    /**
     * Retourne les donateurs
     * @return tous les donateurs
     */
    ArrayList<Donateur> getDonateur();

    /**
     * Lorsqu'il reçoit un don
     * @param don
     */
    void receiveDon(Don don);

    /**
     *
     * @return le dernier rapport d'activité
     */
    RapportActivite getLastRapportActivite();

    /**
     * Set le dernier rapport d'activité
     * @param lastRapportActivite
     */
    void setLastRapportActivite(RapportActivite lastRapportActivite);
}
