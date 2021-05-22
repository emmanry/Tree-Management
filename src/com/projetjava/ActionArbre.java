package com.projetjava;

public enum ActionArbre {
    PLANTATION(0),ABATTAGE(1),CLASSIFICATION(2);
    int id;

    ActionArbre(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

}
