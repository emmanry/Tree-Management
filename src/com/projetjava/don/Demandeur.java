package com.projetjava.don;

import com.projetjava.RapportActivite;

public interface Demandeur {

    void demandeDon(String message);

    void addDonateur(Donateur donateur);

    void removeDonateur(Donateur donateur);

    void receiveDon(Don don);

    RapportActivite getLastRapportActivite();

    void setLastRapportActivite(RapportActivite lastRapportActivite);
}
