package com.projetjava;

import com.projetjava.don.Don;
import com.projetjava.finance.Depense;
import com.projetjava.finance.Facture;

import java.util.ArrayList;
import java.util.List;

public class RapportActivite {
    private List<Don> doneurs;

    private List<Depense> depenses;

    private final Association association;

    public RapportActivite(Association association){
        doneurs = new ArrayList<>();
        depenses = new ArrayList<>();
        this.association = association;
    }

    public void addDon(Don don){
        doneurs.add(don);
    }

    public void addDepense(Depense depense){
        depenses.add(depense);
    }

    public Association getAssociation() {
        return association;
    }

    //todo les tostring
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Rapport Activité : \n");
        stringBuilder.append(association.getNom());
        stringBuilder.append("\n");
        stringBuilder.append("Dépenses : \n");
        for (Depense depense:depenses) {
            stringBuilder.append(depense);
        }
        stringBuilder.append("\nRecette : \n");
        for (Don don:doneurs) {
            stringBuilder.append(don);
        }
        stringBuilder.append(String.format("Cotisations : %f €",
                association.getListeMembres().size() * association.getPrixCotisation()));

        return stringBuilder.toString();
    }
}
