package com.projetjava.don;

import com.projetjava.Association;
import com.projetjava.RapportActivite;

public interface Donateur {

    void receiveDemandeDon(String message, Demandeur demandeur, double montant,RapportActivite rapport);

    String getNom();
}
