package com.projetjava;

import com.projetjava.notification.Notifiable;

import java.util.ArrayList;
import java.util.List;

public class Association implements Notifiable {

    private ServiceMairie serviceMairie;
    private ExerciceBudgetaire exerciceBudgetaire;
    private ArrayList<Membre> listeMembres = new ArrayList<>();
    private List<String> notifications = new ArrayList<>();

    public Association(){

    }

    public ArrayList<Membre> getListeMembres(){
        return listeMembres;
    }

    public void setListeMembres(Membre membre){
        this.listeMembres.add(membre);
    }


    @Override
    public void notifier(ActionArbre action, Arbre arbre) {
        notifications.add(action.toString() + arbre);
        //todo retirer des listes de classification
        switch (action){
            case ABATTAGE:
                break;
            case CLASSIFICATION:
                break;
        }
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
}
/*(Service Mairie,ExerciceBudgetaire,Listes de classification (1 par année),liste des membres
        ,président,listes des ArbresVisité (et arbre prévu),liste rapport d'activité,liste des donateurs,montant du compte bancaire)*/