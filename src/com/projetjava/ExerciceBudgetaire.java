package com.projetjava;

import com.projetjava.don.Don;
import com.projetjava.finance.Depense;

import java.util.ArrayList;
import java.util.List;

public class ExerciceBudgetaire {

    /**
     * Donnateur et le montant donné lors d'un exercice budgétaire
     */
    private RapportActivite rapportActivite;

    private Association association;

    public ExerciceBudgetaire(Association association){

        rapportActivite = new RapportActivite(association);
        this.association = association;
    }

    public void addDon(Don don){
        rapportActivite.addDon(don);
    }

    public void addDepense(Depense depense){
        rapportActivite.addDepense(depense);
    }

    public RapportActivite getRapportActivite() {
        return rapportActivite;
    }

    public void fin(String messageDemandeDon){
        //Révocation des membres
        //membre à supprimer
        List<Membre> membres = new ArrayList<>();
        for(Membre membre:association.getListeMembres()){
            if(!membre.hasCotiser()){
                membres.add(membre);
            }
        }
        //déinscription des membres
        for(Membre membre:membres){
            association.deinscription(membre);
        }

        //todo classification

        //Rapport d'activité déjà calculé
        association.setLastRapportActivite(rapportActivite);

        //envoi des demandes subventions/don et réception des sommes
        association.demandeDon(messageDemandeDon);
    }
}
