package com.projetjava;

import com.projetjava.don.Don;
import com.projetjava.finance.Depense;
import com.projetjava.finance.Facture;

import java.util.ArrayList;
import java.util.List;

public class RapportActivite {
    private List<Don> listeDons;

    private List<Depense> listeDepenses;

    private final Association association;

    public RapportActivite(Association association){
        listeDons = new ArrayList<>();
        listeDepenses = new ArrayList<>();
        this.association = association;
    }

    public void addDon(Don don){
        listeDons.add(don);
    }

    public void addDepense(Depense depense){
        listeDepenses.add(depense);
    }

    public Association getAssociation() {
        return association;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("{ Rapport Activité : \n");
        stringBuilder.append("[Association : " + association.getNom() + "\n");
        stringBuilder.append("[Dépenses : \n");
        for (Depense depense : listeDepenses) {
            if(depense == listeDepenses.get(listeDepenses.size() - 1)){
                stringBuilder.append(depense.getMontant());
            }
            stringBuilder.append(depense.getMontant() + " ; ");
        }
        stringBuilder.append("\n[Recettes : \n");
        for (Don don : listeDons) {
            stringBuilder.append(don.toString());
        }
        stringBuilder.append(String.format("Cotisations : %f €",
                association.getListeMembres().size() * association.getPrixCotisation()));

        return stringBuilder.toString();
    }
}
