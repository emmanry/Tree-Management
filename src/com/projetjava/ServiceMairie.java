package com.projetjava;

import com.projetjava.notification.Notifiable;
import com.projetjava.notification.Notifieur;

import java.util.ArrayList;

public class ServiceMairie implements Notifieur {

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
}