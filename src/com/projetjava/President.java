package com.projetjava;

import com.projetjava.exception.DateException;

/**
 * Président d'une association
 * @see Membre
 */
public class President extends Membre{

    public President(String nom, String prenom, Association assoc, int anneeNaissance, int moisNaissance, int jourNaissance, int anneeInscription, int moisInscription, int jourInscription, String adresseMembre) throws DateException {
        super(nom, prenom, assoc, anneeNaissance, moisNaissance, jourNaissance, anneeInscription, moisInscription, jourInscription, adresseMembre);
    }

}
