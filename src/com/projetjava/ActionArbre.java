package com.projetjava;

/**
 * Différente action possible sur un arbre
 */
public enum ActionArbre {
    PLANTATION(0),ABATTAGE(1),CLASSIFICATION(2);
    private final int id;

    ActionArbre(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

}
