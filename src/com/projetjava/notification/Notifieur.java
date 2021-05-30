package com.projetjava.notification;

import com.projetjava.ActionArbre;
import com.projetjava.Arbre;

/**
 * Notifie d'un changement d'état d'un arbre
 */
public interface Notifieur {
    /**
     * Notifie un changement d'état
     * @param actionArbre
     * @param arbre
     */
    void notifier(ActionArbre actionArbre, Arbre arbre);

    /**
     * Ajoute un objet qui veut être notifier d'un changement
     * @param notifiable
     */
    void addNotifiable(Notifiable notifiable);

    /**
     * Retire un notifiable
     * @param notifiable
     */
    void removeNotifiable(Notifiable notifiable);
}
