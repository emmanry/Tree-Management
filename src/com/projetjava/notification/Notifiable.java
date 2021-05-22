package com.projetjava.notification;

import com.projetjava.ActionArbre;
import com.projetjava.Arbre;

/**
 * Objet qui vont être notifier par un changement d'état d'un arbre
 */
public interface Notifiable {
    /**
     * Méthode appelé lorsqu'il est notifier
     * @param action
     */
    public void notifier(ActionArbre action, Arbre arbre);
}
