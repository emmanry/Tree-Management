package com.projetjava;

import com.projetjava.don.Don;
import com.projetjava.finance.Depense;

import java.util.ArrayList;
import java.util.List;

/**
 * Exercice Budgétaire d'une application
 *
 */
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
        for(Membre membre : association.getListeMembres()){
            if(!membre.hasCotiser(annee)){
                membres.add(membre);
            }
        }

        //déinscription des membres
        for(Membre membre : membres){
            association.removeMembre(membre);
        }

        // Constitution et transmission de la liste proposée pour la classification en arbres remarquables
        this.association.envoyerListeArbresNomines(this.annee);

        // Rapport d'activité
        RapportActivite last = this.rapportActivite;
        association.setLastRapportActivite(this.rapportActivite);
        // On crée un nouveau rapport d'activité pour l'année suivante
        this.rapportActivite = new RapportActivite(association);


        // Envoi des demandes subventions/don et réception des sommes
        association.demandeDon(messageDemandeDon);


        annee++;
        return last;
    }
}
