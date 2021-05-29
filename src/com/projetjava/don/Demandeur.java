package com.projetjava.don;

import com.projetjava.RapportActivite;

import java.util.ArrayList;

public interface Demandeur {

    void demandeDon(String message);

    void addDonateur(Donateur donateur);

    void removeDonateur(Donateur donateur);

    ArrayList<Donateur> getDonateur();

    void receiveDon(Don don);

    RapportActivite getLastRapportActivite();

    void setLastRapportActivite(RapportActivite lastRapportActivite);
}
