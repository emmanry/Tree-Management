package com.projetjava;

import com.projetjava.notification.Notifiable;

public class Personne implements Notifiable {

    private String nom;
    private String prenom;

    public Personne(String lastname, String firstname){
        this.nom = lastname;
        this.prenom = firstname;
    }

    public String getNom(){
        return this.nom;
    }

    public String getPrenom(){
        return this.prenom;
    }

    @Override
    public void notifier(ActionArbre action, Arbre arbre) {
        //todo faire quelque chose
        System.out.println(action.toString() + arbre);
    }
}
