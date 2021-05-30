package com.projetjava;

import com.projetjava.don.Demandeur;
import com.projetjava.don.Don;
import com.projetjava.don.Donateur;
import com.projetjava.notification.Notifiable;
import com.projetjava.notification.Notifieur;

import java.util.ArrayList;

/**
 * Service d'une Mairie
 */
public class ServiceMairie implements Notifieur, Donateur {

    private final String nom;

    /**
     * Liste de tout ceux qui veulent être notifier d'une modification d'état d'un arbre
     */
    private final ArrayList<Notifiable> listeEntite;

    public ServiceMairie(String name){
        this.nom = name;
        listeEntite = new ArrayList<>();
    }

    public String getNom(){
        return this.nom;
    }

    public void notifier(ActionArbre actionArbre, Arbre arbre){
        for (Notifiable notifiable: listeEntite) {
            notifiable.notifier(actionArbre,arbre);
        }
    }

    public void addNotifiable(Notifiable notifiable){
        listeEntite.add(notifiable);
    }

    public void removeNotifiable(Notifiable notifiable){
        listeEntite.remove(notifiable);
    }

    @Override
    public void receiveDemandeDon(String message, Demandeur demandeur,RapportActivite rapport) {
        demandeur.receiveDon(new Don(500,this));
    }

    public ArrayList<Association> getAssociation(){
        ArrayList<Association> associations = new ArrayList<>();

        for (Notifiable entity:listeEntite) {
            if(entity instanceof Association){
                associations.add((Association) entity);
            }
        }
        return associations;
    }
}