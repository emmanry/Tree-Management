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

    private int annee;

    public ExerciceBudgetaire(Association association,int annee){
        this.annee = annee;
        rapportActivite = new RapportActivite(association);
        this.association = association;
    }

    public int getAnnee() {
        return annee;
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

    public RapportActivite fin(String messageDemandeDon){
        //Révocation des membres
        //membre à supprimer
        List<Membre> membres = new ArrayList<>();
        for(Membre membre:association.getListeMembres()){
            if(!membre.hasCotiser(annee)){
                membres.add(membre);
            }
        }

        //déinscription des membres
        for(Membre membre:membres){
            association.removeMembre(membre);
        }

        //todo classification


        //Rapport d'activité
        RapportActivite last = rapportActivite;
        association.setLastRapportActivite(rapportActivite);
        //on créer un nouveau rapport d'activité pour l'année
        rapportActivite = new RapportActivite(association);


        //envoi des demandes subventions/don et réception des sommes
        //l'argent récupéré sera mis dans le nouveau rapport d'activité
        association.demandeDon(messageDemandeDon);

        annee++;
        return last;
    }
}
