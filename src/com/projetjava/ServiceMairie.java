package com.projetjava;

import com.projetjava.don.Demandeur;
import com.projetjava.don.Don;
import com.projetjava.don.Donateur;
import com.projetjava.notification.Notifiable;
import com.projetjava.notification.Notifieur;

import java.util.ArrayList;

public class ServiceMairie implements Notifieur, Donateur {

    private String nom;

    private ArrayList<Notifiable> listEntite;

    public ServiceMairie(String name){
        this.nom = name;
        listEntite = new ArrayList<>();
    }

    public String getNom(){
        return this.nom;
    }

    public void notifier(ActionArbre actionArbre, Arbre arbre){
        for (Notifiable notifiable: listEntite) {
            notifiable.notifier(actionArbre,arbre);
        }
    }

    public void addNotifier(Notifiable notifiable){
        listEntite.add(notifiable);
    }

    public void removeNotifier(Notifiable notifiable){
        listEntite.remove(notifiable);
    }

    @Override
    public void receiveDemandeDon(String message, Demandeur demandeur, RapportActivite rapport) {
        //todo autre comportement ?
        demandeur.receiveDon(new Don(500.f,this));
    }

    public ArrayList<Association> getAssociation(){
        ArrayList<Association> associations = new ArrayList<>();

        for (Notifiable entity:listEntite) {
            if(entity instanceof Association){
                associations.add((Association) entity);
            }
        }
        return associations;
    }
}