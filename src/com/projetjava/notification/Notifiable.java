package com.projetjava.notification;

import com.projetjava.ActionArbre;
import com.projetjava.Arbre;

/**
 * Objets qui vont être notifiés par un changement d'état d'un arbre
 */
public interface Notifiable {
    /**
     * Méthode appelée lorsqu'il est notifié
     * @param action
     */
    public void notifier(ActionArbre action, Arbre arbre);
}
