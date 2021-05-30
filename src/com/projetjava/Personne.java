package com.projetjava;

import com.projetjava.don.Demandeur;
import com.projetjava.don.Don;
import com.projetjava.don.Donateur;
import com.projetjava.notification.Notifiable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Personne
 * @see com.projetjava.notification.Notifiable
 * @see com.projetjava.don.Donateur
 */
public class Personne implements Notifiable, Donateur {

    private String nom;
    private String prenom;
    private MyDate dateDeNaissance;
    private List<String> notifications = new ArrayList<>();

    public Personne(String lastname, String firstname, int anneeNaissance, int moisNaissance, int jourNaissance){
        this.nom = lastname;
        this.prenom = firstname;
        this.dateDeNaissance = new MyDate(anneeNaissance, moisNaissance, jourNaissance);
    }

    public String getNom(){
        return this.nom;
    }

    public String getPrenom(){
        return this.prenom;
    }

    public MyDate getDateDeNaissance(){
        return this.dateDeNaissance;
    }

    /**
     * Une personne s'inscrit à une Association et en devient Membre
     * @param association
     * @param anneeInscription
     * @param moisInscription
     * @param jourInscription
     * @param adresseMembre
     */
    public void inscription(Association association, int anneeInscription, int moisInscription, int jourInscription, String adresseMembre){
        Membre membre = new Membre(this.nom, this.prenom , association, this.dateDeNaissance.getDate().get(Calendar.YEAR), this.dateDeNaissance.getDate().get(Calendar.MONTH), this.dateDeNaissance.getDate().get(Calendar.DAY_OF_MONTH), anneeInscription, moisInscription, jourInscription, adresseMembre);
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
    public void receiveDemandeDon(String message, Demandeur demandeur,RapportActivite rapport) {
        demandeur.receiveDon(new Don(15,this));
    }

    /**
     * Méthode toString
     * @return String
     */
    public String toString(){
        return "\n" +
                "{ Personne.........."                    + "\n" +
                "[ Nom..............."   + this.nom       + " ] \n" +
                "[ Prénom............"   + this.prenom    + " ) ] \n}"
                ;
    }

}
