package com.projetjava.notification;

import com.projetjava.ActionArbre;
import com.projetjava.Arbre;

/**
 * Notifie d'un changement d'état d'un arbre
 */
public interface Notifieur {
    public void notifier(ActionArbre actionArbre, Arbre arbre);

    public void addNotifiable(Notifiable notifiable);

    public void removeNotifiable(Notifiable notifiable);
}
