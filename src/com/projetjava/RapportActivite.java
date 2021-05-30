package com.projetjava;

import com.projetjava.don.Don;
import com.projetjava.finance.Depense;

import java.util.ArrayList;
import java.util.List;

/**
 * Rapport d'activité d'un exercice budgétaire
 */
public class RapportActivite {

    private final List<Don> listeDons;
    private final List<Depense> listeDepenses;
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

    /**
     * Méthode toString
     * @return String
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("{ Rapport Activité : \n");
        stringBuilder.append("[Association : " + association.getNom() + "]\n");
        stringBuilder.append("[Dépenses : \n");
        for (Depense depense : listeDepenses) {
            stringBuilder.append(depense);
        }
        stringBuilder.append("]\n");
        stringBuilder.append("[Recettes dons : \n");
        for (Don don : listeDons) {
            stringBuilder.append(don.toString());
        }
        stringBuilder.append("]\n");
        stringBuilder.append(String.format("[Recettes cotisations : %f €\n",
                association.getListeMembres().size() * association.getPrixCotisation()));
        stringBuilder.append("]\n");
        stringBuilder.append(String.format("[Solde compte : %f €",association.getSolde()) + "]\n");
        return stringBuilder.toString();
    }
}
