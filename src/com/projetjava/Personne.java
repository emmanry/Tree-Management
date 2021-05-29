package com.projetjava;

import com.projetjava.don.Demandeur;
import com.projetjava.don.Don;
import com.projetjava.don.Donateur;
import com.projetjava.notification.Notifiable;

import java.util.ArrayList;
import java.util.List;

public class Personne implements Notifiable, Donateur {

    protected String nom;
    protected String prenom;
    private List<String> notifications = new ArrayList<>();

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
        notifications.add(action.toString() + arbre);
    }

    /**
     *
     * @return liste des notifications
     */
    public List<String> alertNotifications(){
        List<String> tmp = notifications;
        notifications = null;
        return tmp;
    }

    @Override
    public void receiveDemandeDon(String message, Demandeur demandeur, RapportActivite rapport) {
        //todo autre comportement ?
        demandeur.receiveDon(new Don(15.f,this));
    }

    public String toString(){
        return "\n" +
                "{ Personne.........."                    + "\n" +
                "[ Nom..............."   + this.nom       + " ] \n" +
                "[ Prénom............"   + this.prenom    + " ) ] \n}"
                ;
    }

}
